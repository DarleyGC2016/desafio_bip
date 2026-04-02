package com.example.ejb.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;

import com.example.ejb.model.Beneficio;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;
    
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Beneficio from;
        Beneficio to;

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Transferência para o mesmo benefício não é permitida");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido para transferência");
        }

        if (fromId > toId) {
            from = em.find(
                    Beneficio.class,
                    fromId,
                    LockModeType.PESSIMISTIC_WRITE);
            to = em.find(
                    Beneficio.class,
                    toId,
                    LockModeType.PESSIMISTIC_WRITE);
        } else {
            to = em.find(
                    Beneficio.class,
                    toId,
                    LockModeType.PESSIMISTIC_WRITE);
            from = em.find(
                    Beneficio.class,
                    fromId,
                    LockModeType.PESSIMISTIC_WRITE);
        }

        if (from == null || to == null) {
            throw new IllegalArgumentException("Benefício não encontrado");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para transferência");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

    }

}
