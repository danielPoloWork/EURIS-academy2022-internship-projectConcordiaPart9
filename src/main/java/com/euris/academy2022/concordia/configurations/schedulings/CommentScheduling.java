package com.euris.academy2022.concordia.configurations.schedulings;

import com.euris.academy2022.concordia.businessLogics.services.CommentService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloCommentService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.CommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPutRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloCommentDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.comments.CommentPostFromTrello;
import com.euris.academy2022.concordia.utils.TimeUtil;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CommentScheduling {

    public static void fetchAndPull(TrelloCommentService trelloCommentService, CommentService commentService, MemberService memberService) {
        try {
            ResponseDto<List<TrelloCommentDto>> response = trelloCommentService.getAllComments();
            for (TrelloCommentDto comment : response.getBody()) {
                ResponseDto<CommentDto> commentFound = commentService.getByIdTrelloComment(comment.getId());

                if (Optional.ofNullable(commentFound.getBody()).isEmpty()) {
                    ResponseDto<MemberDto> memberFound = memberService.getByIdTrelloMember(comment.getIdMemberCreator());

                    CommentPostFromTrello commentNew = CommentPostFromTrello.builder()
                            .idTrelloComment(comment.getId())
                            .idTask(comment.getIdCard())
                            .uuidMember(memberFound.getBody().getUuid())
                            .text(comment.getText())
                            .dateCreation(LocalDateTime.now())
                            .dateUpdate(TimeUtil.parseToLocalDateTime(comment.getDate()))
                            .build();

                    ResponseDto<CommentDto> commentCreated = commentService.insertFromTrello(commentNew.toModel());

                    System.out.printf("%s  [pullComment   ] executed at %s  INFO : %s : %s → %s\n",
                            Thread.currentThread().getName(),
                            new Date(),
                            comment.getId(),
                            HttpResponseType.NOT_FOUND.getLabel(),
                            commentCreated.getHttpResponse().getLabel());
                } else {
                    if (TimeUtil.parseToLocalDateTime(comment.getDate()).isAfter(commentFound.getBody().getDateUpdate().truncatedTo(ChronoUnit.SECONDS))) {

                        CommentPutRequest commentOld = CommentPutRequest.builder()
                                .uuid(commentFound.getBody().getUuid())
                                .idTrelloComment(comment.getId())
                                .text(comment.getText())
                                .dateUpdate(TimeUtil.parseToLocalDateTime(comment.getDate()))
                                .build();

                        ResponseDto<CommentDto> commentUpdated = commentService.updateFromTrello(commentOld.toModel());

                        System.out.printf("%s  [pullCard      ] executed at %s  INFO : %s : %s → %s\n",
                                Thread.currentThread().getName(),
                                new Date(),
                                comment.getId(),
                                HttpResponseType.FOUND.getLabel(),
                                commentUpdated.getHttpResponse().getLabel());
                    } else {
                        System.out.printf("%s  [pullCard      ] executed at %s  INFO : %s : %s → %s\n",
                                Thread.currentThread().getName(),
                                new Date(),
                                comment.getId(),
                                HttpResponseType.FOUND.getLabel(),
                                HttpResponseType.OK.getLabel());
                    }
                }
            }
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : INTERRUPTED MANUALLY\n",
                    Thread.currentThread().getName(),
                    new Date());
        } catch (NullPointerException e) {
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : RECORD IS NULL\n",
                    Thread.currentThread().getName(),
                    new Date());
        }
    }
}
