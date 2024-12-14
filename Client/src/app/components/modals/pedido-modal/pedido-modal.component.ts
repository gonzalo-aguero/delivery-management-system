import { Component } from '@angular/core';
import { ItemMenu } from '../../../models/item-menu.model';
import { PedidoService } from '../../../services/pedido.service';
import { VendedorService } from '../../../services/vendedor.service';
import { Vendedor } from '../../../models/vendedor.model';
import { ItemMenuService } from '../../../services/item-menu.service';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-pedido-modal',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './pedido-modal.component.html',
  styleUrl: './pedido-modal.component.css'
})
export class PedidoModalComponent {


    pedir = true;

    vendedores : Vendedor[] = [];
    items : ItemMenu[] = [];
    itemSeleccionados : Array<[ItemMenu,number]> = new Array();

    selectedTI: number = -1;
    selectedTS: number = -1;
    
    seleccionar = true;
    total=0;
    subtotal = 0;

    pagoForm= new FormGroup({
      metodo: new FormControl(''),
      cuit: new FormControl(''),
      alias: new FormControl(''),
      cliente: new FormControl(''),
    });

    constructor(private _pedidoService: PedidoService, private _vendedorService:VendedorService, private _itemService:ItemMenuService){

    } 

    ngOnInit(){
      this._vendedorService.getVendedores().subscribe({
        next: (vendedores: Vendedor[]) => {
          this.vendedores = vendedores;
          },
          error: (error) => {
            console.error('Error al obtener los vendedores', error);
            }
      });
      this._itemService.getItemsByVendedor(this.vendedores[0].id.toString()).subscribe({
        next: (items: ItemMenu[]) => {
          this.items = items;
          },
          error: (error) => {
            console.error('Error al obtener los items', error);
            }
      })
    }

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
      if(this.seleccionar && this.selectedTI >= 0){
      this.itemSeleccionados.push([this.items[this.selectedTI], 1]);
      }else if(this.selectedTS >= 0){
        this.itemSeleccionados.splice(this.selectedTS);
      }
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
      })
    this.total = 0;
    this.itemSeleccionados = new Array();
    }

    onPedir(){
      console.log("PEDILOOO ",this.pedir)
      this.pedir = !this.pedir;
    }

    cambiaMetodo(event: Event) {
      const selectElement = event.target as HTMLSelectElement;
      const metodo = selectElement.value;
      this._pedidoService.calcularTotal([metodo,this.total]).subscribe({
        next: (subtotal: number) => {
          this.subtotal = subtotal;
          },
          error: (error)=>{
            console.error('Error al calcular el subtotal',error);
          }
      });
      }

}
