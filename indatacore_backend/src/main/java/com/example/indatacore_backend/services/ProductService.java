package com.example.indatacore_backend.services;



import com.example.indatacore_backend.shared.dtos.ProductDto;

import java.util.List;


public interface ProductService {

	List<ProductDto> getProducts();
	
	ProductDto createProduct(ProductDto productDto);

	ProductDto createRandomProduct();




}
