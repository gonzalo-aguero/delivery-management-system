
import { DetallePedido } from './detalle-pedido.model';
import { Pago } from './pago.model';

export class Pedido {
  id: number;
  clienteId: number;
  estado: string;
  detallePedido: DetallePedido[];
  datosPago: Pago;
  formaPagoTipo: string;

  constructor(pedido: any) {
    this.id = pedido.id || 0;
    this.clienteId = pedido.clienteId || 0;
    this.estado = pedido.estado || '';
    this.detallePedido = (pedido.detallePedido || []).map((detalle: any) => new DetallePedido(detalle));
    this.datosPago = new Pago(pedido.datosPago);
    this.formaPagoTipo = pedido.datosPago ? pedido.datosPago.formaPago : '';
  }
}
