import { beforeEach, describe, expect, it } from 'vitest';
import { TextoBrevePipe } from './texto-breve.pipe';
import { TestBed } from '@angular/core/testing';

describe('TextoBrevePipe', () => {
  let pipe: TextoBrevePipe;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TextoBrevePipe]
    });
    pipe = TestBed.inject(TextoBrevePipe);
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('deve retornar o texto completo', () => {
    const texto = 'Texto curto';
    const resultado = pipe.transform(texto, 20);
    expect(resultado).toBe(texto);
  });

  it('deve retornar o texto resumido', () => {
    const descricao = 'Cartão de Alimentação para funcionários';
    const resultado = pipe.transform(descricao, 10);
    expect(resultado).toBe('Cartão de ...');
  });

  it('deve retornar string vazia para texto nulo ou indefinido', () => {
    expect(pipe.transform('')).toBe('');
  })

});
