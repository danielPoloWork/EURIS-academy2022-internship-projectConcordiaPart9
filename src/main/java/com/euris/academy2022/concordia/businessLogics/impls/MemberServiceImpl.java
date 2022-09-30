package com.euris.academy2022.concordia.businessLogics.impls;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.MemberRole;

import java.util.List;
import java.util.Optional;

public class MemberServiceImpl implements MemberService {

    MemberJpaRepository memberJpaRepository;

    public MemberServiceImpl(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }


    @Override
    public ResponseDto<Member> insert(Member member) {
        ResponseDto<Member> response = new ResponseDto<>();
        Integer memberCreated = memberJpaRepository.insert(
                member.getId(),
                member.getName(),
                member.getSurname(),
                member.getRole().getLabel()
        );
        response.setHttpRequest(HttpRequestType.POST);

        if (memberCreated != 1) {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(member);
        }
        return response;
    }


    @Override
    public ResponseDto<Member> update(Member member) {
        ResponseDto<Member> response = new ResponseDto<>();
        Optional<Member> memberFound = memberJpaRepository.findById(member.getId());

        if (memberFound.isEmpty()) {
            response.setHttpRequest(HttpRequestType.GET);
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {

            Integer memberUpdated = memberJpaRepository.update(
                    member.getId(),
                    member.getName(),
                    member.getSurname(),
                    member.getRole().getLabel()
            );

            response.setHttpRequest(HttpRequestType.PUT);

            if (memberUpdated != 1) {
                response.setHttpResponse(HttpResponseType.NOT_UPDATED);
                response.setCode(HttpResponseType.NOT_UPDATED.getCode());
                response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.UPDATED);
                response.setCode(HttpResponseType.UPDATED.getCode());
                response.setDesc(HttpResponseType.UPDATED.getDesc());
                response.setBody(member);
            }
        }
        return response;
    }

    @Override
    public ResponseDto<Member> deleteById(String id) {

        ResponseDto<Member> response = new ResponseDto<>();
        Optional<Member> memberFound = memberJpaRepository.findById(id);

        if (memberFound.isEmpty()) {
            response.setHttpRequest(HttpRequestType.GET);
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
        } else {
            Integer memberDeleted = memberJpaRepository.removeById(id);

            response.setHttpRequest(HttpRequestType.DELETE);

            if (memberDeleted != 1) {
                response.setHttpResponse(HttpResponseType.NOT_DELETED);
                response.setCode(HttpResponseType.NOT_DELETED.getCode());
                response.setDesc(HttpResponseType.NOT_DELETED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.DELETED);
                response.setCode(HttpResponseType.DELETED.getCode());
                response.setDesc(HttpResponseType.DELETED.getDesc());
                response.setBody(memberFound.get());
            }
        }

        return response;
    }

    @Override
    public ResponseDto<List<Member>> getAll() {
        ResponseDto<List<Member>> response = new ResponseDto<>();
        List<Member> memberListFound = memberJpaRepository.findAll();

        response.setHttpRequest(HttpRequestType.GET);

        if (memberListFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberListFound);
        }

        return response;
    }

    @Override
    public ResponseDto<Member> getById(String id) {
        ResponseDto<Member> response = new ResponseDto<>();
        Optional<Member> memberFound = memberJpaRepository.findById(id);

        response.setHttpRequest(HttpRequestType.GET);

        if (memberFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberFound.get());
        }
        return response;
    }

    @Override
    public ResponseDto<List<Member>> getByName(String name) {
        ResponseDto<List<Member>> response = new ResponseDto<>();
        List<Member> memberListFound = memberJpaRepository.findByName(name);

        response.setHttpRequest(HttpRequestType.GET);

        if (memberListFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberListFound);
        }

        return response;
    }


    @Override
    public ResponseDto<List<Member>> getBySurname(String surname) {
        ResponseDto<List<Member>> response = new ResponseDto<>();
        List<Member> memberListFound = memberJpaRepository.findBySurname(surname);

        response.setHttpRequest(HttpRequestType.GET);

        if (memberListFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberListFound);
        }

        return response;
    }

    @Override
    public ResponseDto<List<Member>> getByRole(MemberRole role) {
        ResponseDto<List<Member>> response = new ResponseDto<>();
        List<Member> memberListFound = memberJpaRepository.findByRole(role.getLabel());

        response.setHttpRequest(HttpRequestType.GET);

        if (memberListFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberListFound);
        }

        return response;
    }
}
