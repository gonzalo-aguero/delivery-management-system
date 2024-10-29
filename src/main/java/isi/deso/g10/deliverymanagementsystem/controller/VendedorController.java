/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.PruebaVendedores;
import isi.deso.g10.deliverymanagementsystem.dao.VendedoresDao;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author giuli
 */
public class VendedorController implements Controller {

    PantallaPrincipal menu;
    
    //DAOS
    VendedoresDao vendedoresDao;
    
    HashSet<Vendedor> vendedores;
    
    public VendedorController(PantallaPrincipal menu) {
        this.menu = menu;
        vendedoresDao = new PruebaVendedores();
        setTablaVendedores();
    }

    @Override
    public void addFrameListeners() {
        //Aca debería haber listeners del frame de creacion, edición y eliminación, creo que la busqueda se podría hacer en el MenuController tranquilamente
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private void setTablaVendedores(){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Coordenadas");
        modelo.addColumn("Acciones");
      
        JTable table = menu.getTabla();
        table.setModel(modelo);
        
        table.getColumn("Acciones").setCellRenderer(new ButtonsPanelRenderer());
        table.getColumn("Acciones").setCellEditor(new ButtonsPanelEditor(new ButtonsPanel()));
        
        HashSet<Vendedor> vendedores = vendedoresDao.getVendedores();
        
        //Llena la tabla de vendedores
        for(Vendedor vendedor: vendedores){
            modelo.addRow(new Object[]{vendedor.getId(),
                vendedor.getNombre(),
                vendedor.getDireccion(),
                "[" + vendedor.getCoordenadas().getLatitud() + ";" + vendedor.getCoordenadas().getLongitud() + "]",
                new ButtonsPanel()
            });
        }
        
        
        
        
    }
    
}
