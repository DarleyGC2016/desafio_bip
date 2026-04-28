import { AfterViewInit, Component, signal, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButton } from '@angular/material/button';
import { Router } from '@angular/router';

import { Beneficio } from '../../../shared/models/beneficio';
import { BeneficioService } from '../../../core/services/beneficio.service';
import { PageResponse } from '../../../shared/models/page';
import { TextoBrevePipe } from '../../../shared/pipes/texto-breve/texto-breve.pipe';

@Component({
  selector: 'app-beneficio-list',
  imports: [
    CommonModule,
    TextoBrevePipe,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatIconModule,
    MatButton
  ],
  templateUrl: './beneficio-list.component.html',
  styleUrl: './beneficio-list.component.css',
})
export class BeneficioListComponent implements AfterViewInit {

  displayedColumns = signal<string[]>(['Id', 'Nome', 'Descrição', 'Valor', "Ações"]);
  dataSource = new MatTableDataSource<Beneficio>();
  isLoading = signal(false);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  

  constructor(private beneficioService: BeneficioService, 
    private router: Router
  ) { }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.carregarBeneficios();
  }

  carregarBeneficios(): void {
    this.isLoading.set(true);

    const page = this.paginator ? this.paginator.pageIndex : 0;
    const size = this.paginator ? this.paginator.pageSize : 10;

    const tempo = new Promise(response => setTimeout(response, 600));

    this.beneficioService.list(page, size).subscribe({
      next: async (response: PageResponse<Beneficio>) => {
        await tempo;
        this.dataSource.data = response.content;
        this.paginator.length = response.totalElements;
        this.isLoading.set(false);
      },
      error: (error) => {
        console.error('Erro ao carregar benefícios deste enpoint:', error.url);
        this.isLoading.set(false);
      }
    });
  }

  enviarBeneficioEscolhido(id: number): void {
        console.log('ID - beneficio: ',id);
        
        this.router.navigate(['/beneficios',id]);
  }
}

