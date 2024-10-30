/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedoresDao;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author giuli
 */
public class MenuController implements Controller{

    private PantallaPrincipal menu;
    private DefaultTableModel tableModel;
   
   
    private Controller subController;
    private final VendedorController vendedorController;
    private final ClienteController clienteController;
    
    
    private String title= "Lista de ";
    private String crearButton= "Crear nuevo ";
    
    public MenuController(PantallaPrincipal menu){
        this.menu = menu;
        
        this.vendedorController = new VendedorController(this.menu);
        this.clienteController = new ClienteController(this.menu);
        tableModel = (DefaultTableModel) menu.getTabla().getModel();
        addFrameListeners();
        setVendedores();
        subController.setTable();
        
      
        
        
        menu.setVisible(true);
        
    }
    
    @Override
    public void addFrameListeners() {
        menu.getVendedoresButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVendedores();
                subController.setTable();
                //menu.setTable();
                
            }
        });
        menu.getClientesButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setClientes();
                subController.setTable();
                //menu.setTable();
                
            }
        });
    }
    
    
    private void setVendedores(){
        menu.getTituloLabel().setText(title + "vendedores");
        menu.getCrearButton().setText(crearButton + "vendedor");
       
        subController = vendedorController;
        
        
        
    }
    
    private void setClientes(){
        menu.getTituloLabel().setText(title + "clientes");
        menu.getCrearButton().setText(crearButton + "cliente");
        
        subController = clienteController;
    }

    @Override
    public void setTable() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   
    
    
}
