import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ativoResposta',
  standalone: true
})
export class AtivoRespostaPipe implements PipeTransform {
  transform(ativo: boolean): string {
    return !!ativo ? "Sim" : "Não";
  }
}
