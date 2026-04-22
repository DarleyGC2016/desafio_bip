package com.example.backend.controller;

import org.springframework.data.domain.Page;
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

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class BeneficioController {

    private final BeneficioService beneficioService;

    @Operation(summary = "Lista benefícios disponíveis", description = "listar benefícios disponíveis organizados por páginas")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Benefícios listados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Não foi possível listar os benefícios!"),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado nenhum benefício no servidor!")
    })
    @GetMapping("/beneficios")
    public ResponseEntity<Page<BeneficioConsultaProjection>> listBeneficiosWithoutVersion(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(beneficioService.findByBeneficiosWithoutVersion(page, size));
    }

    @Operation(summary = "Realiza a transferência de benefícios entre contas", description = "Transferir dinheiro entre benefícioss, especificando o valor e os IDs dos benefícios de origem e destino")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na transferência de benefícios!"),
            @ApiResponse(responseCode = "404", description = "Benefício de origem ou destino não encontrado para transferência!")
    })
    @PutMapping("/transferir")
    public ResponseEntity<String> transfer(@RequestBody TransferirDTO transferirDTO) {
        beneficioService.transfer(transferirDTO);
        return ResponseEntity.ok("Transferência de R$" + transferirDTO.amount() + " realizada com sucesso");
    }

    @Operation(summary = "Cria um novo benefício", description = "Endpoint para cadastrar benefício")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado um novo Benefício com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na criação de um novo benefício!")
    })
    @PostMapping("/beneficio/novo")
    public ResponseEntity<String> saveBeneficio(@Valid @RequestBody BeneficioDTO beneficioDTO) {
        beneficioService.save(beneficioDTO);
        return ResponseEntity.ok("Beneficio salvo com sucesso!");
    }

    @Operation(summary = "Atualiza um benefício", description = "Atualizar um benefício existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Benefício atualizado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na atualização do benefício!"),
            @ApiResponse(responseCode = "404", description = "Benefício não encontrado para atualização!")
    })
    @PutMapping("/beneficio/{id}")
    public ResponseEntity<String> updateBeneficio(@PathVariable Long id,
            @Valid @RequestBody BeneficioDTO beneficioDTO) {
        beneficioService.update(id, beneficioDTO);
        return ResponseEntity.ok("Benefício atualizado com sucesso!");
    }

    @Operation(summary = "Exclui um benefício", description = "Excluir um benefício")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Benefício excluído com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na exclusão do benefício!"),
            @ApiResponse(responseCode = "404", description = "Benefício não encontrado para exclusão!")
    })
    @DeleteMapping("/beneficio/delete/{id}")
    public ResponseEntity<String> deleteBeneficio(@PathVariable Long id) {
        beneficioService.delete(id);
        return ResponseEntity.ok("Benefício excluído com sucesso!");
    }

    @Operation(summary = "Detalha um benefício", description = "Detalhar um unico benefício")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Benefício detalhado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro na consulta do benefício!"),
            @ApiResponse(responseCode = "404", description = "Benefício não encontrado!")
    })
    @GetMapping("/beneficio/{id}")
    public ResponseEntity<Beneficio> detailBeneficio(@PathVariable Long id) {
        Beneficio beneficio = beneficioService.detailBeneficio(id);
        return ResponseEntity.ok(beneficio);
    }

}
