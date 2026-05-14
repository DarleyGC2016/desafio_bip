import { beforeEach, describe, expect, it, vi } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferirFormComponent } from './transferir-form.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter, Router } from '@angular/router';
import { provideNgxMask } from 'ngx-mask';
import { BeneficioService } from '../../../core/services/beneficio.service';
import { of, throwError } from 'rxjs';
import { Transferir } from '../../../shared/models/beneficio';

describe('TransferirFormComponent', () => {
  let component: TransferirFormComponent;
  let fixture: ComponentFixture<TransferirFormComponent>;
  let service: BeneficioService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransferirFormComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        provideNgxMask()
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TransferirFormComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(BeneficioService);
    router = TestBed.inject(Router);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve salvar - sucesso',  () => {
    const mockTransferir = {
      fromId: 1,
      toId: 2,
      amount: 100.00
    } as Transferir;
    const spy = vi.spyOn(service, 'tranferirBeneficio').mockReturnValue(of(`Transferência de R$ ${mockTransferir.amount} realizada com sucesso!`));
    const spyRouter = vi.spyOn(router, 'navigate').mockResolvedValue(true);
    component.transferir.set(mockTransferir);
    component.salvar();
    expect(spy).toHaveBeenCalledWith(mockTransferir);
    expect(spyRouter).toHaveBeenCalledWith(['/beneficios/todos']);
  });

  it('Deve salvar - erro', () => {
    const beneficioI = {
      nome: '',
      descricao: '',
      valor: 0.00,
      version: 0,
      ativo: true
    };
    const messageError = {
      error:
      {
        message: 'Erro ao transferir benefício!'
      }
    };
    const spy = vi.spyOn(service, 'tranferirBeneficio').mockReturnValue(throwError(() => new Error(messageError as any)));

    component.salvar();
    expect(spy).toHaveBeenCalled();
  });

  it('Deve cancelar', () => {
    const navigateSpy = vi.spyOn(router, 'navigate').mockResolvedValue(true);
    component.cancelar();
    expect(navigateSpy).toHaveBeenCalledWith(["/beneficios/todos"]);
  });
});
