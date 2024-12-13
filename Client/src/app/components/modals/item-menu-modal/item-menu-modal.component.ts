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
        peso: new FormControl({value: '', disabled: true}),
        graduacion: new FormControl({value: '', disabled: true}),
        volumen : new FormControl({value: '', disabled: true})
  });

  ngOnInit(){
    if(this.itemMenu){
      console.log(this.itemMenu)
      this.rellenarFormulario(this.itemMenu)
    }
  }

  onSubmit(){
    if(this.itemForm.valid){
      console.log(this.itemForm.value)
        const itemMenu = new ItemMenu({
          id: 0,
          nombre : this.itemForm.value.nombre,
          descripcion : this.itemForm.value.descripcion,
          precio : this.itemForm.value.precio,
          categoriaId: Number(this.itemForm.value.categoria),
          calorias : this.itemForm.value.calorias,
          celiaco : this.itemForm.value.celiaco,
          vegano : this.itemForm.value.vegano,
          vegetariano : this.itemForm.value.vegetariano,
          vendedorId: Number(this.itemForm.value.vendedor)
        })

          if (this.itemForm.value.tipo == 'PLATO') {
            itemMenu.tipo = Tipo.PLATO;
            itemMenu.peso = Number(this.itemForm.value.peso);
          } else if(this.itemForm.value.tipo == 'BEBIDA') {
            itemMenu.tipo = Tipo.BEBIDA;
            itemMenu.graduacionAlcoholica = Number(this.itemForm.value.graduacion);
            itemMenu.volumenEnMl = Number(this.itemForm.value.volumen);
          }
          else{
            throw new Error('Tipo erroneo ' + this.itemForm.get('tipo')?.value)
          }
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

  onChangeTipo(event: Event){
    event.preventDefault();
    if(this.itemForm.value.tipo == 'PLATO'){
      this.itemForm.controls['peso'].enable();
      this.itemForm.controls['volumen'].disable();
      this.itemForm.controls['graduacion'].disable();
    } else if(this.itemForm.value.tipo == 'BEBIDA'){
      this.itemForm.controls['peso'].disable();
      this.itemForm.controls['volumen'].enable();
      this.itemForm.controls['graduacion'].enable();
    }
    console.log(this.itemForm.value.vendedor);
  }
}
