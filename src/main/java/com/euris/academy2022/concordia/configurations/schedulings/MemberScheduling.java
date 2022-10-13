package com.euris.academy2022.concordia.configurations.schedulings;

import com.euris.academy2022.concordia.businessLogics.services.trelloServices.TrelloMemberService;
import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.trelloDTOs.TrelloMemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.members.MemberPostRequest;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MemberScheduling {

    public static void fetchAndPull(TrelloMemberService trelloMemberService, MemberService memberService, String idBoard) {
        try {
            ResponseDto<List<TrelloMemberDto>> response = trelloMemberService.getMembersByBoardId(idBoard);

            for (TrelloMemberDto member : response.getBody()) {
                ResponseDto<MemberDto> memberFound = memberService.getByIdTrelloMember(member.getId());

                if (Optional.ofNullable(memberFound.getBody()).isEmpty()) {

                    MemberPostRequest memberNew = MemberPostRequest.builder()
                            .idTrelloMember(member.getId())
                            .username(member.getUsername())
                            .password("wrs")
                            .role(MemberRole.WRS)
                            .firstName(member.getFullName())
                            .lastName("- - -")
                            .dateCreation(LocalDateTime.now())
                            .dateUpdate(LocalDateTime.now())
                            .build();

                    ResponseDto<MemberDto> memberCreated = memberService.insertFromTrello(memberNew.toModel());

                    System.out.printf("%s  [pullMember    ] executed at %s  INFO : %s : %s → %s\n",
                            Thread.currentThread().getName(),
                            new Date(),
                            member.getId(),
                            HttpResponseType.NOT_FOUND.getLabel(),
                            memberCreated.getHttpResponse().getLabel());
                } else {
                    System.out.printf("%s  [pullMember    ] executed at %s  INFO : %s : %s → %s\n",
                            Thread.currentThread().getName(),
                            new Date(),
                            member.getId(),
                            HttpResponseType.FOUND.getLabel(),
                            HttpResponseType.OK.getLabel());
                }
            }
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : ********MemberScheduling : INTERRUPTED MANUALLY\n",
                    Thread.currentThread().getName(),
                    new Date());
        } catch (NullPointerException e) {
            System.out.printf("%s  [fetchTrello   ] executed at %s  WARN : ********MemberScheduling : NULL POINTER\n",
                    Thread.currentThread().getName(),
                    new Date());
        }
    }
}