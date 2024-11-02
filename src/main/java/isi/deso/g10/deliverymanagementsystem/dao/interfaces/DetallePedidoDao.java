/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import isi.deso.g10.deliverymanagementsystem.exception.ItemNoEncontradoException;
import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;

import java.util.List;

/**
 *
 * Filtrado por: nombreCliente, idVendedor, nombreVendedor, rangoMontoTotal
 * Ordenamiento por: nombreCliente, nombreVendedor, montoTotal. (ASC or DESC)
 *
 * @author giuli
 */
public interface DetallePedidoDao {

    public enum TipoOrdenamiento {
        ASC, DESC
    }

    List<DetallePedido> buscarPorIdVendedor(int idVendedor) throws ItemNoEncontradoException;

    List<DetallePedido> buscarPorNombreVendedor(String nombreVendedor) throws ItemNoEncontradoException;

    List<DetallePedido> buscarPorNombreCliente(String nombreCliente) throws ItemNoEncontradoException;

    List<DetallePedido> buscarPorRangoMontoTotal(double montoMinimo, double montoMaximo) throws ItemNoEncontradoException;

    List<DetallePedido> ordenarPorNombreVendedor(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;

    List<DetallePedido> ordenarPorNombreCliente(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;

    List<DetallePedido> ordenarPorMontoTotal(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException;

    public List<DetallePedido> obtenerDetallesPedidos();

    public DetallePedido agregarDetallePedido(DetallePedido detallePedido);

    public DetallePedido actualizarDetallePedido(DetallePedido detallePedido);

    public boolean eliminarDetallePedido(int id);

    public DetallePedido buscarDetallePedidoPorId(int id);

}
