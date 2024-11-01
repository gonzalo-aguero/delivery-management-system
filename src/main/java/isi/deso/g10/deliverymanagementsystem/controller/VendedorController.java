/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.VendedoresMemory;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedoresDao;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author giuli
 */
public class VendedorController implements Controller {

    PantallaPrincipal menu;
    DefaultTableModel tableModel;
    
    //DAOS
    VendedoresDao vendedoresDao;
    
    List<Vendedor> vendedores;
    
    public VendedorController(PantallaPrincipal menu) {
        this.menu = menu;
        vendedoresDao = new VendedoresMemory();
    }

    @Override
    public void addFrameListeners() {
        //Aca debería haber listeners del frame de creacion, edición y eliminación, creo que la busqueda se podría hacer en el MenuController tranquilamente
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   

    @Override
    public void setTable() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Coordenadas");
        modelo.addColumn("Acciones");
      
        JTable table = menu.getTabla();
        table.setModel(modelo);
        this.tableModel = modelo;

        // CONFIGURACION BOTONES EDITAR Y ELIMINAR
        table.getColumn("Acciones").setCellRenderer(new ButtonsPanelRenderer());

        // Creamos y configuramos los botones para cada elemento
        ButtonsPanel buttonsPanel = new ButtonsPanel();

        // Define acciones personalizadas para cada botón
        Consumer<Integer> editAction = (row) -> editarButtonHandler(row);
        Consumer<Integer> deleteAction = (row) -> eliminarButtonHandler(row);

        // Crea el editor de la celda pasando las acciones como parámetros
        ButtonsPanelEditor buttonsPanelEditor = new ButtonsPanelEditor(buttonsPanel, editAction, deleteAction);

        table.getColumn("Acciones").setCellEditor(buttonsPanelEditor);
        
        vendedores = vendedoresDao.getVendedores();
        
        //Llena la tabla de vendedores
        for(Vendedor vendedor: vendedores){
            modelo.addRow(new Object[]{
                vendedor.getId(),
                vendedor.getNombre(),
                vendedor.getDireccion(),
                "[" + vendedor.getCoordenadas().getLatitud() + ";" + vendedor.getCoordenadas().getLongitud() + "]",
                new ButtonsPanel()
            });
        }
        
    }
    
    private void editarButtonHandler(int row) {
        Object id = this.tableModel.getValueAt(row, 0);
        JOptionPane.showMessageDialog(this.menu.getParent(), "Editar en fila: " + row + " ID: " + id.toString());
    }

    private void eliminarButtonHandler(int row) {
        Object id = this.tableModel.getValueAt(row, 0);
        JOptionPane.showMessageDialog(this.menu.getParent(), "Eliminar en fila: " + row + " ID: " + id.toString());
    }
    
}
