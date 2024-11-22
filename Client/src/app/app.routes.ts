import { Routes } from '@angular/router';
import { TablaVendedorComponent } from './components/tabla-vendedor/tabla-vendedor.component';
import { TablaClienteComponent } from './components/tabla-cliente/tabla-cliente.component';
import { TablaItemMenuComponent } from './components/tabla-item-menu/tabla-item-menu.component';
import { TablaPedidoComponent } from './components/tabla-pedido/tabla-pedido.component';

export const routes: Routes = [
    { path: '', redirectTo: 'clientes', pathMatch: 'full' },
    { path: 'clientes', component: TablaClienteComponent },
    { path: 'vendedores', component: TablaVendedorComponent },
    { path: 'items', component: TablaItemMenuComponent },
    { path: 'pedidos', component: TablaPedidoComponent },
];
