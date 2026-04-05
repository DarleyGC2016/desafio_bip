package com.example.backend.service;

import com.example.backend.repository.BeneficioRepoistory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.BeneficioDTO;
import com.example.backend.DTO.TransferirDTO;
import com.example.backend.parser.BeneficioParse;
import com.example.ejb.model.Beneficio;
import com.example.ejb.service.BeneficioEjbService;

import jakarta.transaction.Transactional;

@Service
public class BenicifioService {

    private final BeneficioRepoistory beneficioRepoistory;

    @Autowired
    private BeneficioEjbService serviceEjb;

    public BenicifioService(BeneficioRepoistory beneficioRepoistory) {
        this.beneficioRepoistory = beneficioRepoistory;
    }

    @Transactional
    public void transfer(TransferirDTO transferirDTO) {
        serviceEjb.transfer(transferirDTO.fromId(), transferirDTO.toId(), transferirDTO.amount());
        throw new IllegalArgumentException("Transferência realizada com sucesso");

    }

    @Transactional
    public Beneficio save(BeneficioDTO dto) {
        List<Beneficio> existingBeneficios = beneficioRepoistory.findByNome(dto.nome());
        List<String> Nomes = existingBeneficios.stream()
                .filter(beneficio -> beneficio
                        .getNome()
                        .toLowerCase()
                        .equals(
                                dto
                                        .nome()
                                        .toLowerCase()))
                .map(beneficio -> {
                    return "O Benefício '" + beneficio.getNome() + "' já existe! ";
                })
                .toList();

        if (!Nomes.isEmpty()) {
            throw new IllegalArgumentException(Nomes.get(0));
        }
        Beneficio beneficio = BeneficioParse.toEntity(dto);

        return beneficioRepoistory.save(beneficio);
    }

    @Transactional
    public List<Beneficio> listBeneficios() {
        return beneficioRepoistory.findAll();
    }

}
