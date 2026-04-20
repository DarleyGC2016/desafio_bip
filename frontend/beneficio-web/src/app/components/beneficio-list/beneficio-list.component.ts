import { Component, OnInit, signal } from '@angular/core';
import { BeneficioService } from '../../services/beneficio.service';
import { Beneficio } from '../../models/beneficio';
import { CommonModule } from '@angular/common';
import { TextoBrevePipe } from '../../pipes/texto-breve-pipe';

@Component({
  selector: 'app-beneficio-list',
  imports: [CommonModule, TextoBrevePipe],
  templateUrl: './beneficio-list.component.html',
  styleUrl: './beneficio-list.component.css',
})
export class BeneficioListComponent implements OnInit {
  listBeneficios = signal<Beneficio[]>([]);
  constructor(private beneficioService: BeneficioService) { }

  ngOnInit() {
    this.carregarBeneficios();
  }

  carregarBeneficios() {
    this.beneficioService.list(0, 10).subscribe({
      next: (dados) => {
        this.listBeneficios.set(dados);
      },
      error: (error) => {
        console.error('Erro ao carregar benefícios:', error);
      }
    });
  }
}

