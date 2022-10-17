package com.euris.academy2022.concordia.businessLogics.services.trelloServices;

import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Member;

import java.util.List;

public interface UserDetailsManagerService {

  ResponseDto<List<MemberDto>> responseLoadByList(List<Member> memberList);
  ResponseDto<MemberDto> responsePostByModel(Member member);
  ResponseDto<MemberDto> responsePutByModel(Member member);
  ResponseDto<String> responseDeleteByUsername(String username);
}
