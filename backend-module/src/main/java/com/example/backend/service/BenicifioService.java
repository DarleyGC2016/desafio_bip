package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ejb.service.BeneficioEjbService;

@Service
public class BenicifioService {
    
    @Autowired
    private BeneficioEjbService serviceEjb; 

    public void hello() {// Exemplo de uso do serviço EJB
        serviceEjb.hello(); // Exemplo de uso do serviço EJB via configuração   
    }
}
