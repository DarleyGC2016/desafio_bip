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
        findByNome(dto.nome());
        Beneficio beneficio = BeneficioParse.toEntity(dto);
        beneficioRepoistory.save(beneficio);
    }

    @Transactional
    public Beneficio update(Long id, BeneficioDTO dto) {
        Beneficio beneficio = beneficioRepoistory
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefício não encontrado!"));

        findByNome(dto.nome());

        Beneficio beneficioAtualizado = BeneficioParse.toEntity(dto, beneficio);

        return beneficioRepoistory.saveAndFlush(beneficioAtualizado);
    }

    private void findByNome(String nome) {
        List<Beneficio> existingBeneficios = beneficioRepoistory.findByNome(nome);
        List<String> nomes = existingBeneficios.stream()
                .filter(beneficio -> beneficio
                        .getNome()
                        .toLowerCase()
                        .equals(
                                nome.toLowerCase()))
                .map(beneficio -> {
                    return "O Benefício '" + beneficio.getNome() + "' já existe! ";
                })
                .toList();

        if (!nomes.isEmpty()) {
            throw new IllegalArgumentException(nomes.get(0));
        }
    }

    @Transactional
    public void delete(Long id) {
        Beneficio beneficio = beneficioRepoistory.findById(id)
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
