package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.UserDetailsManagerService;
import com.euris.academy2022.concordia.businessLogics.services.UserService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userDetailManager")
public class UserDetailsManagerController {

	private final UserDetailsManagerService userDetailsManagerService;
	private final UserService userService;

	public UserDetailsManagerController(UserDetailsManagerService userDetailsManagerService, UserService userService) {
		this.userDetailsManagerService = userDetailsManagerService;
		this.userService = userService;
	}

	@GetMapping("/load")
	public List<User> loadUserList() {
		List<User> response = userService.getAll();
		return userDetailsManagerService.getUserListByUserList(response);
	}
}

