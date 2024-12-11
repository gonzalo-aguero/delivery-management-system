import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, FormsModule } from '@angular/forms';
import { ClienteService } from '../../../services/cliente.service';
import { Cliente } from '../../../models/cliente.model';
import { HttpClientModule } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
import { ClienteModalComponent } from '../../modals/cliente-modal/cliente-modal.component';
import { Coordenada } from '../../../models/coordenada.model';


@Component({
  selector: 'app-tabla-cliente',
  standalone: true,
  imports: [FormsModule, CommonModule,HttpClientModule,ClienteModalComponent],
  templateUrl: './tabla-cliente.component.html',
  styleUrl: './tabla-cliente.component.css'
})
export class TablaClienteComponent {


  filtro: string = '';

  clientes: Cliente[] = [];

  modal: ClienteModalComponent | null = null;

  clienteSeleccionado: Cliente | undefined = undefined;

  modo:string = '';

  constructor(private _clienteService: ClienteService){}

  clientesFiltrados = [...this.clientes];

  ngOnInit(): void {
    this.obtenerClientes();
  }

  async obtenerClientes() {
    try {
      const clientes = await lastValueFrom(this._clienteService.getClientes());
      this.clientes = clientes;
      this.clientesFiltrados = [...this.clientes];
    } catch (error) {
      console.error('Error al obtener los clientes:', error);
    }
  }

  crearCliente() {
    this.modo = "Crear";
    this.modal = new ClienteModalComponent();
  }

  editarCliente(cliente: any) {
    this.modo = "Editar";
    this.clienteSeleccionado = cliente;
    this.modal = new ClienteModalComponent();

  }

  async eliminarCliente(id: number) {
    try {
      await lastValueFrom(this._clienteService.deleteCliente(id));

      this.clientes = this.clientes.filter(cliente => cliente.id !== id);
      this.clientesFiltrados = [...this.clientes];
      console.log('Cliente eliminado con éxito');
    } catch (error) {
      console.error('Error al eliminar el cliente:', error);
    }
  }

  filtrarClientes() {
    const filtro = this.filtro.toLowerCase();
    this.clientesFiltrados = this.clientes.filter(cliente =>
      cliente.nombre.toLowerCase().includes(filtro)
    );
  }

  onCancel() {
   this.modal = null;
   this.modo = '';
   this.clienteSeleccionado = undefined;
  }

  onSubmit(cliente: Cliente) {

    console.log(cliente);
    if(this.modo == "Crear"){
      this._clienteService.crearCliente(cliente).subscribe((data) => {
        this.clientes.push(data);
        this.clientesFiltrados = [...this.clientes];
        this.modal = null;
        this.modo = '';
    }
      )
  }
  else if(this.modo == "Editar"){
    this._clienteService.editarCliente(cliente).subscribe((data) => {
      this.clientes.push(data);
      this.clientesFiltrados = [...this.clientes];
      this.modal = null;
      this.modo = '';
    }
    )
  }

  this.obtenerClientes();
  }



}
