/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giuli
 */
public interface ItemMenuDao extends GenericDao<ItemMenu>{
    
    public ArrayList<ItemMenu> buscarVendedor(Vendedor vendedor);
    
    public List<Categoria> getCategorias();
    
    public List<ItemMenu> buscarItemsPorNombre(String cadena);
}
