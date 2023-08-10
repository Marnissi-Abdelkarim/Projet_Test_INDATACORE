package com.example.indatacore_backend.requests;

import lombok.Data;
@Data
public class UserLoginRequest {
	String email;
	String password;
}
