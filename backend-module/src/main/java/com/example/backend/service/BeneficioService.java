package com.example.backend.service;

import com.example.backend.repository.BeneficioRepository;
import com.example.backend.repository.projection.BeneficioConsultaProjection;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.BeneficioDTO;
import com.example.backend.DTO.TransferirDTO;
import com.example.backend.parser.BeneficioParse;
import com.example.ejb.model.Beneficio;
import com.example.ejb.service.BeneficioEjbService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BeneficioService {

    private final BeneficioRepository beneficioRepoistory;

    private final BeneficioEjbService serviceEjb;

    @Transactional
    public void transfer(TransferirDTO transferirDTO) {
        serviceEjb.transfer(transferirDTO.fromId(), transferirDTO.toId(), transferirDTO.amount());
    }

    @Transactional
    public void save(BeneficioDTO dto) {
        verificaExisteNomeDuplicado(dto.nome());
        var beneficio = BeneficioParse.toEntity(dto);
        beneficioRepoistory.save(beneficio);
    }

    @Transactional
    public Beneficio update(Long id, BeneficioDTO dto) {
        var beneficio = beneficioRepoistory
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefício não encontrado!"));

        verificaExisteNomeDuplicado(dto.nome());

        Beneficio beneficioAtualizado = BeneficioParse.toEntity(dto, beneficio);

        return beneficioRepoistory.saveAndFlush(beneficioAtualizado);
    }

    public void verificaExisteNomeDuplicado(String nome) {
        if (beneficioRepoistory.existsByNomeIgnoreCase(nome)) {
            throw new IllegalArgumentException("O Benefício '" + nome + "' já existe! ");
        }
    }

    @Transactional
    public void delete(Long id) {
        var beneficio = beneficioRepoistory.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefício não encontrado!"));
        beneficioRepoistory.delete(beneficio);
    }

    @Transactional
    public List<BeneficioConsultaProjection> findByBeneficiosWithoutVersion(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("id").ascending());
        return beneficioRepoistory.findByBeneficiosWithoutVersion(pageable);
    }

    @Transactional
    public Beneficio detailBeneficio(Long id) {
        return beneficioRepoistory.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefício não encontrado!"));
    }

}
