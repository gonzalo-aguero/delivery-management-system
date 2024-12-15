import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_URL } from '../environment';
import { Pedido } from '../models/pedido.model';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {
  

  private apiUrl = `${API_URL}/pedido`;

  constructor(private http:HttpClient) { }

  getPedidoById(id : number): Observable<Pedido>{
    return this.http.get<Pedido>(`${this.apiUrl}/${id}`);
  }

  getPedidos(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(this.apiUrl);
  }
  deletePedido(id: number): Observable<Pedido>{
    return this.http.delete<Pedido>(`${this.apiUrl}/delete/${id}`);
  }

  crearPedido(pedido : Pedido): Observable<Pedido> {
    return this.http.post<Pedido>(`${this.apiUrl}`,pedido);
  }

  editarPedido(pedido : Pedido) {
    return this.http.put<Pedido>(`${this.apiUrl}`,pedido);
  }

  calcularTotal(data: (string | number)[]) : Observable<number>{
    return this.http.get<number>(`${this.apiUrl}?monto=${data[1]}&formaPago=${data[0]}`);
  }
}
