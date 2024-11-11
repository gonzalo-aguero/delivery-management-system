package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteMySQLDaoImpl extends GenericMySQLDaoImpl<Cliente> {

    // Singleton instance for Cliente
    private static ClienteMySQLDaoImpl instance;

    private ClienteMySQLDaoImpl() {
        // Constructor privado para evitar instanciación externa
    }

    // Use Singleton pattern for Cliente
    public static synchronized ClienteMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new ClienteMySQLDaoImpl();
        }
        return instance;
    }

    @Override
    protected Cliente mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(resultSet.getInt("id_cliente"));
        cliente.setCuit(resultSet.getString("cuit"));
        cliente.setNombre(resultSet.getString("nombre"));
        cliente.setEmail(resultSet.getString("email"));
        cliente.setDireccion(resultSet.getString("direccion"));
        cliente.setCoordenadas(new Coordenada(resultSet.getDouble("latitud"), resultSet.getDouble("longitud")));
        return cliente;
    }

    @Override
    protected String getTableName() {
        return "cliente";
    }

    @Override
    protected String getPrimaryKeyColumn() {
        return "id_cliente";
    }

    @Override
    public Cliente crear(Cliente entity) {
        String sql = "INSERT INTO " + getTableName() + " (cuit, nombre, email, direccion, latitud, longitud) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getCuit());
            statement.setString(2, entity.getNombre());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getDireccion());
            statement.setDouble(5, entity.getCoordenadas().getLatitud());
            statement.setDouble(6, entity.getCoordenadas().getLongitud());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                entity = null;
                throw new SQLException("Creating cliente failed, no rows affected.");
            }

            // Retrieve generated keys (ID) if there is an auto-increment ID in the database
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                } else {
                    entity = null;
                    throw new SQLException("Creating cliente failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear cliente", ex);
        }

        return entity;
    }

    @Override
    public Cliente actualizar(Cliente entity) {
        String sql = "UPDATE " + getTableName() + " SET cuit = ?, nombre = ?, email = ?, direccion = ?, latitud = ?, longitud = ? WHERE " + getPrimaryKeyColumn() + " = ?";
        
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getCuit());
            statement.setString(2, entity.getNombre());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getDireccion());
            statement.setDouble(5, entity.getCoordenadas().getLatitud());
            statement.setDouble(6, entity.getCoordenadas().getLongitud());
            statement.setInt(7, entity.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                entity = null;
                throw new SQLException("Updating cliente failed, no rows affected.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteMySQLDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return entity;
    }
}