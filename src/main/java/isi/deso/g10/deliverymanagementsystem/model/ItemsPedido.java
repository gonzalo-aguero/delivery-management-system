/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import java.util.ArrayList;

/**
 * Equivalente a la clase PedidoDetalle del diagrama de clases del enunciado.
 * @author gonzalo90fa
 */
public class ItemsPedido {
    private ArrayList<ItemMenu> items = new ArrayList<>();
    private Pedido pedido;

    public ArrayList<ItemMenu> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemMenu> items) {
        this.items = items;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
}
