package com.example.backend.parser;

import java.util.Objects;

import com.example.backend.DTO.BeneficioDTO;
import com.example.ejb.model.Beneficio;

public class BeneficioParse {

    public static Beneficio toEntity(BeneficioDTO dto) {
        Objects.requireNonNull(dto, "BeneficioDTO não pode ser nulo");
        return new Beneficio(dto.nome(), dto.descricao(), dto.valor());
    }

    public static Beneficio toEntity(BeneficioDTO dto, Beneficio beneficioExistente) {
        var beneficio = new Beneficio();
        beneficio.setId(beneficioExistente.getId());
        beneficio.setNome(dto.nome());
        beneficio.setDescricao(dto.descricao());
        beneficio.setValor(dto.valor());
        beneficio.setVersion(beneficioExistente.getVersion());
        return beneficio;
    }

}
