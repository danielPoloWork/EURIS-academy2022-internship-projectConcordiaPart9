package com.euris.academy2022.concordia.configurations.synchronizations;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.*;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPostFromTrello;
import com.euris.academy2022.concordia.utils.TimeUtil;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CommentSync {

    /*
     * Insert from trello: OK
     * Update from trello:  OK
     * Delete from DB if it has been deleted from Trello TODO
     **/
    public static void fetchAndPull(TrelloCommentService trelloCommentService, CommentService commentService, MemberService memberService) {
        try {
            ResponseDto<List<TrelloCommentDto>> allTrelloComments = trelloCommentService.getAllComments();
            forEachTrelloCommentFetchAndPull(allTrelloComments, trelloCommentService, commentService ,memberService);
//            ResponseDto<List<CommentDto>> allConcordiaCommentsWhereIdTrelloCommentIsNotNUll = commentService.getAllWhereIdTrelloTaskIsNotNull();
//
//            removeCommentsDeletedOnTrello(
//                    allTrelloComments,
//                    allConcordiaCommentsWhereIdTrelloCommentIsNotNUll,
//                    commentService);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            printCatchLog("fetchTrello   ", "*******CommentScheduling", "INTERRUPTED MANUALLY");
        }
    }

    private static void forEachTrelloCommentFetchAndPull(ResponseDto<List<TrelloCommentDto>> allTrelloComments, TrelloCommentService trelloCommentService, CommentService commentService, MemberService memberService) {
        for (TrelloCommentDto trelloComment : allTrelloComments.getBody()) {
            ResponseDto<CommentDto> concordiaCommentFound = commentService.getByIdTrelloComment(trelloComment.getId());
            ifConcordiaCommentFoundThenAddElseUpdate(concordiaCommentFound, memberService, commentService, trelloComment);
        }
    }

    private static void ifConcordiaCommentFoundThenAddElseUpdate(ResponseDto<CommentDto> concordiaCommentFound, MemberService memberService, CommentService commentService, TrelloCommentDto trelloComment) {
        if (Optional.ofNullable(concordiaCommentFound.getBody()).isEmpty()) {
            ResponseDto<MemberDto> memberFound = memberService.getByIdTrelloMember(trelloComment.getIdMemberCreator());
            ifMemberFoundThenAddElseReturnNotFound(memberFound, trelloComment, commentService);
        } else {
            idTrelloDateIsAfterThenUpdateElseReturnOk(trelloComment, concordiaCommentFound, commentService);
        }
    }

    private static void ifMemberFoundThenAddElseReturnNotFound(ResponseDto<MemberDto> memberFound, TrelloCommentDto trelloComment, CommentService commentService) {
        if (Optional.ofNullable(memberFound.getBody()).isPresent()) {
            addAndCheckIfSucceeded(memberFound, trelloComment, commentService);
        } else {
            printLog3Labels("pullComment   ", trelloComment.getIdMemberCreator(), HttpResponseType.NOT_FOUND.getLabel(), HttpResponseType.FAILED.getLabel());
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
            printLog3Labels("pullComment   ", trelloComment.getId(), HttpResponseType.NOT_FOUND.getLabel(), HttpResponseType.CREATED.getLabel());
        } else {
            printLog3Labels("pullComment   ", trelloComment.getId(), HttpResponseType.NOT_FOUND.getLabel(), HttpResponseType.NOT_CREATED.getLabel());
        }
    }

    private static void idTrelloDateIsAfterThenUpdateElseReturnOk(TrelloCommentDto trelloComment, ResponseDto<CommentDto> concordiaCommentFound, CommentService commentService) {
        if (TimeUtil.parseToLocalDateTime(trelloComment.getDateLastEdited()).isAfter(concordiaCommentFound.getBody().getDateUpdate().truncatedTo(ChronoUnit.SECONDS))) {
            updateAndCheckIfSucceeded(trelloComment, concordiaCommentFound, commentService);
        } else {
            printLog3Labels("pullComment   ", trelloComment.getId(), HttpResponseType.FOUND.getLabel(), HttpResponseType.OK.getLabel());
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
            printLog3Labels("pullComment   ", trelloComment.getId(), HttpResponseType.FOUND.getLabel(), HttpResponseType.UPDATED.getLabel());
        } else {
            printLog3Labels("pullComment   ", trelloComment.getId(), HttpResponseType.FOUND.getLabel(), HttpResponseType.NOT_UPDATED.getLabel());
        }
    }

//    private static void removeCommentsDeletedOnTrello(ResponseDto<List<TrelloCommentDto>> allTrelloComments, ResponseDto<List<CommentDto>> allConcordiaComments, CommentService commentService) {
//        ListUtil<String> listUtil = new ListUtil<>();
//
//        List<String> listTrelloId = allTrelloComments
//                .getBody()
//                .stream()
//                .map(f -> new StringIdDto().getId()).toList();
//        List<String> listConcordiaId = allConcordiaComments
//                .getBody()
//                .stream()
//                .map(f -> new StringIdDto().getId()).toList();
//
//        List<String> notMatchingComments = listUtil.findNotMatchingRecords(listConcordiaId, listTrelloId);
//
//        ifTrelloAndConcordiaAreNotSynchronizedThenCheckAndDelete(notMatchingComments, allConcordiaComments, commentService);
//
//    }
//
//    private static void ifTrelloAndConcordiaAreNotSynchronizedThenCheckAndDelete(List<String> notMatchingComments, ResponseDto<List<CommentDto>> allConcordiaComments, CommentService commentService) {
//        if (notMatchingComments.isEmpty()) {
//            System.out.printf("%s  [pullComment   ] executed at %s  INFO : NOTHING TO DELETE\n",
//                    Thread.currentThread().getName(),
//                    new Date());
//        } else {
//            forEachConcordiaCommentCheckIfTrelloCommentIdIsNotMatching(allConcordiaComments, notMatchingComments, commentService);
//        }
//    }
//
//    private static void forEachConcordiaCommentCheckIfTrelloCommentIdIsNotMatching(ResponseDto<List<CommentDto>> allConcordiaComments, List<String> notMatchingComments, CommentService commentService) {
//        for (CommentDto comment : allConcordiaComments.getBody()) {
//            forEachNotMatchingConcordiaCommentIdCheckAndDelete(comment, notMatchingComments, commentService);
//        }
//    }
//
//    private static void forEachNotMatchingConcordiaCommentIdCheckAndDelete(CommentDto comment, List<String> notMatchingComments, CommentService commentService) {
//        for (String id : notMatchingComments) {
//            ifConcordiaCommentEqualsNotMatchingCommentIdThenDelete(comment, id, commentService);
//        }
//    }
//
//    private static void ifConcordiaCommentEqualsNotMatchingCommentIdThenDelete(CommentDto comment, String id, CommentService commentService) {
//        if (comment.getIdTrelloComment().equals(id)) {
//            commentService.removeByUuid(comment.getUuid());
//            printLog3Labels("pullComment   ", comment.getIdTrelloComment(), HttpResponseType.FOUND.getLabel(), HttpResponseType.DELETED.getLabel());
//        }
//    }

    public static void fetchAndPush(TrelloCommentService trelloCommentService, CommentService commentService, TaskService taskService) {
        try {
            ResponseDto<List<CommentDto>> allConcordiaComments = commentService.getAll();
            forEachCommentFetchAndPush(allConcordiaComments, trelloCommentService, commentService, taskService);
            //checkIfDeletedCommentsAreSynchronized(trelloCommentService, commentService);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.printf("%s  [fetchConcordia] executed at %s  WARN : *******CommentScheduling : INTERRUPTED MANUALLY\n",
                    Thread.currentThread().getName(),
                    new Date());
        } catch (NullPointerException e) {
            System.out.printf("%s  [fetchConcordia] executed at %s  WARN : *******CommentScheduling : NULL POINTER\n",
                    Thread.currentThread().getName(),
                    new Date());
        }
    }

    private static void forEachCommentFetchAndPush(ResponseDto<List<CommentDto>> allConcordiaComments, TrelloCommentService trelloCommentService, CommentService commentService, TaskService taskService) {
        for (CommentDto concordiaComment : allConcordiaComments.getBody()) {
            System.out.println("DB_ID_TRELLO_COMMENT_"+concordiaComment.getIdTrelloComment());
            ifIdTrelloNotFoundThenAddElseUpdate(concordiaComment, trelloCommentService, taskService);
        }
    }

    private static void ifIdTrelloNotFoundThenAddElseUpdate(CommentDto concordiaComment, TrelloCommentService trelloCommentService, TaskService taskService) {
        if (Optional.ofNullable(concordiaComment.getIdTrelloComment()).isEmpty()) {
            ifNotFoundCheckIfTaskExists(concordiaComment, trelloCommentService, taskService);
        } else {
            //ResponseDto<TrelloCommentDto> commentFound = trelloCommentService.getCommentByIdComment(comment.getIdTrelloComment());
            //ifFoundUpdateToTrello(commentFound, comment, trelloCommentService, commentService);
            System.out.println("ELSE_UPDATE");
        }
    }

    private static void ifNotFoundCheckIfTaskExists(CommentDto concordiaComment, TrelloCommentService trelloCommentService, TaskService taskService) {
        Optional<String> concordiaIdTask = taskService.findIdByUuidComment(concordiaComment.getUuid());
        ifFoundThenAddCommentToTrelloElseReturnNotCreated(concordiaIdTask, trelloCommentService, concordiaComment);
    }

    private static void ifFoundThenAddCommentToTrelloElseReturnNotCreated(Optional<String> concordiaIdTask, TrelloCommentService trelloCommentService, CommentDto concordiaComment) {
        if (concordiaIdTask.isPresent()) {
            trelloCommentService.addCommentByIdCardAndText(concordiaIdTask.get(), concordiaComment.getText());
            printLog3Labels("pushComment   ", concordiaIdTask.get(), HttpResponseType.FOUND.getLabel(), HttpResponseType.CREATED.getLabel());
        } else {
            printLog2Labels("pushComment   ", HttpResponseType.NOT_FOUND.getLabel(), HttpResponseType.NOT_CREATED.getLabel());
        }
    }

    private static void ifFoundUpdateToTrello(ResponseDto<TrelloCommentDto> commentFound, CommentDto comment, TrelloCommentService trelloCommentService, CommentService commentService) {
        if (Optional.ofNullable(commentFound.getBody()).isPresent()) {
            ifNeededUpdateComment(commentFound, comment, trelloCommentService);
        } else {
            System.out.printf("%s  [pushComment   ] executed at %s  INFO : %s : %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    comment.getIdTrelloComment(),
                    HttpResponseType.NOT_FOUND.getLabel());
        }
    }

    private static void ifNeededUpdateComment(ResponseDto<TrelloCommentDto> commentFound, CommentDto comment, TrelloCommentService trelloCommentService) {
        if (TimeUtil.parseToLocalDateTime(commentFound.getBody().getDateLastEdited()).isBefore(comment.getDateUpdate())) {
            trelloCommentService.updateCommentByIdCardAndIdCommentAndText(
                    comment.getTaskDto().getId(),
                    comment.getIdTrelloComment(),
                    comment.getText());
            System.out.printf("%s  [pushComment   ] executed at %s  INFO : %s : %s → %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    comment.getIdTrelloComment(),
                    HttpResponseType.FOUND.getLabel(),
                    HttpResponseType.UPDATED.getLabel());
        } else {
            System.out.printf("%s  [pushComment   ] executed at %s  INFO : %s : %s → %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    comment.getIdTrelloComment(),
                    HttpResponseType.FAILED.getLabel(),
                    HttpResponseType.NOT_UPDATED.getLabel());
        }
    }

