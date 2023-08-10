package com.example.indatacore_backend.shared.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProductDto implements Serializable{

	private long id;
	private String name;
	private String category;
	private double price;
	private int quantity;

}
