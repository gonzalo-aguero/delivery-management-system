import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ItemMenu } from '../models/item-menu.model';
import { API_URL } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class ItemMenuService {
  
  private apiUrl = `${API_URL}/itemmenu`;

  constructor(private http:HttpClient) { }

  getItems(): Observable<ItemMenu[]> {
    return this.http.get<ItemMenu[]>(this.apiUrl);
  }
  deleteItem(id: number): Observable<ItemMenu>{
    return this.http.delete<ItemMenu>(`${this.apiUrl}/delete/${id}`);
  }

  crearItem(item : ItemMenu): Observable<ItemMenu> {
    return this.http.post<ItemMenu>(`${this.apiUrl}`,item);
  }

  editarItem(item : ItemMenu) {
    return this.http.put<ItemMenu>(`${this.apiUrl}`,item);
  }

  getItemsByVendedor(id : string): Observable<ItemMenu[]>{
    return this.http.get<ItemMenu[]>(`${this.apiUrl}?vendedor=${id}`);
  }
  
}
