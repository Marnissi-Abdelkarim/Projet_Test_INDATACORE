package com.example.indatacore_backend.shared.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class UserDto implements Serializable {
	private static final long serialVersionUID = -5088503118315914910L;
	private long id;
	private String userId;
	private String username;
	private String email;
	private String password;
	private String encryptedPassword;
	private String emailVerificationToken;
	private Boolean emailVerificationStatus = false;
	private Collection<RoleDto> roles = new ArrayList<>();
	
	public UserDto() {}
	public UserDto(String username, String email, String password) {
		this.username=username;
		this.email=email;
		this.password=password;
	}

}
