import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Cliente } from '../../../models/cliente.model';
import { Coordenada } from '../../../models/coordenada.model';

@Component({
  selector: 'app-cliente-modal',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './cliente-modal.component.html',
  styleUrl: './cliente-modal.component.css'
})
export class ClienteModalComponent {

  @Input() cliente?: Cliente;
  @Input() modo: string = '';
  @Output() submit = new EventEmitter<Cliente>()
  @Output() cancel = new EventEmitter<void>()




  clienteForm = new FormGroup({
    nombre: new FormControl('', Validators.required),
    cuit: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    direccion: new FormControl('', Validators.required),
    coordenadas: new FormGroup({
      latitud: new FormControl('', Validators.required),
      longitud: new FormControl('', Validators.required)
    })
  })

  ngOnInit() {
    if (this.cliente) {
      this.rellenarFormulario(this.cliente);
    }
  }

  onSubmit(event: Event) {
    event.preventDefault();
    if (this.clienteForm.valid) {
      const cliente = new Cliente({
        id: this.cliente ? this.cliente.id : 0,
        nombre: this.clienteForm.get('nombre')?.value || '',
        cuit: this.clienteForm.get('cuit')?.value || '',
        email: this.clienteForm.get('email')?.value || '',
        direccion: this.clienteForm.get('direccion')?.value || '',
        coordenadas: this.clienteForm.get('coordenadas')?.value
          ? new Coordenada({
            latitud: Number(this.clienteForm.get('coordenadas.latitud')?.value) || 0,
            longitud: Number(this.clienteForm.get('coordenadas.longitud')?.value) || 0,
          })
          : undefined,
      });
      this.submit.emit(cliente);
    } else {
      alert('Falta algun campo o valores incorrectos');
    }
  }

  onCancel() {
    this.cancel.emit();
  }


  rellenarFormulario(cliente: Cliente) {
    this.clienteForm.patchValue({
      nombre: cliente.nombre,
      cuit: cliente.cuit,
      email: cliente.email,
      direccion: cliente.direccion,
      coordenadas: {
        latitud: cliente.coordenadas?.latitud?.toString() || '',
        longitud: cliente.coordenadas?.longitud?.toString() || ''
      }
    });
  }

}
