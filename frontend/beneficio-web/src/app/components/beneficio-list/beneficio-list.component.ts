import { AfterViewInit, Component, OnInit, signal, ViewChild } from '@angular/core';
import { BeneficioService } from '../../services/beneficio.service';
import { Beneficio } from '../../models/beneficio';
import { CommonModule } from '@angular/common';
import { TextoBrevePipe } from '../../pipes/texto-breve-pipe';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { PageResponse } from '../../models/page';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-beneficio-list',
  imports: [
    CommonModule,
    TextoBrevePipe,
    MatTableModule,
    MatPaginatorModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './beneficio-list.component.html',
  styleUrl: './beneficio-list.component.css',
})
export class BeneficioListComponent implements AfterViewInit {
  
  displayedColumns = signal<string[]>(['Id', 'Nome', 'Descrição', 'Valor']);
  dataSource = new MatTableDataSource<Beneficio>();
  isLoading = signal(false);

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  
  constructor(private beneficioService: BeneficioService) { }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.carregarBeneficios();
  }

  carregarBeneficios() {
    this.isLoading.set(true);

    const page = this.paginator ? this.paginator.pageIndex : 0;
    const size = this.paginator ? this.paginator.pageSize : 10;

    const tempo = new Promise(response => setTimeout(response, 600));

    this.beneficioService.list(page, size).subscribe({
      next: async(response: PageResponse<Beneficio>) => {
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
}

