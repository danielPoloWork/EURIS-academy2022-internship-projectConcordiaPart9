package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    MemberJpaRepository memberJpaRepository;

    public MemberServiceImpl(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public Optional<Member> insert(Member member) {
        return memberJpaRepository.insert(
                member.getId(),
                member.getName(),
                member.getSurname(),
                member.getRole().getLabel()
        );
    }

    @Override
    public Optional<Member> update(Member member) {
        Optional<Member> memberFound = getById(member.getId());

        return memberFound.isEmpty() ? Optional.empty() : Optional.of(memberJpaRepository.save(member));
    }

    @Override
    public Boolean deleteById(String id) {
        memberJpaRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public List<Member> getAll() {
        return memberJpaRepository.findAll();
    }

    @Override
    public Optional<Member> getById(String id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public List<Member> getByName(String name) {
        return memberJpaRepository.findByName(name);
    }

    @Override
    public List<Member> getBySurname(String surname) {
        return memberJpaRepository.findBySurname(surname);
    }
    
    @Override
    public List<Member> getByRole(MemberRole role) {
        return memberJpaRepository.findByRole(role.getLabel());
    }
}
