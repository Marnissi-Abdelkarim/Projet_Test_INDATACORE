package com.example.indatacore_backend.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

	@NotNull(message = "This field must be not null.")
	@Size(min = 3, message = "This field must be longer than 3 characters.")
	private String username;

	@NotNull(message = "This field must be not null.")
	@Email(message = "This field must be an email.")
	private String email;

	@NotNull(message = "This field must be not null.")
	@Size(min = 4, max = 12, message = "This field must be between 4 and 12.")
	private String password;

}
