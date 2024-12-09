import { Component } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Vendedor } from '../../../models/vendedor.model';
import { Coordenada } from '../../../models/coordenada.model';

@Component({
  selector: 'app-vendedor-modal',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './vendedor-modal.component.html',
  styleUrls: ['./vendedor-modal.component.css']
})
export class VendedorModalComponent {
  @Input() vendedor?: Vendedor;
  @Input() modo: string = '';
  @Output() submit = new EventEmitter<Vendedor>();
  @Output() cancel = new EventEmitter<void>();

  vendedorForm = new FormGroup({
    nombre: new FormControl(''),
    direccion: new FormControl(''),
    coordenadas: new FormGroup({
      latitud: new FormControl(''),
      longitud: new FormControl('')
    })
  });

  ngOnInit() {
    if (this.vendedor) {
      this.rellenarFormulario(this.vendedor);
    }
  }

  onSubmit() {
    if (this.vendedorForm.valid) {
      const vendedor = new Vendedor({
        id: this.vendedor ? this.vendedor.id : 0,
        nombre: this.vendedorForm.get('nombre')?.value || '',
        direccion: this.vendedorForm.get('direccion')?.value || '',
        coordenadas: this.vendedorForm.get('coordenadas')?.value
          ? new Coordenada({
            latitud: Number(this.vendedorForm.get('coordenadas.latitud')?.value) || 0,
            longitud: Number(this.vendedorForm.get('coordenadas.longitud')?.value) || 0,
          })
          : undefined,
      });

      console.log('Vendedor creado:', vendedor);
      this.submit.emit(vendedor);
    } else {
      console.error('Formulario inválido:', this.vendedorForm.errors);
    }
  }

  onCancel() {
    this.cancel.emit();
  }

  rellenarFormulario(vendedor: Vendedor) {
    this.vendedorForm.patchValue({
      nombre: vendedor.nombre,
      direccion: vendedor.direccion,
      coordenadas: {
        latitud: vendedor.coordenadas?.latitud?.toString() || '',
        longitud: vendedor.coordenadas?.longitud?.toString() || ''
      }
    });
  }
}
