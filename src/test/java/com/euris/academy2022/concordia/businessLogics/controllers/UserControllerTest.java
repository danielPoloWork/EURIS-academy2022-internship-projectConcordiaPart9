package com.euris.academy2022.concordia.businessLogics.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.euris.academy2022.concordia.businessLogics.services.UserService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

//@Import(value = {
//    SecurityCfgTest.class,
//    SecurityCfg.class
//})
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class UserControllerTest {

  @Autowired
  private MockMvc client;

  @MockBean
  private UserService userService;

  private ObjectMapper objectMapper;
  private User user1;
  private User user2;
  private User user3;
  private User user4;
  private List<User> userList;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
    user1 = User.builder()
        .uuid("1")
        .role(UserRole.ADMIN)
        .username("username1")
        .password("password1")
        .build();
    user2 = User.builder()
        .uuid("2")
        .role(UserRole.ADMIN)
        .username("username2")
        .password("password2")
        .build();
    user3 = User.builder()
        .uuid("3")
        .role(UserRole.MANAGER)
        .username("username3")
        .password("password3")
        .build();
    user4 = User.builder()
        .uuid("4")
        .role(UserRole.MEMBER_A1)
        .username("username4")
        .password("password4")
        .build();

    userList = new ArrayList<>();
    userList.add(user1);
    userList.add(user2);
    userList.add(user3);
    userList.add(user4);
  }

  private final String REQUEST_MAPPING = "/api/user";

  @Test
  @DisplayName("GIVEN role, username, password WHEN insert THEN response should be CREATED")
  void insertTest_ShouldBeCreated() throws Exception {
    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.POST);
    response.setHttpResponse(HttpResponseType.CREATED);
    response.setCode(HttpResponseType.CREATED.getCode());
    response.setDesc(HttpResponseType.CREATED.getDesc());
    response.setBody(user1);

    Mockito
        .when(userService.insert(Mockito.any(User.class)))
        .thenReturn(response);

    client
        .perform(post(REQUEST_MAPPING)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user1)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.POST.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.CREATED.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.CREATED.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.CREATED.getDesc()))
        .andExpect(jsonPath("$.body.uuid").value(user1.getUuid()))
        .andExpect(jsonPath("$.body.role").value(user1.getRole().getLabel()))
        .andExpect(jsonPath("$.body.username").value(user1.getUsername()))
        .andExpect(jsonPath("$.body.password").value(user1.getPassword()));
  }

  @Test
  @DisplayName("IF args are missing WHEN insert THEN response should be NOT_CREATED")
  void insertTest_ShouldBeNotCreated() throws Exception {
    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.POST);
    response.setHttpResponse(HttpResponseType.NOT_CREATED);
    response.setCode(HttpResponseType.NOT_CREATED.getCode());
    response.setDesc(HttpResponseType.NOT_CREATED.getDesc());

    Mockito
        .when(userService.insert(Mockito.any(User.class)))
        .thenReturn(response);

    client
        .perform(post(REQUEST_MAPPING)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user1)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.POST.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_CREATED.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_CREATED.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_CREATED.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }

  @Test
  @DisplayName("GIVEN uuid, role, username, password WHEN update THEN response should be UPDATED")
  void updateTest_ShouldBeUpdated() throws Exception {

    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.PUT);
    response.setHttpResponse(HttpResponseType.UPDATED);
    response.setCode(HttpResponseType.UPDATED.getCode());
    response.setDesc(HttpResponseType.UPDATED.getDesc());
    response.setBody(user1);

    Mockito
        .when(userService.update(Mockito.any(User.class)))
        .thenReturn(response);

    client
        .perform(put(REQUEST_MAPPING)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user1)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.UPDATED.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.UPDATED.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.UPDATED.getDesc()))
        .andExpect(jsonPath("$.body.uuid").value(user1.getUuid()))
        .andExpect(jsonPath("$.body.role").value(user1.getRole().getLabel()))
        .andExpect(jsonPath("$.body.username").value(user1.getUsername()))
        .andExpect(jsonPath("$.body.password").value(user1.getPassword()));
  }

  @Test
  @DisplayName("IF something goes wrong WHEN update THEN response should be NOT_UPDATED")
  void updateTest_ShouldBeNotUpdated() throws Exception {

    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.PUT);
    response.setHttpResponse(HttpResponseType.NOT_UPDATED);
    response.setCode(HttpResponseType.NOT_UPDATED.getCode());
    response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

    Mockito
        .when(userService.update(Mockito.any(User.class)))
        .thenReturn(response);

    client
        .perform(put(REQUEST_MAPPING)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user1)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_UPDATED.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_UPDATED.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_UPDATED.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }

  @Test
  @DisplayName("GIVEN wrong uuid OR uuid==null  WHEN update THEN response should be NOT_FOUND")
  void updateTest_ShouldBeNotFound() throws Exception {

    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.PUT);
    response.setHttpResponse(HttpResponseType.NOT_FOUND);
    response.setCode(HttpResponseType.NOT_FOUND.getCode());
    response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

    Mockito
        .when(userService.update(Mockito.any(User.class)))
        .thenReturn(response);

    client
        .perform(put(REQUEST_MAPPING)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user1)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.PUT.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }


  @Test
  @DisplayName("GIVEN a right uuid WHEN deleteByUuid AND uuid is present THEN response should be DELETED")
  void deleteByUuidTest_ShouldBeDeleted() throws Exception {

    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.DELETE);
    response.setHttpResponse(HttpResponseType.DELETED);
    response.setCode(HttpResponseType.DELETED.getCode());
    response.setDesc(HttpResponseType.DELETED.getDesc());
    response.setBody(user1);

    Mockito
        .when(userService.removeByUuid(Mockito.anyString()))
        .thenReturn(response);

    client
        .perform(delete(REQUEST_MAPPING + "/" + user1.getUuid()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.DELETED.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.DELETED.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.DELETED.getDesc()))
        .andExpect(jsonPath("$.body.uuid").value(user1.getUuid()))
        .andExpect(jsonPath("$.body.role").value(user1.getRole().getLabel()))
        .andExpect(jsonPath("$.body.username").value(user1.getUsername()))
        .andExpect(jsonPath("$.body.password").value(user1.getPassword()));
  }


  @Test
  @DisplayName("GIVEN a uuid WHEN deleteByUuid BUT couldn't delete THEN response should be NOT_DELETED")
  void deleteByUuidTest_ShouldBeNotDeleted() throws Exception {

    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.DELETE);
    response.setHttpResponse(HttpResponseType.NOT_DELETED);
    response.setCode(HttpResponseType.NOT_DELETED.getCode());
    response.setDesc(HttpResponseType.NOT_DELETED.getDesc());

    Mockito
        .when(userService.removeByUuid(Mockito.anyString()))
        .thenReturn(response);

    client
        .perform(delete(REQUEST_MAPPING + "/" + user1.getUuid()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_DELETED.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_DELETED.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_DELETED.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }

  @Test
  @DisplayName("GIVEN a uuid WHEN deleteByUuid BUT couldn't find it THEN response should be NOT_FOUND")
  void deleteByUuidTest_ShouldBeNotFound() throws Exception {

    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.DELETE);
    response.setHttpResponse(HttpResponseType.NOT_FOUND);
    response.setCode(HttpResponseType.NOT_FOUND.getCode());
    response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

    Mockito
        .when(userService.removeByUuid(Mockito.anyString()))
        .thenReturn(response);

    client
        .perform(delete(REQUEST_MAPPING + "/" + user1.getUuid()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.DELETE.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }

  @Test
  @DisplayName("GIVEN getAll() WHEN record are present THEN response should be FOUND")
  void getAllTest_ShouldBeFound() throws Exception {
    ResponseDto<List<User>> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);
    response.setHttpResponse(HttpResponseType.FOUND);
    response.setCode(HttpResponseType.FOUND.getCode());
    response.setDesc(HttpResponseType.FOUND.getDesc());
    response.setBody(userList);

    Mockito
        .when(userService.getAll())
        .thenReturn(response);

    client
        .perform(get(REQUEST_MAPPING))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
        .andExpect(jsonPath("$.body[0].uuid").value(userList.get(0).getUuid()))
        .andExpect(jsonPath("$.body[0].role").value(userList.get(0).getRole().getLabel()))
        .andExpect(jsonPath("$.body[0].username").value(userList.get(0).getUsername()))
        .andExpect(jsonPath("$.body[0].password").value(userList.get(0).getPassword()));
  }

  @Test
  @DisplayName("GIVEN getAll() WHEN table is empty THEN response should be FOUND")
  void getAllTest_ShouldBeNotFound() throws Exception {
    ResponseDto<List<User>> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);
    response.setHttpResponse(HttpResponseType.NOT_FOUND);
    response.setCode(HttpResponseType.NOT_FOUND.getCode());
    response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

    Mockito
        .when(userService.getAll())
        .thenReturn(response);

    client
        .perform(get(REQUEST_MAPPING))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }

  @Test
  @DisplayName("GIVEN a right uuid WHEN getByUuid() THEN response should be FOUND")
  void getByUuidTest_ShouldBeFound() throws Exception{

    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);
    response.setHttpResponse(HttpResponseType.FOUND);
    response.setCode(HttpResponseType.FOUND.getCode());
    response.setDesc(HttpResponseType.FOUND.getDesc());
    response.setBody(user1);

    Mockito
        .when(userService.getByUuid(Mockito.anyString()))
        .thenReturn(response);

    client
        .perform(get(REQUEST_MAPPING + "/" + user1.getUuid()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
        .andExpect(jsonPath("$.body.uuid").value(user1.getUuid()))
        .andExpect(jsonPath("$.body.role").value(user1.getRole().getLabel()))
        .andExpect(jsonPath("$.body.username").value(user1.getUsername()))
        .andExpect(jsonPath("$.body.password").value(user1.getPassword()));
  }

  @Test
  @DisplayName("GIVEN a wrong uuid WHEN getByUuid() THEN response should be NOT_FOUND")
  void getByUuidTest_ShouldBeNotFound() throws Exception{
    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);
    response.setHttpResponse(HttpResponseType.NOT_FOUND);
    response.setCode(HttpResponseType.NOT_FOUND.getCode());
    response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

    Mockito
        .when(userService.getByUuid(Mockito.anyString()))
        .thenReturn(response);

    client
        .perform(get(REQUEST_MAPPING + "/anyString"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }

  @Test
  @DisplayName("GIVEN a right role WHEN getByRole() THEN response should be FOUND")
  void getByRoleTest_ShouldBeFound() throws Exception{
    ResponseDto<List<User>> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);
    response.setHttpResponse(HttpResponseType.FOUND);
    response.setCode(HttpResponseType.FOUND.getCode());
    response.setDesc(HttpResponseType.FOUND.getDesc());
    response.setBody(userList);

    Mockito
        .when(userService.getByRole(Mockito.any(UserRole.class)))
        .thenReturn(response);

    client
        .perform(get(REQUEST_MAPPING + "/role=" + UserRole.ADMIN.getLabel()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
        .andExpect(jsonPath("$.body[0].uuid").value(userList.get(0).getUuid()))
        .andExpect(jsonPath("$.body[0].role").value(userList.get(0).getRole().getLabel()))
        .andExpect(jsonPath("$.body[0].username").value(userList.get(0).getUsername()))
        .andExpect(jsonPath("$.body[0].password").value(userList.get(0).getPassword()));
  }

  @Test
  @DisplayName("GIVEN a wrong role WHEN getByRole() THEN response should be NOT_FOUND")
  void getByRoleTest_ShouldBeNotFound() throws Exception{
    ResponseDto<List<User>> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);
    response.setHttpResponse(HttpResponseType.NOT_FOUND);
    response.setCode(HttpResponseType.NOT_FOUND.getCode());
    response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

    Mockito
        .when(userService.getByRole(Mockito.any(UserRole.class)))
        .thenReturn(response);

    client
        .perform(get(REQUEST_MAPPING + "/role=" + UserRole.MEMBER_B1.getLabel()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }

  @Test
  @DisplayName("GIVEN a right username WHEN getByUsername() THEN response should be FOUND")
  void getByUsernameTest_ShouldBeFound() throws Exception{

    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);
    response.setHttpResponse(HttpResponseType.FOUND);
    response.setCode(HttpResponseType.FOUND.getCode());
    response.setDesc(HttpResponseType.FOUND.getDesc());
    response.setBody(user1);

    Mockito
        .when(userService.getByUsername(Mockito.anyString()))
        .thenReturn(response);

    client
        .perform(get(REQUEST_MAPPING + "/username=" + user1.getUsername()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.FOUND.getDesc()))
        .andExpect(jsonPath("$.body.uuid").value(user1.getUuid()))
        .andExpect(jsonPath("$.body.role").value(user1.getRole().getLabel()))
        .andExpect(jsonPath("$.body.username").value(user1.getUsername()))
        .andExpect(jsonPath("$.body.password").value(user1.getPassword()));
  }

  @Test
  @DisplayName("GIVEN a wrong username WHEN getByUsername() THEN response should be NOT_FOUND")
  void getByUsernameTest_ShouldBeNotFound() throws Exception{
    ResponseDto<User> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);
    response.setHttpResponse(HttpResponseType.NOT_FOUND);
    response.setCode(HttpResponseType.NOT_FOUND.getCode());
    response.setDesc(HttpResponseType.NOT_FOUND.getDesc());

    Mockito
        .when(userService.getByUsername(Mockito.anyString()))
        .thenReturn(response);

    client
        .perform(get(REQUEST_MAPPING + "/username=anyString"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.httpRequest").value(HttpRequestType.GET.getLabel()))
        .andExpect(jsonPath("$.httpResponse").value(HttpResponseType.NOT_FOUND.getLabel()))
        .andExpect(jsonPath("$.code").value(HttpResponseType.NOT_FOUND.getCode()))
        .andExpect(jsonPath("$.desc").value(HttpResponseType.NOT_FOUND.getDesc()))
        .andExpect(jsonPath("$.body").isEmpty());
  }
}