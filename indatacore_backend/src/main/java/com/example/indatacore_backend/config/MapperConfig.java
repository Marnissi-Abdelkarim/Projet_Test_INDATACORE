package com.example.indatacore_backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {

        ModelMapper model = new ModelMapper();
        model.getConfiguration().setAmbiguityIgnored(true);
        return model;
    }

}