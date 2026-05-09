import { beforeEach, describe, expect, it } from 'vitest';
import { TextoBrevePipe } from './texto-breve.pipe';

describe('TextoBrevePipe', () => {
  it('create an instance', () => {
    const pipe = new TextoBrevePipe();
    expect(pipe).toBeTruthy();
  });
});
