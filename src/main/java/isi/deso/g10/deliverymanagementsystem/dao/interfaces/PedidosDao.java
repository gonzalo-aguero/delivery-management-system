/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import java.util.List;

/**
 *
 * @author giuli
 */
public interface PedidosDao {
    
    public List<Pedido> obtenerPedidos();

    public Pedido agregarPedido(Pedido pedido);

    public Pedido actualizarPedido(Pedido pedido);

    public boolean eliminarPedido(int id);

    public Pedido buscarPedidoPorId(int id);
    
}
