/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gonzalo90fa
 */
public class VendedorMySQLDaoImpl extends GenericMySQLDaoImpl<Vendedor> {

    // Singleton instance for Vendedor
    private static VendedorMySQLDaoImpl instance;

    private VendedorMySQLDaoImpl() {
        // Constructor privado para evitar instanciación externa
    }

    // Use Singleton pattern for Vendedor
    public static synchronized VendedorMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new VendedorMySQLDaoImpl();
        }
        return instance;
    }

    @Override
    protected Vendedor mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(resultSet.getInt("id_vendedor"));
        vendedor.setNombre(resultSet.getString("nombre"));
        vendedor.setDireccion(resultSet.getString("direccion"));
        vendedor.setCoordenadas(new Coordenada(resultSet.getDouble("latitud"), resultSet.getDouble("longitud")));
        return vendedor;
    }

    @Override
    protected String getTableName() {
        return "Vendedor";
    }

    @Override
    protected String getPrimaryKeyColumn() {
        return "id_vendedor";
    }

    @Override
    public Vendedor crear(Vendedor entity) {
        String sql = "INSERT INTO " + getTableName() + " (nombre, direccion, latitud, longitud) VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getNombre());
            statement.setString(2, entity.getDireccion());
            statement.setDouble(3, entity.getCoordenadas().getLatitud());
            statement.setDouble(4, entity.getCoordenadas().getLongitud());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                entity = null;
                throw new SQLException("Creating vendedor failed, no rows affected.");
            }

            // Retrieve generated keys (ID) if there is an auto-increment ID in the database
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                } else {
                    entity = null;
                    throw new SQLException("Creating vendedor failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendedorMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear vendedor", ex);
        }

        return entity;
    }

    @Override
    public Vendedor actualizar(Vendedor entity) {
        String sql = "UPDATE " + getTableName() + " SET nombre = ?, direccion = ?, latitud = ?, longitud = ? WHERE " + getPrimaryKeyColumn() + " = ?";
        
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getNombre());
            statement.setString(2, entity.getDireccion());
            statement.setDouble(3, entity.getCoordenadas().getLatitud());
            statement.setDouble(4, entity.getCoordenadas().getLongitud());
            statement.setInt(5, entity.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                entity = null;
                throw new SQLException("Updating vendedor failed, no rows affected.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(VendedorMySQLDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return entity;
    }

}
