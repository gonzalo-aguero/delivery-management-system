import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tabla-pedido',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tabla-pedido.component.html',
  styleUrl: './tabla-pedido.component.css'
})
export class TablaPedidoComponent {
    filtro: string = '';
    pedidos = [
      { id: 1, idCliente: 1, estado: 'ENTREGADO', monto: 1000, formaDePago: 'Mercado Pago'},
      { id: 2, idCliente: 2, estado: 'RECIBIDO', monto: 2000, formaDePago: 'Transferencia'},
      { id: 3, idCliente: 3, estado: 'EN_ENVIO', monto: 1700, formaDePago: 'Transferencia'},
    ];
  
    pedidosFiltrados = [...this.pedidos];
  
    crearPedido() {
      alert('Crear pedido');
    }
  
    editarPedido(pedido: any) {
      alert(`Editar Pedido: ${pedido.nombre}`);
    }
  
    eliminarPedido(id: number) {
      this.pedidos = this.pedidos.filter(v => v.id !== id);
      this.filtrarPedidos();
      alert('Pedido eliminado');
    }
  
    filtrarPedidos() {
      const filtro = this.filtro.toLowerCase();
      this.pedidosFiltrados = this.pedidos.filter(pedido =>
        pedido.id.toString().includes(filtro)
      );
    }
  }
    
