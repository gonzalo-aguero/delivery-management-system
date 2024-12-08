import { Cliente } from './cliente.model';
import { DetallePedido } from './detalle-pedido.model';
import { Pago } from './pago.model';

export class Pedido {
  id: number;
  cliente: Cliente;
  estado: string; 
  detallePedido: DetallePedido[];
  datosPago: Pago;
  formaPagoTipo: string;

  constructor(pedido: any) {
    this.id = pedido.id || 0;
    this.cliente = new Cliente(pedido.cliente);  
    this.estado = pedido.estado || '';
    this.detallePedido = (pedido.detallePedido || []).map((detalle: any) => new DetallePedido(detalle)); 
    this.datosPago = new Pago(pedido.datosPago);
    this.formaPagoTipo = pedido.datosPago ? pedido.datosPago.formaPago : '';
  }
}