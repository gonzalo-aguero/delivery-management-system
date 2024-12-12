import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup , ReactiveFormsModule } from '@angular/forms';
import { Vendedor } from '../../../models/vendedor.model';
import { Categoria } from '../../../models/categoria.model';
import { ItemMenu, Tipo } from '../../../models/item-menu.model';

@Component({
  selector: 'app-item-menu-modal',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './item-menu-modal.component.html',
  styleUrl: './item-menu-modal.component.css'
})
export class ItemMenuModalComponent {

  @Input() modo = '';
  @Input() categorias : Categoria[] = [];
  @Input() vendedores : Vendedor[] = []; 
  @Input() itemMenu?: ItemMenu;

  @Output() submit = new EventEmitter<ItemMenu>();
  @Output() cancel = new EventEmitter<void>();

  tipos : string [] = ["PLATO","BEBIDA"];

  itemForm = new FormGroup({
        vendedor: new FormControl<Vendedor | null>(null),
        nombre : new FormControl(''),
        tipo : new FormControl<Tipo | null>(null),
        descripcion : new FormControl(''),
        precio : new FormControl(''),
        categoria : new FormControl<Categoria | null>(null),
        calorias: new FormControl(''),
        celiaco: new FormControl(false),
        vegano: new FormControl(false),
        vegetariano: new FormControl(false),
        peso: new FormControl(''),
        graduacion: new FormControl(''),
        volumen : new FormControl('')
  });

  ngOnInit(){
    if(this.itemMenu){
      console.log(this.itemMenu)
      this.rellenarFormulario(this.itemMenu)
    }
  }

  onSubmit(){
    if(this.itemForm.valid){
        const itemMenu = new ItemMenu({
          id: this.itemMenu ? this.itemMenu.id : 0 ,
          nombre : this.itemForm.get('nombre')?.value,
          descripcion : this.itemForm.get('descripcion')?.value,
          precio : this.itemForm.get('precio')?.value,
          categoria: this.itemForm.get('categoria')?.value,
          calorias : this.itemForm.get('calorias')?.value,
          celiaco : this.itemForm.get('celiaco')?.value,
          vegano : this.itemForm.get('vegano')?.value,
          vegetariano : this.itemForm.get('vegetariano')?.value,
          vendedor: this.itemForm.get('vendedor')?.value
        })

          if (this.itemForm.get('tipo')?.value == 'PLATO') {
            itemMenu.tipo = Tipo.PLATO;
            itemMenu.peso = Number(this.itemForm.get('peso')?.value);
          } else if(this.itemForm.get('tipo')?.value == 'BEBIDA') {
            itemMenu.tipo = Tipo.BEBIDA;
            itemMenu.graduacionAlcoholica = Number(this.itemForm.get('graduacion')?.value);
            itemMenu.volumenEnMl = Number(this.itemForm.get('volumen')?.value);
          }
          else{
            throw new Error('Tipo erroneo ' + this.itemForm.get('tipo')?.value)
          }
          console.log('Categoria:', JSON.stringify(itemMenu.categoria));
          console.log('Vendedor:', JSON.stringify(itemMenu.vendedor));
          console.log("Item menu creado: " , itemMenu)
          console.log(itemMenu.categoria)
          this.submit.emit(itemMenu); 
          return;
    }
    else throw new Error('Formulario invalido');
  }

  onCancel(){
    this.cancel.emit();
  }


  rellenarFormulario(itemMenu: ItemMenu) {
    this.itemForm.patchValue({
      nombre: itemMenu.nombre,
      vendedor: itemMenu.vendedor,
      descripcion: itemMenu.descripcion,
      tipo: itemMenu.tipo,
      precio: String(itemMenu.precio),
      categoria: itemMenu.categoria,
      calorias: String(itemMenu.calorias),
      celiaco: itemMenu.aptoCeliaco,
      vegano: itemMenu.aptoVegano,
      vegetariano: itemMenu.aptoVegetariano,
    })
    if(itemMenu.tipo == 'PLATO'){
      this.itemForm.patchValue({
      peso : String(itemMenu.peso)
      })
    }else if(itemMenu.tipo == 'BEBIDA'){
    this.itemForm.patchValue({
      graduacion : String(itemMenu.graduacionAlcoholica),
      volumen : String(itemMenu.volumenEnMl)
    })
    }
  }

}
