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

    ResponseDto<Member> insert(Member member);

    ResponseDto<Member> update(Member member);

    ResponseDto<Member> deleteById(String id);

    ResponseDto<List<Member>> getAll();

    ResponseDto<Member> getById(String id);

    ResponseDto<List<Member>> getByName(String name);

    ResponseDto<List<Member>> getBySurname(String surname);

    ResponseDto<List<Member>> getByRole(MemberRole role);
}
