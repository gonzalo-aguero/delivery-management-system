import { Coordenada } from './coordenada.model';

export class Vendedor {
  id: number;
  nombre: string;
  direccion: string;
  coordenadas: Coordenada;

  constructor(vendedor: any) {
    this.id = vendedor.id || 0;
    this.nombre = vendedor.nombre || '';
    this.direccion = vendedor.direccion || '';
    this.coordenadas = new Coordenada(vendedor.coordenadas);
  }
}