import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ItemMenuModalComponent } from '../../modals/item-menu-modal/item-menu-modal.component';
import { ItemMenu } from '../../../models/item-menu.model';
import { ItemMenuService } from '../../../services/item-menu.service';
import { VendedorService } from '../../../services/vendedor.service';
import { CategoriaService } from '../../../services/categoria.service';
import { lastValueFrom } from 'rxjs';

@Component({
  selector: 'app-tabla-item-menu',
  standalone: true,
  imports: [FormsModule, CommonModule,ItemMenuModalComponent],
  templateUrl: './tabla-item-menu.component.html',
  styleUrl: './tabla-item-menu.component.css'
})
export class TablaItemMenuComponent {
  filtro: string = '';
  modal?: ItemMenuModalComponent;

  items : ItemMenu[] = [];

  itemsFiltrados = [...this.items];


  constructor(private _itemMenuService:ItemMenuService,private _vendedorService:VendedorService,private _categoriaService: CategoriaService){}

  ngOnInit(){
    this.obtenerItems();
  }
  
  async obtenerItems() {
    try {
      const items = await lastValueFrom(this._itemMenuService.getItems());
      console.log('Items obtenidos:', items);
      this.items = items;
      this.itemsFiltrados = [...this.items];
    } catch (error) {
      console.error('Error al obtener los items:', error);
    }
  }


  crearItem() {
    alert('Crear pedido');
  }

  editarItem(item: any) {
    alert(`Editar item: ${item.nombre}`);
  }

  eliminarItem(id: number) {
    this.items = this.items.filter(v => v.id !== id);
    this.filtrarItems();
    alert('Item eliminado');
  }

  filtrarItems() {
    const filtro = this.filtro.toLowerCase();
    this.itemsFiltrados = this.items.filter(item =>
      item.nombre.toLowerCase().includes(filtro)
    );
  }
}
  