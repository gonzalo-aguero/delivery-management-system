/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import isi.deso.g10.deliverymanagementsystem.strategy.ContextoPedido;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gonzalo90fa
 */
public class Pedido {
    public Pedido() { }

    public Pedido(ArrayList<ItemMenu> itemsPedido, Cliente cliente) {
        this.detallePedido = new DetallePedido(itemsPedido);
        this.cliente = cliente;
        this.contextoPedido = new ContextoPedido();
    }

    public enum EstadoPedido {
       RECIBIDO, EN_PROCESO, PENDIENTE_DE_PAGO, ENTREGADO, FINALIZADO
    }

    private Cliente cliente;
    private EstadoPedido estado;
    private DetallePedido detallePedido;
    private ContextoPedido contextoPedido;
    
    
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
        this.estado = estado;
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
    
    

}
