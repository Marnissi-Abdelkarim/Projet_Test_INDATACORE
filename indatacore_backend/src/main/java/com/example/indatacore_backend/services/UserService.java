package com.example.indatacore_backend.services;


import com.example.indatacore_backend.entities.UserEntity;
import com.example.indatacore_backend.shared.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
	UserEntity findUserByEmail(String email);
	UserDto createUser(UserDto userDto);
	UserDto createAdmin(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String id,UserDto userDto);
	void deleteUser(String id);
	List<UserDto> getUsers(int page, int limit,String search);
}
