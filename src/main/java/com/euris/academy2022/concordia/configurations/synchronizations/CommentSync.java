package com.euris.academy2022.concordia.configurations.synchronizations;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.*;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPutRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPostFromTrello;
import com.euris.academy2022.concordia.dataPersistences.models.Comment;
import com.euris.academy2022.concordia.utils.JsonUtil;
import com.euris.academy2022.concordia.utils.TimeUtil;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.function.EntityResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.euris.academy2022.concordia.utils.constants.TrelloConstant.ID;

public class CommentSync {

    /*
     * Insert from trello: OK
     * Update from trello: OK
     * Delete from DB if it has been deleted from Trello TODO
     **/
    public static void fetchAndPull(TrelloCommentService trelloCommentService, CommentService commentService, MemberService memberService) {
        try {
            ResponseDto<List<TrelloCommentDto>> allTrelloComments = trelloCommentService.getAllComments();
            ResponseDto<List<CommentDto>> allConcordiaComments = commentService.getAll();
            forEachTrelloCommentFetchAndPull(allTrelloComments, allConcordiaComments, trelloCommentService, commentService ,memberService);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            printCatchLog("fetchTrello   ", "*******CommentScheduling", "INTERRUPTED MANUALLY");
        }
    }

    private static void forEachTrelloCommentFetchAndPull(ResponseDto<List<TrelloCommentDto>> allTrelloComments, ResponseDto<List<CommentDto>> allConcordiaComments, TrelloCommentService trelloCommentService, CommentService commentService, MemberService memberService) {
        for (TrelloCommentDto trelloComment : allTrelloComments.getBody()) {
            ResponseDto<CommentDto> concordiaCommentFound = commentService.getByIdTrelloComment(trelloComment.getId());
            ifConcordiaCommentFoundThenAddElseUpdate(concordiaCommentFound, memberService, commentService, trelloComment);
        }
    }

    private static void ifConcordiaCommentFoundThenAddElseUpdate(ResponseDto<CommentDto> concordiaCommentFound, MemberService memberService, CommentService commentService, TrelloCommentDto trelloComment) {
        if (Optional.ofNullable(concordiaCommentFound.getBody()).isEmpty()) {
            // IF IS NULL INSERT
            ResponseDto<MemberDto> memberFound = memberService.getByIdTrelloMember(trelloComment.getIdMemberCreator());
            ifMemberFoundThenAddElseReturnNotFound(memberFound, trelloComment, commentService);
        } else {
            //IF IS FOUND UPDATE
            idTrelloDateIsAfterThenUpdateElseReturnOk(trelloComment, concordiaCommentFound, commentService);
        }
    }

    private static void ifMemberFoundThenAddElseReturnNotFound(ResponseDto<MemberDto> memberFound, TrelloCommentDto trelloComment, CommentService commentService) {
        if (Optional.ofNullable(memberFound.getBody()).isPresent()) {
            addAndCheckIfSucceeded(memberFound, trelloComment, commentService);
        } else {
            printLog1Label2Response("pullComment   ", trelloComment.getIdMemberCreator(), HttpResponseType.NOT_FOUND.getLabel(), HttpResponseType.FAILED.getLabel());
        }
    }

    private static void addAndCheckIfSucceeded(ResponseDto<MemberDto> memberFound, TrelloCommentDto trelloComment, CommentService commentService) {
        CommentPostFromTrello commentNew = CommentPostFromTrello.builder()
                .idTrelloComment(trelloComment.getId())
                .idTask(trelloComment.getIdCard())
                .uuidMember(memberFound.getBody().getUuid())
                .text(trelloComment.getText())
                .dateCreation(LocalDateTime.now())
                .dateUpdate(TimeUtil.parseToLocalDateTime(trelloComment.getDateLastEdited()))
                .build();

        ResponseDto<CommentDto> commentCreated = commentService.insertFromTrello(commentNew.toModel());
        ifSucceededPrintCreatedElseNotCreated(commentCreated, trelloComment);
    }

    private static void ifSucceededPrintCreatedElseNotCreated(ResponseDto<CommentDto> commentCreated, TrelloCommentDto trelloComment) {
        if (commentCreated.getHttpResponse().getLabel().equals(HttpResponseType.CREATED.getLabel())) {
            printLog1Label2Response("pullComment   ", trelloComment.getId(), HttpResponseType.NOT_FOUND.getLabel(), HttpResponseType.CREATED.getLabel());
        } else {
            printLog1Label2Response("pullComment   ", trelloComment.getId(), HttpResponseType.NOT_FOUND.getLabel(), HttpResponseType.NOT_CREATED.getLabel());
        }
    }

