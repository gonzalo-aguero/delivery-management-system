import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, FormsModule } from '@angular/forms';
import { VendedorService } from '../../../services/vendedor.service';
import { Vendedor } from '../../../models/vendedor.model';
import { HttpClientModule } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
import { VendedorModalComponent } from '../../modals/vendedor-modal/vendedor-modal.component';
import { Coordenada } from '../../../models/coordenada.model';

@Component({
  selector: 'app-tabla-vendedor',
  standalone: true,
  imports: [FormsModule, CommonModule, VendedorModalComponent],
  templateUrl: './tabla-vendedor.component.html',
  styleUrls: ['./tabla-vendedor.component.css']
})
export class TablaVendedorComponent {
  filtro: string = '';

  vendedores: Vendedor[] = [];

  modal: VendedorModalComponent | null = null;

  vendedorSeleccionado: Vendedor | undefined = undefined;

  modo: string = '';

  constructor(private _vendedorService: VendedorService) {}

  vendedoresFiltrados = [...this.vendedores];

  ngOnInit(): void {
    this.obtenerVendedores();
  }

  async obtenerVendedores() {
    try {
      const vendedores = await lastValueFrom(this._vendedorService.getVendedores());
      console.log('Vendedores obtenidos:', vendedores);
      this.vendedores = vendedores;
      this.vendedoresFiltrados = [...this.vendedores];
      
    } catch (error) {
      console.error('Error al obtener los vendedores:', error);
    }
  }

  crearVendedor() {
    this.modo = 'Crear';
    this.modal = new VendedorModalComponent();
  }

  editarVendedor(vendedor: any) {
    this.modo = 'Editar';
    this.vendedorSeleccionado = vendedor;
    this.modal = new VendedorModalComponent();
  }

  async eliminarVendedor(id: number) {
    try {
      await lastValueFrom(this._vendedorService.deleteVendedor(id));
      this.vendedores = this.vendedores.filter(vendedor => vendedor.id !== id);
      this.vendedoresFiltrados = [...this.vendedores];
      console.log('Vendedor eliminado con éxito');
    } catch (error) {
      console.error('Error al eliminar el vendedor:', error);
    }
  }

  filtrarVendedores() {
    const filtro = this.filtro.toLowerCase();
    this.vendedoresFiltrados = this.vendedores.filter(vendedor =>
      vendedor.nombre.toLowerCase().includes(filtro)
    );
  }

  onCancel() {
    this.modal = null;
    this.modo = '';
    this.vendedorSeleccionado = undefined;
  }

  onSubmit(vendedor: Vendedor) {

    console.log(vendedor);
    if(this.modo == "Crear"){
      this._vendedorService.crearVendedor(vendedor).subscribe( (data) => {
        this.vendedores.push(data);
        this.vendedoresFiltrados = [...this.vendedores];
        this.modal = null;
        this.modo = '';
      });
    }else if(this.modo == "Editar"){
      this._vendedorService.editarVendedor(vendedor).subscribe((data) => {
        this.vendedores.push(data);
        this.vendedoresFiltrados = [...this.vendedores];
        this.modal = null;
        this.modo = '';
      });
    }

    this.obtenerVendedores();
  }


}
