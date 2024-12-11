import { Plato } from './plato.model';
import { Bebida } from './bebida.model';
import { Categoria } from './categoria.model';
import { Vendedor } from './vendedor.model';

export class ItemMenu {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  categoria: Categoria;
  calorias: number;
  aptoCeliaco: boolean;
  aptoVegetariano: boolean;
  aptoVegano: boolean;
  vendedor: Vendedor;
  peso?: number;
  graduacionAlcoholica?: number; 
  volumenEnMl?: number;  

  constructor(itemMenu: any) {
    this.id = itemMenu.id || 0;
    this.nombre = itemMenu.nombre || '';
    this.descripcion = itemMenu.descripcion || '';
    this.precio = itemMenu.precio || 0;
    this.categoria = itemMenu.categoria || null;
    this.calorias = itemMenu.calorias || 0;
    this.aptoCeliaco = itemMenu.aptoCeliaco || false;
    this.aptoVegetariano = itemMenu.aptoVegetariano || false;
    this.aptoVegano = itemMenu.aptoVegano || false;
    this.vendedor = itemMenu.vendedorId || 0;

  
    if (itemMenu instanceof Plato) {
      this.peso = itemMenu.peso || 0;
    } else if (itemMenu instanceof Bebida) {
      this.graduacionAlcoholica = itemMenu.graduacionAlcoholica || 0;
      this.volumenEnMl = itemMenu.volumenEnMl || 0;
    }
  }
}