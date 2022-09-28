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
        Optional<Member> optionalMember = memberJpaRepository.findById(member.getId());
        if (optionalMember.isEmpty()) {
            return Optional.of(memberJpaRepository.save(member));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Member> update(Member member) {
        Optional<Member> optionalMember = memberJpaRepository.findById(member.getId());
        if (optionalMember.isPresent()) {
            return Optional.of(memberJpaRepository.save(member));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteAll() {
        memberJpaRepository.deleteAll();
        return Boolean.TRUE;
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
