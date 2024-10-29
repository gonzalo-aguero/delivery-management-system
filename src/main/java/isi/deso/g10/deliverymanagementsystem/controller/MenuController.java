/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.VendedoresDao;
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

    PantallaPrincipal menu;
    DefaultTableModel tableModel;
   
    
    
    
    
    Controller subController;
    
    
    String title= "Lista de ";
    String crearButton= "Crear nuevo ";
    
    public MenuController(PantallaPrincipal menu){
        this.menu = menu;
        tableModel = (DefaultTableModel) menu.getTabla().getModel();
        subController = new VendedorController(menu);
    }
    @Override
    public void addFrameListeners() {
        menu.getVendedoresButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVendedores();
            }
           
        });
    }
    
    
    private void setVendedores(){
        menu.getTituloLabel().setText(title + "vendedores");
        menu.getCrearButton().setText(crearButton + "vendedor");
        
        //Borra todos los elementos en la tabla
        tableModel.setRowCount(0);
        
        subController = new VendedorController(menu);
        
        
    }
    
    
}
