package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberJpaRepository;

    public MemberServiceImpl(MemberJpaRepository memberJpaRepository) {
        this.memberJpaRepository = memberJpaRepository;
    }

    @Override
    public ResponseDto<MemberDto> insert(Member member) {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        Integer memberCreated = memberJpaRepository.insert(
                member.getIdTrelloMember(),
                member.getUsername(),
                member.getPassword(),
                member.getRole().getLabel(),
                member.getName(),
                member.getSurname());

        response.setHttpRequest(HttpRequestType.POST);

        if (memberCreated != 1) {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(member.toDto());
        }
        return response;
    }

    @Override
    public ResponseDto<MemberDto> update(Member member) {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        Optional<Member> memberFound = memberJpaRepository.findByUuid(member.getUuid());

        response.setHttpRequest(HttpRequestType.PUT);

        if (memberFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {

            Integer memberUpdated = memberJpaRepository.update(
                    member.getUuid(),
                    member.getIdTrelloMember(),
                    member.getUsername(),
                    member.getPassword(),
                    member.getRole().getLabel(),
                    member.getName(),
                    member.getSurname());

            if (memberUpdated != 1) {
                response.setHttpResponse(HttpResponseType.NOT_UPDATED);
                response.setCode(HttpResponseType.NOT_UPDATED.getCode());
                response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.UPDATED);
                response.setCode(HttpResponseType.UPDATED.getCode());
                response.setDesc(HttpResponseType.UPDATED.getDesc());
                response.setBody(member.toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<MemberDto> removeByUuid(String uuid) {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        Optional<Member> memberFound = memberJpaRepository.findByUuid(uuid);

        response.setHttpRequest(HttpRequestType.DELETE);

        if (memberFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            Integer memberDeleted = memberJpaRepository.removeByUuid(uuid);

            if (memberDeleted != 1) {
                response.setHttpResponse(HttpResponseType.NOT_DELETED);
                response.setCode(HttpResponseType.NOT_DELETED.getCode());
                response.setDesc(HttpResponseType.NOT_DELETED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.DELETED);
                response.setCode(HttpResponseType.DELETED.getCode());
                response.setDesc(HttpResponseType.DELETED.getDesc());
                response.setBody(memberFound.get().toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<List<MemberDto>> getAll() {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();
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
            response.setBody(memberListFound.stream()
                    .map(Member::toDto)
                    .collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public ResponseDto<MemberDto> getByUuid(String uuid) {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        Optional<Member> memberFound = memberJpaRepository.findByUuid(uuid);

        response.setHttpRequest(HttpRequestType.GET);

        if (memberFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberFound.get().toDto());
        }
        return response;
    }

    @Override
    public ResponseDto<MemberDto> getByIdTrelloMember(String idTrelloMember) {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        Optional<Member> memberFound = memberJpaRepository.findByIdTrelloMember(idTrelloMember);

        response.setHttpRequest(HttpRequestType.GET);

        if (memberFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberFound.get().toDto());
        }
        return response;
    }

    @Override
    public ResponseDto<MemberDto> getByUsername(String username) {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        Optional<Member> memberFound = memberJpaRepository.findByUsername(username);

        response.setHttpRequest(HttpRequestType.GET);

        if (memberFound.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(memberFound.get().toDto());
        }
        return response;
    }

    @Override
    public ResponseDto<List<MemberDto>> getByName(String name) {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

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
            response.setBody(memberListFound.stream()
                    .map(Member::toDto)
                    .collect(Collectors.toList()));
        }
        return response;
    }

    @Override
    public ResponseDto<List<MemberDto>> getBySurname(String surname) {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();
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
            response.setBody(memberListFound.stream().map(Member::toDto).collect(Collectors.toList()));
        }

        return response;
    }

    @Override
    public ResponseDto<List<MemberDto>> getByRole(MemberRole role) {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

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
            response.setBody(memberListFound.stream()
                    .map(Member::toDto)
                    .collect(Collectors.toList()));
        }
        return response;
    }
}