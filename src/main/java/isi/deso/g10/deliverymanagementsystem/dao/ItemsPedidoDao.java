/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.ItemsPedido;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.List;

/**
 * 
 * Filtrado por: nombreCliente, idVendedor, nombreVendedor, rangoMontoTotal
 * Ordenamiento por: nombreCliente, nombreVendedor, montoTotal. (ASC or DESC)
 * 
 * @author giuli
 */
public interface ItemsPedidoDao {
    
    public enum TipoOrdenamiento {
        ASC, DESC
    }
    
    List<ItemsPedido> buscarPorIdVendedor(int idVendedor) throws ItemNoEncontradoException;

    List<ItemsPedido> buscarPorNombreVendedor(String nombreVendedor) throws ItemNoEncontradoException;
    
    List<ItemsPedido> buscarPorNombreCliente(String nombreCliente) throws ItemNoEncontradoException;

    List<ItemsPedido> buscarPorRangoMontoTotal(double montoMinimo, double montoMaximo) throws ItemNoEncontradoException;List<ItemMenu> buscarPorCategoria(Categoria categoria, List<Vendedor> Vendedores) throws ItemNoEncontradoException;

    List<ItemsPedido> ordenarPorNombreVendedor(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;

    List<ItemsPedido> ordenarPorNombreCliente(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;

    List<ItemsPedido> ordenarPorMontoTotal(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;
}
