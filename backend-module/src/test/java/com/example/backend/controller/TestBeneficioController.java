package com.example.backend.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import com.example.backend.DTO.BeneficioDTO;
import com.example.backend.service.BeneficioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BeneficioController.class)
public class TestBeneficioController {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BeneficioService beneficioService;

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

        mockMvc.perform(post("/api/v1/novo")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Beneficio salvo com sucesso!"));

        verify(beneficioService).save(beneficioDTO);
    }

    @DisplayName("Não deve criar um novo benefício com bad request")
    @Test
    void saveBeneficioWithBadRequest() throws Exception {
        var beneficioDTO =  "\"{}\""; 
        String json = objectMapper.writeValueAsString(beneficioDTO);

        mockMvc.perform(post("/api/v1/novo")
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

        mockMvc.perform(put("/api/v1/beneficio/{id}", id)
                .contentType("application/json")
                .content(json))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(beneficioService);        
    }

//     @DisplayName("Não deve atualizar quando um benefício não for encontrado")
//     @Test
//     void updateBeneficioWithNotFound() throws Exception {
//         Long id = 99L;
//         var beneficioDTO = new BeneficioDTO(
//                 "Benefício A",
//                 "Benefício A",
//                 BigDecimal.valueOf(100));

//         String json = objectMapper.writeValueAsString(beneficioDTO);

//         doThrow(new IllegalArgumentException("Beneficio não encontrado!"))
//         .when(beneficioService).update(eq(id), any(BeneficioDTO.class));

//         mockMvc.perform(put("/api/v1/beneficio/{id}", id)
//                 .contentType("application/json")
//                 .content(json))
//                 .andExpect(status().isNotFound());
     
//     }
}
