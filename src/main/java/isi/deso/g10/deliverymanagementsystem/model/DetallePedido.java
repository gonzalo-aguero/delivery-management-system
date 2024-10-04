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
public class DetallePedido {

    private ArrayList<ItemMenu> items = new ArrayList<>();
    private Pedido pedido;

    public DetallePedido(ArrayList<ItemMenu> items) {
        this.items = items;
    }

    public double calcularMontoTotal() {
        return items.stream()
                .map(ItemMenu::getPrecio)
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

}
