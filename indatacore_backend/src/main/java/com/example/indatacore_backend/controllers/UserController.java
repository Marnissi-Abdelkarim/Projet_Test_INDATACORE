package com.example.indatacore_backend.controllers;

import com.example.indatacore_backend.exception.UserException;
import com.example.indatacore_backend.requests.UserRequest;
import com.example.indatacore_backend.responses.UserErrorMessages;
import com.example.indatacore_backend.responses.UserResponse;
import com.example.indatacore_backend.services.UserService;
import com.example.indatacore_backend.shared.dtos.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
		UserDto userDto = userService.getUserByUserId(id);
		ModelMapper modelMapper = new ModelMapper();
		UserResponse userResponse =  modelMapper.map(userDto, UserResponse.class);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	//get all users by pages
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<UserResponse> getAllUsers(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "limit", defaultValue = "15") int limit,
			@RequestParam(value = "search", defaultValue = "") String search,Principal principal) {
		List<UserResponse> userResponses = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit, search);
		for (UserDto userDto : users) {
			ModelMapper modelMapper = new ModelMapper();
			UserResponse userResponse = modelMapper.map(userDto, UserResponse.class);
			userResponses.add(userResponse);
		}
		return userResponses;
	}

	//create user
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {
		System.out.println(userRequest);
		if (userRequest.getUsername().isEmpty()) {
			throw new UserException(UserErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		}
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userRequest, UserDto.class);
		UserDto createUser = userService.createUser(userDto);
		UserResponse userResponse = modelMapper.map(createUser, UserResponse.class);
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}

	//create admin
		@PostMapping(path = "/admin" ,consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, produces = {
				MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
		public ResponseEntity<UserResponse> createAdmin(@RequestBody @Valid UserRequest userRequest) throws Exception {
			System.out.println(userRequest);
			if (userRequest.getUsername().isEmpty()) {
				throw new UserException(UserErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
			}
			ModelMapper modelMapper = new ModelMapper();
			UserDto userDto = modelMapper.map(userRequest, UserDto.class);
			UserDto createUser = userService.createAdmin(userDto);
			UserResponse userResponse = modelMapper.map(createUser, UserResponse.class);
			return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
		}
	//update user 
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody @Valid UserRequest userRequest) {
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userRequest, UserDto.class);
		UserDto updateUser = userService.updateUser(id, userDto);
		UserResponse userResponse = modelMapper.map(updateUser,UserResponse.class);
		return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
	}
	//delete user
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
