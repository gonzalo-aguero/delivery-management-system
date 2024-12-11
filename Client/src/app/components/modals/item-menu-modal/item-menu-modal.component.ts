import { Component, Input } from '@angular/core';
import { FormControl, FormGroup , ReactiveFormsModule } from '@angular/forms';
import { Vendedor } from '../../../models/vendedor.model';
import { Categoria } from '../../../models/categoria.model';

@Component({
  selector: 'app-item-menu-modal',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './item-menu-modal.component.html',
  styleUrl: './item-menu-modal.component.css'
})
export class ItemMenuModalComponent {

  @Input() modo = '';
  categorias : Categoria[] = [];
  vendedores : Vendedor[] = []; 
  tipos : string [] = ["Plato","Bebida"];




  itemForm = new FormGroup({
        nombre : new FormControl(''),
        tipo : new FormControl(''),
        descripcion : new FormControl(''),
        precio : new FormControl(''),
        categoria : new FormControl(''),
        calorias: new FormControl(''),
        celiaco: new FormControl(''),
        vegano: new FormControl(''),
        vegetariano: new FormControl(''),
        peso: new FormControl(''),
        graduacionAlcoholica: new FormControl(''),
        volumenEnMl : new FormControl('')
  });



}
