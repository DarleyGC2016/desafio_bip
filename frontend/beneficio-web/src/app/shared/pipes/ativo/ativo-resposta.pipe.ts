import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ativoResposta',
  standalone: true
})
export class AtivoRespostaPipe implements PipeTransform {
  transform(ativo: boolean): string {
    if(!ativo) 
      return 'Não';
    return !!ativo ? 'Sim' : 'Não';
  }
}
