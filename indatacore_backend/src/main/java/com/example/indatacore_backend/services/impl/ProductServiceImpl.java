package com.example.indatacore_backend.services.impl;


import com.example.indatacore_backend.entities.ProductEntity;
import com.example.indatacore_backend.repositories.ProductRepository;
import com.example.indatacore_backend.services.ProductService;
import com.example.indatacore_backend.shared.Utils;
import com.example.indatacore_backend.shared.dtos.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService, CommandLineRunner {

	@Autowired
	Utils util;
	@Autowired
	ProductRepository productRepository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	Random random;

	//load objects from csv file
	@Override
	public void run(String... args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("src/main/resources/products.csv"));
		String line = "";
		while ((line = br.readLine()) != null ){
			List<String> data = Arrays.asList(line.split(","));
			ProductDto productDto = ProductDto.builder()
					.name(data.get(0))
					.category(data.get(1))
					.price(Double.parseDouble(data.get(2)))
					.quantity(Integer.parseInt(data.get(3)))
					.build();

			ProductEntity productEntity = mapper.map(productDto, ProductEntity.class);
			productRepository.save(productEntity);
		}
	}

	// get all products
	@Override
	public List<ProductDto> getProducts() {

		List<ProductDto> productsDto = new ArrayList<ProductDto>();
		List<ProductEntity> productEntities = productRepository.findAll();

		for (ProductEntity productEntity : productEntities) {
			ProductDto productDto = mapper.map(productEntity, ProductDto.class);
			productsDto.add(productDto);
		}
		return productsDto;
	}


	// create product
	@Override
	public ProductDto createProduct(ProductDto productDto) {

		ProductEntity productEntity = mapper.map(productDto, ProductEntity.class);

		ProductEntity newProduct = productRepository.save(productEntity);

		ProductDto createdProductDto = mapper.map(newProduct, ProductDto.class);

		return createdProductDto;
	}

	// create Random Product
	@Override
	public ProductDto createRandomProduct(){

		ProductDto productDto = ProductDto.builder()
				.name(util.generateString(6))
				.category(util.generateString(5))
				.price(random.nextDouble()*100)
				.quantity(random.nextInt(100)+1)
				.build();

		ProductEntity productEntity = mapper.map(productDto, ProductEntity.class);
		ProductEntity newProduct = productRepository.save(productEntity);
		ProductDto createdProductDto = mapper.map(newProduct, ProductDto.class);
		return createdProductDto;

	}
}
