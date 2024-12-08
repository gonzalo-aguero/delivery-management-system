import { Pedido } from './pedido.model';
import { ItemMenu } from './item-menu.model';

export class DetallePedido {
  id: number;
  pedido?: Pedido; 
  item: ItemMenu; 
  cantidad: number;

  constructor(data: Partial<DetallePedido> = {}) {
    this.id = data.id || 0;
    this.pedido = data.pedido ? new Pedido(data.pedido) : undefined;
    this.item = new ItemMenu(data.item);
    this.cantidad = data.cantidad || 0;
  }
}