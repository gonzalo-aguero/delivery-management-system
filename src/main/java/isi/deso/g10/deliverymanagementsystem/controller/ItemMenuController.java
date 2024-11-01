/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.ItemMenuMemory;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ItemMenuDao;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author giuli
 */
public class ItemMenuController implements Controller {

    private DefaultTableModel tableModel;
    
    //DAO
    private final ItemMenuDao itemsMenuDao;
    
    private ArrayList<ItemMenu> itemsMenu;
    
    private final PantallaPrincipal menu;

    public ItemMenuController(PantallaPrincipal menu) {
        this.itemsMenuDao = new ItemMenuMemory();
        this.menu = menu;
        
    }
    
    
    
    @Override
    public void addFrameListeners() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setTable() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Categoria");
        modelo.addColumn("Calorias");
        modelo.addColumn("Apto para");
        modelo.addColumn("Acciones");
        
        
        JTable table = menu.getTabla();
        table.setModel(modelo);
        
        table.getColumn("Acciones").setCellRenderer(new ButtonsPanelRenderer());
        table.getColumn("Acciones").setCellEditor(new ButtonsPanelEditor(new ButtonsPanel()));
        
        itemsMenu = itemsMenuDao.getItemMenus();
        
        for(ItemMenu item: itemsMenu){
            modelo.addRow(new Object[]{
            item.getId(),
            item.getNombre(),
            item.getPrecio(),
            item.getCategoria().getTipoItem(),
            item.getCalorias(),
            (item.isAptoCeliaco()? "celiaco\n" : "") + (item.isAptoVegano()? "vegano\n" : "") + (item.isAptoVegetariano()? "vegetariano\n" : ""),
            new ButtonsPanel()
            });
        }
    }
    
}
