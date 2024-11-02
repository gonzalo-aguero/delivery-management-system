/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.PedidosDao;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import java.util.ArrayList;

/**
 *
 * @author giuli
 */
public class PedidoMemory implements PedidosDao {

    private static PedidoMemory self;
    
    private PedidoMemory(){
        self=this;
            }
    
    public static PedidoMemory getInstance(){
        if(self==null){
            self = new PedidoMemory();
        }
        return self;
       }
    
    @Override
    public ArrayList<Pedido> getPedidos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
