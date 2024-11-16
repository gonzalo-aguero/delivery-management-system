/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.Pedido.EstadoPedido;
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
        Pedido pedido = new Pedido();
        pedido.setId(resultSet.getInt("id"));

        // Get and set Cliente
        Cliente cliente = new Cliente();
        cliente.setId(resultSet.getInt("clienteId"));
        cliente.setCuit(resultSet.getString("cuit"));
        cliente.setNombre(resultSet.getString("nombre_cliente"));
        cliente.setEmail(resultSet.getString("email_cliente"));
        cliente.setDireccion(resultSet.getString("direccion_cliente"));
        cliente.setCoordenadas(
                new Coordenada(resultSet.getDouble("latitud_cliente"), resultSet.getDouble("longitud_cliente")));
        pedido.setCliente(cliente);

        // Get and set EstadoPedido
        EstadoPedido estado;
        try {
            estado = EstadoPedido.valueOf(resultSet.getString("estado_pedido"));
        } catch (IllegalArgumentException e) {
            estado = null;
        }
        pedido.setEstado(estado);

        // Get and set Forma de Pago
        FormaPagoI formaPago;
        String tipoFormaPago = resultSet.getString("tipoFormaPago");
        if ("MercadoPago".equals(tipoFormaPago)) {
            formaPago = new FormaMercadoPago(resultSet.getString("aliasVendedor"));
        } else if ("Transferencia".equals(tipoFormaPago)) {
            formaPago = new FormaTransferencia(
                    resultSet.getString("cuitVendedor"),
                    resultSet.getString("cbuVendedor"));
        } else {
            formaPago = null;
        }
        pedido.setFormapago(formaPago);

        return pedido;
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
    public Pedido crear(Pedido entity) {
        String sql = "INSERT INTO " + getTableName() + " (clienteId, estado_pedido, formaPagoId) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, entity.getCliente().getId());
            statement.setString(2, entity.getEstado().toString());
            statement.setInt(3, entity.getFormapago().getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                entity = null;
                throw new SQLException("Creating pedido failed, no rows affected.");
            }

            // Retrieve generated keys (ID) if there is an auto-increment ID in the database
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                } else {
                    entity = null;
                    throw new SQLException("Creating pedido failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidoMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear pedido", ex);
        }

        return entity;
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

    @Override
    public Pedido obtenerPorId(int id) {
        String sql = "SELECT p.id, p.clienteId, p.estado_pedido, p.formaPagoId, " +
                "c.cuit, c.nombre AS nombre_cliente, c.email AS email_cliente, c.direccion AS direccion_cliente, c.latitud AS latitud_cliente, c.longitud AS longitud_cliente, "
                +
                "f.tipoFormaPago, f.aliasVendedor, f.cuitVendedor, f.cbuVendedor " +
                "FROM Pedido p " +
                "JOIN Cliente c ON p.clienteId = c.id_cliente " +
                "JOIN FormaPago f ON p.formaPagoId = f.id " +
                "WHERE p.id = ?";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEntity(resultSet);
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