package com.example.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.ejb.model.Beneficio;


@Repository
public interface BeneficioRepoistory extends JpaRepository<Beneficio, Long>{
   List<Beneficio> findByNome(String nome);
}

