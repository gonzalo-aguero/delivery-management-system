/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.strategy;

import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;

import java.util.ArrayList;


public class ContextoPedido {
    private FormaDePago _formaDePago;
    
    public ContextoPedido() { }

    public Pedido pedido(){
        return new Pedido();
    }
    
    public double totalizar(ArrayList<ItemMenu> itemsMenu){
        double total = 0;
        

        //Calcula total
        for(ItemMenu item : itemsMenu){
            total += item.getPrecio();
        }
        
        //Aplica el recargo de la forma de pago
        total = _formaDePago.totalizar(total);
        
        return total;
    }
    
    public void setFormaTransferencia(){
        _formaDePago = new FormaTransferencia();
    }
    
    public void setFormaMercadoPago(){
        _formaDePago = new FormaMercadoPago();
    }

    public FormaDePago getFormaDePago() {
        return _formaDePago;
    }
    
    
    
    
}
