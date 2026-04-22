import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Beneficio, Transferir } from '../models/beneficio';
import { PageResponse } from '../models/page';

@Injectable({
  providedIn: 'root',
})
export class BeneficioService {
  private readonly api: string = 'http://localhost:8080/api/v1';
  constructor(private http: HttpClient) { }

  list(page: number, size: number): Observable<PageResponse<Beneficio>> {
    const params = new HttpParams();
    params.set("page", page.toString());
    params.set("size", size.toString());
    return this.http.get<PageResponse<Beneficio>>(`${this.api}/beneficios`, { params });
  };

  update(id: number, beneficio: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${this.api}/beneficios/${id}`, beneficio);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/beneficio/delete/${id}`);
  }

  detail(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.api}/beneficio/${id}`);
  }

  create(beneficio: Beneficio): Observable<Beneficio> {
    return this.http.post<Beneficio>(`${this.api}/beneficio/novo`, beneficio);
  }

  tranferirBeneficio(transfer: Transferir): Observable<String> {
    return this.http.put<String>(`${this.api}/transferir`, transfer, { responseType: 'text' as 'json' });
  }

}
