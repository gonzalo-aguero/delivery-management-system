import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, FormsModule } from '@angular/forms';
import { PedidoService } from '../../../services/pedido.service';
import { Pedido } from '../../../models/pedido.model';
import { HttpClientModule } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
import { PedidoModalComponent } from '../../modals/pedido-modal/pedido-modal.component';
import { Cliente } from '../../../models/cliente.model';
import { ClienteService } from '../../../services/cliente.service';
import { EditarPedidoModalComponent } from "../../modals/editar-pedido-modal/editar-pedido-modal.component";

@Component({
  selector: 'app-tabla-pedido',
  standalone: true,
  imports: [FormsModule, CommonModule, HttpClientModule, PedidoModalComponent, EditarPedidoModalComponent],
  templateUrl: './tabla-pedido.component.html',
  styleUrl: './tabla-pedido.component.css'
})
export class TablaPedidoComponent {

    filtro: string = '';

    pedidos: Pedido[] = [];
    clientes: Cliente[] = [];


    pedidoSeleccionado: Pedido | undefined = undefined;

    modo:string = '';
    isModal= false;
    modalEditar= false;

    constructor(private _pedidoService: PedidoService, private _clienteService: ClienteService){}

    pedidosFiltrados = [...this.pedidos];

    ngOnInit(): void {
      this.obtenerPedidos();

      this._clienteService.getClientes().subscribe(res => {
        this.clientes = res;
      }, error => console.error(error));
    }

    async obtenerPedidos() {
      this._pedidoService.getPedidos().subscribe(res => {
        this.pedidos = res;
        this.filtrarPedidos();
        console.log(this.pedidos);
      }, error => console.error(error));
    }

    async refreshPedidos() {
      await this.obtenerPedidos();
    }


    async crearPedido() {
      this.modo = 'crear';
      this.isModal=true;

      await this.refreshPedidos();
    }


    async editarPedido(pedido: Pedido) {
      this.modo = 'editar';
      this.pedidoSeleccionado = pedido;
      this.modalEditar = true;
      await this.refreshPedidos();
    }


    async eliminarPedido(id: number) {
      try {
        this._pedidoService.deletePedido(id).subscribe();
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

    onCancel(){
      this.isModal = false;
      this.modalEditar = false;
    }

    getCliente(pedido: any){
      return this.clientes.find(c => c.id == Number(pedido.idCliente))?.nombre;
    }

    onEditarSubmit(pedido: Pedido) {
      this._pedidoService.editarPedido(pedido).subscribe((data) => {
        this.pedidos.push(data);
        this.pedidosFiltrados = [...this.pedidos];
        this.modalEditar = false;
        this.modo = '';
      });
    
    }

  }

