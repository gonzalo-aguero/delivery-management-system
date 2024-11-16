/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.dao.memory.CategoriaMemory;
import isi.deso.g10.deliverymanagementsystem.dao.mysql.mappers.PedidoMapper;
import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.Pedido.EstadoPedido;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaTransferencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giuli
 */
public class PedidoMySQLDaoImpl extends GenericMySQLDaoImpl<Pedido> {

    // Singleton instance for Pedido
    private static PedidoMySQLDaoImpl instance;
    private static final PedidoMapper pedidoMapper = new PedidoMapper();;

    private PedidoMySQLDaoImpl() {
        // Constructor privado para evitar instanciación externa
    }

    // Use Singleton pattern for Pedido
    public static synchronized PedidoMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new PedidoMySQLDaoImpl();
        }
        return instance;
    }

    @Override
    protected Pedido mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return pedidoMapper.mapResultSetToEntity(resultSet);
    }

    @Override
    protected String getTableName() {
        return "Pedido";
    }

    @Override
    protected String getPrimaryKeyColumn() {
        return "id";
    }

    @Override
    public Pedido crear(Pedido pedido) {
        String sqlPedido = "INSERT INTO Pedido (clienteId, estado_pedido, formaPagoId) VALUES (?, ?, ?)";
        String sqlDetallePedido = "INSERT INTO DetallePedido (id_pedido, id_item) VALUES (?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement statementPedido = connection.prepareStatement(sqlPedido,
                    Statement.RETURN_GENERATED_KEYS)) {
                // Insertar Pedido
                statementPedido.setInt(1, pedido.getCliente().getId());
                statementPedido.setString(2, pedido.getEstado().name());
                statementPedido.setInt(3, pedido.getFormapago().getId());

                int affectedRows = statementPedido.executeUpdate();

                if (affectedRows == 0) {
                    connection.rollback();
                    throw new SQLException("Creating pedido failed, no rows affected.");
                }

                // Obtener ID generado para Pedido
                try (ResultSet generatedKeys = statementPedido.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pedido.setId(generatedKeys.getInt(1));
                    } else {
                        connection.rollback();
                        throw new SQLException("Creating pedido failed, no ID obtained.");
                    }
                }

                // Insertar DetallePedido
                try (PreparedStatement statementDetallePedido = connection.prepareStatement(sqlDetallePedido)) {
                    for (ItemMenu item : pedido.getDetallePedido().getItems()) {
                        statementDetallePedido.setInt(1, pedido.getId());
                        statementDetallePedido.setInt(2, item.getId());
                        statementDetallePedido.addBatch();
                    }
                    statementDetallePedido.executeBatch();
                }

                connection.commit(); // Confirmar transacción
            } catch (SQLException ex) {
                connection.rollback(); // Revertir transacción en caso de error
                Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear pedido", ex);
                throw ex;
            } finally {
                connection.setAutoCommit(true); // Restaurar modo de auto-commit
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear pedido", ex);
        }

        return pedido;
    }

    @Override
    public Pedido actualizar(Pedido entity) {
        String sql = "UPDATE " + getTableName() + " SET clienteId = ?, estado_pedido = ?, formaPagoId = ? WHERE "
                + getPrimaryKeyColumn() + " = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, entity.getCliente().getId());
            statement.setString(2, entity.getEstado().toString());
            statement.setInt(3, entity.getFormapago().getId());
            statement.setInt(4, entity.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                entity = null;
                throw new SQLException("Updating pedido failed, no rows affected.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar pedido", ex);
        }

        return entity;
    }

    
    /**
     * Obtiene el detalle de un pedido por su ID.
     *
     * @param pedidoId El ID del pedido.
     * @return Un objeto DetallePedido que contiene una lista de los ítems del menú asociados al pedido.
     * @throws SQLException Si ocurre un error al acceder a la base de datos.
     */
    private DetallePedido obtenerDetallePedidoPorPedidoId(int pedidoId) {
        String sql = "SELECT dp.id, dp.id_item, im.nombre, im.descripcion, im.precio, im.categoria, im.calorias, " +
             "im.aptoCeliaco, im.aptoVegetariano, im.aptoVegano, im.id_vendedor, " +
             "b.graduacionAlcoholica, b.volumenEnMl, " +
             "p.peso " +
             "FROM DetallePedido dp " +
             "JOIN ItemMenu im ON dp.id_item = im.id " +
             "LEFT JOIN Bebida b ON im.id = b.id_item " +
             "LEFT JOIN Plato p ON im.id = p.id_item " +
             "WHERE dp.id_pedido = ?";

        List<ItemMenu> items = new ArrayList<>();

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pedidoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ItemMenu item = pedidoMapper.mapResultSetToItemMenu(resultSet);
                    items.add(item);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE,
                    "Error al obtener detalle del pedido", ex);
        }

        DetallePedido detallePedido = new DetallePedido((ArrayList<ItemMenu>) items);
        return detallePedido;
    }


    @Override
    public Pedido obtenerPorId(int id) {
        String sql = "SELECT p.id, p.clienteId, p.estado_pedido, p.formaPagoId, " +
                "c.cuit AS cuit_cliente, c.nombre AS nombre_cliente, c.email AS email_cliente, c.direccion AS direccion_cliente, c.latitud AS latitud_cliente, c.longitud AS longitud_cliente, "
                +
                "f.id AS id_FormaPago, f.tipoFormaPago, f.aliasVendedor, f.cuitVendedor, f.cbuVendedor " +
                "FROM Pedido p " +
                "JOIN Cliente c ON p.clienteId = c.id_cliente " +
                "JOIN FormaPago f ON p.formaPagoId = f.id " +
                "WHERE p.id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Pedido pedido = mapResultSetToEntity(resultSet);

                    DetallePedido detallePedido = obtenerDetallePedidoPorPedidoId(id);
                    detallePedido.setPedido(pedido);
                    pedido.setDetallePedido(detallePedido);
                    
                    return pedido;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al obtener pedido por ID",
                    ex);
        }

        return null;
    }

    @Override
    public List<Pedido> obtenerTodos() {
        String sql = "SELECT p.id, p.clienteId, p.estado_pedido, p.formaPagoId, " +
                "c.cuit, c.nombre AS nombre_cliente, c.email AS email_cliente, c.direccion AS direccion_cliente, c.latitud AS latitud_cliente, c.longitud AS longitud_cliente, "
                +
                "f.tipoFormaPago, f.aliasVendedor, f.cuitVendedor, f.cbuVendedor " +
                "FROM Pedido p " +
                "JOIN Cliente c ON p.clienteId = c.id_cliente " +
                "JOIN FormaPago f ON p.formaPagoId = f.id";

        List<Pedido> pedidos = new ArrayList<>();

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pedido pedido = mapResultSetToEntity(resultSet);
                pedidos.add(pedido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al obtener todos los pedidos",
                    ex);
        }

        return pedidos;
    }
}