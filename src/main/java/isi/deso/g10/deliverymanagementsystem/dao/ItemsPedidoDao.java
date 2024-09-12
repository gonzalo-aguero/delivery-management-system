/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Comida;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.List;

/**
 *
 * @author giuli
 */
public interface ItemsPedidoDao { 
    
    ItemMenu buscarId(int id, List<Vendedor> Vendedores) throws ItemNoEncontradoException;

    ItemMenu buscarNombre(String nombre, List<Vendedor> Vendedores) throws ItemNoEncontradoException;

    List<ItemMenu> buscarPorRangoPrecio(double minimo, double maximo, List<Vendedor> Vendedores) throws ItemNoEncontradoException;

    List<ItemMenu> buscarPorCategoria(Categoria categoria, List<Vendedor> Vendedores) throws ItemNoEncontradoException;

    List<Comida> buscarComidas(List<Vendedor> Vendedores) throws ItemNoEncontradoException;

    List<Bebida> buscarBebidas(List<Vendedor> Vendedores) throws ItemNoEncontradoException;

    List<ItemMenu> buscarPorRestaurante(Vendedor vendedor) throws ItemNoEncontradoException;

}
