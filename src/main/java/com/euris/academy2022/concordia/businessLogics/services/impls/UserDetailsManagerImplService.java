package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.UserDetailsManagerService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Member;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsManagerImplService implements UserDetailsManagerService {

  private final UserDetailsManager userDetailsManager;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserDetailsManagerImplService(UserDetailsManager userDetailsManager, BCryptPasswordEncoder passwordEncoder) {
    this.userDetailsManager = userDetailsManager;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public ResponseDto<List<MemberDto>> responseLoadByList(List<Member> memberList) {
    ResponseDto<List<MemberDto>> response = new ResponseDto<>();

    response.setHttpRequest(HttpRequestType.GET);

    if (memberList.isEmpty()) {
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {
      memberList.forEach(member -> {
        if (!userDetailsManager.userExists(member.getUsername())) {
          userDetailsManager.createUser(User
              .withUsername(member.getUsername())
              .password(passwordEncoder.encode(member.getPassword()))
              .roles(member.getRole().toString())
              .build());
        }
      });
      response.setHttpResponse(HttpResponseType.FETCHED);
      response.setCode(HttpResponseType.FETCHED.getCode());
      response.setDesc(HttpResponseType.FETCHED.getDesc());
      response.setBody(memberList.stream().map(Member::toDto).toList());
    }

    return response;
  }

  @Override
  public ResponseDto<MemberDto> responsePostByModel(Member member) {
    Optional<Member> memberOptional = Optional.ofNullable(member);
    ResponseDto<MemberDto> response = new ResponseDto<>();

    if (memberOptional.isEmpty()) {
      response.setHttpRequest(HttpRequestType.POST);
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {
        if (!userDetailsManager.userExists(memberOptional.get().getUsername())) {
          userDetailsManager.createUser(User
              .withUsername(memberOptional.get().getUsername())
              .password(passwordEncoder.encode(memberOptional.get().getPassword()))
              .roles(memberOptional.get().getRole().toString())
              .build());
        }
      response.setHttpRequest(HttpRequestType.POST);
      response.setHttpResponse(HttpResponseType.CREATED);
      response.setCode(HttpResponseType.CREATED.getCode());
      response.setDesc(HttpResponseType.CREATED.getDesc());
      response.setBody(memberOptional.get().toDto());
    }

    return response;
  }

  @Override
  public ResponseDto<MemberDto> responsePutByModel(Member member) {
    Optional<Member> memberOptional = Optional.ofNullable(member);
    ResponseDto<MemberDto> response = new ResponseDto<>();

    if (memberOptional.isEmpty()) {
      response.setHttpRequest(HttpRequestType.PUT);
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {
      if (userDetailsManager.userExists(memberOptional.get().getUsername())) {
        userDetailsManager.updateUser(User
            .withUsername(memberOptional.get().getUsername())
            .password(passwordEncoder.encode(memberOptional.get().getPassword()))
            .roles(memberOptional.get().getRole().toString())
            .build());
      }
      response.setHttpRequest(HttpRequestType.PUT);
      response.setHttpResponse(HttpResponseType.UPDATED);
      response.setCode(HttpResponseType.UPDATED.getCode());
      response.setDesc(HttpResponseType.UPDATED.getDesc());
      response.setBody(memberOptional.get().toDto());
    }
    return response;
  }

  @Override
  public ResponseDto<String> responseDeleteByUsername(String username) {
    ResponseDto<String> response = new ResponseDto<>();

    if (username.isEmpty()) {
      response.setHttpRequest(HttpRequestType.DELETE);
      response.setHttpResponse(HttpResponseType.NOT_FOUND);
      response.setCode(HttpResponseType.NOT_FOUND.getCode());
      response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
    } else {
      if (userDetailsManager.userExists(username)) {
        userDetailsManager.deleteUser(username);
      }
      response.setHttpRequest(HttpRequestType.DELETE);
      response.setHttpResponse(HttpResponseType.DELETED);
      response.setCode(HttpResponseType.DELETED.getCode());
      response.setDesc(HttpResponseType.DELETED.getDesc());
      response.setBody(username);
    }
    return response;
  }
}