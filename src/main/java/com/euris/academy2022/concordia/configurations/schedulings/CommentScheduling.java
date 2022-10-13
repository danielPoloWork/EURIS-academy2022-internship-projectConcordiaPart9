package com.euris.academy2022.concordia.configurations.schedulings;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.TaskService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.*;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPutRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPostFromTrello;
import com.euris.academy2022.concordia.utils.ListUtil;
import com.euris.academy2022.concordia.utils.TimeUtil;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CommentScheduling {

    // download tutti i comment da trello
    // controllo se ogni comment esiste in DB
    // se non esiste lo inserisco su local
    // se esiste:
    // contorllo se la data su trello è più recente
    // se è più recente faccio update su local
    // se la data è uguale è già aggiornato e quindi ritorno OK
    // controllo se il DB local ha un comment con trello id che non esiste su trello
    // se esiste in local va eliminato da local
    // se non esiste neanche in local ritorno OK
    public static void fetchAndPull(TrelloCommentService trelloCommentService, CommentService commentService, MemberService memberService) {
        try {
            ResponseDto<List<TrelloCommentDto>> allTrelloComments = trelloCommentService.getAllComments();

            for (TrelloCommentDto comment : allTrelloComments.getBody()) {
                ResponseDto<CommentDto> commentFound = commentService.getByIdTrelloComment(comment.getId());

                if (Optional.ofNullable(commentFound.getBody()).isEmpty()) {
                    ResponseDto<MemberDto> memberFound = memberService.getByIdTrelloMember(comment.getIdMemberCreator());

                    CommentPostFromTrello commentNew = CommentPostFromTrello.builder()
                            .idTrelloComment(comment.getId())
                            .idTask(comment.getIdCard())
                            .uuidMember(memberFound.getBody().getUuid())
                            .text(comment.getText())
                            .dateCreation(LocalDateTime.now())
                            .dateUpdate(TimeUtil.parseToLocalDateTime(comment.getDateLastEdited()))
                            .build();

                    ResponseDto<CommentDto> commentCreated = commentService.insertFromTrello(commentNew.toModel());

                    System.out.printf("%s  [pullComment   ] executed at %s  INFO : %s : %s → %s\n",
                            Thread.currentThread().getName(),
                            new Date(),
                            comment.getId(),
                            HttpResponseType.NOT_FOUND.getLabel(),
                            HttpResponseType.CREATED.getLabel());
                } else {
                    if (TimeUtil.parseToLocalDateTime(comment.getDateLastEdited()).isBefore(commentFound.getBody().getDateUpdate().truncatedTo(ChronoUnit.SECONDS))) {

                        System.out.println("IM_HERE_UPDATE");
                        CommentPutRequest commentOld = CommentPutRequest.builder()
                                .uuid(commentFound.getBody().getUuid())
                                .idTrelloComment(comment.getId())
                                .text(comment.getText())
                                .dateUpdate(TimeUtil.parseToLocalDateTime(comment.getDateLastEdited()))
                                .build();

                        ResponseDto<CommentDto> commentUpdated = commentService.updateFromTrello(commentOld.toModel());

                        System.out.printf("%s  [pullComment   ] executed at %s  INFO : %s : %s → %s\n",
                                Thread.currentThread().getName(),
                                new Date(),
                                comment.getId(),
                                HttpResponseType.FOUND.getLabel(),
                                HttpResponseType.UPDATED.getLabel());
                    } else {
                        System.out.printf("%s  [pullComment   ] executed at %s  INFO : %s : %s → %s\n",
                                Thread.currentThread().getName(),
                                new Date(),
                                comment.getId(),
                                HttpResponseType.FOUND.getLabel(),
                                HttpResponseType.OK.getLabel());
                    }
                }
            }

//            ResponseDto<List<CommentDto>> allConcordiaCommentsWhereIdTrelloCommentIsNotNUll = commentService.getAllWhereIdTrelloTaskIsNotNull();
//
//            removeCommentsDeletedOnTrello(
//                    allTrelloComments,
//                    allConcordiaCommentsWhereIdTrelloCommentIsNotNUll,
//                    commentService);

            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : *******CommentScheduling : INTERRUPTED MANUALLY\n",
                    Thread.currentThread().getName(),
                    new Date());
        } catch (NullPointerException e) {
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : *******CommentScheduling : NULL POINTER\n",
                    Thread.currentThread().getName(),
                    new Date());
        }
    }

    private static void removeCommentsDeletedOnTrello(ResponseDto<List<TrelloCommentDto>> allTrelloComments, ResponseDto<List<CommentDto>> allConcordiaComments, CommentService commentService) {
        ListUtil<String> listUtil = new ListUtil<>();

        List<String> listTrelloId = allTrelloComments
                .getBody()
                .stream()
                .map(f -> new StringIdDto().getId()).toList();
        List<String> listConcordiaId = allConcordiaComments
                .getBody()
                .stream()
                .map(f -> new StringIdDto().getId()).toList();

        List<String> notMatchingComments = listUtil.findNotMatchingRecords(listConcordiaId, listTrelloId);

        ifTrelloAndConcordiaAreNotSynchronizedThenCheckAndDelete(notMatchingComments, allConcordiaComments, commentService);

    }

    private static void ifTrelloAndConcordiaAreNotSynchronizedThenCheckAndDelete(List<String> notMatchingComments, ResponseDto<List<CommentDto>> allConcordiaComments, CommentService commentService) {
        if (notMatchingComments.isEmpty()) {
            System.out.printf("%s  [pullComment   ] executed at %s  INFO : NOTHING TO DELETE\n",
                    Thread.currentThread().getName(),
                    new Date());
        } else {
            forEachConcordiaCommentCheckIfTrelloCommentIdIsNotMatching(allConcordiaComments, notMatchingComments, commentService);
        }
    }

    private static void forEachConcordiaCommentCheckIfTrelloCommentIdIsNotMatching(ResponseDto<List<CommentDto>> allConcordiaComments, List<String> notMatchingComments, CommentService commentService) {
        for (CommentDto comment : allConcordiaComments.getBody()) {
            forEachNotMatchingConcordiaCommentIdCheckAndDelete(comment, notMatchingComments, commentService);
        }
    }

    private static void forEachNotMatchingConcordiaCommentIdCheckAndDelete(CommentDto comment, List<String> notMatchingComments, CommentService commentService) {
        for (String id : notMatchingComments) {
            ifConcordiaCommentEqualsNotMatchingCommentIdThenDelete(comment, id, commentService);
        }
    }

    private static void ifConcordiaCommentEqualsNotMatchingCommentIdThenDelete(CommentDto comment, String id, CommentService commentService) {
        if (comment.getIdTrelloComment().equals(id)) {
            commentService.removeByUuid(comment.getUuid());
            System.out.printf("%s  [pullComment    ] executed at %s  INFO : %s : %s → %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    comment.getIdTrelloComment(),
                    HttpResponseType.FOUND.getLabel(),
                    HttpResponseType.DELETED.getLabel());
        }
    }

    public static void fetchAndPush(TrelloCommentService trelloCommentService, CommentService commentService, TaskService taskService) {
        try {
            ResponseDto<List<CommentDto>> commentList = commentService.getAll();
            forEachCommentFetchAndPull(commentList, trelloCommentService, commentService, taskService);
    //        checkIfDeletedCommentsAreSynchronized(trelloCommentService, commentService);
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

    private static void forEachCommentFetchAndPull(ResponseDto<List<CommentDto>> commentList, TrelloCommentService trelloCommentService, CommentService commentService, TaskService taskService) {
        for (CommentDto comment : commentList.getBody()) {
            if (Optional.ofNullable(comment.getIdTrelloComment()).isEmpty()) {
                ifMissingAddComment(comment, trelloCommentService, taskService);
            } else {
                ResponseDto<TrelloCommentDto> commentFound = trelloCommentService.getCommentByIdComment(comment.getIdTrelloComment());
                ifFoundUpdateOrDeleteComment(commentFound, comment, trelloCommentService, commentService);
            }
        }
    }

    private static void ifMissingAddComment(CommentDto comment, TrelloCommentService trelloCommentService, TaskService taskService) {
        Optional<String> idCard = taskService.findIdByUuidComment(comment.getUuid());

        if (idCard.isPresent()) {
            trelloCommentService.addCommentByIdCardAndText(idCard.get(), comment.getText());
            System.out.printf("%s  [pushComment   ] executed at %s  INFO : %s : %s → %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    idCard.get(),
                    HttpResponseType.FOUND.getLabel(),
                    HttpResponseType.CREATED.getLabel());
        } else {
            System.out.printf("%s  [pushComment   ] executed at %s  INFO : ********************TASK : %s → %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    HttpResponseType.NOT_FOUND.getLabel(),
                    HttpResponseType.NOT_CREATED.getLabel());
        }
    }

    private static void ifFoundUpdateOrDeleteComment(ResponseDto<TrelloCommentDto> commentFound, CommentDto comment, TrelloCommentService trelloCommentService, CommentService commentService) {
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

    private static void checkIfDeletedCommentsAreSynchronized(TrelloCommentService trelloCommentService, CommentService commentService) {
        ResponseDto<List<TrelloCommentDto>> allTrelloComments = trelloCommentService.getAllComments();
        ResponseDto<List<CommentDto>> allConcordiaCommentsWhereIdTrelloCommentIsNotNUll = commentService.getAllWhereIdTrelloTaskIsNotNull();

        removeCommentsDeletedOnConcordia(
                allConcordiaCommentsWhereIdTrelloCommentIsNotNUll,
                allTrelloComments,
                trelloCommentService);
    }

    private static void removeCommentsDeletedOnConcordia(ResponseDto<List<CommentDto>> allConcordiaCommentsWhereIdTrelloCommentIsNotNUll, ResponseDto<List<TrelloCommentDto>> allTrelloComments, TrelloCommentService trelloCommentService) {
        ListUtil<String> listUtil = new ListUtil<>();

        List<String> listTrelloId = allTrelloComments
                .getBody()
                .stream()
                .map(f -> new StringIdDto().getId()).toList();
        List<String> listConcordiaId = allConcordiaCommentsWhereIdTrelloCommentIsNotNUll
                .getBody()
                .stream()
                .map(f -> new StringIdDto().getId()).toList();

        List<String> notMatchingComments = listUtil.findNotMatchingRecords(listTrelloId, listConcordiaId);

        ifConcordiaAndTrelloAreNotSynchronizedThenCheckAndDelete(notMatchingComments, allTrelloComments, trelloCommentService);
    }

    private static void ifConcordiaAndTrelloAreNotSynchronizedThenCheckAndDelete(List<String> notMatchingComments, ResponseDto<List<TrelloCommentDto>> allTrelloComments, TrelloCommentService trelloCommentService) {
        if (notMatchingComments.isEmpty()) {
            System.out.printf("%s  [pullComment    ] executed at %s  INFO : NOTHING TO DELETE\n",
                    Thread.currentThread().getName(),
                    new Date());
        } else {
            forEachTrelloCommentCheckIfConcordiaCommentIdIsNotMatching(allTrelloComments, notMatchingComments, trelloCommentService);
        }
    }

    private static void forEachTrelloCommentCheckIfConcordiaCommentIdIsNotMatching(ResponseDto<List<TrelloCommentDto>> allTrelloComments, List<String> notMatchingComments, TrelloCommentService trelloCommentService) {
        for (TrelloCommentDto comment : allTrelloComments.getBody()) {
            forEachNotMatchingTrelloCommentIdCheckAndDelete(comment, notMatchingComments, trelloCommentService);
        }
    }

    private static void forEachNotMatchingTrelloCommentIdCheckAndDelete(TrelloCommentDto comment, List<String> notMatchingComments, TrelloCommentService trelloCommentService) {
        for (String id : notMatchingComments) {
            ifTrelloCommentEqualsNotMatchingCommentIdThenDelete(comment, id, trelloCommentService);
        }
    }

    private static void ifTrelloCommentEqualsNotMatchingCommentIdThenDelete(TrelloCommentDto comment, String id, TrelloCommentService trelloCommentService) {
        if (comment.getId().equals(id)) {
            trelloCommentService.removeCommentByIdCardAndIdComment(comment.getIdCard(), comment.getId());
            System.out.printf("%s  [pullComment    ] executed at %s  INFO : %s : %s → %s\n",
                    Thread.currentThread().getName(),
                    new Date(),
                    comment.getId(),
                    HttpResponseType.FOUND.getLabel(),
                    HttpResponseType.DELETED.getLabel());
        }
    }
}
