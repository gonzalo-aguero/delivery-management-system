import { ItemMenu } from './item-menu.model';

export class Bebida extends ItemMenu {
  override graduacionAlcoholica: number;
  override volumenEnMl: number;

  constructor(data: Partial<Bebida> = {}) {
    // Llama al constructor de la clase padre
    super(data);
    this.graduacionAlcoholica = data.graduacionAlcoholica || 0;
    this.volumenEnMl = data.volumenEnMl || 0;
  }
}