package com.euris.academy2022.concordia.businessLogics.controllers;


import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.members.MemberPostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.members.MemberPutRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseDto<MemberDto> insert(@RequestBody MemberPostRequest memberDto) {
        return memberService.insert(memberDto.toModel());
    }

    @PutMapping
    public ResponseDto<MemberDto> update(@RequestBody MemberPutRequest memberDto) {
        return memberService.update(memberDto.toModel());
    }

    @DeleteMapping("/{uuid}")
    public ResponseDto<MemberDto> removeByUuid(@PathVariable String uuid) {
        return memberService.removeByUuid(uuid);
    }

    @GetMapping
    public ResponseDto<List<MemberDto>> getAll() {
        return memberService.getAll();
    }

    @GetMapping("/{uuid}")
    public ResponseDto<MemberDto> getByUuid(@PathVariable String uuid) {
        return memberService.getByUuid(uuid);
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
    public ResponseDto<List<MemberDto>> getByRole(@PathVariable String role) {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        try {
            MemberRole roleEnum = MemberRole.valueOf(role);
            return memberService.getByRole(roleEnum);
        } catch (IllegalArgumentException e) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
            return response;
        }
    }

    @GetMapping("/name={name}")
    public ResponseDto<List<MemberDto>> getByName(@PathVariable String name) {
        return memberService.getByName(name);
    }

    @GetMapping("/surname={surname}")
    public ResponseDto<List<MemberDto>> getBySurname(@PathVariable String surname) {
        return memberService.getBySurname(surname);
    }
}
