/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Categoria.TipoItem;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giuli
 */
public class ItemMenuMySQLDaoImpl extends GenericMySQLDaoImpl<ItemMenu> {

    private static ItemMenuMySQLDaoImpl instance;

    private ItemMenuMySQLDaoImpl() {
        // Constructor privado para evitar instanciación externa
    }

    // Use Singleton pattern for Cliente
    public static synchronized ItemMenuMySQLDaoImpl getInstance() {
        if (instance == null) {
            instance = new ItemMenuMySQLDaoImpl();
        }
        return instance;
    }

    @Override
    protected ItemMenu mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        ItemMenu itemMenu;
        String tipo;
        if (resultSet.getString("graduacionAlcoholica") != null) {
            itemMenu = new Bebida();
            tipo = "bebida";
        } else if (resultSet.getString("peso") != null) {
            itemMenu = new Plato();
            tipo = "plato";
        } else
            throw new RuntimeException("El resultado no coincide con alguno de los dos tipos(Plato/Bebida)");

        if (itemMenu != null) {
            itemMenu.setId(resultSet.getInt("id"));
            itemMenu.setNombre(resultSet.getString("nombre"));
            itemMenu.setDescripcion(resultSet.getString("descripcion"));
            itemMenu.setPrecio(resultSet.getDouble("precio"));
            Categoria categoria = new Categoria(0,
                    resultSet.getString("categoria"),
                    tipo == "bebida" ? TipoItem.BEBIDA : TipoItem.COMIDA);
            itemMenu.setCategoria(categoria);
            itemMenu.setCalorias(resultSet.getInt("calorias"));
            itemMenu.setAptoCeliaco(resultSet.getBoolean("aptoceliaco"));
            itemMenu.setAptoVegano(resultSet.getBoolean("aptovegano"));
            itemMenu.setAptoVegetariano(resultSet.getBoolean("aptovegetariano"));
            itemMenu.setVendedor(VendedorMySQLDaoImpl.getInstance().obtenerPorId(resultSet.getInt("id_vendedor")));
        }

        if (itemMenu instanceof Plato p) {
            p.setPeso(resultSet.getDouble("peso"));

            return p;
        } else if (itemMenu instanceof Bebida b) {
            b.setVolumenEnMl(resultSet.getDouble("volumenEnMl"));
            b.setGraduacionAlcoholica(resultSet.getDouble("graduacionAlcoholica"));

            return b;
        }

        return null;
    }

    @Override
    protected String getTableName() {
        return "itemmenu";
    }

    @Override
    protected String getPrimaryKeyColumn() {
        return "id";
    }

    protected String getChildrenTableName(ItemMenu entity) {
        if (entity instanceof Plato) {
            return "plato";
        } else if (entity instanceof Bebida) {
            return "bebida";
        } else
            return null;
    }

    @Override
    public ItemMenu crear(ItemMenu entity) {
        String sql = "INSERT INTO " + getTableName()
                + "(nombre,descripcion,precio,categoria,calorias,aptoCeliaco,aptoVegetariano,aptoVegano) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getNombre());
            statement.setString(2, entity.getDescripcion());
            statement.setDouble(3, entity.getPrecio());
            statement.setString(4, entity.getCategoria().toString());
            statement.setDouble(5, entity.getCalorias());
            statement.setBoolean(6, entity.isAptoCeliaco());
            statement.setBoolean(7, entity.isAptoVegetariano());
            statement.setBoolean(8, entity.isAptoVegano());

            // Ejecutar la consulta
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear item menu", e);
        }

        if (entity instanceof Plato plato) {
            sql = "INSERT INTO " + getChildrenTableName(entity) + "VALUES (?,?)";

            try (Connection connection = getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, plato.getId());
                statement.setDouble(2, plato.getPeso());

                statement.executeUpdate();
            } catch (SQLException e) {
                Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear item menu", e);
            }
        }

        else if (entity instanceof Bebida bebida) {
            sql = "INSERT INTO " + getChildrenTableName(entity) + "VALUES (?,?,?)";

            try (Connection connection = getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, bebida.getId());
                statement.setDouble(2, bebida.getGraduacionAlcoholica());
                statement.setDouble(3, bebida.getVolumenEnMl());

                statement.executeUpdate();
            } catch (SQLException e) {
                Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear item menu", e);
            }
        }

        return entity;
    }

    @Override
    public ItemMenu actualizar(ItemMenu entity) {
        String sql = "UPDATE " + getTableName() +
                " SET nombre = ?, descripcion = ?, precio = ?, categoria = ?, calorias = ?, aptoCeliaco = ?, aptoVegetariano = ?, aptoVegano = ? "
                +
                "WHERE " + getPrimaryKeyColumn() + " = ?";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getNombre());
            statement.setString(2, entity.getDescripcion());
            statement.setDouble(3, entity.getPrecio());
            statement.setString(4, entity.getCategoria().toString());
            statement.setDouble(5, entity.getCalorias());
            statement.setBoolean(6, entity.isAptoCeliaco());
            statement.setBoolean(7, entity.isAptoVegetariano());
            statement.setBoolean(8, entity.isAptoVegano());
            statement.setInt(9, entity.getId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La actualización falló, no se afectaron filas.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar ItemMenu",
                    ex);
            return null;
        }

        if (entity instanceof Plato plato) {
            sql = "UPDATE " + getChildrenTableName(entity) + " SET peso = ? WHERE id = ?";
            try (Connection connection = getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, plato.getPeso());
                statement.setInt(2, plato.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar Plato",
                        e);
            }
        } else if (entity instanceof Bebida bebida) {
            sql = "UPDATE " + getChildrenTableName(entity)
                    + " SET graduacionAlcoholica = ?, volumenEnMl = ? WHERE id = ?";
            try (Connection connection = getConnection();
                    PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setDouble(1, bebida.getGraduacionAlcoholica());
                statement.setDouble(2, bebida.getVolumenEnMl());
                statement.setInt(3, bebida.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar Bebida",
                        e);
            }
        }

        return entity;
    }

}
