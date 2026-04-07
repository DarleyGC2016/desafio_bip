package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.DTO.BeneficioDTO;
import com.example.backend.DTO.TransferirDTO;
import com.example.backend.repository.projection.BeneficioConsultaProjection;
import com.example.backend.service.BeneficioService;
import com.example.ejb.model.Beneficio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class BeneficioController {

    private final BeneficioService beneficioService;

    @Operation(summary = "Lista benefícios disponíveis", description = "Endpoint para listar benefícios disponíveis")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @GetMapping("/beneficios")
    public ResponseEntity<List<BeneficioConsultaProjection>> listBeneficiosWithoutVersion(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(beneficioService.findByBeneficiosWithoutVersion(page, size));
    }

    @Operation(summary = "Realiza a transferência de benefícios entre contas", description = "Endpoint para transferir benefícios entre contas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PutMapping("/transferir")
    public ResponseEntity<String> transfer(@RequestBody TransferirDTO transferirDTO) {
        beneficioService.transfer(transferirDTO);
        return ResponseEntity.ok("Transferência de R$" + transferirDTO.amount() + " realizada com sucesso");
    }

    @Operation(summary = "Cria um novo usuário", description = "Endpoint para cadastrar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PostMapping("/novo")
    public ResponseEntity<String> saveBeneficio(@Valid @RequestBody BeneficioDTO beneficioDTO) {
        beneficioService.save(beneficioDTO);
        return ResponseEntity.ok("Beneficio salvo com sucesso!");
    }

    @Operation(summary = "Cria um novo usuário", description = "Endpoint para cadastrar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @PutMapping("/beneficio/{id}")
    public ResponseEntity<Void> updateBeneficio(@PathVariable Long id,
            @Valid @RequestBody BeneficioDTO beneficioDTO) {
        beneficioService.update(id, beneficioDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cria um novo usuário", description = "Endpoint para cadastrar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @DeleteMapping("/beneficio/delete/{id}")
    public ResponseEntity<Void> deleteBeneficio(@PathVariable Long id) {
        beneficioService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cria um novo usuário", description = "Endpoint para cadastrar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
    })
    @GetMapping("/beneficio/{id}")
    public ResponseEntity<Beneficio> detailBeneficio(@PathVariable Long id) {
        Beneficio beneficio = beneficioService.detailBeneficio(id);
        return ResponseEntity.ok(beneficio);
    }

}
