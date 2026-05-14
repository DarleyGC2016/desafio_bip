import { beforeEach, describe, expect, it, vi } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioFormComponent } from './beneficio-form.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter, Router } from '@angular/router';
import { provideNgxMask } from 'ngx-mask';
import { BeneficioService } from '../../../../core/services/beneficio.service';
import { Beneficio } from '../../../../shared/models/beneficio';
import { of, throwError } from 'rxjs';

describe('BeneficioFormComponent', () => {
  let component: BeneficioFormComponent;
  let fixture: ComponentFixture<BeneficioFormComponent>;
  let service: BeneficioService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioFormComponent],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        provideNgxMask()
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BeneficioFormComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(BeneficioService);
    router = TestBed.inject(Router);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve salvar - sucesso', () => {
    const beneficioI = {
      id: 1,
      nome: 'Benefício',
      descricao: 'Descrição do benefício',
      valor: 100.00,
      version: 0,
      ativo: true
    } as Beneficio;
    const spy = vi.spyOn(service, 'create').mockReturnValue(of('Beneficio salvo com sucesso!'));
    const spyRouter = vi.spyOn(router, 'navigate').mockResolvedValue(true);

    component.beneficio.set(beneficioI);
    component.salvar();
    
    expect(spy).toHaveBeenCalledWith(beneficioI);
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
        message: 'Erro ao salvar benefício!'
      }
    };
    const spy = vi.spyOn(service, 'create').mockReturnValue(throwError(() => new Error(messageError as any)));

    component.salvar();
    expect(spy).toHaveBeenCalled();
  });

  it('Deve cancelar', () => {
    const navigateSpy = vi.spyOn(router, 'navigate').mockResolvedValue(true);
    component.cancelar();
    expect(navigateSpy).toHaveBeenCalledWith([""]);
  });
});
