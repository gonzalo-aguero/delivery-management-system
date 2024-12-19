import { Coordenada } from './coordenada.model';

export class Cliente {
  id: number;
  cuit: string;
  nombre: string;
  email: string;
  direccion: string;
  coordenadas?: Coordenada;

  constructor(data: Partial<Cliente> = {}) {
    this.id = data.id || 0;
    this.cuit = data.cuit || '';
    this.nombre = data.nombre || '';
    this.email = data.email || '';
    this.direccion = data.direccion || '';
    this.coordenadas = data.coordenadas ? new Coordenada(data.coordenadas) : undefined;
  }
}