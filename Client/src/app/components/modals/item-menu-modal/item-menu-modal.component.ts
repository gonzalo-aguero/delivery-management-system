import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup , ReactiveFormsModule, Validators } from '@angular/forms';
import { Vendedor } from '../../../models/vendedor.model';
import { Categoria } from '../../../models/categoria.model';
import { ItemMenu, Tipo } from '../../../models/item-menu.model';
import { VendedorService } from '../../../services/vendedor.service';
import { CategoriaService } from '../../../services/categoria.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-item-menu-modal',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './item-menu-modal.component.html',
  styleUrl: './item-menu-modal.component.css'
})
export class ItemMenuModalComponent implements OnInit{

  @Input() modo = '';
  categorias : Categoria[] = [];
  vendedores : Vendedor[] = []; 
  @Input() itemMenu?: ItemMenu;

  @Output() enviar = new EventEmitter<ItemMenu>();
  @Output() cancel = new EventEmitter<void>();

  tipos : string [] = ["PLATO","BEBIDA"];

  itemForm = new FormGroup({
    vendedor: new FormControl('', Validators.required),
    nombre : new FormControl('', Validators.required),
    tipo : new FormControl('', Validators.required),
    descripcion : new FormControl('', Validators.required),
    precio : new FormControl('', Validators.required),
    categoria : new FormControl('', Validators.required),
    calorias: new FormControl('', Validators.required),
    celiaco: new FormControl(false),
    vegano: new FormControl(false),
    vegetariano: new FormControl(false),
    peso: new FormControl({value: '', disabled: true}),
    graduacion: new FormControl({value: '', disabled: true}),
    volumen : new FormControl({value: '', disabled: true})
  });

  constructor(private _vendedorService: VendedorService, private _categoriaService: CategoriaService) { }

  ngOnInit(){
    this._vendedorService.getVendedores().subscribe(vendedores => {
      this.vendedores = vendedores;
    });

    this._categoriaService.getCategorias().subscribe(categorias => {
      this.categorias = categorias;
    });
    if(this.itemMenu){
      this.rellenarFormulario(this.itemMenu);
    }
  }

  ngOnChanges() {
    if (this.itemMenu) {
      this.rellenarFormulario(this.itemMenu);
    }
  }

  onSubmit(){
    console.log(this.itemForm);

    if(this.itemForm.valid){
      console.log(this.itemForm);
      const itemAEnviar = new ItemMenu({
        id: this.itemMenu?.id,
        nombre : this.itemForm.value.nombre,
        descripcion : this.itemForm.value.descripcion,
        precio : this.itemForm.value.precio,
        categoriaId: Number(this.itemForm.value.categoria),
        calorias : this.itemForm.value.calorias,  
        aptoCeliaco : this.itemForm.controls['celiaco'].value,
        aptoVegano : this.itemForm.controls['vegano'].value,
        aptoVegetariano : this.itemForm.controls['vegetariano'].value,
        vendedorId: this.itemForm.controls['vendedor'].value,
        vendedor: this.vendedores.find(vendedor => vendedor.id == Number(this.itemForm.controls['vendedor'].value)),
        categoria: this.categorias.find(categoria => categoria.id == Number(this.itemForm.value.categoria)),
      });
      
      if (this.itemForm.get('tipo')?.value == 'PLATO' || this.itemForm.get('tipo')?.value == Tipo.PLATO) {
        itemAEnviar.tipo = Tipo.PLATO;
        itemAEnviar.peso = Number(this.itemForm.value.peso);
      } else if(this.itemForm.get('tipo')?.value == 'BEBIDA' || this.itemForm.get('tipo')?.value == Tipo.BEBIDA) {
        itemAEnviar.tipo = Tipo.BEBIDA;
        itemAEnviar.graduacionAlcoholica = Number(this.itemForm.value.graduacion);
        itemAEnviar.volumenEnMl = Number(this.itemForm.value.volumen);
      }
      else{
        throw new Error('Tipo erroneo ');
      }
      this.enviar.emit(itemAEnviar);
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
      vendedor: itemMenu.vendedor.id.toString(),
      descripcion: itemMenu.descripcion,
      tipo: itemMenu.tipo,
      precio: itemMenu.precio.toString(),
      categoria: itemMenu.categoria.id.toString(),
      calorias: itemMenu.calorias.toString(),
      celiaco: itemMenu.aptoCeliaco,
      vegano: itemMenu.aptoVegano,
      vegetariano: itemMenu.aptoVegetariano,
      peso: itemMenu.tipo === 'PLATO' ? itemMenu.peso?.toString() : '',
      graduacion: itemMenu.tipo === 'BEBIDA' ? itemMenu.graduacionAlcoholica?.toString() : '',
      volumen: itemMenu.tipo === 'BEBIDA' ? itemMenu.volumenEnMl?.toString() : ''
    });
    if(itemMenu.tipo === 'PLATO'){
      this.itemForm.controls['peso'].enable();
      this.itemForm.controls['volumen'].disable();
      this.itemForm.controls['graduacion'].disable();
    } else if(itemMenu.tipo === 'BEBIDA'){
      this.itemForm.controls['peso'].disable();
      this.itemForm.controls['volumen'].enable();
      this.itemForm.controls['graduacion'].enable();
    }
    this.itemForm.controls['tipo'].disable();
    this.itemForm.controls['vendedor'].disable();
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
  }
}
