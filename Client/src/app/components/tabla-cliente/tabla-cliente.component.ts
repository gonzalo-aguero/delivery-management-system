import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-tabla-cliente',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tabla-cliente.component.html',
  styleUrl: './tabla-cliente.component.css'
})
export class TablaClienteComponent {
  filtro: string = '';
  clientes = [
    { id: 1, cuit: '20-54231892-2', nombre: 'Juan Pérez', email: 'asd@hot.com', direccion: 'Datos adicionales', coordenadas: { latitud: 0, longitud: 0 } },
    { id: 2, cuit: '20-54231892-2', nombre: 'María López', email: 'asd@hot.com', direccion: 'Datos adicionales', coordenadas: { latitud: 0, longitud: 0 } },
    { id: 3, cuit: '20-54231892-2', nombre: 'Carlos García', email: 'asd@hot.com', direccion: 'Datos adicionales', coordenadas: { latitud: 0, longitud: 0 } },
  ];

  clientesFiltrados = [...this.clientes];

  crearCliente() {
    alert('Crear cliente');
  }

  editarCliente(cliente: any) {
    alert(`Editar cliente: ${cliente.nombre}`);
  }

  eliminarCliente(id: number) {
    this.clientes = this.clientes.filter(v => v.id !== id);
    this.filtrarClientes();
    alert('Cliente eliminado');
  }

  filtrarClientes() {
    const filtro = this.filtro.toLowerCase();
    this.clientesFiltrados = this.clientes.filter(cliente =>
      cliente.nombre.toLowerCase().includes(filtro)
    );
  }
}
  