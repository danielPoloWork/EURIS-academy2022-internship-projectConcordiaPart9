package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MemberService {

    Optional<Member> insert(Member member);

    Optional<Member> update(Member member);

    Boolean deleteById(String id);

    List<Member> getAll();

    Optional<Member> getById(String id);

    List<Member> getByName(String name);

    List<Member> getBySurname(String surname);

    List<Member> getByRole(MemberRole role);
}
