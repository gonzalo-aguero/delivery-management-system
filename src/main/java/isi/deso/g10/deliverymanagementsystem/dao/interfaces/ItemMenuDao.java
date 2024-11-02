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
public interface ItemMenuDao {

    public List<ItemMenu> obtenerItemsMenu();

    public ItemMenu agregarItemMenu(ItemMenu itemMenu);

    public boolean actualizarItemMenu(ItemMenu itemMenu);

    public boolean eliminarItemMenu(int id);

    public ItemMenu buscarItemMenuPorId(int id);
    
    public ArrayList<ItemMenu> buscarVendedor(Vendedor vendedor);
    
    public List<Categoria> getCategorias();
    
    public List<ItemMenu> buscarItemsPorNombre(String cadena);
}
