/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.GenericDao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gonzalo90fa
 * @param <T> Tipo de la entidad (clase)
 */
public abstract class GenericMySQLDaoImpl<T> implements GenericDao<T> {

    // Database URL, username, and password
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tpdesarrollo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Open a connection
    protected Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error connecting to database", e);
        }
    }

    /**
     * This method is responsible for mapping a row from the ResultSet (the
     * result of a SQL query) to an instance of the specific entity class.
     *
     * @param resultSet
     * @return The corresponding entity
     * @throws SQLException
     */
    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;
    
    protected abstract String getTableName();

    protected abstract String getPrimaryKeyColumn();

    // Generic method to create a record
    @Override
    public abstract T crear(T entity);

    // Generic method to retrieve a record by ID
    @Override
    public T obtenerPorId(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToEntity(resultSet);
            }
        } catch (SQLException e) {
            Logger.getLogger(GenericMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error retrieving record by ID", e);
        }
        return null;
    }

    // Generic method to retrieve all records
    @Override
    public List<T> obtenerTodos() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            Logger.getLogger(GenericMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error retrieving all records", e);
        }
        return list;
    }

    // Generic method to update a record
    @Override
    public abstract T actualizar(T entity);

    // Generic method to delete a record by ID
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKeyColumn() + " = ?";
        boolean deleted = false;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.executeUpdate();
            
            // Check if the deletion was successful
            if (statement.getUpdateCount() == 0) {
                throw new SQLException("No rows affected, deletion failed.");
            }

            deleted = true;
        } catch (SQLException e) {
            Logger.getLogger(GenericMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error deleting record by ID", e);
        }
        return deleted;
    }

    // Generic method to delete all records
    public boolean eliminarTodos() {
        String sql = "DELETE FROM " + getTableName();
        boolean deleted = false;
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            
            // Check if the deletion was successful
            if (statement.getUpdateCount() == 0) {
                throw new SQLException("No rows affected, deletion failed.");
            }

            deleted = true;
        } catch (SQLException e) {
            Logger.getLogger(GenericMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error deleting all records", e);
        }
        return deleted;
    }
}
