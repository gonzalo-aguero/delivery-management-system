/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.Dao;

import isi.deso.g10.deliverymanagementsystem.Classes.Categoria;
import isi.deso.g10.deliverymanagementsystem.Classes.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.Classes.Vendedor;

/**
 *
 * @author giuli
 */
public interface ItemsPedidoDao {
        
    public ItemMenu buscarNombre(String nombre);
    
    public ItemMenu buscarId(int id);
    
    public ItemMenu buscarPorRangoPrecio(double minimo,double maximo);
    
    public ItemMenu buscarPorCategoria(Categoria categoria);
    
    public ItemMenu buscarComidas(Categoria categoria);
    
    public ItemMenu buscarBebidas(Categoria categoria);
    
    public ItemMenu buscarPorRestaurante(Vendedor vendedor);
        
}
