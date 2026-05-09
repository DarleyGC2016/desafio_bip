import { beforeEach, describe, expect, it } from 'vitest';
import { AtivoRespostaPipe } from './ativo-resposta.pipe';

describe('AtivoRespostaPipe', () => {

  let pipe: AtivoRespostaPipe;

  beforeEach(() => {
    pipe = new AtivoRespostaPipe();
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
    const resultdo = pipe.transform(false);
    expect(resultdo).toBe('Não');
  });    
});
