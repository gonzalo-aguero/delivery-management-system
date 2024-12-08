export class Pago {
    id: number;
    pedidoId: number; 
    monto: number;
    formaPago: string;
    fecha: Date;
    nombreCliente: string;
    cuitCliente: string;
  
    constructor(pago: any) {
      this.id = pago.id || 0;
      this.pedidoId = pago.pedidoId || 0; 
      this.monto = pago.monto || 0;
      this.formaPago = pago.formaPago || '';
      this.fecha = pago.fecha || new Date();
      this.nombreCliente = pago.nombreCliente || '';
      this.cuitCliente = pago.cuitCliente || '';
    }
  }