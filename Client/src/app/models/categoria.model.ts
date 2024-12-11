export class Categoria{
    id: number;
    descripcion: string;
    tipoItem: string;
  
    constructor(data: Partial<Categoria> = {}) {
      this.id = data.id || 0;
      this.descripcion = data.descripcion || '';
      this.tipoItem = data.tipoItem || '';
    }
  }