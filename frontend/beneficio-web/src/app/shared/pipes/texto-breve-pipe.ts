import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'textoBreve',
})
export class TextoBrevePipe implements PipeTransform {
  transform(texto: string, limite: number = 20): string {
    if (!texto) return '';
    return texto.length > limite? texto.substring(0, limite) + '...' : texto;
  }
}
