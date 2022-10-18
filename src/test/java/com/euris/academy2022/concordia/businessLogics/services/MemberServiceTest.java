package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.businessLogics.services.impls.MemberServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.jpaRepositories.MemberJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class MemberServiceTest {

    @MockBean
    private MemberJpaRepository memberJpaRepository;

    private MemberService memberService;
    private Member member;
    private List<Member> memberList;
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
    }

    @Test
    void insertTest_CREATED() {

        member.setDateCreation(LocalDateTime.now());
        member.setDateUpdate(LocalDateTime.now());

        when(memberJpaRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        when(memberJpaRepository.insert(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<MemberDto> response = memberService.insert(member);

        verify(memberJpaRepository, times(1)).findByUsername(anyString());
        verify(memberJpaRepository, times(1))
                .insert(anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class));

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.CREATED, response.getHttpResponse());
        assertEquals(HttpResponseType.CREATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.CREATED.getDesc(), response.getDesc());
        assertEquals(member.getUuid(), response.getBody().getUuid());
        assertEquals(member.getIdTrelloMember(), response.getBody().getIdTrelloMember());
        assertEquals(member.getUsername(), response.getBody().getUsername());
        assertEquals(member.getRole(), response.getBody().getRole());
        assertEquals(member.getFirstName(), response.getBody().getFirstName());
        assertEquals(member.getLastName(), response.getBody().getLastName());
        assertEquals(member.getDateCreation(), response.getBody().getDateCreation());
        assertEquals(member.getDateUpdate(), response.getBody().getDateUpdate());
    }

    @Test
    void insertTest_NOT_CREATED_jpaError() {

        when(memberJpaRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        when(memberJpaRepository.insert(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<MemberDto> response = memberService.insert(member);

        verify(memberJpaRepository, times(1)).findByUsername(anyString());
        verify(memberJpaRepository, times(1))
                .insert(anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class));

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_CREATED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_CREATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_CREATED.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void insertTest_NOT_CREATED_UsernameAlreadyPresent() {

        when(memberJpaRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(member));

        ResponseDto<MemberDto> response = memberService.insert(member);

        verify(memberJpaRepository, times(1)).findByUsername(anyString());

        assertEquals(HttpRequestType.POST, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_CREATED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_CREATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_CREATED.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void updateTest_UPDATED() {

        member.setDateUpdate(LocalDateTime.now());

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(member));

        when(memberJpaRepository.update(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<MemberDto> response = memberService.update(member);

        verify(memberJpaRepository, times(1))
                .update(anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class));

        assertEquals(HttpRequestType.PUT, response.getHttpRequest());
        assertEquals(HttpResponseType.UPDATED, response.getHttpResponse());
        assertEquals(HttpResponseType.UPDATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.UPDATED.getDesc(), response.getDesc());
        assertEquals(member.getUuid(), response.getBody().getUuid());
        assertEquals(member.getRole(), response.getBody().getRole());
        assertEquals(member.getFirstName(), response.getBody().getFirstName());
        assertEquals(member.getLastName(), response.getBody().getLastName());
        assertEquals(member.getDateUpdate(), response.getBody().getDateUpdate());
    }

    @Test
    void updateTest_NOT_UPDATED() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(member));

        when(memberJpaRepository.update(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<MemberDto> response = memberService.update(member);

        verify(memberJpaRepository, times(1))
                .update(anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class));

        assertEquals(HttpRequestType.PUT, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_UPDATED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_UPDATED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_UPDATED.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void updateTest_NOT_FOUND() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.update(member);

        assertEquals(HttpRequestType.PUT, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void deleteByUuidTest_DELETED() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(member));

        when(memberJpaRepository.removeByUuid(anyString()))
                .thenReturn(1);

        ResponseDto<MemberDto> response = memberService.removeByUuid(anyString());

        verify(memberJpaRepository, times(1)).removeByUuid(anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.DELETED, response.getHttpResponse());
        assertEquals(HttpResponseType.DELETED.getCode(), response.getCode());
        assertEquals(HttpResponseType.DELETED.getDesc(), response.getDesc());
        assertEquals(member.getUuid(), response.getBody().getUuid());
    }

    @Test
    void deleteByUuidTest_NOT_DELETED() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(member));

        when(memberJpaRepository.removeByUuid(anyString()))
                .thenReturn(0);

        ResponseDto<MemberDto> response = memberService.removeByUuid(anyString());

        verify(memberJpaRepository, times(1)).removeByUuid(anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_DELETED, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_DELETED.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_DELETED.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void deleteByUuidTest_NOT_FOUND() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.removeByUuid(anyString());

        assertEquals(HttpRequestType.DELETE, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getAllTest_FOUND() {

        when(memberJpaRepository.findAll())
                .thenReturn(memberList);

        ResponseDto<List<MemberDto>> response = memberService.getAllMemberDto();

        verify(memberJpaRepository, times(1)).findAll();

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(memberList.size(), response.getBody().size());
        assertEquals(member.getUuid(), response.getBody().get(0).getUuid());
    }

    @Test
    void getAllTest_NOT_FOUND() {

        when(memberJpaRepository.findAll())
                .thenReturn(new ArrayList<>());

        ResponseDto<List<MemberDto>> response = memberService.getAllMemberDto();

        verify(memberJpaRepository, times(1)).findAll();

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByUuidTest_FOUND() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.of(member));

        ResponseDto<MemberDto> response = memberService.getMemberDtoByUuid(member.getUuid());

        verify(memberJpaRepository, times(1)).findByUuid(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(member.getUuid(), response.getBody().getUuid());
    }

    @Test
    void getByUuidTest_NOT_FOUND() {

        when(memberJpaRepository.findByUuid(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.getMemberDtoByUuid(member.getUuid());

        verify(memberJpaRepository, times(1)).findByUuid(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByIdTrelloTest_FOUND() {

        when(memberJpaRepository.findByIdTrelloMember(anyString()))
                .thenReturn(Optional.of(member));

        ResponseDto<MemberDto> response = memberService.getByIdTrelloMember(member.getIdTrelloMember());

        verify(memberJpaRepository, times(1)).findByIdTrelloMember(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(member.getIdTrelloMember(), response.getBody().getIdTrelloMember());
    }

    @Test
    void getByIdTrelloTest_NOT_FOUND() {

        when(memberJpaRepository.findByIdTrelloMember(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.getByIdTrelloMember(member.getIdTrelloMember());

        verify(memberJpaRepository, times(1)).findByIdTrelloMember(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByUsernameTest_FOUND() {

        when(memberJpaRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(member));

        ResponseDto<MemberDto> response = memberService.getByUsername(member.getUsername());

        verify(memberJpaRepository, times(1)).findByUsername(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(member.getUsername(), response.getBody().getUsername());
    }

    @Test
    void getByUsernameTest_NOT_FOUND() {

        when(memberJpaRepository.findByUsername(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<MemberDto> response = memberService.getByUsername(member.getUsername());

        verify(memberJpaRepository, times(1)).findByUsername(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByFirstNameTest_FOUND() {

        when(memberJpaRepository.findByFirstName(anyString()))
                .thenReturn(memberList);

        ResponseDto<List<MemberDto>> response = memberService.getByFirstName(member.getFirstName());

        verify(memberJpaRepository, times(1)).findByFirstName(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(memberList.size(), response.getBody().size());
        assertEquals(member.getFirstName(), response.getBody().get(0).getFirstName());
    }

    @Test
    void getByFirstNameTest_NOT_FOUND() {

        when(memberJpaRepository.findByFirstName(anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<MemberDto>> response = memberService.getByFirstName(member.getFirstName());

        verify(memberJpaRepository, times(1)).findByFirstName(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByLastNameTest_FOUND() {

        when(memberJpaRepository.findByLastName(anyString()))
                .thenReturn(memberList);

        ResponseDto<List<MemberDto>> response = memberService.getByLastName(member.getFirstName());

        verify(memberJpaRepository, times(1)).findByLastName(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(memberList.size(), response.getBody().size());
        assertEquals(member.getLastName(), response.getBody().get(0).getLastName());
    }

    @Test
    void getByLastNameTest_NOT_FOUND() {

        when(memberJpaRepository.findByLastName(anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<MemberDto>> response = memberService.getByLastName(member.getFirstName());

        verify(memberJpaRepository, times(1)).findByLastName(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByRoleTest_FOUND() {

        when(memberJpaRepository.findByRole(anyString()))
                .thenReturn(memberList);

        ResponseDto<List<MemberDto>> response = memberService.getMemberDtoListByRole(member.getRole());

        verify(memberJpaRepository, times(1)).findByRole(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.FOUND.getDesc(), response.getDesc());
        assertEquals(memberList.size(), response.getBody().size());
        assertEquals(member.getRole(), response.getBody().get(0).getRole());
    }

    @Test
    void getByRoleTest_NOT_FOUND() {

        when(memberJpaRepository.findByRole(anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<MemberDto>> response = memberService.getMemberDtoListByRole(member.getRole());

        verify(memberJpaRepository, times(1)).findByRole(anyString());

        assertEquals(HttpRequestType.GET, response.getHttpRequest());
        assertEquals(HttpResponseType.NOT_FOUND, response.getHttpResponse());
        assertEquals(HttpResponseType.NOT_FOUND.getCode(), response.getCode());
        assertEquals(HttpResponseType.NOT_FOUND.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }


}