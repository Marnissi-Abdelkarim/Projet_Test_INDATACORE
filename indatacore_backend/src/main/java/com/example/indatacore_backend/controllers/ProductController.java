package com.example.indatacore_backend.controllers;


import com.example.indatacore_backend.requests.ProductRequest;
import com.example.indatacore_backend.responses.ProductResponse;
import com.example.indatacore_backend.services.ProductService;
import com.example.indatacore_backend.shared.dtos.ProductDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ModelMapper modelMapper;

    //CREATE PRODUCT
    @PostMapping(path ="/add",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {

        ProductDto productDto = modelMapper.map(productRequest, ProductDto.class);
        ProductDto createdProduct = productService.createProduct(productDto);
        ProductResponse productResponse = modelMapper.map(createdProduct, ProductResponse.class);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    //CREATE RANDOM PRODUCT
    @PostMapping(path = "/addRandom",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ProductResponse> createRandomProduct() {

        ProductDto createdProduct = productService.createRandomProduct();
        ProductResponse productResponse = modelMapper.map(createdProduct, ProductResponse.class);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }



    //GET ALL PRODUCTS
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productsResponses = new ArrayList<>();

        List<ProductDto> products = productService.getProducts();
        for (ProductDto productDto : products) {
            ProductResponse productResponse = modelMapper.map(productDto, ProductResponse.class);
            productsResponses.add(productResponse);
        }

        return productsResponses;
    }


}
