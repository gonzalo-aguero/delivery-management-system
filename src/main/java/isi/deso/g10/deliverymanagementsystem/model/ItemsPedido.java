/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import java.util.ArrayList;

/**
 * Equivalente a la clase PedidoDetalle del diagrama de clases del enunciado.
 *
 * @author gonzalo90fa
 */
public class ItemsPedido {

    private ArrayList<ItemMenu> items = new ArrayList<>(); //Cada pedido puede involucrar una o más instancias de ItemMenu
    private Pedido pedido;

    public ItemsPedido(ArrayList<ItemMenu> items, Pedido pedido) {
        this.items = items;
        this.pedido = pedido;
    }

    public double calcularMontoTotal() {
        return items.stream()
                .map(item -> item.getPrecio())
                .reduce(0d, Double::sum);
    }
    
    /**
     * @return Retorna el vendedor de los items basándose en el vendedor asociado al primer item de la lista
     */
    public Vendedor getVendedor(){
        return items.get(0).getVendedor();//Cada itemMenu tiene referencia a su vendedor
    }

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
