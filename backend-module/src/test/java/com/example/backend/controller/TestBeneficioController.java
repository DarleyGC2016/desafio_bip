package com.example.backend.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.DTO.BeneficioDTO;
import com.example.backend.DTO.TransferirDTO;
import com.example.backend.mock.MockBeneficio;
import com.example.backend.repository.BeneficioRepository;
import com.example.backend.repository.projection.BeneficioConsultaProjection;
import com.example.backend.service.BeneficioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("Testes do BeneficioController")
@WebMvcTest(BeneficioController.class)
public class TestBeneficioController {
        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private BeneficioService beneficioService;

        @Mock
        private BeneficioRepository beneficioRepository;

        @Mock
        private BeneficioConsultaProjection beneficioConsultaProjection;

        @Autowired
        private ObjectMapper objectMapper;

        @DisplayName("Deve criar um novo benefício com sucesso")
        @Test
        void saveBeneficioWithSuccess() throws Exception {
                var beneficioDTO = new BeneficioDTO(
                                "Benefício A",
                                "Benefício A",
                                BigDecimal.valueOf(100));
                String json = objectMapper.writeValueAsString(beneficioDTO);

                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o BeneficioDTO para JSON!");
                }

                mockMvc.perform(post("/api/v1/beneficio/novo")
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Beneficio salvo com sucesso!"));

                verify(beneficioService).save(beneficioDTO);
        }

        @DisplayName("Não deve criar um novo benefício com bad request")
        @Test
        void saveBeneficioWithBadRequest() throws Exception {
                var beneficioDTO = "\"{}\"";
                String json = objectMapper.writeValueAsString(beneficioDTO);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o BeneficioDTO para JSON!");
                }

