import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, FormsModule } from '@angular/forms';
import { PedidoService } from '../../../services/pedido.service';
import { Pedido } from '../../../models/pedido.model';
import { HttpClientModule } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
import { PedidoModalComponent } from '../../modals/pedido-modal/pedido-modal.component';

@Component({
  selector: 'app-tabla-pedido',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule, PedidoModalComponent],
  templateUrl: './tabla-pedido.component.html',
  styleUrl: './tabla-pedido.component.css'
})
export class TablaPedidoComponent {
    filtro: string = '';

    pedidos: Pedido[] = [];

    modal: PedidoModalComponent | null = null;

    pedidoSeleccionado: Pedido | undefined = undefined;

    modo:string = '';

    constructor(private _pedidoService: PedidoService){}

    pedidosFiltrados = [...this.pedidos];

    ngOnInit(): void {
      this.obtenerPedidos();
    }

    async obtenerPedidos() {
      try {
        const pedidos = await lastValueFrom(this._pedidoService.getPedidos());
        this.pedidos = pedidos;
        this.pedidosFiltrados = [...this.pedidos];
        console.log("PEDIDOS OBTENIDOS:", this.pedidos);
      } catch (error) {
        console.error('Error al obtener los pedidos:', error);
        alert('Error al cargar los pedidos');
      }
    }

    async refreshPedidos() {
      await this.obtenerPedidos();
    }

    // Actualizar método crearPedido
    async crearPedido() {
      this.modo = 'crear';
      this.pedidoSeleccionado = undefined;
      // Después de crear, refrescar lista
      await this.refreshPedidos();
    }

    // Actualizar método editarPedido
    async editarPedido(pedido: Pedido) {
      this.modo = 'editar';
      this.pedidoSeleccionado = pedido;
      await this.refreshPedidos();
    }

    // Actualizar método eliminarPedido
    async eliminarPedido(id: number) {
      try {
        this._pedidoService.deletePedido(id);
        await this.refreshPedidos();
        alert('Pedido eliminado correctamente');
      } catch (error) {
        console.error('Error al eliminar pedido:', error);
        alert('Error al eliminar el pedido');
      }
    }

    filtrarPedidos() {
      const filtro = this.filtro.toLowerCase();
      this.pedidosFiltrados = this.pedidos.filter(pedido =>
        pedido.id.toString().includes(filtro)
      );
    }
  }

