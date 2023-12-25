package com.techsavvy.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PermissionConfig {

    @Bean
    List<String> permissions() {
        return List.of("CREATE_USER","UPDATE_USER","DELETE_USER");
    }
}
