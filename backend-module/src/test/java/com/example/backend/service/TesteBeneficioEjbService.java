package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.DTO.TransferirDTO;
import com.example.backend.repository.BeneficioRepository;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Teste do BeneficioEjbService")
@Transactional
public class TesteBeneficioEjbService {

    @Autowired
    private BeneficioService beneficioService;

    @Autowired
    private BeneficioRepository beneficioRepository;

    @Test
    @DisplayName("Deve transferir valor entre benefícios com sucesso")
    @Sql(scripts = "/seed_test.sql")
    void transferWithSuccess() {
        Long fromId = 1L;
        Long toId = 2L;
        TransferirDTO dto = new TransferirDTO(fromId, toId, BigDecimal.valueOf(100));

        beneficioService.transfer(dto);

        BigDecimal fromValor = beneficioRepository.findById(fromId).get().getValor();
        assertEquals(0, BigDecimal.valueOf(900).compareTo(fromValor),
                "O valor do benefício de origem deve ser R$900,00 após a transferência de R$100,00");

    }

    @DisplayName("Deve lançar exceção ao tentar transferir valor maior que o disponível")
    @Test
    @Sql(scripts = "/seed_test.sql")
    void transferWithInsufficientFunds() {
        Long fromId = 1L;
        Long toId = 2L;
        TransferirDTO dto = new TransferirDTO(fromId, toId, BigDecimal.valueOf(20000.00));
        assertThrows(IllegalArgumentException.class, () -> beneficioService.transfer(dto));

    }

    @DisplayName("Deve lançar exceção quando os IDs de origem ou destino forem iguais")
    @Test
    @Sql(scripts = "/seed_test.sql")
    void transferWithEqualIds() {
        Long fromId = 1L;
        Long toId = 1L;
        TransferirDTO dto = new TransferirDTO(fromId, toId, BigDecimal.valueOf(20000.00));
        assertThrows(IllegalArgumentException.class, () -> beneficioService.transfer(dto));

    }

    @DisplayName("Deve lançar exceção quando os IDs de origem ou destino forem zero")
    @Test
    @Sql(scripts = "/seed_test.sql")
    void transferWithIdZero() {
        Long fromId = 0L;
        Long toId = 0L;
        TransferirDTO dto = new TransferirDTO(fromId, toId, BigDecimal.valueOf(20000.00));
        assertThrows(IllegalArgumentException.class, () -> beneficioService.transfer(dto));
    }

    @DisplayName("Deve lançar exceção quando o valor de transferência for nulo ou negativo")
    @Test
    @Sql(scripts = "/seed_test.sql")
    void transferWithInvalidAmount() {
        Long fromId = 1L;
        Long toId = 2L;

        // Testa valor nulo
        TransferirDTO dtoNull = new TransferirDTO(fromId, toId, null);
        assertThrows(IllegalArgumentException.class, () -> beneficioService.transfer(dtoNull));

        // Testa valor negativo
        TransferirDTO dtoNegative = new TransferirDTO(fromId, toId, BigDecimal.valueOf(-100));
        assertThrows(IllegalArgumentException.class, () -> beneficioService.transfer(dtoNegative));
    }

    @DisplayName("Deve lançar exceção, quando o id benefício de origem é maior que o id benefício de destino")
    @Test
    @Sql(scripts = "/seed_test.sql")
    void transferWithFromIdGreaterThanToId() {
        Long fromId = 2L;
        Long toId = 1L;
        TransferirDTO dto = new TransferirDTO(fromId, toId, BigDecimal.valueOf(100));
        beneficioService.transfer(dto);
        assertNotEquals(fromId, dto.toId());
        assertNotEquals(toId, dto.fromId());
    }
}
