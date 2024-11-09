/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.memory;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.PedidosDao;
import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaTransferencia;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author giuli
 */
public class PedidoMemory implements PedidosDao {

    private static PedidoMemory self;
    private ArrayList<Pedido> pedidos;
    private int newId = 1; //determina la id del proximo vendedor que se agregue

    private PedidoMemory() {
        this.pedidos = generarPedidos();
        self = this;
    }

    public static PedidoMemory getInstance() {
        if (self == null) {
            self = new PedidoMemory();
        }
        return self;
    }

    @Override
    public List<Pedido> obtenerTodos() {
        return pedidos;
    }

    @Override
    public Pedido crear(Pedido pedido) {
        pedido.setId(newId);
        newId++;
        pedidos.add(pedido); // Agrega el pedido a la lista
        return pedido; // Retorna el pedido que se ha agregado
    }

    @Override
    public Pedido actualizar(Pedido pedido) {
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
    public boolean eliminar(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                pedidos.remove(p); // Elimina el pedido encontrado
                return true; // Retorna true si el pedido fue eliminado
            }
        }
        return false; // Retorna false si no se encontró el pedido
    }

    @Override
    public Pedido obtenerPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p; // Retorna el pedido encontrado
            }
        }
        return null; // Retorna null si no se encuentra el pedido
    }
    
    private ArrayList<Pedido> generarPedidos(){
        
        // Crear instancias de Cliente
        Cliente cliente1 = new Cliente(1, "20-12345678-9", "Jorge", "cliente1@example.com", "Calle Falsa 123", new Coordenada(123,123));

        Categoria minutas = new Categoria(1, "minuta", Categoria.TipoItem.COMIDA);
        Categoria bebida = new Categoria(1, "bebida", Categoria.TipoItem.BEBIDA);

        Plato milanesaPollo = new Plato(200, 1, "Milanesa de Pollo", "Milanesa de Pollo", 4500, minutas, 400, false, false, false);
        Plato pizzaMuzza = new Plato(350, 2, "Pizza de Muzza", "Pizza con queso mozzarella", 3000, minutas, 800, false, false, false);
        Plato papasFritas = new Plato(250, 3, "Papas Fritas", "Papas fritas crocantes", 1500, minutas, 600, false, true, true);
        Bebida cocaCola = new Bebida(0, 500, 4, "Coca Cola", "Gaseosa Coca Cola 500ml", 500, bebida, 200, true, true, true);

        ArrayList<ItemMenu> itemsPedido1 = new ArrayList<ItemMenu>();
        ArrayList<ItemMenu> itemsPedido2 = new ArrayList<ItemMenu>();

        itemsPedido1.add(milanesaPollo);
        itemsPedido1.add(milanesaPollo);
        itemsPedido1.add(milanesaPollo);
        itemsPedido1.add(pizzaMuzza);
        itemsPedido2.add(papasFritas);
        itemsPedido2.add(papasFritas);
        itemsPedido2.add(papasFritas);
        itemsPedido2.add(cocaCola);
        itemsPedido2.add(pizzaMuzza);

        
        ArrayList<Pedido> pedidos = new ArrayList<>(Arrays.asList(
            new Pedido(1, itemsPedido1, cliente1, new FormaMercadoPago("alias.alias")),
            new Pedido(2, itemsPedido2, cliente1, new FormaTransferencia("23-91239912-10", "0001238489001230100")),
            new Pedido(3, itemsPedido2, cliente1, new FormaTransferencia("19-93110001-12", "0012000450549390012"))
        ));

        pedidos.get(0).setEstado(Pedido.EstadoPedido.ENTREGADO);
        pedidos.get(1).setEstado(Pedido.EstadoPedido.RECIBIDO);
        pedidos.get(2).setEstado(Pedido.EstadoPedido.EN_ENVIO);
        
        newId = 4;
        
        return pedidos;
    }

}
