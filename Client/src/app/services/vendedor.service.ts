import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { API_URL } from '../environment';
import { Observable } from 'rxjs';
import { Vendedor } from '../models/vendedor.model';

@Injectable({
  providedIn: 'root'
})
export class VendedorService {
  private apiUrl = `${API_URL}/vendedor`;

  constructor(private http: HttpClient) { }

  getVendedores(): Observable<Vendedor[]> {
    return this.http.get<Vendedor[]>(this.apiUrl);
  }

  deleteVendedor(id: number): Observable<Vendedor>{
    return this.http.delete<Vendedor>(`${this.apiUrl}/delete/${id}`);
  }

  crearVendedor(vendedor: Vendedor): Observable<Vendedor> {
    return this.http.post<Vendedor>(`${this.apiUrl}`,vendedor);
  }

  editarVendedor(vendedor: Vendedor) {
    return this.http.put<Vendedor>(`${this.apiUrl}`,vendedor);
  }
}
