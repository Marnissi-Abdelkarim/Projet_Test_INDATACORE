package com.example.indatacore_backend.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
	@NotNull(message = "This field cannot be null")
	@Size(min = 3, message = "This field must be longer than 3 characters")
	String name;

	@NotNull(message = "This field cannot be null")
	String category;
	
	@NotNull(message = "This field cannot be null")
	@DecimalMin(value = "0.0" ,inclusive = false,message = "The price must be greater than 0.")
	Double price;

	@NotNull(message = "This field cannot be null")
	Integer quantity;

}
