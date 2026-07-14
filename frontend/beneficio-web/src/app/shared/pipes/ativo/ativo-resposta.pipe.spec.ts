import { beforeEach, describe, expect, it } from 'vitest';
import { AtivoRespostaPipe } from './ativo-resposta.pipe';
import { TestBed } from '@angular/core/testing';

describe('AtivoRespostaPipe', () => {

  let pipe: AtivoRespostaPipe;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AtivoRespostaPipe]
    });
    pipe = TestBed.inject(AtivoRespostaPipe);
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('deve estar ativo Sim', () => {
    const ativo: boolean = true;
    const resultdo = pipe.transform(ativo);
    expect(resultdo).toBe('Sim');
  });

  it('deve estar ativo Não', () => {
    const ativo: boolean = false;
    const resultdo = pipe.transform(ativo);
    expect(resultdo).toBe('Não');
  });

  it('deve testar valores nulos ou não definidos, quando o ativo for falso', () => {
    // @ts-ignore
    expect(pipe.transform(null)).toBe('Não');
    // @ts-ignore
    expect(pipe.transform(undefined)).toBe('Não');
  });
});
