import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ItemMenuModalComponent } from '../../modals/item-menu-modal/item-menu-modal.component';
import { ItemMenu } from '../../../models/item-menu.model';
import { ItemMenuService } from '../../../services/item-menu.service';
import { VendedorService } from '../../../services/vendedor.service';
import { CategoriaService } from '../../../services/categoria.service';
import { lastValueFrom } from 'rxjs';
import { Categoria } from '../../../models/categoria.model';
import { Vendedor } from '../../../models/vendedor.model';

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
  modo: string = '';


  vendedores : Vendedor[] = [];
  categorias : Categoria[] = [];
  items : ItemMenu[] = [];

  itemsFiltrados = [...this.items];
  itemSeleccionado: any;


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


  async crearItem() {
    this.modo = 'Crear';
  
    try {
      // Espera a que categorías y vendedores se carguen
      const [categorias, vendedores] = await Promise.all([
        this.getCategorias(),
        this.getVendedores(),
      ]);
  
      this.categorias= categorias;
      this.vendedores= vendedores;
      this.modal = new ItemMenuModalComponent();
  
      console.log('Categorías y vendedores cargados:', categorias, vendedores);
    } catch (error) {
      console.error('Error al cargar datos para crear el ítem:', error);
    }
  }

  async editarItem(item: any) {
    this.modo = "Editar";
    try {
      // Espera a que categorías y vendedores se carguen
      const [categorias, vendedores] = await Promise.all([
        this.getCategorias(),
        this.getVendedores(),
      ]);
  
      this.categorias= categorias;
      this.vendedores= vendedores;
      this.itemSeleccionado = item;
      this.modal = new ItemMenuModalComponent();
  
      console.log('Categorías y vendedores cargados:', categorias, vendedores);
    } catch (error) {
      console.error('Error al cargar datos para crear el ítem:', error);
    }
    
    
  }

  async eliminarItem(id: number) {
    try {
      await lastValueFrom(this._itemMenuService.deleteItem(id));
      this.items = this.items.filter(items => items.id !== id);
      this.itemsFiltrados = [...this.items];
      console.log('Cliente eliminado con éxito');
    } catch (error) {
      console.error('Error al eliminar el item:', error);
    }
  }

  filtrarItems() {
    const filtro = this.filtro.toLowerCase();
    this.itemsFiltrados = this.items.filter(item =>
      item.nombre.toLowerCase().includes(filtro)
    );
  }

  onCancel() {
    this.modal = undefined;
    this.modo = ''  
    this.itemSeleccionado = undefined;
  }

  onSubmit(item: ItemMenu){
    if(this.modo == "Crear"){
      this._itemMenuService.crearItem(item).subscribe((data) => {
        this.items.push(data);
        this.itemsFiltrados = [...this.items];
        this.modal = undefined;
        this.modo = '';
    }
      )
  }
  else if(this.modo == "Editar"){
    this._itemMenuService.editarItem(item).subscribe((data) => {
      this.items.push(data);
      this.itemsFiltrados = [...this.items];
      this.modal = undefined;
      this.modo = '';
      this.itemSeleccionado=undefined;
    }
    )
  }
  }
  async getCategorias(): Promise<Categoria[]> {
    try {
      const categorias = await lastValueFrom(this._categoriaService.getCategorias());
      return categorias; 
    } catch (error) {
      console.error('Error al obtener categorías:', error);
      return []; 
    }
  }
  
  async getVendedores(): Promise<Vendedor[]> {
    try {
      const vendedores = await lastValueFrom(this._vendedorService.getVendedores());
      return vendedores;
    } catch (error) {
      console.error('Error al obtener vendedores:', error);
      return []; 
    }
  }

  
}
  