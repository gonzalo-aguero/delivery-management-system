/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.VendedoresDao;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.util.HashSet;
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
        
        
        
        HashSet<Vendedor> vendedores = vendedoresDao.getVendedores();
        
        for(Vendedor vendedor: vendedores){
            modelo.addRow(new Object[]{vendedor.getId(),
                vendedor.getNombre(),
                vendedor.getDireccion(),
                "[" + vendedor.getCoordenadas().getLatitud() + ";" + vendedor.getCoordenadas().getLongitud() + "]" });
        }
        
        menu.getTabla().setModel(modelo);
    }
    
}
