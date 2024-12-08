import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tabla-vendedor',
  standalone: true,
  imports: [
    FormsModule, CommonModule
  ],
  templateUrl: './tabla-vendedor.component.html',
  styleUrl: './tabla-vendedor.component.css'
})
export class TablaVendedorComponent {
  filtro: string = '';
  vendedores = [
    { id: 1, nombre: 'Juan Pérez', direccion: 'Datos adicionales', coordenadas: { latitud: 0, longitud: 0 } },
    { id: 2, nombre: 'María López', direccion: 'Datos adicionales', coordenadas: { latitud: 0, longitud: 0 } },
    { id: 3, nombre: 'Carlos García', direccion: 'Datos adicionales', coordenadas: { latitud: 0, longitud: 0 } },
  ];

  vendedoresFiltrados = [...this.vendedores];

  crearVendedor() {
    alert('Crear vendedor');
  }

  editarVendedor(vendedor: any) {
    alert(`Editar vendedor: ${vendedor.nombre}`);
  }

  eliminarVendedor(id: number) {
    this.vendedores = this.vendedores.filter(v => v.id !== id);
    this.filtrarVendedores();
    alert('Vendedor eliminado');
  }

  filtrarVendedores() {
    const filtro = this.filtro.toLowerCase();
    this.vendedoresFiltrados = this.vendedores.filter(vendedor =>
      vendedor.nombre.toLowerCase().includes(filtro)
    );
  }
}
