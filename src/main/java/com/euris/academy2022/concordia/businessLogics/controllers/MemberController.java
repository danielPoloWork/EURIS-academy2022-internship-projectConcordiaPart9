package com.euris.academy2022.concordia.businessLogics.controllers;


import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.UserDetailsManagerService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.members.MemberPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.members.MemberPutRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    private final UserDetailsManagerService userDetailsManagerService;

    public MemberController(MemberService memberService, UserDetailsManagerService userDetailsManagerService) {
        this.memberService = memberService;
        this.userDetailsManagerService = userDetailsManagerService;
    }

    @PostMapping
    public ResponseDto<MemberDto> insert(@RequestBody MemberPostRequest memberDto) {
        ResponseDto<MemberDto> response = memberService.insert(memberDto.toModel());
        if (response != null && response.getBody() != null) {
            Member member = Member.builder()
                    .username(memberDto.getUsername())
                    .password(memberDto.getPassword())
                    .role(memberDto.getRole())
                    .build();
            userDetailsManagerService.responsePostByModel(member);
        }
        return response;
    }

    @PutMapping
    public ResponseDto<MemberDto> update(@RequestBody MemberPutRequest memberDto) {
        ResponseDto<MemberDto> response = memberService.update(memberDto.toModel());
        ResponseDto<Member> memberFound = memberService.getMemberByUuid(memberDto.getUuid());
        if (response != null && response.getBody() != null && memberFound.getBody() != null) {
            Member member = Member.builder()
                    .username(memberFound.getBody().getUsername())
                    .password(memberDto.getPassword())
                    .role(memberDto.getRole())
                    .build();
            userDetailsManagerService.responsePutByModel(member);
        }
        return response;
    }

    @DeleteMapping("/{uuid}")
    public ResponseDto<MemberDto> removeByUuid(@PathVariable String uuid) {
        ResponseDto<MemberDto> response = memberService.removeByUuid(uuid);
        if (response != null && response.getBody() != null) {
            userDetailsManagerService.responseDeleteByUsername(response.getBody().getUsername());
        }
        return response;
    }

    @GetMapping
    public ResponseDto<List<MemberDto>> getAll() {
        return memberService.getAllMemberDto();
    }

    @GetMapping("/{uuid}")
    public ResponseDto<MemberDto> getByUuid(@PathVariable String uuid) {
        return memberService.getMemberDtoByUuid(uuid);
    }

    @GetMapping("/idTrelloMember={idTrelloMember}")
    public ResponseDto<MemberDto> getByIdTrelloMember(@PathVariable String idTrelloMember) {
        return memberService.getByIdTrelloMember(idTrelloMember);
    }

    @GetMapping("/username={username}")
    public ResponseDto<MemberDto> getByUsername(@PathVariable String username) {
        return memberService.getByUsername(username);
    }

    @GetMapping("/role={role}")
    public ResponseDto<List<MemberDto>> getByRole(@PathVariable MemberRole role) {
        return memberService.getMemberDtoListByRole(role);
    }

    @GetMapping("/name={name}")
    public ResponseDto<List<MemberDto>> getByName(@PathVariable String name) {
        return memberService.getByFirstName(name);
    }

    @GetMapping("/surname={surname}")
    public ResponseDto<List<MemberDto>> getBySurname(@PathVariable String surname) {
        return memberService.getByLastName(surname);
    }
}