//    private static void checkIfDeletedCommentsAreSynchronized(TrelloCommentService trelloCommentService, CommentService commentService) {
//        ResponseDto<List<TrelloCommentDto>> allTrelloComments = trelloCommentService.getAllComments();
//        ResponseDto<List<CommentDto>> allConcordiaCommentsWhereIdTrelloCommentIsNotNUll = commentService.getAllWhereIdTrelloTaskIsNotNull();
//
//        removeCommentsDeletedOnConcordia(
//                allConcordiaCommentsWhereIdTrelloCommentIsNotNUll,
//                allTrelloComments,
//                trelloCommentService);
//    }
//
//    private static void removeCommentsDeletedOnConcordia(ResponseDto<List<CommentDto>> allConcordiaCommentsWhereIdTrelloCommentIsNotNUll, ResponseDto<List<TrelloCommentDto>> allTrelloComments, TrelloCommentService trelloCommentService) {
//        ListUtil<String> listUtil = new ListUtil<>();
//
//        List<String> listTrelloId = allTrelloComments
//                .getBody()
//                .stream()
//                .map(f -> new StringIdDto().getId()).toList();
//        List<String> listConcordiaId = allConcordiaCommentsWhereIdTrelloCommentIsNotNUll
//                .getBody()
//                .stream()
//                .map(f -> new StringIdDto().getId()).toList();
//
//        List<String> notMatchingComments = listUtil.findNotMatchingRecords(listTrelloId, listConcordiaId);
//
//        ifConcordiaAndTrelloAreNotSynchronizedThenCheckAndDelete(notMatchingComments, allTrelloComments, trelloCommentService);
//    }
//
//    private static void ifConcordiaAndTrelloAreNotSynchronizedThenCheckAndDelete(List<String> notMatchingComments, ResponseDto<List<TrelloCommentDto>> allTrelloComments, TrelloCommentService trelloCommentService) {
//        if (notMatchingComments.isEmpty()) {
//            System.out.printf("%s  [pullComment    ] executed at %s  INFO : NOTHING TO DELETE\n",
//                    Thread.currentThread().getName(),
//                    new Date());
//        } else {
//            forEachTrelloCommentCheckIfConcordiaCommentIdIsNotMatching(allTrelloComments, notMatchingComments, trelloCommentService);
//        }
//    }
//
//    private static void forEachTrelloCommentCheckIfConcordiaCommentIdIsNotMatching(ResponseDto<List<TrelloCommentDto>> allTrelloComments, List<String> notMatchingComments, TrelloCommentService trelloCommentService) {
//        for (TrelloCommentDto comment : allTrelloComments.getBody()) {
//            forEachNotMatchingTrelloCommentIdCheckAndDelete(comment, notMatchingComments, trelloCommentService);
//        }
//    }
//
//    private static void forEachNotMatchingTrelloCommentIdCheckAndDelete(TrelloCommentDto comment, List<String> notMatchingComments, TrelloCommentService trelloCommentService) {
//        for (String id : notMatchingComments) {
//            ifTrelloCommentEqualsNotMatchingCommentIdThenDelete(comment, id, trelloCommentService);
//        }
//    }
//
//    private static void ifTrelloCommentEqualsNotMatchingCommentIdThenDelete(TrelloCommentDto comment, String id, TrelloCommentService trelloCommentService) {
//        if (comment.getId().equals(id)) {
//            trelloCommentService.removeCommentByIdCardAndIdComment(comment.getIdCard(), comment.getId());
//            System.out.printf("%s  [pullComment    ] executed at %s  INFO : %s : %s → %s\n",
//                    Thread.currentThread().getName(),
//                    new Date(),
//                    comment.getId(),
//                    HttpResponseType.FOUND.getLabel(),
//                    HttpResponseType.DELETED.getLabel());
//        }
//    }

    private static void printCatchLog(String operation, String schedule, String error) {
        //e.printStackTrace();
        System.out.printf("%s  [" + operation + "] executed at %s  WARN : %s : %s\n",
                Thread.currentThread().getName(),
                new Date(),
                schedule,
                error);
    }
    private static void printLog2Labels(String operation, String labelA, String labelB) {
        System.out.printf("%s  [" + operation +"] executed at %s  INFO : ********************TASK : %s → %s\n",
                Thread.currentThread().getName(),
                new Date(),
                labelA,
                labelB);
    }
    private static void printLog3Labels(String operation, String labelA, String labelB, String labelC) {
        System.out.printf("%s  [" + operation + "] executed at %s  INFO : %s : %s → %s\n",
                Thread.currentThread().getName(),
                new Date(),
                labelA,
                labelB,
                labelC);
    }
}
