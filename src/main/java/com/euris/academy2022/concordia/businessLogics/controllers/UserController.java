package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.UserService;
//import com.euris.academy2022.concordia.businessLogics.services.UserDetailsManagerService;
import com.euris.academy2022.concordia.dataPersistences.dataModels.User;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.users.UserPostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.users.UserPutRequest;

import com.euris.academy2022.concordia.utils.enums.UserRole;
import java.util.List;
import java.util.Optional;

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
//	private final UserDetailsManagerService userDetailsManagerService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

//	public UserController(UserService userService,	UserDetailsManagerService userDetailsManagerService) {
//		this.userService = userService;
//		this.userDetailsManagerService = userDetailsManagerService;
//	}

	@PostMapping
	public Optional<User> insert(@RequestBody UserPostRequest userDto) {
		Optional<User> responseUserService = userService.insert(userDto.toModel());
//		if (responseUserService.isPresent()) {
//			Optional<User> responseUDMS = userDetailsManagerService.insert(responseUserService.get());
//		}
		return responseUserService;
	}

	@PutMapping
	public Optional<User> update(@RequestBody UserPutRequest userDto) {
		Optional<User> responseUserService = userService.update(userDto.toModel());
//		if (responseUserService.isPresent()) {
//			Optional<User> responseUDMS = userDetailsManagerService.update(responseUserService.get());
//		}
		return responseUserService;
	}

	@DeleteMapping("/{uuid}")
	public Boolean deleteByUuid(@PathVariable String uuid) {
		Boolean responseUserService = Boolean.FALSE;
		if (!uuid.isEmpty()) {
			Optional<User> uuidFound = getByUuid(uuid);
			if (uuidFound.isPresent()) {
				responseUserService = userService.deleteByUuid(uuid);
//				if (responseUserService) {
//					Boolean responseUDMS = userDetailsManagerService.deleteByUsername(uuidFound.get().getUsername());
//				}
			}
		}
		return responseUserService;
	}

	@GetMapping
	public List<User> getAll() {
		return userService.getAll();
	}

	@GetMapping("/{uuid}")
	public Optional<User> getByUuid(@PathVariable String uuid) {
		return userService.getByUuid(uuid);
	}

	@GetMapping("/role={role}")
	public List<User> getByRole(@PathVariable UserRole role) {
		return userService.getByRole(role);
	}

	@GetMapping("/username={username}")
	public Optional<User> getByUsername(@PathVariable String username) {
		return userService.getByUsername(username);
	}
}