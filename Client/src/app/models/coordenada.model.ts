export class Coordenada {
    latitud: number;
    longitud: number;
  
    constructor(data: Partial<Coordenada> = {}) {
      this.latitud = data.latitud || 0;
      this.longitud = data.longitud || 0;
    }
  }