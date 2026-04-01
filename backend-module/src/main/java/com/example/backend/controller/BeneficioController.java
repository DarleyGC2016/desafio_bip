package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.backend.service.BenicifioService;

import java.util.*;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    @Autowired
    private BenicifioService benicifioService;

    @GetMapping
    public List<String> list() {
        benicifioService.hello(); // Exemplo de uso do serviço
        return Arrays.asList("Beneficio A", "Beneficio B", "Beneficio C");
    }
}

