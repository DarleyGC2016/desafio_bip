package com.example.backend.mock;

import java.math.BigDecimal;

import com.example.backend.repository.projection.BeneficioConsultaProjection;

public record MockBeneficio(Long id, String nome, String descricao, BigDecimal valor ) implements BeneficioConsultaProjection {

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    @Override
    public BigDecimal getValor() {
        return valor;
    }

    
}
