package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.trelloServices.UserDetailsManagerService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.MemberDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;

import java.util.List;

import com.euris.academy2022.concordia.dataPersistences.models.Member;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userDetailManager")
public class UserDetailsManagerController {

	private final UserDetailsManagerService userDetailsManagerService;
	private final MemberService memberService;

	public UserDetailsManagerController(UserDetailsManagerService userDetailsManagerService, MemberService memberService) {
		this.userDetailsManagerService = userDetailsManagerService;
		this.memberService = memberService;
	}

	@GetMapping
	public ResponseDto<List<MemberDto>> fetch() {
		ResponseDto<List<Member>> response = memberService.getAllMember();
		return userDetailsManagerService.responseLoadByList(response.getBody());
	}

//	@PostMapping
//	public ResponseDto<MemberDto> postUserDetailManager(@PathVariable String uuidMember) {
//		ResponseDto<Member> response = memberService.getMemberByUuid(uuidMember);
//		return userDetailsManagerService.responsePostByModel(response.getBody());
//	}
//
//	@PutMapping
//	public ResponseDto<MemberDto> putUserDetailManager(@PathVariable String uuidMember) {
//		ResponseDto<Member> response = memberService.getMemberByUuid(uuidMember);
//		return userDetailsManagerService.responsePutByModel(response.getBody());
//	}
//
//	@DeleteMapping
//	public ResponseDto<String> deleteUserDetailManager(@PathVariable String username) {
//		return userDetailsManagerService.responseDeleteByUsername(username);
//	}

}

