package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ejb.service.BeneficioEjbService;

@Configuration
public class ConfigEjb {
    
    @Bean
    public BeneficioEjbService ejbService() {
        return new BeneficioEjbService(); // Classe que está dentro do ejb-module
    }
}
