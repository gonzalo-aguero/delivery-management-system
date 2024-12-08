import { ItemMenu } from './item-menu.model';

export class Plato extends ItemMenu {
  override peso: number;

  constructor(plato: any) {
    super(plato);  
    this.peso = plato.peso || 0;
  }
}