    private static void idTrelloDateIsAfterThenUpdateElseReturnOk(TrelloCommentDto trelloComment, ResponseDto<CommentDto> concordiaCommentFound, CommentService commentService) {
        if (TimeUtil.parseToLocalDateTime(trelloComment.getDateLastEdited()).isAfter(concordiaCommentFound.getBody().getDateUpdate().truncatedTo(ChronoUnit.SECONDS))) {
            updateAndCheckIfSucceeded(trelloComment, concordiaCommentFound, commentService);
        } else {
            printLog1Label2Response("pullComment   ", trelloComment.getId(), HttpResponseType.FOUND.getLabel(), HttpResponseType.OK.getLabel());
        }
    }

    private static void updateAndCheckIfSucceeded(TrelloCommentDto trelloComment, ResponseDto<CommentDto> concordiaCommentFound, CommentService commentService) {
        CommentFromTrelloDto commentOld = CommentFromTrelloDto.builder()
                .uuid(concordiaCommentFound.getBody().getUuid())
                .idTrelloComment(trelloComment.getId())
                .text(trelloComment.getText())
                .dateUpdate(TimeUtil.parseToLocalDateTime(trelloComment.getDateLastEdited()))
                .build();

        ResponseDto<CommentFromTrelloDto> commentUpdated = commentService.updateFromTrello(commentOld);
        ifSucceededPrintUpdatedElseNotUpdated(commentUpdated, trelloComment);
    }

    private static void ifSucceededPrintUpdatedElseNotUpdated(ResponseDto<CommentFromTrelloDto> commentUpdated, TrelloCommentDto trelloComment) {
        if (commentUpdated.getHttpResponse().getLabel().equals(HttpResponseType.UPDATED.getLabel())) {
            printLog1Label2Response("pullComment   ", trelloComment.getId(), HttpResponseType.FOUND.getLabel(), HttpResponseType.UPDATED.getLabel());
        } else {
            printLog1Label2Response("pullComment   ", trelloComment.getId(), HttpResponseType.FOUND.getLabel(), HttpResponseType.NOT_UPDATED.getLabel());
        }
    }

