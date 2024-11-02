/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import java.util.ArrayList;

/**
 *
 * @author giuli
 */
public interface ItemMenuDao {

    public ArrayList<ItemMenu> getItemMenus();

    public ItemMenu buscarItemMenu(int id);

    public boolean eliminarItemMenu(int id);

    public boolean modificarItemMenu(String descripcion, double precio, int id);

    public ItemMenu crearNuevoItemMenu(String tipo, Categoria cat, double peso, double graduacionAlcoholica, double volumenEnMl, String nombre, String descripcion, double precio, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano);

    public ItemMenu save(ItemMenu itemMenu);

}
