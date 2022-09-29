package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.Member;
import com.euris.academy2022.concordia.utils.enums.MemberRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class MemberControllerTest {

    @Autowired
    private MockMvc client;

    @MockBean
    private MemberService memberService;




    @Test
    void test_update() {
    }

    @Test
    void test_deleteById() {
    }

    @Test
    void test_getAll() throws Exception {
        List<Member> members = new ArrayList<>();

        String id = "123";
        String name = "Mario";
        String surname = "Rossi";
        MemberRole role = MemberRole.A1;

        Member member = Member.builder()
                .id(id)
                .name(name)
                .surname(surname)
                .role(role)
                .build();

        members.add(member);

        Mockito.when(memberService.getAll()).thenReturn(members);

        client.perform(get("/api/member"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(member.getId()))
                .andExpect(jsonPath("$[0].name").value(member.getName()))
                .andExpect(jsonPath("$[0].surname").value(member.getSurname()))
                .andExpect(jsonPath("$[0].role").value(member.getRole().getLabel()));


    }

    @Test
    void test_getById() {
    }

    @Test
    void test_getByName() {
    }

    @Test
    void test_getBySurname() {
    }

    @Test
    void test_getByRole() {
    }


}
