/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;

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
    private final ItemMenuController itemMenuController; 
    private final PedidoController pedidoController;
    
    
    private String title= "Lista de ";
    private String crearButton= "Crear nuevo ";
    
    public MenuController(PantallaPrincipal menu){
        this.menu = menu;
        
        this.vendedorController = new VendedorController(this.menu);
        this.clienteController = new ClienteController(this.menu);
        this.itemMenuController = new ItemMenuController(this.menu);
        this.pedidoController = new PedidoController(this.menu);
        tableModel = (DefaultTableModel) menu.getTabla().getModel();
        addFrameListeners();
        setVendedores();
       
        
        menu.setVisible(true);
        
    }
    
    @Override
    public void addFrameListeners() {
        menu.getVendedoresButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVendedores();
            }
        });
        menu.getClientesButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setClientes();
            }
        });
        menu.getItemsButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               setItemsMenu();
            }
        });
        menu.getPedidosButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setPedidos();
            } 
        });
        menu.getCrearButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               crear();
            } 
        });
    }
    
    
    private void setVendedores(){
        menu.getTituloLabel().setText(title + "vendedores");
        menu.getCrearButton().setText(crearButton + "vendedor");
       
        subController = vendedorController;
        subController.setTable();
        
        
    }
    
    private void setClientes(){
        menu.getTituloLabel().setText(title + "clientes");
        menu.getCrearButton().setText(crearButton + "cliente");
        
        subController = clienteController;
        subController.setTable();
    }
    
    private void setItemsMenu(){
        menu.getTituloLabel().setText(title + "items menu");
        menu.getCrearButton().setText(crearButton + "item menu");
        
        subController = itemMenuController;
        subController.setTable();
    }
    
    private void setPedidos(){
        menu.getTituloLabel().setText(title + "pedidos");
        menu.getCrearButton().setText(crearButton + "pedido");
        
        subController = pedidoController;
        subController.setTable();
    }
    

    @Override
    public void setTable() {
        subController.setTable();
    }

    @Override
    public void crear() {
        subController.crear();
    }
    
   
    
    
}
