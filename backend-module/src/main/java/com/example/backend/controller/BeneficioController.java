package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.DTO.BeneficioDTO;
import com.example.backend.DTO.TransferirDTO;
import com.example.backend.service.BenicifioService;
import com.example.ejb.model.Beneficio;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class BeneficioController {

    
    private final  BenicifioService benicifioService;

    public BeneficioController(BenicifioService benicifioService) {
        this.benicifioService = benicifioService;
    }


    @Operation(summary = "Lista benefícios disponíveis")
    @GetMapping("/beneficios")
    public ResponseEntity<List<Beneficio>> listBeneficios() {
        return ResponseEntity.ok(benicifioService.listBeneficios());
    }
    

    @Operation(summary = "Realiza a transferência de benefícios entre contas")
    @PutMapping("/transferir")
    public ResponseEntity<Void> transfer(@RequestBody TransferirDTO transferirDTO) {
        benicifioService.transfer(transferirDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/novo")
    public ResponseEntity<Beneficio> saveBeneficio(@Valid @RequestBody BeneficioDTO beneficioDTO) {
                
        return ResponseEntity.ok(benicifioService.save(beneficioDTO));
    }
    
        
}

