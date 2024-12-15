import { Component, EventEmitter, Output } from '@angular/core';
import { ItemMenu } from '../../../models/item-menu.model';
import { PedidoService } from '../../../services/pedido.service';
import { VendedorService } from '../../../services/vendedor.service';
import { Vendedor } from '../../../models/vendedor.model';
import { ItemMenuService } from '../../../services/item-menu.service';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Cliente } from '../../../models/cliente.model';
import { ClienteService } from '../../../services/cliente.service';
import { Pedido } from '../../../models/pedido.model';

@Component({
  selector: 'app-pedido-modal',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule, FormsModule],
  templateUrl: './pedido-modal.component.html',
  styleUrl: './pedido-modal.component.css'
})
export class PedidoModalComponent {

  @Output() cancel: EventEmitter<void> = new EventEmitter<void>();
  
  pedir = true;

  vendedores : Vendedor[] = [];
  clientes: Cliente[] = [];
  items : ItemMenu[] = [];
  itemSeleccionados : Array<[ItemMenu,number]> = new Array();

  selectedTI: number = -1;
  selectedTS: number = -1;
  
  seleccionar = true;
  total=0;
  subtotal = 0;

  cantidad = new FormGroup({
    cantidad: new FormControl(1, Validators.required)
  });

  pagoForm= new FormGroup({
    metodo: new FormControl('', Validators.required),
    cuit: new FormControl({value:'',disabled:true}, Validators.required),
    alias: new FormControl('', Validators.required),
    cliente: new FormControl('', Validators.required),
  });

  constructor(private _pedidoService: PedidoService, private _vendedorService:VendedorService, private _itemService:ItemMenuService, private _clienteService: ClienteService){

  } 

  ngOnInit(){
    this._vendedorService.getVendedores().subscribe(res => {
      this.vendedores = res;

      this._itemService.getItemsByVendedor(this.vendedores[0].id.toString()).subscribe(res => {
        this.items = res;
      }, error=> console.error(error));

    }, error => console.error(error));

    this._clienteService.getClientes().subscribe({
      next: (clientes: Cliente[]) => {
        this.clientes = clientes;
      },
      error: (error) => {
        console.error('Error al obtener los clientes', error);
      }
    });
  };

  selectTI(i:number){
    this.selectedTI = i;
    this.seleccionar = true;
    this.selectedTS = -1;
  }

  selectTS(i:number){
    this.selectedTS = i;
    this.seleccionar = false;
    this.selectedTI = -1;
  }

  seleccionarBtn(){
    if(this.selectedTI >= this.items.length) return;
    if(this.seleccionar && this.selectedTI >= 0){
      this.itemSeleccionados.push([this.items[this.selectedTI], Number(this.cantidad.value.cantidad)]);
      this.items.splice(this.selectedTI, 1);
      this.cantidad.controls.cantidad.setValue(1);
    }else if(this.selectedTS >= 0){
      this.items.push(this.itemSeleccionados[this.selectedTS][0]);
      this.items.sort((a,b) => a.id - b.id);
      this.itemSeleccionados.splice(this.selectedTS, 1);
    }
    this.total = this.itemSeleccionados.reduce((acc, [item, cantidad]) => acc + item.precio * cantidad, 0);
  }

  onSelectChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const idVendedor = selectElement.value;

    this._itemService.getItemsByVendedor(idVendedor).subscribe({
      next: (items: ItemMenu[]) => {
          this.items = items;
        },
        error: (error) => {
          console.error('Error al obtener los items', error);
        }
    });
    this.total = 0;
    this.itemSeleccionados = new Array();
  }

  onPedir(){
    console.log("PEDILOOO ",this.pedir)
    this.pedir = !this.pedir;
  }

  cambiaMetodo(event: Event) {
    if(this.pagoForm.value.metodo === 'transferencia'){
      this.subtotal = this.total * (1.02);
    } else if(this.pagoForm.value.metodo === 'mercadopago'){
      this.subtotal = this.total*1.04;
    }
  }

  onCancel(){
    this.cancel.emit();
  }

  cambiaCliente(){
    var cliente = this.clientes.find(c => c.id == Number(this.pagoForm.value.cliente));
    if (cliente) {
      this.pagoForm.controls.cuit.setValue(cliente.cuit);
    }
  }

  onEnviarPedido(){
    var pedido = new Pedido({
      id: 0,
      clienteId: Number(this.pagoForm.value.cliente),
      estado: 'PENDIENTE_DE_PAGO',
      detallePedido: this.itemSeleccionados.map(([item, cantidad]) => ({itemId: item.id, cantidad})),
      datosPago: {monto: this.subtotal, formaPago: this.pagoForm.value.metodo, cuitCliente: this.pagoForm.value.cuit, nombreCliente: this.clientes.find(c => c.id == Number(this.pagoForm.value.cliente))?.nombre},
    });    
    console.log(this.items);
    console.log(pedido);
    this._pedidoService.crearPedido(pedido).subscribe(res => {
      alert('Pedido enviado correctamente');
      this.cancel.emit();
    }, error => {
      console.error('Error al enviar el pedido:', error);
      alert('Error al enviar el pedido');
    });
  }
}
