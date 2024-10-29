/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.PruebaClientes;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ClientesDao;
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.util.HashSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author giuli
 */
public class ClienteController implements Controller{

    
    //DAO
    ClientesDao clientesDao;
    
    HashSet<Cliente> clientes;
    
    PantallaPrincipal menu;
    public ClienteController(PantallaPrincipal menu) {
        this.menu=menu;
        clientesDao = new PruebaClientes();
        setTablaClientes();
    }

    @Override
    public void addFrameListeners() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void setTablaClientes(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Cuit");
        modelo.addColumn("Nombre");
        modelo.addColumn("Email");
        modelo.addColumn("Dirección");
        modelo.addColumn("Coordenadas");
        modelo.addColumn("Acciones");
      
        JTable table = menu.getTabla();
        table.setModel(modelo);
        
        table.getColumn("Acciones").setCellRenderer(new ButtonsPanelRenderer());
        table.getColumn("Acciones").setCellEditor(new ButtonsPanelEditor(new ButtonsPanel()));
        
         HashSet<Cliente> clientes = clientesDao.getClientes();
        
        //Llena la tabla de vendedores
        for(Cliente cliente: clientes){
            modelo.addRow(new Object[]{cliente.getId(),
                cliente.getCuit(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion(),
                "[" + cliente.getCoordenadas().getLatitud() + ";" + cliente.getCoordenadas().getLongitud() + "]",
                new ButtonsPanel()
            });
        }
    }
    
}
