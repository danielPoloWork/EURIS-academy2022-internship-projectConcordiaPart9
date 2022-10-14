package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.ConcordiaApplication;
import com.euris.academy2022.concordia.businessLogics.services.impls.MemberServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConcordiaApplication.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class MemberServiceTest {

    @MockBean
    private MemberJpaRepository memberJpaRepository;
    private MemberService memberService;

    private Member member;

    private List<Member> memberList;

    private List<MemberDto> memberDtoList;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void init() {

        passwordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);

        memberService = new MemberServiceImpl(memberJpaRepository, passwordEncoder);

        member = Member.builder()
                .uuid("uuidMember")
                .idTrelloMember("idMember")
                .username("user")
                .password("pass")
                .role(MemberRole.A1)
                .firstName("name")
                .lastName("surname")
                .build();

        memberList = new ArrayList<>();
        memberList.add(member);
        memberDtoList = memberList.stream().map(Member::toDto).toList();
    }

    @Test
    void insertTest_CREATED() {

        member.setDateCreation(LocalDateTime.now());
        member.setDateUpdate(LocalDateTime.now());

        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.CREATED);
        expectedResponse.setCode(HttpResponseType.CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.CREATED.getDesc());
        expectedResponse.setBody(member.toDto());

        Mockito
                .when(memberJpaRepository.insert(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<MemberDto> response = memberService.insert(member);

        Mockito.verify(memberJpaRepository, Mockito.times(1))
                .insert(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getUuid(), response.getBody().getUuid());
        Assertions.assertEquals(expectedResponse.getBody().getIdTrelloMember(), response.getBody().getIdTrelloMember());
        Assertions.assertEquals(expectedResponse.getBody().getUsername(), response.getBody().getUsername());
        Assertions.assertEquals(expectedResponse.getBody().toModel().getPassword(), response.getBody().toModel().getPassword());
        Assertions.assertEquals(expectedResponse.getBody().getRole(), response.getBody().getRole());
        Assertions.assertEquals(expectedResponse.getBody().getFirstName(), response.getBody().getFirstName());
        Assertions.assertEquals(expectedResponse.getBody().getLastName(), response.getBody().getLastName());
        Assertions.assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        Assertions.assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());
    }

    @Test
    void insertTest_NOT_CREATED() {

        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_CREATED);
        expectedResponse.setCode(HttpResponseType.NOT_CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito
                .when(memberJpaRepository.insert(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<MemberDto> response = memberService.insert(member);

        Mockito.verify(memberJpaRepository, Mockito.times(1))
                .insert(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());

    }

    @Test
    void updateTest_FOUND_UPDATED() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        member.setDateUpdate(LocalDateTime.now());

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.UPDATED);
        expectedResponse.setCode(HttpResponseType.UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.UPDATED.getDesc());
        expectedResponse.setBody(member.toDto());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        Mockito
                .when(memberJpaRepository.update(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<MemberDto> response = memberService.update(member);

        Mockito.verify(memberJpaRepository, Mockito.times(1))
                .update(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getUuid(), response.getBody().getUuid());
        Assertions.assertEquals(expectedResponse.getBody().toModel().getPassword(), response.getBody().toModel().getPassword());
        Assertions.assertEquals(expectedResponse.getBody().getRole(), response.getBody().getRole());
        Assertions.assertEquals(expectedResponse.getBody().getFirstName(), response.getBody().getFirstName());
        Assertions.assertEquals(expectedResponse.getBody().getLastName(), response.getBody().getLastName());
        Assertions.assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());
    }

    @Test
    void updateTest_FOUND_NOT_UPDATED() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_UPDATED);
        expectedResponse.setCode(HttpResponseType.NOT_UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        Mockito
                .when(memberJpaRepository.update(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<MemberDto> response = memberService.update(member);

        Mockito.verify(memberJpaRepository, Mockito.times(1))
                .update(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void updateTest_NOT_FOUND() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.update(member);

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void deleteByUuidTest_FOUND_DELETED() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.DELETED);
        expectedResponse.setCode(HttpResponseType.DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.DELETED.getDesc());
        expectedResponse.setBody(member.toDto());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        Mockito
                .when(memberJpaRepository.removeByUuid(Mockito.anyString()))
                .thenReturn(1);

        ResponseDto<MemberDto> response = memberService.removeByUuid(Mockito.anyString());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).removeByUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getUuid(), response.getBody().getUuid());
    }

    @Test
    void deleteByUuidTest_FOUND_NOT_DELETED() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_DELETED);
        expectedResponse.setCode(HttpResponseType.NOT_DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        Mockito
                .when(memberJpaRepository.removeByUuid(Mockito.anyString()))
                .thenReturn(0);

        ResponseDto<MemberDto> response = memberService.removeByUuid(Mockito.anyString());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).removeByUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void deleteByUuidTest_NOT_FOUND() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.removeByUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getAllTest_FOUND() {
        ResponseDto<List<MemberDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(memberDtoList);

        Mockito
                .when(memberJpaRepository.findAll())
                .thenReturn(memberList);

        ResponseDto<List<MemberDto>> response = memberService.getAll();

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findAll();

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getUuid(), response.getBody().get(0).getUuid());
    }

    @Test
    void getAllTest_NOT_FOUND() {
        ResponseDto<List<MemberDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findAll())
                .thenReturn(new ArrayList<>());

        ResponseDto<List<MemberDto>> response = memberService.getAll();

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findAll();

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getByUuidTest_FOUND() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(member.toDto());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        ResponseDto<MemberDto> response = memberService.getMemberDtoByUuid(member.getUuid());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getUuid(), response.getBody().getUuid());
    }

    @Test
    void getByUuidTest_NOT_FOUND() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByUuid(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.getMemberDtoByUuid(member.getUuid());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByUuid(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getByIdTrelloTest_FOUND() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(member.toDto());

        Mockito
                .when(memberJpaRepository.findByIdTrelloMember(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        ResponseDto<MemberDto> response = memberService.getByIdTrelloMember(member.getIdTrelloMember());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByIdTrelloMember(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getIdTrelloMember(), response.getBody().getIdTrelloMember());
    }

    @Test
    void getByIdTrelloTest_NOT_FOUND() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByIdTrelloMember(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.getByIdTrelloMember(member.getIdTrelloMember());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByIdTrelloMember(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getByUsernameTest_FOUND() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(member.toDto());

        Mockito
                .when(memberJpaRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(member));

        ResponseDto<MemberDto> response = memberService.getByUsername(member.getUsername());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByUsername(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getUsername(), response.getBody().getUsername());
    }

    @Test
    void getByUsernameTest_NOT_FOUND() {
        ResponseDto<MemberDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.getByUsername(member.getUsername());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByUsername(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getByFirstNameTest_FOUND() {
        ResponseDto<List<MemberDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(memberDtoList);

        Mockito
                .when(memberJpaRepository.findByFirstName(Mockito.anyString()))
                .thenReturn(memberList);

        ResponseDto<List<MemberDto>> response = memberService.getByFirstName(member.getFirstName());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByFirstName(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getFirstName(), response.getBody().get(0).getFirstName());
    }

    @Test
    void getByFirstNameTest_NOT_FOUND() {
        ResponseDto<List<MemberDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByFirstName(Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<MemberDto>> response = memberService.getByFirstName(member.getFirstName());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByFirstName(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getByLastNameTest_FOUND() {
        ResponseDto<List<MemberDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(memberDtoList);

        Mockito
                .when(memberJpaRepository.findByLastName(Mockito.anyString()))
                .thenReturn(memberList);

        ResponseDto<List<MemberDto>> response = memberService.getByLastName(member.getFirstName());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByLastName(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getLastName(), response.getBody().get(0).getLastName());
    }

    @Test
    void getByLastNameTest_NOT_FOUND() {
        ResponseDto<List<MemberDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByLastName(Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<MemberDto>> response = memberService.getByLastName(member.getFirstName());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByLastName(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }

    @Test
    void getByRoleTest_FOUND() {
        ResponseDto<List<MemberDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(memberDtoList);

        Mockito
                .when(memberJpaRepository.findByRole(Mockito.anyString()))
                .thenReturn(memberList);

        ResponseDto<List<MemberDto>> response = memberService.getByRole(member.getRole());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByRole(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        Assertions.assertEquals(expectedResponse.getBody().get(0).getRole(), response.getBody().get(0).getRole());
    }

    @Test
    void getByRoleTest_NOT_FOUND() {
        ResponseDto<List<MemberDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(memberJpaRepository.findByRole(Mockito.anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<MemberDto>> response = memberService.getByRole(member.getRole());

        Mockito.verify(memberJpaRepository, Mockito.times(1)).findByRole(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }



}