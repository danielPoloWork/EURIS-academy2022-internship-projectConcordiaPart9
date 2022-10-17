package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.utils.constants.TrelloConstant;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberJpaRepository memberJpaRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberJpaRepository memberJpaRepository, BCryptPasswordEncoder passwordEncoder) {
        this.memberJpaRepository = memberJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseDto<MemberDto> insert(Member member) {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        Integer memberCreated = memberJpaRepository.insert(
                null,
                member.getUsername(),
                passwordEncoder.encode(member.getPassword()),
                member.getRole().getLabel(),
                member.getFirstName(),
                member.getLastName(),
                LocalDateTime.now(),
                LocalDateTime.now());

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
    public ResponseDto<MemberDto> insertFromTrello(Member member) {
        ResponseDto<MemberDto> response = new ResponseDto<>();

        Integer memberCreated = memberJpaRepository.insert(
                member.getIdTrelloMember(),
                member.getUsername(),
                passwordEncoder.encode(member.getPassword()),
                member.getRole().getLabel(),
                member.getFirstName(),
                member.getLastName(),
                member.getDateCreation(),
                member.getDateUpdate());

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
                    passwordEncoder.encode(member.getPassword()),
                    member.getRole().getLabel(),
                    member.getFirstName(),
                    member.getLastName(),
                    LocalDateTime.now());

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
    public ResponseDto<MemberDto> updateFromTrello(Member member) {
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
                    passwordEncoder.encode(member.getPassword()),
                    member.getRole().getLabel(),
                    member.getFirstName(),
                    member.getLastName(),
                    member.getDateUpdate());

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
    public ResponseDto<List<Member>> getAllMember() {
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
    public ResponseDto<List<MemberDto>> getAllMemberDto() {

        ResponseDto<List<MemberDto>> dtoResponse = new ResponseDto<>();
        ResponseDto<List<Member>> modelResponse = getAllMember();

        dtoResponse.setHttpRequest(modelResponse.getHttpRequest());
        dtoResponse.setHttpResponse(modelResponse.getHttpResponse());
        dtoResponse.setCode(modelResponse.getCode());
        dtoResponse.setDesc(modelResponse.getDesc());

        if (modelResponse.getBody() != null) {
            dtoResponse.setBody(modelResponse.getBody().stream().map(Member::toDto).toList());
        }

        return dtoResponse;
    }

    @Override
    public ResponseDto<Member> getMemberByUuid(String uuid) {
        ResponseDto<Member> response = new ResponseDto<>();

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
            response.setBody(memberFound.get());
        }
        return response;
    }

    @Override
    public ResponseDto<MemberDto> getMemberDtoByUuid(String uuid) {
        ResponseDto<MemberDto> dtoResponse = new ResponseDto<>();
        ResponseDto<Member> modelResponse = getMemberByUuid(uuid);

        dtoResponse.setHttpRequest(modelResponse.getHttpRequest());
        dtoResponse.setHttpResponse(modelResponse.getHttpResponse());
        dtoResponse.setCode(modelResponse.getCode());
        dtoResponse.setDesc(modelResponse.getDesc());

        if (modelResponse.getBody() != null) {
            dtoResponse.setBody(modelResponse.getBody().toDto());
        }

        return dtoResponse;
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
    public ResponseDto<List<Member>> getMemberListByRole(MemberRole role) {
        ResponseDto<List<Member>> response = new ResponseDto<>();
        response.setHttpRequest(HttpRequestType.GET);

        List<Member> memberListFound = memberJpaRepository.findByRole(role.getLabel());

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
    public ResponseDto<List<MemberDto>> getByFirstName(String firstName) {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();

        List<Member> memberListFound = memberJpaRepository.findByFirstName(firstName);

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
    public ResponseDto<List<MemberDto>> getByLastName(String lastName) {
        ResponseDto<List<MemberDto>> response = new ResponseDto<>();
        List<Member> memberListFound = memberJpaRepository.findByLastName(lastName);

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
    public ResponseDto<List<MemberDto>> getMemberDtoListByRole(MemberRole role) {
        ResponseDto<List<MemberDto>> dtoResponse = new ResponseDto<>();
        ResponseDto<List<Member>> modelResponse = getMemberListByRole(role);

        dtoResponse.setHttpRequest(modelResponse.getHttpRequest());
        dtoResponse.setHttpResponse(modelResponse.getHttpResponse());
        dtoResponse.setCode(modelResponse.getCode());
        dtoResponse.setDesc(modelResponse.getDesc());

        if (modelResponse.getBody() != null) {
            dtoResponse.setBody(modelResponse.getBody().stream().map(Member::toDto).toList());
        }

        return dtoResponse;
    }
}