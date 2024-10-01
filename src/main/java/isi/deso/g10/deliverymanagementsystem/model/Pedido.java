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
    public Pedido() {
        this.itemsPedido = new ArrayList<>();
    }
    public Pedido(List<ItemMenu> itemsPedido, Cliente cliente) {
        this.itemsPedido = itemsPedido;
        this.cliente = cliente;
        this.contextoPedido = new ContextoPedido();
    }

    public enum EstadoPedido {
       RECIBIDO, EN_PROCESO, PENDIENTE_DE_PAGO, ENTREGADO, FINALIZADO
    }

    private Cliente cliente;
    private EstadoPedido estado;
    private List<ItemMenu> itemsPedido;
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

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public List<ItemMenu> getItemsPedido() {
        return this.itemsPedido;
    }

    public void AddItemsPedido(ItemMenu item) {
        this.itemsPedido.add(item);
    }

    public void SetItemsPedido(ArrayList<ItemMenu> items) {
        this.itemsPedido = items;
    }

}
