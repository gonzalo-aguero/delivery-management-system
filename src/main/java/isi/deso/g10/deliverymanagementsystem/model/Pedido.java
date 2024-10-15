/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import isi.deso.g10.deliverymanagementsystem.observer.Observable;
import isi.deso.g10.deliverymanagementsystem.observer.PedidoObserver;
import isi.deso.g10.deliverymanagementsystem.strategy.ContextoPedido;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gonzalo90fa
 */
public class Pedido implements Observable {

    public Pedido() { }
    
    public Pedido(int idPedido ,ArrayList<ItemMenu> itemsPedido, Cliente cliente) {
        this.id = idPedido;
        this.detallePedido = new DetallePedido(itemsPedido);
        this.cliente = cliente;
        this.contextoPedido = new ContextoPedido();
        
        this.observers = new ArrayList<>();
    }

    public enum EstadoPedido {
       RECIBIDO, EN_ENVIO, EN_PROCESO, PENDIENTE_DE_PAGO, ENTREGADO, FINALIZADO
    }

    private int id;
    private Cliente cliente;
    private EstadoPedido estado;
    private DetallePedido detallePedido;
    private ContextoPedido contextoPedido;
    private List<PedidoObserver> observers;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setFormaDePago(String formaDePago) {
        if(formaDePago.equalsIgnoreCase("mercadopago")) {
            this.contextoPedido.setFormaMercadoPago();
        } else if(formaDePago.equalsIgnoreCase("transferencia")){
            this.contextoPedido.setFormaTransferencia();
        }
    }
    // Punto de entrada para obtener el costo total del pedido
    public double costoFinal() {
        return this.contextoPedido.totalizar(detallePedido.getItems());
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        //Esto evita que se notifique a los observadores si el estado no cambia
        if (this.estado != estado) {
            this.estado = estado;
            notifyObservers();
        }
    }

    public ArrayList<ItemMenu> getItemsPedido() {
        return this.detallePedido.getItems();
    }

    public DetallePedido getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(DetallePedido detallePedido) {
        this.detallePedido = detallePedido;
    }

    public ContextoPedido getContextoPedido() {
        return contextoPedido;
    }

    public void setContextoPedido(ContextoPedido contextoPedido) {
        this.contextoPedido = contextoPedido;
    }

    @Override
    public void addObserver(PedidoObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public boolean removeObserver(PedidoObserver o) {
        return observers.remove(o);
    }

    /**
     * Notifies all registered observers about changes to the current Pedido instance.
     * This method iterates through the list of observers and calls their update method,
     * passing the current instance of Pedido as a parameter.
     */
    @Override
    public void notifyObservers() {
        for (PedidoObserver observer : observers) {
            observer.update(this);
        }
    }

}
