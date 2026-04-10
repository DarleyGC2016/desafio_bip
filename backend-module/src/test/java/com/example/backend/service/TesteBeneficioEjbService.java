package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.DTO.TransferirDTO;
import com.example.backend.repository.BeneficioRepository;
import com.example.ejb.service.BeneficioEjbService;

@DataJpaTest
@ActiveProfiles("test")
@Import(BeneficioEjbService.class)
@DisplayName("Teste do BeneficioEjbService")
@Transactional
public class TesteBeneficioEjbService {

    // @Autowired
    // private BeneficioService beneficioService;

    @Autowired
    private BeneficioEjbService beneficioEjbService;

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Test
    @DisplayName("Deve transferir valor entre benefícios com sucesso")
    @Sql(scripts = "/seed.sql")
    void transferWithSuccess() {
        Long fromId = 1L;
        Long toId = 2L;
        TransferirDTO dto = new TransferirDTO(fromId, toId, BigDecimal.valueOf(100));

        beneficioEjbService.transfer(dto.fromId(), dto.toId(), dto.amount());

        BigDecimal fromValor = beneficioRepository.findById(fromId).get().getValor();   
        assertEquals(0, BigDecimal.valueOf(900).compareTo(fromValor), "O valor do benefício de origem deve ser R$900,00 após a transferência de R$100,00");

    }
}
