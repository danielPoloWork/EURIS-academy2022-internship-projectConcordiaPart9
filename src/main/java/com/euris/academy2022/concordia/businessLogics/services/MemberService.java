package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MemberService {

    ResponseDto<MemberDto> insert(Member member);

    ResponseDto<MemberDto> update(Member member);

    ResponseDto<MemberDto> deleteById(String id);

    ResponseDto<List<MemberDto>> getAll();

    ResponseDto<MemberDto> getById(String id);

    ResponseDto<List<MemberDto>> getByName(String name);

    ResponseDto<List<MemberDto>> getBySurname(String surname);

    ResponseDto<List<MemberDto>> getByRole(MemberRole role);
}