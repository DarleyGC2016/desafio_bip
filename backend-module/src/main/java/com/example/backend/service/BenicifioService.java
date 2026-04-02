package com.example.backend.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ejb.service.BeneficioEjbService;

import jakarta.transaction.Transactional;

@Service
public class BenicifioService {
    
    @Autowired
    private BeneficioEjbService serviceEjb; 

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        serviceEjb.transfer(fromId, toId, amount);
    }
}
