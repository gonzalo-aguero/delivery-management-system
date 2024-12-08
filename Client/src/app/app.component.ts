import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';
import { ClienteModalComponent } from "./components/modals/cliente-modal/cliente-modal.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, ClienteModalComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Client';
  constructor(private router: Router) { }

  onVendedores() {
    this.router.navigate(['/vendedores']);
  }

  onClientes() {
    this.router.navigate(['/clientes']);
  }

  onItems() {
    this.router.navigate(['/items']);
  }

  onPedidos() {
    this.router.navigate(['/pedidos']);
  }
}
