import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Beneficio, Transferir } from '../../shared/models/beneficio';
import { PageResponse } from '../../shared/models/page';

@Injectable({
  providedIn: 'root',
})
export class BeneficioService {
  
  private readonly api: string = 'http://localhost:8080/api/v1/beneficios';
  constructor(private http: HttpClient) { }

  list(page: number, size: number): Observable<PageResponse<Beneficio>> {
    const params = new HttpParams();
    params.set("page", page.toString());
    params.set("size", size.toString());
    return this.http.get<PageResponse<Beneficio>>(`${this.api}/todos`, { params });
  };

  update(id: number, beneficio: Beneficio): Observable<string> {
    return this.http.put<string>(`${this.api}/editar/${id}`, beneficio, { responseType: 'text' as 'json' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete<string>(`${this.api}/excluir/${id}`, { responseType: 'text' as 'json' } );
  }

  detail(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.api}/detalhe/${id}`);
  }

  create(beneficio: Beneficio): Observable<string> {
    return this.http.post<string>(`${this.api}/novo`, beneficio, { responseType: 'text' as 'json' });
  }

  tranferirBeneficio(transfer: Transferir): Observable<string> {
    return this.http.put<string>(`${this.api}/transferir`, transfer, { responseType: 'text' as 'json' });
  }

}
