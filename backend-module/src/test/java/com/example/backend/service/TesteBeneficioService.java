package com.example.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.example.backend.DTO.BeneficioDTO;
import com.example.backend.DTO.TransferirDTO;
import com.example.backend.parser.BeneficioParse;
import com.example.backend.repository.BeneficioRepository;
import com.example.backend.repository.projection.BeneficioConsultaProjection;
import com.example.ejb.model.Beneficio;
import com.example.ejb.service.BeneficioEjbService;

@DisplayName("Teste unitário do BeneficioService")
@ExtendWith(MockitoExtension.class)
public class TesteBeneficioService {

    @Mock
    private BeneficioRepository beneficioRepository;

    @Mock
    private BeneficioEjbService serviceEjb;

    @InjectMocks
    private BeneficioService beneficioService;

    @SuppressWarnings("null")
    @Test
    @DisplayName("Deve salvar um benefício com sucesso")
    void saveWithSuccess() {
        var beneficioDto = new BeneficioDTO(
                "Benefício A",
                "Benefício A",
                BigDecimal.valueOf(100));

        when(beneficioRepository.existsByNomeIgnoreCase(anyString())).thenReturn(false);
        Beneficio beneficio = BeneficioParse.toEntity(beneficioDto);
        if (beneficio == null) {
            throw new IllegalArgumentException(
                    "Erro ao converter os dados do Benefício DTO para entidade! Verifique os dados fornecidos.");
        }
        beneficioService.save(beneficioDto);
        verify(beneficioRepository).save(any(Beneficio.class));
    }

    @Test
    @DisplayName("Deve verficar se existe um benefício com nome duplicado")
    void saveWithDuplicateNome() {
        var beneficioDto = new BeneficioDTO(
                "Benefício A",
                "Benefício A",
                BigDecimal.valueOf(100));

        Beneficio beneficio = BeneficioParse.toEntity(beneficioDto);
        if (beneficio == null) {
            throw new IllegalArgumentException(
                    "Erro ao converter o Benefício DTO para entidade! Verifique os dados fornecidos.");
        }
        when(beneficioRepository.existsByNomeIgnoreCase(beneficioDto.nome())).thenReturn(true);
        var exception = assertThrows(IllegalArgumentException.class, () -> beneficioService.save(beneficioDto));

        assertEquals("O Benefício 'Benefício A' já existe! ", exception.getMessage());
        verify(beneficioRepository, never()).saveAndFlush(beneficio);
    }

    @SuppressWarnings("null")
    @DisplayName("Deve atualizar um benefício com sucesso")
    @Test
    void updateWithSuccess() {
        Long id = 1L;
        Long version = 0L;
        BeneficioDTO dto = new BeneficioDTO("Cartão Alimentação", "Cartão para uso em estabelecimentos credenciados",
                BigDecimal.valueOf(100.90));
        Beneficio beneficioExistente = BeneficioParse.toEntity(dto, id, version);
        if (beneficioExistente == null) {
            throw new IllegalArgumentException(
                    "Erro ao converter o Benefício DTO para entidade! Verifique os dados fornecidos.");
        }
        when(beneficioRepository.findById(id)).thenReturn(Optional.of(beneficioExistente));
        when(beneficioRepository.existsByNomeIgnoreCase(beneficioExistente.getNome())).thenReturn(false);
        when(beneficioRepository.saveAndFlush(any(Beneficio.class))).thenReturn(beneficioExistente);

        beneficioService.update(id, dto);

        verify(beneficioRepository, times(1)).saveAndFlush(any(Beneficio.class));
    }

    @DisplayName("Deve atualizar se o id for do benefício que será atualizado")
    @Test
    void updateBeneficioComID() {
        Long id = 9L;
        var beneficioDto = new BeneficioDTO(
                "Benefício A",
                "Benefício A",
                BigDecimal.valueOf(100));

        when(beneficioRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class, () -> beneficioService.update(id, beneficioDto));
        assertEquals("Benefício não encontrado!", exception.getMessage());

        verify(beneficioRepository, never()).existsByNomeIgnoreCase(anyString());

    }

    @DisplayName("Excluir um benefício com sucesso")
    @Test
    void deleteWithSuccess() {
        Long id = 1L;
        Beneficio beneficio = new Beneficio();
        beneficio.setId(id);

        when(beneficioRepository.findById(id)).thenReturn(Optional.of(beneficio));

        beneficioService.delete(id);
        verify(beneficioRepository, times(1)).delete(beneficio);
    }

    @DisplayName("Deve lançar exceção ao tentar excluir um benefício que não existe")
    @Test
    void deleteBeneficioNaoExiste() {
        Long id = 1L;

        when(beneficioRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class, () -> beneficioService.delete(id));
        assertEquals("Benefício não encontrado!", exception.getMessage());

        verifyNoMoreInteractions(beneficioRepository);
    }

    @DisplayName("Deve retornar os detalhes de um benefício com sucesso")
    @Test
    void detailBeneficioWithSuccess() {
        Long id = 1L;
        Beneficio beneficio = new Beneficio();
        beneficio.setId(id);
        beneficio.setNome("Benefício A");
        beneficio.setDescricao("Benefício A");
        beneficio.setValor(BigDecimal.valueOf(100));

        when(beneficioRepository.findById(id)).thenReturn(Optional.of(beneficio));

        var result = beneficioService.detailBeneficio(id);
        assertEquals(beneficio, result);
    }

    @DisplayName("Deve lançar exceção ao tentar obter detalhes de um benefício que não existe")
    @Test
    void detailBeneficioNotExist() {
        Long id = 1L;

        when(beneficioRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(IllegalArgumentException.class, () -> beneficioService.detailBeneficio(id));
        assertEquals("Benefício não encontrado!", exception.getMessage());
    }

    @DisplayName("Listar benefícios sem versão com sucesso")
    @Test
    void listBeneficiosWithoutVersion() {
        int pagina = 0;
        int tamanho = 10;

        List<BeneficioConsultaProjection> mockListBenficios = List.of();

        when(beneficioRepository.findByBeneficiosWithoutVersion(any(Pageable.class))).thenReturn(mockListBenficios);

        List<BeneficioConsultaProjection> beneficios = beneficioService.findByBeneficiosWithoutVersion(pagina, tamanho);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(beneficioRepository, times(1)).findByBeneficiosWithoutVersion(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        assertEquals(pagina, pageable.getPageNumber());
        assertEquals(tamanho, pageable.getPageSize());
        assertEquals(Sort.by("id").ascending(), pageable.getSort());
        assertEquals(mockListBenficios, beneficios);
    }

    @DisplayName("Deve fazer a transferência de benefícios com sucesso")
    @Test
    void transferWithSuccess() {
        Long fromId = 1L;
        Long toId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);
        TransferirDTO transferirDTO = new TransferirDTO(fromId, toId, amount);

        beneficioService.transfer(transferirDTO);

        verify(serviceEjb, times(1)).transfer(fromId, toId, amount);
    }
}