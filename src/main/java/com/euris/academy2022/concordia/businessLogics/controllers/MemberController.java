package com.euris.academy2022.concordia.businessLogics.controllers;


import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.members.MemberPostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.members.MemberPutRequest;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public Optional<Member> insert(@RequestBody MemberPostRequest memberDto) {
        return memberService.insert(memberDto.toModel());
    }

    @PutMapping
    public Optional<Member> update(@RequestBody MemberPutRequest memberDto) {
        return memberService.update(memberDto.toModel());
    }

    @DeleteMapping("/{id}")
    public Boolean deleteById(@PathVariable String id) {
        return memberService.deleteById(id);
    }

    @GetMapping
    public List<Member> getAll() {
        return memberService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Member> getById(@PathVariable String id) {
        return memberService.getById(id);
    }

    @GetMapping("/name={name}")
    public List<Member> getByName(@PathVariable String name) {
        return memberService.getByName(name);
    }

    @GetMapping("/surname={surname}")
    public List<Member> getBySurname(@PathVariable String surname) {
        return memberService.getBySurname(surname);
    }

    @GetMapping("/role={role}")
    public List<Member> getByRole(@PathVariable MemberRole role) {
        return memberService.getByRole(role);
    }
}
