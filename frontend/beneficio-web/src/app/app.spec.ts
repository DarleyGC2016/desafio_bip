import { describe, it, expect, beforeEach } from 'vitest';
import { TestBed } from '@angular/core/testing';
import { App } from './app';
import { provideRouter, Router } from '@angular/router';
import { routes } from './app.routes';
import { Location } from '@angular/common';

describe('App', () => {
  let router: Router;
  let local: Location;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideRouter(routes)],
    }).compileComponents();
    router = TestBed.inject(Router);
    local = TestBed.inject(Location);
    router.initialNavigation();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(App);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it('Deve renderizar o template', () => {
    const fixture = TestBed.createComponent(App);
    fixture.detectChanges();

    expect(fixture.nativeElement).toBeTruthy();
  });

  it('Deve rota do home', async () => {
    const route = await router.navigate(['']);
    expect(local.path(route)).toBe('');
  });

  it('Deve ter rota para o home', async () => {
    const route = await router.navigate(['']);
    expect(local.path(route)).toBe('');
  });

  it('Deve ter rota para o lista de benefícios', async () => {
    const route = await router.navigate(['/beneficios/todos']);
    expect(local.path(route)).toBe('/beneficios/todos');
  });

  it('Deve ter rota para o detalhe do benefício', async () => {
    const route = await router.navigate(['/beneficios/detalhe/1']);
    expect(local.path(route)).toBe('/beneficios/detalhe/1');
  });

  it('Deve ter rota para um novo benefício', async () => {
    const route = await router.navigate(['/beneficios/novo']);
    expect(local.path(route)).toBe('/beneficios/novo');
  });

  it('Deve ter rota para transferir valor entre benefícios', async () => {
    const route = await router.navigate(['/beneficios/transferir']);
    expect(local.path(route)).toBe('/beneficios/transferir');
  });

});
