/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.exception.ItemNoEncontradoException;
import isi.deso.g10.deliverymanagementsystem.model.ItemPedido;
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
    
    List<ItemPedido> buscarPorIdVendedor(int idVendedor) throws ItemNoEncontradoException;

    List<ItemPedido> buscarPorNombreVendedor(String nombreVendedor) throws ItemNoEncontradoException;
    
    List<ItemPedido> buscarPorNombreCliente(String nombreCliente) throws ItemNoEncontradoException;

    List<ItemPedido> buscarPorRangoMontoTotal(double montoMinimo, double montoMaximo) throws ItemNoEncontradoException;

    List<ItemPedido> ordenarPorNombreVendedor(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;

    List<ItemPedido> ordenarPorNombreCliente(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;

    List<ItemPedido> ordenarPorMontoTotal(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;
}
