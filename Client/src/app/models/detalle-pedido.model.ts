import { Pedido } from './pedido.model';
import { ItemMenu } from './item-menu.model';

export class DetallePedido {
  itemId: number; 
  cantidad: number;

  constructor(data: Partial<DetallePedido> = {}) {
    this.itemId = data.itemId || 0;
    this.cantidad = data.cantidad || 0;
  }
}