    /*
     * Insert from localhost to trello: OK
     * Update from localhost to trello: OK
     * Delete from localhost to Trello if it has been deleted from Trello TODO
     **/
    public static void fetchAndPush(TrelloCommentService trelloCommentService, CommentService commentService, TaskService taskService) {
        try {
            ResponseDto<List<CommentDto>> allConcordiaComments = commentService.getAll();
            ResponseDto<List<TrelloCommentDto>> allTrelloComments = trelloCommentService.getAllComments();
            forEachCommentFetchAndPush(allConcordiaComments, allTrelloComments, trelloCommentService, commentService, taskService);
            //checkIfDeletedCommentsAreSynchronized(trelloCommentService, commentService);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.printf("%s  [fetchConcordia] executed at %s  WARN : *******CommentScheduling : INTERRUPTED MANUALLY\n",
                    Thread.currentThread().getName(),
                    new Date());
        }
    }

    private static void forEachCommentFetchAndPush(ResponseDto<List<CommentDto>> allConcordiaComments, ResponseDto<List<TrelloCommentDto>> allTrelloComments, TrelloCommentService trelloCommentService, CommentService commentService, TaskService taskService) {
        for (CommentDto concordiaComment : allConcordiaComments.getBody()) {
            ifIdTrelloNotFoundThenAddElseUpdate(allTrelloComments, concordiaComment, trelloCommentService, commentService, taskService);
        }
    }

    private static void ifIdTrelloNotFoundThenAddElseUpdate(ResponseDto<List<TrelloCommentDto>> allTrelloComments, CommentDto concordiaComment, TrelloCommentService trelloCommentService, CommentService commentService, TaskService taskService) {
        if (Optional.ofNullable(concordiaComment.getIdTrelloComment()).isEmpty()) {
            ifNotFoundCheckIfTaskExists(concordiaComment, trelloCommentService, taskService, commentService);
        } else {
            // REMOVED COMMENT - TIME TO BOOM
           forEachTrelloCommentFindByIdThenCompareDatesIfSucceedThenUpdate(allTrelloComments, concordiaComment, trelloCommentService);
        }
    }

    private static void ifNotFoundCheckIfTaskExists(CommentDto concordiaComment, TrelloCommentService trelloCommentService, TaskService taskService, CommentService commentService) {
        Optional<String> concordiaIdTask = taskService.findIdByUuidComment(concordiaComment.getUuid());
        ifFoundThenAddCommentToTrelloElseReturnNotCreated(concordiaIdTask, trelloCommentService, concordiaComment, commentService);
    }

    private static void ifFoundThenAddCommentToTrelloElseReturnNotCreated(Optional<String> concordiaIdTask, TrelloCommentService trelloCommentService, CommentDto concordiaComment, CommentService commentService) {
        if (concordiaIdTask.isPresent()) {
            ResponseDto<ResponseEntity<String>> response = trelloCommentService.addCommentByIdCardAndText(concordiaIdTask.get(), concordiaComment.getText());

            if (Optional.ofNullable(response.getBody()).isPresent()) {
                String trelloCommentId = JsonUtil.getIdFromResponse(response.getBody().getBody());
                printLog1Label2Response("pushComment   ", trelloCommentId, HttpResponseType.FOUND.getLabel(), HttpResponseType.CREATED.getLabel());

                CommentFromTrelloDto commentOld = CommentFromTrelloDto.builder()
                        .uuid(concordiaComment.getUuid())
                        .idTrelloComment(trelloCommentId)
                        .text(concordiaComment.getText())
                        .build();

                System.out.println("\n\nTEST_UPDATE"+concordiaComment.getUuid()+trelloCommentId+concordiaComment.getText());
                ResponseDto<CommentFromTrelloDto> commentUpdatedWithTrelloCommentId = commentService.updateTrelloCommentIdMissing(commentOld);

                if (Optional.ofNullable(commentUpdatedWithTrelloCommentId.getBody()).isPresent()) {
                    printLog1Label2Response("pushComment   ", commentOld.getIdTrelloComment(), HttpResponseType.FOUND.getLabel(), HttpResponseType.UPDATED.getLabel());
                } else {
                    printLog1Label2Response("pushComment   ", commentOld.getIdTrelloComment(), HttpResponseType.FOUND.getLabel(), HttpResponseType.NOT_UPDATED.getLabel());
                }
            } else {
                printLog2Response("pushComment   ", HttpResponseType.NOT_FOUND.getLabel(), HttpResponseType.NOT_CREATED.getLabel());
            }
        }
    }

    private static void forEachTrelloCommentFindByIdThenCompareDatesIfSucceedThenUpdate(ResponseDto<List<TrelloCommentDto>> allTrelloComments, CommentDto concordiaComment, TrelloCommentService trelloCommentService) {
        for (TrelloCommentDto trelloComment : allTrelloComments.getBody()) {
            if (trelloComment.getId().equals(concordiaComment.getIdTrelloComment())) {
                ifTrelloDateIsBeforeConcordiaDateThenUpdateToTrello(trelloComment, concordiaComment, trelloCommentService);
            } else {
                printLog1Label1Response("pushComment   ", concordiaComment.getIdTrelloComment(), HttpResponseType.NOT_FOUND.getLabel());
            }
        }
    }

    private static void ifTrelloDateIsBeforeConcordiaDateThenUpdateToTrello(TrelloCommentDto trelloComment, CommentDto concordiaComment, TrelloCommentService trelloCommentService) {
        if (TimeUtil.parseToLocalDateTime(trelloComment.getDateLastEdited()).isBefore(concordiaComment.getDateUpdate())) {
            trelloCommentService.updateCommentByIdCardAndIdCommentAndText(
                    concordiaComment.getTaskDto().getId(),
                    concordiaComment.getIdTrelloComment(),
                    concordiaComment.getText());
            printLog1Label2Response("pushComment   ", concordiaComment.getIdTrelloComment(), HttpResponseType.FOUND.getLabel(), HttpResponseType.UPDATED.getLabel());
        } else {
            printLog1Label2Response("pushComment   ", concordiaComment.getIdTrelloComment(), HttpResponseType.FAILED.getLabel(), HttpResponseType.NOT_UPDATED.getLabel());
        }
    }

    private static void printCatchLog(String operation, String schedule, String error) {
        //e.printStackTrace();
        System.out.printf("%s  [" + operation + "] executed at %s  WARN : %s : %s\n",
                Thread.currentThread().getName(),
                new Date(),
                schedule,
                error);
    }
    private static void printLog2Response(String operation, String responseA, String responseB) {
        System.out.printf("%s  [" + operation +"] executed at %s  INFO : ********************TASK : %s → %s\n",
                Thread.currentThread().getName(),
                new Date(),
                responseA,
                responseB);
    }

    private static void printLog1Label1Response(String operation, String labelA, String responseA) {
        System.out.printf("%s  [" + operation + "] executed at %s  INFO : %s : %s\n",
                Thread.currentThread().getName(),
                new Date(),
                labelA,
                responseA);
    }

    private static void printLog1Label2Response(String operation, String labelA, String responseA, String responseB) {
        System.out.printf("%s  [" + operation + "] executed at %s  INFO : %s : %s → %s\n",
                Thread.currentThread().getName(),
                new Date(),
                labelA,
                responseA,
                responseB);
    }
}
