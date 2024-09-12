/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.Dao;

import isi.deso.g10.deliverymanagementsystem.Classes.Categoria;
import isi.deso.g10.deliverymanagementsystem.Classes.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.Classes.Vendedor;
import java.util.List;

/**
 *
 * @author giuli
 */
public interface ItemsPedidoDao {

    public ItemMenu buscarNombre(String nombre, List<ItemMenu> items) throws ItemNoEncontradoException;

    public ItemMenu buscarId(int id, List<ItemMenu> items) throws ItemNoEncontradoException;

    public ItemMenu buscarPorRangoPrecio(double minimo, double maximo, List<ItemMenu> items);

    public List<ItemMenu> buscarPorCategoria(Categoria categoria, List<ItemMenu> items);

    public ItemMenu buscarComidas(Categoria categoria);

    public ItemMenu buscarBebidas(Categoria categoria);

    public ItemMenu buscarPorRestaurante(Vendedor vendedor);

}
