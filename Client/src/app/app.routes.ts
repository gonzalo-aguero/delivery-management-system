import { Routes } from '@angular/router';
import { TablaVendedorComponent } from './components/tablas/tabla-vendedor/tabla-vendedor.component';
import { TablaClienteComponent } from './components/tablas/tabla-cliente/tabla-cliente.component';
import { TablaItemMenuComponent } from './components/tablas/tabla-item-menu/tabla-item-menu.component';
import { TablaPedidoComponent } from './components/tablas/tabla-pedido/tabla-pedido.component';

export const routes: Routes = [
    { path: '', redirectTo: 'clientes', pathMatch: 'full' },
    { path: 'clientes', component: TablaClienteComponent },
    { path: 'vendedores', component: TablaVendedorComponent },
    { path: 'items', component: TablaItemMenuComponent },
    { path: 'pedidos', component: TablaPedidoComponent },
];
