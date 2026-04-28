import { Component, Input, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButton } from '@angular/material/button';

import { Beneficio } from '../../../shared/models/beneficio';
import { BeneficioService } from '../../../core/services/beneficio.service';
import { InputTypesComponent } from '../../../shared/components/input-types/input-types.component';
import { InputAreaTextComponent } from '../../../shared/components/input-area-text/input-area-text.component';
import { AtivoRespostaPipe } from '../../../shared/pipes/ativo/ativo-resposta.pipe';
import { FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputMoedaRealComponent } from '../../../shared/components/input-moeda-real/input-moeda-real.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-beneficio-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButton,
    FormsModule,
    ReactiveFormsModule,
    AtivoRespostaPipe,
    InputTypesComponent,
    InputAreaTextComponent,
    InputMoedaRealComponent
  ],
  templateUrl: './beneficio-detail.component.html',
  styleUrl: './beneficio-detail.component.css',
})

export class BeneficioDetailComponent implements OnInit {

  @Input() id!: string;

  idRouter: number = 0;

  benficio = signal<Beneficio | undefined>(undefined);

  ativar = signal<boolean>(true);

  actionButton: string = 'edit';


  constructor(private service: BeneficioService,
    private _snack: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.buscarUnicoBeneficio();
  }

  buscarUnicoBeneficio(): void {
    this.idRouter = Number(this.id);
    
    this.service.detail(this.idRouter).subscribe({
      next: data => {
        this.benficio.set(data);

      }, error(err) {
        console.log(err);
      },
    })
  }

  editarBeneficio(nome: string, descricao: string, valor: number,) {
    this.ativar.set(false);

    if (this.actionButton === 'save') {
      const valorDecimal = Number(valor).toFixed(2);

      const beneficioU = {
        ...this.benficio(),
        nome: nome,
        descricao: descricao,
        valor: Number(valorDecimal)
      } as Beneficio;

      this.service.update(this.idRouter, beneficioU).subscribe({
        next: (dado) => {
          this._snack.open(dado, 'Fechar', {
            duration: 5000,
            panelClass: ['error-snackbar']
          });

        }, error: async (err) => {
          const msgErro = await err.error?.message || err.error || 'Erro ao atualizar benefício';

          this._snack.open(msgErro, 'Fechar', {
            duration: 5000,
            panelClass: ['error-snackbar']
          });

        }
      });
    }

  }

  mudaIcone() {
    if (this.ativar() === true) {
      this.actionButton = "edit";
      return "edit"
    } else {
      this.actionButton = "save";
      return "save"
    }
  }

  mudaIconeDelete(): string {
    if (this.ativar() === true) {
      return "delete";
    } else {
      return "cancel"
    }
  }

  cancelOrDelete(){    
    if (this.mudaIconeDelete() === 'cancel'){
      this.ativar.set(true);
    }
  }

}