                mockMvc.perform(post("/api/v1/beneficio/novo")
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isBadRequest());
                verifyNoInteractions(beneficioService);

        }

        @DisplayName("Deve atualizar um benefício com sucesso")
        @Test
        void updateBeneficioWithSuccess() throws Exception {
                Long id = 1L;
                var beneficioDTO = new BeneficioDTO(
                                "Benefício A",
                                "Benefício A",
                                BigDecimal.valueOf(100));
                String json = objectMapper.writeValueAsString(beneficioDTO);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o BeneficioDTO para JSON!");
                }
                mockMvc.perform(put("/api/v1/beneficio/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Benefício atualizado com sucesso!"));

                verify(beneficioService).update(eq(id), any(BeneficioDTO.class));
        }

        @DisplayName("Não deve atualizar um benefício com bad request")
        @Test
        void updateBeneficioWithBadRequest() throws Exception {
                Long id = 1L;
                var beneficioDTO = "{\"nome\": \"\", \"valor\": -50}";
                String json = objectMapper.writeValueAsString(beneficioDTO);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o BeneficioDTO para JSON!");
                }
                mockMvc.perform(put("/api/v1/beneficio/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isBadRequest());
                verifyNoInteractions(beneficioService);
        }

        @DisplayName("Não deve atualizar quando um benefício não for encontrado")
        @Test
        void updateBeneficioWithNotFound() throws IllegalArgumentException, Exception {
                Long id = 99L;
                var beneficioDTO = new BeneficioDTO(
                                "Benefício A",
                                "Benefício A",
                                BigDecimal.valueOf(100));

                String json = objectMapper.writeValueAsString(beneficioDTO);

                doThrow(new IllegalArgumentException("Benefício não encontrado!"))
                                .when(beneficioService).update(eq(id), any(BeneficioDTO.class));
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o BeneficioDTO para JSON!");
                }
                mockMvc.perform(put("/api/v1/beneficio/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isNotFound());

        }

        @DisplayName("Deve excluir um benefício com sucesso")
        @Test
        void deleteBeneficioWithSuccess() throws Exception {
                Long id = 1L;
                String json = objectMapper.writeValueAsString(id);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o id para JSON!");
                }
                mockMvc.perform(delete("/api/v1/beneficio/delete/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Benefício excluído com sucesso!"));

                verify(beneficioService).delete(id);
        }

        @DisplayName("Não deve excluir um benefício com bad request")
        @Test
        void deleteBeneficioWithBadRequest() throws Exception {
                Long id = 19L;
                String json = objectMapper.writeValueAsString(id);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o id para JSON!");
                }
                doThrow(new IllegalArgumentException("Este benefício não existe para ser excluído!"))
                                .when(beneficioService).delete(id);
                mockMvc.perform(delete("/api/v1/beneficio/delete/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isBadRequest());

                verify(beneficioService).delete(id);
        }

        @DisplayName("Não deve excluir um benefício não foi encontrado")
        @Test
        void deleteBeneficioWithNotFound() throws Exception {
                Long id = 99L;
                String json = objectMapper.writeValueAsString(id);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o id para JSON!");
                }
                doThrow(new IllegalArgumentException("Benefício não encontrado!"))
                                .when(beneficioService).delete(id);
                mockMvc.perform(delete("/api/v1/beneficio/delete/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isNotFound());

                verify(beneficioService).delete(id);
        }

        @DisplayName("Deve pedir detalhes de um benefício com sucesso")
        @Test
        void detailBeneficioWithSuccess() throws Exception {
                Long id = 1L;
                String json = objectMapper.writeValueAsString(id);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o id para JSON!");
                }
                mockMvc.perform(get("/api/v1/beneficio/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isOk());

                verify(beneficioService).detailBeneficio(id);
        }

        @DisplayName("Não deve pedir detalhes de um benefício com bad request")
        @Test
        void detailBeneficioWithBadRequest() throws Exception {
                Long id = 19L;
                String json = objectMapper.writeValueAsString(id);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o id para JSON!");
                }
                doThrow(new IllegalArgumentException("Este benefício não existe para ser detalhado!"))
                                .when(beneficioService).detailBeneficio(id);
                mockMvc.perform(get("/api/v1/beneficio/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isBadRequest());

                verify(beneficioService).detailBeneficio(id);
        }

        @DisplayName("Não deve pedir detalhes de um benefício não encontrado")
        @Test
        void detailBeneficioWithNotFound() throws Exception {
                Long id = 99L;
                String json = objectMapper.writeValueAsString(id);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o id para JSON!");
                }
                doThrow(new IllegalArgumentException("Benefício não encontrado!"))
                                .when(beneficioService).detailBeneficio(id);
                mockMvc.perform(get("/api/v1/beneficio/{id}", id)
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isNotFound());

                verify(beneficioService).detailBeneficio(id);
        }

        /*
         * @Test
         * 
         * @DisplayName("Deve retornar uma página de benefícios com sucesso")
         * void findByBeneficiosWithoutVersionWithSuccess() {
         * // 1. ARRANGE (Configuração)
         * int pagina = 0;
         * int tamanho = 10;
         * var pageableEsperado = PageRequest.of(pagina, tamanho,
         * Sort.by("id").ascending());
         * 
         * // Criamos um mock da projeção (já que é uma interface)
         * var projecaoMock = Mockito.mock(BeneficioConsultaProjection.class);
         * List<BeneficioConsultaProjection> listaContent = List.of(projecaoMock);
         * 
         * // Criamos o objeto Page de resposta (Content, Pageable, Total)
         * Page<BeneficioConsultaProjection> pageResponse = new PageImpl<>(listaContent,
         * pageableEsperado, 1);
         * 
         * // Mockamos o repositório para retornar a Page
         * when(beneficioRepository.findByBeneficiosWithoutVersion(pageableEsperado))
         * .thenReturn(pageResponse);
         * 
         * // 2. ACT (Execução)
         * Page<BeneficioConsultaProjection> resultado =
         * beneficioService.findByBeneficiosWithoutVersion(pagina, tamanho);
         * 
         * // 3. ASSERT (Verificação)
         * assertNotNull(resultado);
         * assertEquals(1, resultado.getTotalElements()); // Verifica o total
         * assertEquals(1, resultado.getContent().size()); // Verifica a lista interna
         * verify(beneficioRepository).findByBeneficiosWithoutVersion(pageableEsperado);
         * }
         * 
         */
        @DisplayName("Deve listar os benefícios com páginação com sucesso ")
        @Test
        void listBeneficiosWithSuccess() throws Exception {
                int pagina = 0;
                int tamanho = 10;
                var pageableEsperado = PageRequest.of(pagina, tamanho, Sort.by("id").ascending());

                List<BeneficioConsultaProjection> beneficios = new ArrayList<>();
                beneficios.add(
                                new MockBeneficio(
                                                1L,
                                                "Vale Transporte",
                                                "Benefício de transporte para os funcionários",
                                                BigDecimal.valueOf(150.00)));
                Page<BeneficioConsultaProjection> pageResponse = new PageImpl<>(beneficios,
                                pageableEsperado, 1);
                when(beneficioService.findByBeneficiosWithoutVersion(0, 10))
                                .thenReturn(pageResponse);

                mockMvc.perform(get("/api/v1/beneficios"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").isArray());

                verify(beneficioService).findByBeneficiosWithoutVersion(0, 10);

        }

        @DisplayName("Não deve listar os benefícios com bad request")
        @Test
        void listBeneficiosWithBadRequest() throws Exception {
                when(beneficioService.findByBeneficiosWithoutVersion(anyInt(), anyInt()))
                                .thenThrow(new IllegalArgumentException("Erro ao listar os benefícios!"));

                mockMvc.perform(get("/api/v1/beneficios")
                                .param("page", "0")
                                .param("size", "10"))
                                .andExpect(status().isBadRequest());

                verify(beneficioService).findByBeneficiosWithoutVersion(0, 10);
        }

        @DisplayName("Não deve listar os benefícios com not found")
        @Test
        void listBeneficiosWithNotFound() throws Exception {
                when(beneficioService.findByBeneficiosWithoutVersion(anyInt(), anyInt()))
                                .thenThrow(new IllegalArgumentException(
                                                "Não nenhuma lista de benefícios foi encontrada!"));

                mockMvc.perform(get("/api/v1/beneficios")
                                .param("page", "0")
                                .param("size", "10"))
                                .andExpect(status().isNotFound());

                verify(beneficioService).findByBeneficiosWithoutVersion(0, 10);
        }

        @DisplayName("Deve transferir benefícios entre contas com sucesso")
        @Test
        void transferWithSuccess() throws Exception {
                Long fromId = 1L;
                Long toId = 2L;
                BigDecimal amount = BigDecimal.valueOf(50);
                var transferirDTO = new TransferirDTO(fromId, toId, amount);
                String json = objectMapper.writeValueAsString(transferirDTO);

                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o TransferirDTO para JSON!");
                }
                mockMvc.perform(put("/api/v1/transferir")
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isOk())
                                .andExpect(content().string("Transferência de R$" + amount + " realizada com sucesso"));

                verify(beneficioService).transfer(transferirDTO);
        }

        @DisplayName("Não deve transferir benefícios entre contas com bad request")
        @Test
        void transferWithBadRequest() throws Exception {
                Long fromId = 1L;
                Long toId = 2L;
                BigDecimal amount = BigDecimal.valueOf(1050);
                var transferirDTO = new TransferirDTO(fromId, toId, amount);
                String json = objectMapper.writeValueAsString(transferirDTO);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o TransferirDTO para JSON!");
                }
                doThrow(new IllegalArgumentException("Saldo insuficiente para transferência"))
                                .when(beneficioService).transfer(transferirDTO);

                mockMvc.perform(put("/api/v1/transferir")
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isBadRequest())
                                .andExpect(content().string("Saldo insuficiente para transferência"));

                verify(beneficioService).transfer(transferirDTO);
        }

        @DisplayName("Não deve transferir benefícios entre contas com not found")
        @Test
        void transferWithNotFound() throws Exception {
                Long fromId = 90L;
                Long toId = 2L;
                BigDecimal amount = BigDecimal.valueOf(50);
                var transferirDTO = new TransferirDTO(fromId, toId, amount);
                String json = objectMapper.writeValueAsString(transferirDTO);
                if (json == null) {
                        throw new IllegalArgumentException("Erro ao converter o TransferirDTO para JSON!");
                }
                doThrow(new IllegalArgumentException("Benefício não encontrado!"))
                                .when(beneficioService).transfer(transferirDTO);

                mockMvc.perform(put("/api/v1/transferir")
                                .contentType("application/json")
                                .content(json))
                                .andExpect(status().isNotFound())
                                .andExpect(content().string("Benefício não encontrado!"));

                verify(beneficioService).transfer(transferirDTO);
        }
}
