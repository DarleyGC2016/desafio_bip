package com.example.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.backend.service.BenicifioService;

import io.swagger.v3.oas.annotations.Operation;

import java.math.BigDecimal;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    @Autowired
    private BenicifioService benicifioService;

    @Operation(summary = "Realiza a transferência de benefícios entre contas")
    @PostMapping("/transferir")
    public String transfer(@RequestParam Long fromId, @RequestParam Long toId, @RequestParam BigDecimal amount) {
        benicifioService.transfer(fromId, toId, amount);
        return "Transferência realizada com sucesso";
    }
        
}

