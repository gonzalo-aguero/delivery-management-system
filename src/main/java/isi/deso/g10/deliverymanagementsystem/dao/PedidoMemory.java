/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.PedidosDao;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giuli
 */
public class PedidoMemory implements PedidosDao {

    private static PedidoMemory self;
    private ArrayList<Pedido> pedidos;

    private PedidoMemory() {
        this.pedidos = new ArrayList<>();
        self = this;
    }

    public static PedidoMemory getInstance() {
        if (self == null) {
            self = new PedidoMemory();
        }
        return self;
    }

    @Override
    public List<Pedido> obtenerPedidos() {
        return pedidos;
    }

    @Override
    public Pedido agregarPedido(Pedido pedido) {
        pedidos.add(pedido); // Agrega el pedido a la lista
        return pedido; // Retorna el pedido que se ha agregado
    }

    @Override
    public Pedido actualizarPedido(Pedido pedido) {
        for (Pedido p : pedidos) {
            if (p.getId() == pedido.getId()) {
                int index = pedidos.indexOf(p); // Obtiene el índice del pedido encontrado
                pedidos.set(index, pedido); // Actualiza el pedido en la lista
                return pedido; // Retorna el pedido actualizado
            }
        }
        return null; // Retorna null si no se encontró el pedido para actualizar
    }

    @Override
    public boolean eliminarPedido(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                pedidos.remove(p); // Elimina el pedido encontrado
                return true; // Retorna true si el pedido fue eliminado
            }
        }
        return false; // Retorna false si no se encontró el pedido
    }

    @Override
    public Pedido buscarPedidoPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p; // Retorna el pedido encontrado
            }
        }
        return null; // Retorna null si no se encuentra el pedido
    }

}
