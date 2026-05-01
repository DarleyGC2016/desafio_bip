import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { InputMoedaRealComponent } from '../../../../shared/components/input-moeda-real/input-moeda-real.component';
import { InputAreaTextComponent } from '../../../../shared/components/input-area-text/input-area-text.component';
import { InputTypesComponent } from '../../../../shared/components/input-types/input-types.component';
import { MatButton } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { CommonModule } from '@angular/common';
import { Beneficio } from '../../../../shared/models/beneficio';
import { BeneficioService } from '../../../../core/services/beneficio.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-beneficio-form',
  imports: [
    CommonModule,
    MatCardModule,
    MatIconModule,
    MatButton,    
    FormsModule,
    InputTypesComponent,
    InputAreaTextComponent,
    InputMoedaRealComponent,
  ],
  templateUrl: './beneficio-form.component.html',
  styleUrl: './beneficio-form.component.css',
})
export class BeneficioFormComponent {

  beneficio = signal<Beneficio>({ nome: '', descricao: '', valor: 0.0, version: 0, ativo: true });
  ativar = signal<boolean>(false);

  constructor(private service: BeneficioService,
    private _snack: MatSnackBar,
    private router: Router) { }

 
  salvar() {
    const novoBeneficio = {
      ...this.beneficio(),
      nome: this.beneficio().nome,
      descricao: this.beneficio().descricao,
      valor: this.beneficio().valor
    } as Beneficio;
    
    this.service.create(novoBeneficio).subscribe({
      next: (data) =>{
          this._snack.open(data, 'Fechar', {
            duration: 5000,
            panelClass: ['error-snackbar']
          });
          this.router.navigate(['/beneficios/todos']);
      }, error: async (err) => {
          const msgErro = await err.error?.message || err.error || 'Erro ao cadastrar benefício';

          this._snack.open(msgErro, 'Fechar', {
            duration: 5000,
            panelClass: ['error-snackbar']
          });
        }
    });
  }

  cancelar(): void{
    this.router.navigate([""]);
  }
}
