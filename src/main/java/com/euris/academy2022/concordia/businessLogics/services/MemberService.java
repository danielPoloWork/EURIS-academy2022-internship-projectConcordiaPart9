package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import java.util.List;

public interface MemberService {

    ResponseDto<MemberDto> insert(Member member);
    ResponseDto<MemberDto> insertFromTrello(Member member);
    ResponseDto<MemberDto> update(Member member);
    ResponseDto<MemberDto> updateFromTrello(Member member);
    ResponseDto<MemberDto> removeByUuid(String uuid);
    ResponseDto<List<Member>> getAllMember();
    ResponseDto<List<MemberDto>> getAllMemberDto();
    ResponseDto<Member> getMemberByUuid(String uuid);
    ResponseDto<MemberDto> getMemberDtoByUuid(String uuid);
    ResponseDto<MemberDto> getByIdTrelloMember(String idTrelloMember);
    ResponseDto<Member> getMemberByUsername(String username);
    ResponseDto<MemberDto> getMemberDtoByUsername(String username);
    ResponseDto<List<MemberDto>> getByRole(MemberRole role);
    ResponseDto<List<MemberDto>> getByFirstName(String name);
    ResponseDto<List<MemberDto>> getByLastName(String surname);
}