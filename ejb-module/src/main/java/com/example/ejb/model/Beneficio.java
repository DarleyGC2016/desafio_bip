package com.example.ejb.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Beneficio {
    private Long id;
    private String nome; 
    private BigDecimal valor; 
    private String descricao;
    private Boolean ativo;
    private Long version;
}
