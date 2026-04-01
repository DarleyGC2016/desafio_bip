package com.example.ejb.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;

import com.example.ejb.model.Beneficio;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void hello() {
        System.out.println("EJB integrado com Spring Boot!");
        System.out.println("Eu sou o seu Ejb e estou funcionando de novo!");
        System.out.println("Eu sou o seu Ejb e você está usandos os meus dados spring boot!kkkk");
    }

    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        System.out.println("Eu sou o seu Ejb");  
        Beneficio from = em.find(Beneficio.class, fromId);
        Beneficio to   = em.find(Beneficio.class, toId);

        // BUG: sem validações, sem locking, pode gerar saldo negativo e lost update
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        em.merge(from);
        em.merge(to);
    }
}
