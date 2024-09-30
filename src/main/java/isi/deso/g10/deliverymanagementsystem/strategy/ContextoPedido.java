/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.strategy;

import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giuli
 */
public class ContextoPedido {
    private FormaDePago _formaDePago;
    private List<ItemMenu> itemsMenu;
    
    
    public ContextoPedido(){
       _formaDePago = new FormaTransferencia();
       itemsMenu = new ArrayList<>();
    }

    public Pedido pedido(){
        
    }
    
    public double totalizar(){
        double total = 0;
        

        //Calcula total
        for(ItemMenu item : itemsMenu){
            total += item.getPrecio();
        }
        
        //Aplica el recargo de la forma de pago
        total = _formaDePago.totalizar(total);
        
        return total;
    }
    
    
    public List<ItemMenu> getItemsMenu() {
        return itemsMenu;
    }

    public void setItemsMenu(List<ItemMenu> itemsMenu) {
        this.itemsMenu = itemsMenu;
    }
    
    public void setFormaTransferencia(){
        _formaDePago = new FormaTransferencia();
    }
    
    public void setFormaMercadoPago(){
        _formaDePago = new FormaMercadoPago();
    }
    
    
    
}
