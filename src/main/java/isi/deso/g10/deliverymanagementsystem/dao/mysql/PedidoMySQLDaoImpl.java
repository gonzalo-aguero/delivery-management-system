/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import isi.deso.g10.deliverymanagementsystem.dao.mysql.mappers.PedidoMapper;
import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;

/**
 * Implementación del DAO de Pedido para la base de datos MySQL.
 * Esta clase utiliza el patrón Singleton para asegurar que solo se cree una
 * instancia.
 * Proporciona métodos para crear, actualizar y recuperar entidades de Pedido de
 * la base de datos.
 * 
 * Nota: El mapeo de profundidad llega hasta ItemsMenu (con su correspondiente
 * herencia).
 * Si se desea el vendedor del ítem, se debe realizar otra consulta.
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

    protected String getDetalleTableName() {
        return "DetallePedido";
    }

    @Override
    protected String getPrimaryKeyColumn() {
        return "id";
    }

    @Override
    public Pedido crear(Pedido pedido) {
        String sqlPedido = "INSERT INTO " + getTableName()
                + " (clienteId, estado_pedido, formaPagoId) VALUES (?, ?, ?)";
        String sqlDetallePedido = "INSERT INTO " + getDetalleTableName() + " (id_pedido, id_item) VALUES (?, ?)";

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
    public Pedido actualizar(Pedido pedido) {
        String sqlPedido = "UPDATE " + getTableName() + " SET clienteId = ?, estado_pedido = ?, formaPagoId = ? WHERE "
                + getPrimaryKeyColumn() + " = ?";
        String sqlDeleteDetallePedido = "DELETE FROM " + getDetalleTableName() + " WHERE id_pedido = ?";
        String sqlInsertDetallePedido = "INSERT INTO " + getDetalleTableName() + " (id_pedido, id_item) VALUES (?, ?)";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Iniciar transacción

            try (PreparedStatement statementPedido = connection.prepareStatement(sqlPedido)) {
                // Actualizar Pedido
                statementPedido.setInt(1, pedido.getCliente().getId());
                statementPedido.setString(2, pedido.getEstado().name());
                statementPedido.setInt(3, pedido.getFormapago().getId());
                statementPedido.setInt(4, pedido.getId());

                int affectedRows = statementPedido.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    throw new SQLException("Updating pedido failed, no rows affected.");
                }

                // Eliminar DetallePedido existente
                try (PreparedStatement statementDeleteDetallePedido = connection
                        .prepareStatement(sqlDeleteDetallePedido)) {
                    statementDeleteDetallePedido.setInt(1, pedido.getId());
                    statementDeleteDetallePedido.executeUpdate();
                }

                // Insertar nuevo DetallePedido
                try (PreparedStatement statementInsertDetallePedido = connection
                        .prepareStatement(sqlInsertDetallePedido)) {
                    for (ItemMenu item : pedido.getDetallePedido().getItems()) {
                        statementInsertDetallePedido.setInt(1, pedido.getId());
                        statementInsertDetallePedido.setInt(2, item.getId());
                        statementInsertDetallePedido.addBatch();
                    }
                    statementInsertDetallePedido.executeBatch();
                }

                connection.commit(); // Confirmar transacción
            } catch (SQLException ex) {
                connection.rollback(); // Revertir transacción en caso de error
                Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar pedido",
                        ex);
                throw ex;
            } finally {
                connection.setAutoCommit(true); // Restaurar modo de auto-commit
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar pedido", ex);
        }

        return pedido;
    }

    /**
     * Obtiene el detalle de un pedido por su ID.
     *
     * @param pedidoId El ID del pedido.
     * @return Un objeto DetallePedido que contiene una lista de los ítems del menú
     *         asociados al pedido.
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

    /**
     * Obtiene un pedido por su ID.
     *
     * @param id el ID del pedido a obtener.
     * @return el pedido correspondiente al ID proporcionado, o null si no se
     *         encuentra.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
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
                "c.cuit AS cuit_cliente, c.nombre AS nombre_cliente, c.email AS email_cliente, c.direccion AS direccion_cliente, c.latitud AS latitud_cliente, c.longitud AS longitud_cliente, "
                +
                "f.id AS id_FormaPago, f.tipoFormaPago, f.aliasVendedor, f.cuitVendedor, f.cbuVendedor " +
                "FROM Pedido p " +
                "JOIN Cliente c ON p.clienteId = c.id_cliente " +
                "JOIN FormaPago f ON p.formaPagoId = f.id";

        List<Pedido> pedidos = new ArrayList<>();

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Pedido pedido = mapResultSetToEntity(resultSet);

                DetallePedido detallePedido = obtenerDetallePedidoPorPedidoId(pedido.getId());
                detallePedido.setPedido(pedido);
                pedido.setDetallePedido(detallePedido);

                pedidos.add(pedido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al obtener todos los pedidos",
                    ex);
        }

        return pedidos;
    }

    @Override
    public boolean eliminar(int id) {
        String sqlDeleteDetallePedido = "DELETE FROM "+getDetalleTableName()+" WHERE id_pedido = ?";
        String sqlDeletePedido = "DELETE FROM "+getTableName()+" WHERE "+getPrimaryKeyColumn()+" = ?";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false); // Iniciar transacción

            try {
                // Eliminar DetallePedido asociado
                try (PreparedStatement statementDeleteDetallePedido = connection
                        .prepareStatement(sqlDeleteDetallePedido)) {
                    statementDeleteDetallePedido.setInt(1, id);
                    statementDeleteDetallePedido.executeUpdate();
                }

                // Eliminar Pedido
                try (PreparedStatement statementDeletePedido = connection.prepareStatement(sqlDeletePedido)) {
                    statementDeletePedido.setInt(1, id);
                    int affectedRows = statementDeletePedido.executeUpdate();

                    if (affectedRows == 0) {
                        connection.rollback();
                        throw new SQLException("Deleting pedido failed, no rows affected.");
                    }
                }

                connection.commit(); // Confirmar transacción
                return true;
            } catch (SQLException ex) {
                connection.rollback(); // Revertir transacción en caso de error
                Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al eliminar pedido", ex);
                throw ex;
            } finally {
                connection.setAutoCommit(true); // Restaurar modo de auto-commit
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al eliminar pedido", ex);
            return false;
        }
    }
}