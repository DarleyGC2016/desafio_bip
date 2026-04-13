package com.example.ejb.model;

import java.io.Serializable;
import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "beneficio")
public class Beneficio implements Serializable  {   

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Nome do benefício é obrigatório")
    @Size(max = 100, message = "Nome do benefício deve conter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome; 

    @NotNull(message = "Valor do benefício é obrigatório")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor; 

    @Size(max = 255, message = "Descrição do benefício deve conter no máximo 255 caracteres")
    @Column(length = 255)
    private String descricao;

    private boolean ativo = true;

    @Version
    private Long version = 0L;

    public Beneficio() {}

    public Beneficio(String nome, String descricao, BigDecimal valor) {
        setNome(nome);
        setDescricao(descricao);
        setValor(valor);
    }

    public Beneficio(Long id,String nome, String descricao, BigDecimal valor,
            Long version) {
        setNome(nome);
        setDescricao(descricao);            
        setValor(valor);
        setVersion(version);
    }

    
}
