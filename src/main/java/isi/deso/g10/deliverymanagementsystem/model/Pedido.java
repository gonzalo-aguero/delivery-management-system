/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import java.util.List;

/**
 *
 * @author gonzalo90fa
 */
public class Pedido {
    public enum EstadoPedido {
       RECIBIDO, EN_PROCESO, PENDIENTE_DE_PAGO, ENTREGADO, FINALIZADO
    }

    private Cliente cliente;
    private EstadoPedido estado;
    private List<ItemPedido> itemsPedido;
    
   
    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public List<ItemPedido> getItemsPedido() {
        return itemsPedido;
    }
    
}
