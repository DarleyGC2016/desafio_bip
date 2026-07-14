package com.example.backend.DTO;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;

public record BeneficioDTO(@NotNull String nome, @Length(max = 255) String descricao, @NotNull BigDecimal valor) {
    public BeneficioDTO {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do benefício deve ser maior que zero!");
        }
        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome do benefício não pode ser vazio!");
        }
    }   
}