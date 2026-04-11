package com.example.backend.parser;

import java.util.Objects;

import com.example.backend.DTO.BeneficioDTO;
import com.example.ejb.model.Beneficio;

public class BeneficioParse {

    public static Beneficio toEntity(BeneficioDTO dto) {
        Objects.requireNonNull(dto, "BeneficioDTO não pode ser nulo");
        return new Beneficio(dto.nome(), dto.descricao(), dto.valor());
    }

    public static Beneficio toEntity(BeneficioDTO dto, Long id) {
        var beneficio = new Beneficio();
        beneficio.setId(id);
        beneficio.setNome(dto.nome());
        beneficio.setDescricao(dto.descricao());
        beneficio.setValor(dto.valor());
        return beneficio;
    }
    public static Beneficio toEntity(BeneficioDTO dto, Long id, Long version) {
        var beneficio = new Beneficio();
        beneficio.setId(id);
        beneficio.setNome(dto.nome());
        beneficio.setDescricao(dto.descricao());
        beneficio.setValor(dto.valor());
        beneficio.setVersion(version);
        return beneficio;
    }

}
