import { ChangeDetectionStrategy, Component, Input, OnInit, signal } from '@angular/core';
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
import { Router } from '@angular/router';
import { DialogConfirmationComponent } from '../../../shared/components/dialog-confirmation/dialog-confirmation.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-beneficio-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButton,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    AtivoRespostaPipe,
    InputTypesComponent,
    InputAreaTextComponent,
    InputMoedaRealComponent,
  ],
  templateUrl: './beneficio-detail.component.html',
  styleUrl: './beneficio-detail.component.css'
})

export class BeneficioDetailComponent implements OnInit {

  @Input() id!: string;

  idRouter: number = 0;

  benficio = signal<Beneficio | undefined>(undefined);

  ativar = signal<boolean>(true);

  actionButton: string = 'edit';


  constructor(
    private service: BeneficioService,
    private _snack: MatSnackBar,
    private router: Router,
    public dialog: MatDialog
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

  editOrSave(nome: string, descricao: string, valor: number,) {
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
          this.router.navigate(['/beneficios'])

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

  cancelOrDelete(nome: string) {
    if (this.mudaIconeDelete() === 'cancel') {
      this.ativar.set(true);
    } else {
      this.excluicao(nome);
    }
  }

  excluicao(nome: string): void {
    const dialogRef = this.dialog.open(DialogConfirmationComponent, {
      width: "400px",
      data: {
        title: "Aviso de exclução",
        ask: "Desaja excluir esse benefício: ",
        dado: nome
      }
    });

    dialogRef.afterClosed().subscribe({
      next: (data) => {
        if (!!data){
          this.excluirUnicoBeneficio();
        }
      }
    });

  }

  excluirUnicoBeneficio(): void {
    this.service.delete(this.idRouter).subscribe({
      next: (data) => {
        this._snack.open(data, 'Fechar', {
          duration: 5000,
          panelClass: ['error-snackbar']
        });
        this.router.navigate(['/beneficios']);
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
