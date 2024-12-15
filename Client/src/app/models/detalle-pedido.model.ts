import { Pedido } from './pedido.model';
import { ItemMenu } from './item-menu.model';

export class DetallePedido {
  idItemMenu: number; 
  cantidad: number;

  constructor(id: number, cantidad: number) {
    this.idItemMenu = id || 0;
    this.cantidad = cantidad || 0;
  }
}