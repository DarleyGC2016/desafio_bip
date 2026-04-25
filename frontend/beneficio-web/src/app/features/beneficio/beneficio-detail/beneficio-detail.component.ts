import { Component, Input, OnInit, signal } from '@angular/core';
import { BeneficioService } from '../../../core/services/beneficio.service';
import { Beneficio } from '../../../models/beneficio';
import { CommonModule } from '@angular/common';
import { InputTypesComponent } from '../../../shared/components/input-types/input-types.component';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-beneficio-detail',
  standalone: true,
  imports: [
    CommonModule,
    InputTypesComponent,
    MatCardModule,
    MatIconModule,
    MatButton
  ],
  templateUrl: './beneficio-detail.component.html',
  styleUrl: './beneficio-detail.component.css',
})

export class BeneficioDetailComponent implements OnInit {

  @Input() id!: string;

  idRouter: number = 0;

  benficio = signal<Beneficio | undefined>(undefined);

  constructor(private service: BeneficioService) { }

  ngOnInit(): void {
    this.buscarUnicoBeneficio();
  }

  buscarUnicoBeneficio(): void {
    this.idRouter = Number(this.id);
    console.log("Id da rota: ", this.idRouter);
    this.service.detail(this.idRouter).subscribe({
      next: data => {
        this.benficio.set(data);

      }, error(err) {
        console.log(err);
      },
    })
  }

}
