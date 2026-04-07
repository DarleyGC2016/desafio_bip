package com.example.backend.repository.projection;

import java.math.BigDecimal;

public interface BeneficioConsultaProjection {
    Long getId();
    String getNome();
    String getDescricao();
    BigDecimal getValor();
}
