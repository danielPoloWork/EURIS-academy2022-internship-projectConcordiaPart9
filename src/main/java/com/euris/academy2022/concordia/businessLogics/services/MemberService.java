package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MemberService {

    Optional<MemberDto> insert(Member member);

    Optional<MemberDto> update(Member member);

    Boolean deleteAll();

    Boolean deleteById(String id);

    List<MemberDto> getAll();

    Optional<MemberDto> getById(String id);

    List<MemberDto> getByName(String name);

    List<MemberDto> getBySurname(String surname);

    List<MemberDto> getByRole(MemberRole role);
}
