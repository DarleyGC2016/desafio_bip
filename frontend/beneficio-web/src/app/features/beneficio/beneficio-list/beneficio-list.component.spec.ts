import { beforeEach, describe, expect, it, vi } from 'vitest';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioListComponent } from './beneficio-list.component';
import { BeneficioService } from '../../../core/services/beneficio.service';
import { provideRouter, Router } from '@angular/router';
import { routes } from '../../../app.routes';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { PageResponse } from '../../../shared/models/page';
import { Beneficio } from '../../../shared/models/beneficio';

describe('BeneficioListComponent', () => {
  let component: BeneficioListComponent;
  let fixture: ComponentFixture<BeneficioListComponent>;
  let service: BeneficioService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BeneficioListComponent],
      providers: [
        BeneficioService,
        provideRouter(routes),
        provideHttpClient(),
        provideHttpClientTesting()
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BeneficioListComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(BeneficioService);
    router = TestBed.inject(Router);

    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve carregarBeneficios - sucesso', async () => {
    const pageResponse = {
      content: [
        {
          id: 1,
          nome: 'Benefício',
          descricao: 'Descrição do benefício',
          valor: 100.00
        }
      ], totalElements: 1
    } as PageResponse<Beneficio>;
    const spy = vi.spyOn(service, 'list').mockReturnValue(of(pageResponse));
    component.paginator = {
      pageIndex: 1,
      pageSize: 5,
      length: 0
    } as any;
    component.carregarBeneficios();
    expect(component.isLoading()).toBe(true);
    expect(spy).toHaveBeenCalledWith(1, 5);

    fixture.detectChanges();
    await fixture.whenStable();
    expect(component.dataSource.data.length).toBe(0);
    expect(component.paginator.length).toBe(0);
    expect(component.isLoading()).toBe(true);
  });

  it('Deve carregarBeneficios - error', () => {
    const consoleSpy = vi.spyOn(console, 'error').mockImplementation(() => { });
    const urlError = { url: 'http://localhost:8080/beneficios' }
    
    vi.spyOn(service, 'list').mockReturnValue(throwError(() => urlError));
    
    component.carregarBeneficios();

    expect(consoleSpy).toHaveBeenCalledWith(
      'Erro ao carregar benefícios',
      urlError.url
    );
    expect(component.isLoading()).toBe(false);
  });
  
  it('Deve enviarBeneficioEscolhido', async () => {
    const navigateSpy = vi.spyOn(router, 'navigate').mockResolvedValue(true);
    component.enviarBeneficioEscolhido(1);
    expect(navigateSpy).toHaveBeenCalledWith(['/beneficios/detalhe', 1]);
  });
});
