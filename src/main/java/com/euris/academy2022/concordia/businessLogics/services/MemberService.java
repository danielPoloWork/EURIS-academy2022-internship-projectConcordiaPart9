package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    ResponseDto<MemberDto> insert(Member member);
    ResponseDto<MemberDto> update(Member member);
    ResponseDto<MemberDto> removeByUuid(String uuid);
    ResponseDto<List<MemberDto>> getAll();
    ResponseDto<MemberDto> getByUuid(String uuid);
    ResponseDto<MemberDto> getByIdTrelloMember(String idTrelloMember);
    ResponseDto<MemberDto> getByUsername(String username);
    ResponseDto<List<MemberDto>> getByRole(MemberRole role);
    ResponseDto<List<MemberDto>> getByName(String name);
    ResponseDto<List<MemberDto>> getBySurname(String surname);
}