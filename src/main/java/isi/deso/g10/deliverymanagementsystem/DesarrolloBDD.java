/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

import java.util.ArrayList;
import java.util.List;

import isi.deso.g10.deliverymanagementsystem.controller.MenuController;
import isi.deso.g10.deliverymanagementsystem.dao.mysql.ClienteMySQLDaoImpl;
import isi.deso.g10.deliverymanagementsystem.dao.mysql.ItemMenuMySQLDaoImpl;
import isi.deso.g10.deliverymanagementsystem.dao.mysql.PedidoMySQLDaoImpl;
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;
import isi.deso.g10.deliverymanagementsystem.utils.DatabaseInitializer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;

/**
 *
 * @author gonzalo90fa
 */
public class DesarrolloBDD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
         * Esta clase es para ejecutar y probar directamente los dao de mysql
         * sin tener que cargar toda la interfaz.
         *
         * Una vez que funcionen bien todos los daos volvemos a usar la clase
         * Etapa6 para arrancar la aplicacion con la interfaz y actualizar los
         * daos en los controllers...
         */

        // Inicializamos la base de datos
        DatabaseInitializer.initialize();

        // Insertamos los datos de prueba
        DatabaseInitializer.insertTestData();

        PantallaPrincipal menu = new PantallaPrincipal();
        MenuController controller = new MenuController(menu);

        // Crear una instancia del DAO
        PedidoMySQLDaoImpl pedidoDao = PedidoMySQLDaoImpl.getInstance();
        ClienteMySQLDaoImpl clienteDao = ClienteMySQLDaoImpl.getInstance();
        ItemMenuMySQLDaoImpl itemMenuDao = ItemMenuMySQLDaoImpl.getInstance();

        // Probar la inserción de un nuevo pedido
        // Pedido nuevoPedido = new Pedido();
        // nuevoPedido.setCliente(clienteDao.obtenerPorId(1));
        // nuevoPedido.setEstado(Pedido.EstadoPedido.RECIBIDO);
        // ArrayList<ItemMenu> items = new ArrayList<>();
        // items.add(itemMenuDao.obtenerPorId(1));
        // items.add(itemMenuDao.obtenerPorId(2));
        // DetallePedido detallePedido = new DetallePedido(items);
        // detallePedido.setId(1);
        // detallePedido.setPedido(nuevoPedido);
        // nuevoPedido.setDetallePedido(detallePedido);
        // FormaPagoI formaPago = new FormaMercadoPago(null);
        // formaPago.setId(1);
        // nuevoPedido.setFormapago(formaPago);
        // pedidoDao.crear(nuevoPedido);

        // Probar la obtención de un pedido por ID
        for (int i = 1; i < 6; i++) {
            Pedido pedidoObtenido = pedidoDao.obtenerPorId(i);
            System.out.println("Cliente: " + pedidoObtenido.getCliente().getNombre());
            System.out.println("Monto total (con recargo de la Forma de Pago): "
                    + pedidoObtenido.getFormapago().totalizar(pedidoObtenido.getDetallePedido().calcularMontoTotal()));
            
            List<ItemMenu> items = pedidoObtenido.getDetallePedido().getItems();
            if (items.size() > 0) {
                System.out.println("Primer item: " + items.get(0).getNombre() + " - " + items.get(0).getCategoria() + " - " + items.get(0).getClass().getSimpleName());
            }
            if (items.size() > 1) {
                System.out.println("Segundo item: " + items.get(1).getNombre() + " - " + items.get(1).getCategoria() + " - " + items.get(1).getClass().getSimpleName());
            }
        }

        // // Probar la actualización de un pedido
        // pedidoObtenido.setDescripcion("Pedido de prueba actualizado");
        // pedidoDao.update(pedidoObtenido);

        // // Probar la eliminación de un pedido
        // pedidoDao.delete(1);

        // // Probar la obtención de todos los pedidos
        // List<Pedido> pedidos = pedidoDao.obtenerTodos();
        // for (Pedido pedido : pedidos) {
        //     System.out.println(pedido);
        // }

    }

}
