package com.example.backend.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.backend.DTO.BeneficioDTO;
import com.example.backend.parser.BeneficioParse;
import com.example.ejb.model.Beneficio;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para BeneficioParser")
public class TestBeneficioParser {

    @Test
    @DisplayName("Deve converter DTO para novo Benefício")
    void toEntityNovoBeneficio() {
        
       BeneficioDTO dto = new BeneficioDTO("Vale Refeição", "Descrição A", BigDecimal.valueOf(100));

        Beneficio resultado = BeneficioParse.toEntity(dto);

        assertNotNull(resultado);
        assertEquals(dto.nome(), resultado.getNome());
        assertEquals(dto.descricao(), resultado.getDescricao());
        assertEquals(dto.valor(), resultado.getValor());
    }

    @Test
    @DisplayName("Deve atualizar Entidade existente mantendo ID e Versão")
    void toEntityUpdate() {
        
        BeneficioDTO dto = new BeneficioDTO("Nome Atualizado", "Nova Desc", BigDecimal.valueOf(200));
        Beneficio existente = new Beneficio();
        existente.setId(10L);
        existente.setVersion(2L);

        Beneficio resultado = BeneficioParse.toEntity(dto, existente.getId(), existente.getVersion());

        
        assertEquals(10L, resultado.getId(), "O ID deve ser mantido");
        assertEquals(2L, resultado.getVersion(), "A versão deve ser mantida");
        assertEquals("Nome Atualizado", resultado.getNome());
        assertEquals(BigDecimal.valueOf(200), resultado.getValor());
    }


}
