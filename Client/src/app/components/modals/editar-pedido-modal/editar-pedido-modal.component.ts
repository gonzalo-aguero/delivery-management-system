import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup , FormControl, ReactiveFormsModule } from '@angular/forms';
import { PedidoService } from '../../../services/pedido.service';
import { EstadoPedido, Pedido } from '../../../models/pedido.model';

@Component({
  selector: 'app-editar-pedido-modal',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './editar-pedido-modal.component.html',
  styleUrl: './editar-pedido-modal.component.css'
})
export class EditarPedidoModalComponent {


    subtotal= 0;
    recargo = 0;
    @Input() pedidoId = 0;
    

    @Output() cancel = new EventEmitter<void>();
    @Output() submit = new EventEmitter<Pedido>();


    pedido? : Pedido = undefined;
    estados = ['RECIBIDO'
      , 'EN_ENVIO',
       'EN_PROCESO', 'PENDIENTE_DE_PAGO','ENTREGADO', 'FINALIZADO']
  
    pedidoForm= new FormGroup({
      estado: new FormControl(''),
      formapago: new FormControl(''),
      cbu: new FormControl(''),  
      cuit: new FormControl('') 
    });

    constructor(private _pedidoService:PedidoService){}

    ngOnInit(){
      this._pedidoService.getPedidoById(this.pedidoId).subscribe({
        next: (data) => {
          this.pedido = data;
          this.pedidoForm.patchValue(data);
          this.subtotal = data.datosPago.monto;
          },
      })
    }

    onSubmit(event: any) {

      const formData = this.pedidoForm.value;

      if(this.pedido){
      const updatedPedido = new Pedido({
        id: this.pedidoId,
        clienteId: this.pedido.clienteId,
        estado: formData.estado,
        detallePedido: [], 
        datosPago: {
          monto: this.subtotal,
          formaPago: formData.formapago,
          cbu: formData.cbu,
          cuit: formData.cuit
        },
        formaPagoTipo: formData.formapago
      })
      this.submit.emit(updatedPedido);
    } else throw Error("Pedido nulo")
      
    }
    onCancel() {
      this.cancel.emit();
      }
      
    onChange(event : Event) {
      const target = event.target as HTMLInputElement;
      const value = target.value;

      if(value == "mercadopago"){
        this.recargo = 4;
      }else{
        this.recargo = 2;
      }
      }

}
