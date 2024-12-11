import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Categoria } from '../models/categoria.model';
import { HttpClient } from '@angular/common/http';
import { API_URL } from '../environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  
  private apiUrl = `${API_URL}/categoria`;


  constructor(private http: HttpClient){}
  
  getCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.apiUrl);
  }
    
    
  }


