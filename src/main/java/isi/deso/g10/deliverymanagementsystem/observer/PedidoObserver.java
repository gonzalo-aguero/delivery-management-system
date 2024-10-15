package isi.deso.g10.deliverymanagementsystem.observer;

import isi.deso.g10.deliverymanagementsystem.model.Pedido;

public interface PedidoObserver {
    public void update(Pedido pedido);
}