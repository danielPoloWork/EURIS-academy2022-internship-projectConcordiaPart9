package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.UserService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.users.UserPostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.users.UserPutRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.responses.ResponseDto;
import com.euris.academy2022.concordia.utils.enums.UserRole;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseDto<User> insert(@RequestBody UserPostRequest userDto) {
		return userService.insert(userDto.toModel());
	}

	@PutMapping
	public ResponseDto<User> update(@RequestBody UserPutRequest userDto) {
		return userService.update(userDto.toModel());
	}

	@DeleteMapping("/{uuid}")
	public ResponseDto<User> deleteByUuid(@PathVariable String uuid) {
		return userService.deleteByUuid(uuid);
	}

	@GetMapping
	public ResponseDto<List<User>> getAll() {
		return userService.getAll();
	}

	@GetMapping("/{uuid}")
	public ResponseDto<User> getByUuid(@PathVariable String uuid) {
		return userService.getByUuid(uuid);
	}

	@GetMapping("/role={role}")
	public ResponseDto<List<User>> getByRole(@PathVariable UserRole role) {
		return userService.getByRole(role);
	}

	@GetMapping("/username={username}")
	public ResponseDto<User> getByUsername(@PathVariable String username) {
		return userService.getByUsername(username);
	}
}