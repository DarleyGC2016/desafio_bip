import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioDetailComponent } from './beneficio-detail.component';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { BeneficioService } from '../../../core/services/beneficio.service';
import { ActivatedRoute, provideRouter, Router } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { Beneficio } from '../../../shared/models/beneficio';
import { routes } from '../../../app.routes';
import { Location } from '@angular/common';
import { DialogConfirmationComponent } from '../../../shared/components/dialog-confirmation/dialog-confirmation.component';

describe('BeneficioDetailComponent', () => {
  let component: BeneficioDetailComponent;
  let fixture: ComponentFixture<BeneficioDetailComponent>;
  let service: BeneficioService;
  let router: Router;
  let local: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        BeneficioDetailComponent,
        DialogConfirmationComponent
      ],
      providers: [
        BeneficioService,
        provideRouter(routes),
        provideHttpClient(),
        provideHttpClientTesting()
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(BeneficioDetailComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(BeneficioService);
    router = TestBed.inject(Router);
    local = TestBed.inject(Location);
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Deve buscarUnicoBeneficio - sucesso', () => {
    const mockBeneficio = {
      id: 1,
      nome: 'Benefício',
      descricao: 'Descrição',
      valor: 100,
      version: 0,
      ativo: true
    };
    const spy = vi.spyOn(service, 'detail').mockReturnValue(of(mockBeneficio as Beneficio));

    component.id = '1';
    component.buscarUnicoBeneficio();

    expect(spy).toHaveBeenCalledWith(1);
    expect(component.beneficio()).toEqual(mockBeneficio);
  });

  it('Deve buscarUnicoBeneficio - error', () => {

    const consoleSpy = vi.spyOn(console, 'log');
    vi.spyOn(service, 'detail').mockReturnValue(throwError(() => new Error('Erro de API')));

    component.id = '1';
    component.buscarUnicoBeneficio();
    expect(consoleSpy).toHaveBeenCalled();
  });

  it('Deve salvarEdicao - sucesso', () => {
    const mockBeneficio = {
      id: 1,
      nome: 'Benefício',
      descricao: 'Descrição',
      valor: 100,
      version: 0,
      ativo: true
    } as Beneficio;
    const spy = vi.spyOn(service, 'update').mockReturnValue(of('Benefício atualizado com sucesso!'));

    component.beneficio.set(mockBeneficio);
    component.idRouter = 1;
    component.salvarEdicao('Novo Benefício', 'Nova Descrição', 200);

    expect(spy).toHaveBeenCalledWith(1, {
      id: 1,
      nome: 'Novo Benefício',
      descricao: 'Nova Descrição',
      valor: 200,
      version: 0,
      ativo: true
    });

  });

  it('Deve salvarEdicao - error', () => {
    const consoleSpy = vi.spyOn(console, 'log');
    vi.spyOn(service, 'update').mockReturnValue(throwError(() => new Error('Erro de API')));
    component.idRouter = 0;
    component.salvarEdicao('Novo Benefício', 'Nova Descrição', 200);
    expect(consoleSpy).toHaveBeenCalled();
  });

  it('Deve editOrSave (mudarIcone - edit())', async () => {
    const iconeUm = 'edit';
    const spy = vi.spyOn(component, 'trocaIcone').mockReturnValue(iconeUm);
    const route = await router.navigate(['/beneficios/todos']);
    component.editOrSave('Novo Benefício', 'Nova Descrição', 200);
    expect(spy).toHaveBeenCalledWith('edit', 'save');
    expect(local.path(route)).toBe('/beneficios/todos');
  });

  it('Deve editOrSave (salvarEdicao)', async () => {
    const iconeDois = 'save';
    const spy = vi.spyOn(component, 'trocaIcone').mockReturnValue(iconeDois);
    const route = await router.navigate(['/beneficios/todos']);
    component.editOrSave('Novo Benefício', 'Nova Descrição', 200);
    expect(spy).toHaveBeenCalledWith('edit', 'save');
    expect(local.path(route)).toBe('/beneficios/todos');
  });

  it('Deve trocaIcone', () => {
    component.ativar.set(true);
    expect(component.trocaIcone('edit', 'save')).toBe('edit');
    component.ativar.set(false);
    expect(component.trocaIcone('edit', 'save')).toBe('save');
  });


  it('Deve cancelOrDelete (mudarIcone - delete())', async () => {
    const iconeUm = 'delete';
    const spy = vi.spyOn(component, 'trocaIcone').mockReturnValue(iconeUm);
    const route = await router.navigate(['/beneficios/todos']);
    component.cancelOrDelete('Benefício');
    expect(spy).toHaveBeenCalledWith('delete', 'cancel');
    expect(local.path(route)).toBe('/beneficios/todos');
  });

  it('Deve cancelOrDelete (cancelar)', async () => {
    const iconeDois = 'cancel';
    const spy = vi.spyOn(component, 'trocaIcone').mockReturnValue(iconeDois);
    const route = await router.navigate(['/beneficios/todos']);
    component.cancelOrDelete('Benefício');
    expect(spy).toHaveBeenCalledWith('delete', 'cancel');
    expect(local.path(route)).toBe('/beneficios/todos');
  });

  it('Deve excluirUnicoBeneficio - sucesso', () => {
    const spy = vi.spyOn(service, 'delete').mockReturnValue(of('Benefício excluído com sucesso!'));

    component.idRouter = 1;
    component.excluirUnicoBeneficio();
    expect(spy).toHaveBeenCalledWith(1);
  });

  it('Deve excluirUnicoBeneficio - error', () => {
    const consoleSpy = vi.spyOn(console, 'log');
    vi.spyOn(service, 'delete').mockReturnValue(throwError(() => new Error('Erro de API')));
    component.idRouter = 1;
    component.excluirUnicoBeneficio();
    expect(consoleSpy).toHaveBeenCalled();
  });

  it('Deve excluicao', async () => {
    const spy = vi.spyOn(component.dialog, 'open').mockReturnValue({afterClosed: () => of(true)} as any);
    const excluicaoSpy = vi.spyOn(component, 'excluirUnicoBeneficio').mockReturnValue(of('Benefício excluído com sucesso!') as any);
    const route = await router.navigate(['/beneficios/todos']);
    component.idRouter = 1;
    component.excluicao('Benefício');
    component.excluirUnicoBeneficio();
    expect(spy).toHaveBeenCalled();
    expect(excluicaoSpy).toHaveBeenCalled();
    expect(local.path(route)).toBe('/beneficios/todos');
  });
});


