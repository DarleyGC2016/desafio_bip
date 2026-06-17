import { afterEach, beforeEach, describe, expect, it } from 'vitest';
import { TestBed } from '@angular/core/testing';

import { BeneficioService } from './beneficio.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

describe('BeneficioService', () => {
  let service: BeneficioService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        BeneficioService,
        provideHttpClient(),
        provideHttpClientTesting()
      ],
    });
    service = TestBed.inject(BeneficioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('Deve atualizar um benefício', () => {
    const mockResponse = 'Benefício atualizado com sucesso!';
    const beneficioId = 1;
    const beneficioData = { nome: 'Benefício Atualizado', descricao: 'Descrição', valor: 100 };
    service.update(beneficioId, beneficioData as any).subscribe((response) => {
      expect(response).toBe(mockResponse);
    });

    const req = httpMock.expectOne(`${service['api']}/editar/${beneficioId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(beneficioData);
    req.flush(mockResponse);
  });

  it('Deve excluir um benefício', () => {
    const mockResponse = 'Benefício excluído com sucesso!';
    const beneficioId = 1;
    service.delete(beneficioId).subscribe((response) => {
      expect(response).toBe(mockResponse);
    });

    const req = httpMock.expectOne(`${service['api']}/excluir/${beneficioId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(mockResponse);
  });

  it('Deve obter detalhes de um benefício', () => {
    const mockBeneficio = { id: 1, nome: 'Benefício', descricao: 'Descrição', valor: 100 };
    const beneficioId = 1;
    service.detail(beneficioId).subscribe((response) => {
      expect(response).toEqual(mockBeneficio);
    });

    const req = httpMock.expectOne(`${service['api']}/detalhe/${beneficioId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockBeneficio);
  });

  it('Deve criar um novo benefício', () => {
    const mockResponse = 'Beneficio salvo com sucesso!';
    const beneficioData = { nome: 'Novo Benefício', descricao: 'Descrição', valor: 100 };

    service.create(beneficioData as any).subscribe((response) => {
      expect(response).toBe(mockResponse);
    });

    const req = httpMock.expectOne(`${service['api']}/novo`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(beneficioData);
    req.flush(mockResponse);
  });

  it('Deve transferir um benefício', () => {
    const transferData = {
      fromId: 1,
      toId: 2,
      amount: 100
    };
    
    const mockResponse = `Transferência de R$${transferData.amount} realizada com sucesso`;

    service.tranferirBeneficio(transferData as any).subscribe((response) => {
      expect(response).toBe(mockResponse);
    });

    const req = httpMock.expectOne(`${service['api']}/transferir`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(transferData);
    req.flush(mockResponse);
  });

});
