package com.example.backend.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.backend.repository.projection.BeneficioConsultaProjection;
import com.example.ejb.model.Beneficio;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {
   List<Beneficio> findByNome(String nome);

   boolean existsByNomeIgnoreCase(String nome);

   Beneficio findById(long id);

   @Query(value = "SELECT b.id as id,  b.nome as nome, b.descricao as descricao, b.valor  as valor FROM Beneficio b")
   List<BeneficioConsultaProjection> findByBeneficiosWithoutVersion(Pageable pageable);
}
