import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tabla-item-menu',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tabla-item-menu.component.html',
  styleUrl: './tabla-item-menu.component.css'
})
export class TablaItemMenuComponent {
  filtro: string = '';
  items = [
    { id: 1, nombre: 'Milanesa de Pollo', precio: 1000, calorias: 800, categoria: 'COMIDA', aptoPara: 'Celiaco'},
    { id: 2, nombre: 'Milanesa de Carne', precio: 1000, calorias: 700, categoria: 'COMIDA', aptoPara: ''},
    { id: 3, nombre: 'Cerveza', precio: 1000, calorias: 400, categoria: 'BEBIDA', aptoPara: 'Vegano'},
  ];

  itemsFiltrados = [...this.items];

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
  