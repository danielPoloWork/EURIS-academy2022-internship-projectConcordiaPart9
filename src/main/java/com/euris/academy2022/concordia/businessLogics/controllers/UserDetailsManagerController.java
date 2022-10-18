package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.MemberService;
import com.euris.academy2022.concordia.businessLogics.services.UserDetailsManagerService;
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
		List<Member> response = memberService.getAllMemberDto().getBody()
				.stream()
				.map(MemberDto::toModel)
				.toList();
		return userDetailsManagerService.responseLoadByList(response);
	}

}

