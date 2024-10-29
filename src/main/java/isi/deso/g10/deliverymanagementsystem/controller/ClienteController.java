/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ClientesDao;
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.util.HashSet;

/**
 *
 * @author giuli
 */
public class ClienteController implements Controller{

    
    //DAO
    ClientesDao clientesDAO;
    
    HashSet<Cliente> clientes;
    
    PantallaPrincipal menu;
    public ClienteController(PantallaPrincipal menu) {
        this.menu=menu;
        
        setTablaClientes();
    }

    @Override
    public void addFrameListeners() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    setTablaClientes(){
       
    }
    
}
