/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import isi.deso.g10.deliverymanagementsystem.observer.Observable;
import isi.deso.g10.deliverymanagementsystem.observer.PedidoObserver;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gonzalo90fa
 */
public class Pedido implements Observable {
    private int id;
    private Cliente cliente;
    private EstadoPedido estado;
    private DetallePedido detallePedido;
    private Pago datosPago;
    private FormaPagoI formapago;
    private List<PedidoObserver> observers;

    public Pedido() { 
        this.observers = new ArrayList<>();
    }

    public Pedido(int idPedido ,ArrayList<ItemMenu> itemsPedido, Cliente cliente) {
        this.id = idPedido;
        this.detallePedido = new DetallePedido(itemsPedido);
        this.cliente = cliente;
        this.observers = new ArrayList<>();
    }

    public Pedido(int idPedido ,ArrayList<ItemMenu> itemsPedido, Cliente cliente, FormaPagoI formapago) {
        this.id = idPedido;
        this.detallePedido = new DetallePedido(itemsPedido);
        this.cliente = cliente;
        this.formapago = formapago;
        this.observers = new ArrayList<>();
    }

    public enum EstadoPedido {
       RECIBIDO, EN_ENVIO, EN_PROCESO, PENDIENTE_DE_PAGO, ENTREGADO, FINALIZADO
    }

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

    public void setDatosPago(Pago datosPago) {
        this.datosPago = datosPago;
    }
    public Pago getDatosPago() {
        return datosPago;
    }

    public void setFormapago(FormaPagoI formapago) {
        this.formapago = formapago;
    }

    public FormaPagoI getFormapago() {
        return formapago;
    }
    public double costoFinal() {
        if(formapago == null) {
            System.out.println("No se ha ingresado una forma de pago aun :(");
            return 0d;
        }else{
            return formapago.totalizar(detallePedido.calcularMontoTotal());
        }
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

    @Override
    public void notifyObservers() {
        for (PedidoObserver observer : observers) {
            observer.update(this);
        }
    }

}
