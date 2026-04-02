package com.example.ejb.model;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "beneficio")
public class Beneficio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome; 

    @Column
    private BigDecimal valor; 

    @Column
    private String descricao;

    @Column
    private Boolean ativo;

    @Column
    @Version
    private Long version;
    
    @Override
    public String toString() {
        return "Beneficio [id=" + id + ", nome=" + nome + ", valor=" + valor + ", descricao=" + descricao + ", ativo="
                + ativo + ", version=" + version + "]";
    }

    
}
