import { CommonModule } from '@angular/common';
import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButton } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { InputTypesComponent } from '../../../shared/components/input-types/input-types.component';
import { InputMoedaRealComponent } from '../../../shared/components/input-moeda-real/input-moeda-real.component';
import { Transferir } from '../../../shared/models/beneficio';
import { BeneficioService } from '../../../core/services/beneficio.service';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-transferir-form',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButton,
    MatDialogModule,
    FormsModule,
    InputTypesComponent,
    InputMoedaRealComponent,],
  templateUrl: './transferir-form.component.html',
  styleUrl: './transferir-form.component.css',
})
export class TransferirFormComponent {
  transferir = signal<Transferir>({ fromId: 0, toId: 0, amount: 0.0 });
  ativar = signal<boolean>(false);

  constructor(private service: BeneficioService,
    private router: Router,
    private _snack: MatSnackBar
  ) { }

  cancelar(): void {
    this.router.navigate(['/beneficios/todos']);
  }

  salvar(): void {
    const transferirAcao = {
      ...this.transferir(),
    } as Transferir;
    this.service.tranferirBeneficio(transferirAcao).subscribe({
      next: (data) => {
        this._snack.open(data, 'Fechar', {
          duration: 5000,
          panelClass: ['error-snackbar']
        });
        this.router.navigate(['/beneficios/todos']);
      }, error: async (err) => {
        const msgErro = await err.error?.message || err.error || 'Erro ao transferir valor para benefício';

        this._snack.open(msgErro, 'Fechar', {
          duration: 5000,
          panelClass: ['error-snackbar']
        });
      }
    });
  }
}
