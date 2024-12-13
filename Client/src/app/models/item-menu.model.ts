import { Categoria } from './categoria.model';
import { Vendedor } from './vendedor.model';

export enum Tipo {
  PLATO = 'PLATO',
  BEBIDA = 'BEBIDA'
}


export class ItemMenu {
  

  id: number;
  nombre: string;
  tipo?:Tipo;
  descripcion: string;
  precio: number;
  categoria: Categoria;
  categoriaId: Number;
  calorias: number;
  aptoCeliaco: boolean;
  aptoVegetariano: boolean;
  aptoVegano: boolean;
  vendedor: Vendedor;
  vendedorId: Vendedor;
  peso?: number;
  graduacionAlcoholica?: number;
  volumenEnMl?: number;


  constructor(itemMenu: any) {
    this.id = itemMenu.id || 0;
    this.tipo = itemMenu.tipo;
    this.nombre = itemMenu.nombre || '';
    this.descripcion = itemMenu.descripcion || '';
    this.precio = itemMenu.precio || 0;
    this.categoria = itemMenu.categoria || null;
    this.categoriaId = itemMenu.categoriaId || null;
    this.calorias = itemMenu.calorias || 0;
    this.aptoCeliaco = itemMenu.aptoCeliaco || false;
    this.aptoVegetariano = itemMenu.aptoVegetariano || false;
    this.aptoVegano = itemMenu.aptoVegano || false;
    this.vendedor = itemMenu.vendedor || 0;
    this.vendedorId = itemMenu.vendedorId || 0;
    if(this.tipo == Tipo.PLATO) this.peso = itemMenu.peso || 0;
    else{
      this.graduacionAlcoholica = itemMenu.graduacionAlcoholica || 0;
      this.volumenEnMl = itemMenu.volumenEnMl || 0;
    }
    
  }
}