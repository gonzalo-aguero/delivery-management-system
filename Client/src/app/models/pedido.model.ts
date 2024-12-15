
import { DetallePedido } from './detalle-pedido.model';
import { Pago } from './pago.model';

export enum EstadoPedido {
  RECIBIDO='RECIBIDO'
  , EN_ENVIO='EN_ENVIO',
   EN_PROCESO='EN_PROCESO', PENDIENTE_DE_PAGO = 'PENDIENTE_DE_PAGO', ENTREGADO='ENTREGADO', FINALIZADO='FINALIZADO'
}
export class Pedido {
  id: number;
  idCliente: number;
  estado: string;
  detallePedido: DetallePedido[];
  datosPago: Pago;
 


  constructor(pedido: any) {
    this.id = pedido.id || 0;
    this.idCliente = pedido.clienteId || 0;
    this.estado = pedido.estado || '';
    this.detallePedido = (pedido.detallePedido || []).map((detalle: any) => new DetallePedido(detalle.itemId, detalle.cantidad));
    this.datosPago = new Pago(pedido.datosPago);
  }
}
