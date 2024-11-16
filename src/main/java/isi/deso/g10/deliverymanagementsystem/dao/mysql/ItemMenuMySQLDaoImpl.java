/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.dao.memory.CategoriaMemory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author giuli
 */
public class ItemMenuMySQLDaoImpl extends GenericMySQLDaoImpl<ItemMenu>{

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
    public List<ItemMenu> obtenerTodos() {
        List<ItemMenu> list = new ArrayList<>();
        String sql = "SELECT DISTINCT * " +
             "FROM " + getTableName() + " im " +
             "LEFT JOIN plato p ON im.id = p.id_item " +
             "LEFT JOIN bebida b ON im.id = b.id_item";
        try (Connection connection = getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                list.add(mapResultSetToEntity(resultSet));
            }
        } catch (SQLException e) {
            Logger.getLogger(GenericMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error retrieving all records", e);
        }
        return list;
    }

    @Override
    public ItemMenu obtenerPorId(int id) {
        String sql = "SELECT * FROM " 
            + getTableName() + " im " +
             "LEFT JOIN plato p ON im.id = p.id_item " +
             "LEFT JOIN bebida b ON im.id = b.id_item" + " WHERE im." + getPrimaryKeyColumn() + " = ?";
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
    
    
    @Override
    protected ItemMenu mapResultSetToEntity(ResultSet resultSet) throws SQLException {
            ItemMenu itemMenu;
            String tipo;
            if(resultSet.getString("graduacionAlcoholica") != null){
                itemMenu = new Bebida();
                tipo="bebida";
            }
            else if(resultSet.getString("peso") != null){
                itemMenu = new Plato();
                tipo="plato";
            }
            else throw new RuntimeException("El resultado no coincide con alguno de los dos tipos(Plato/Bebida)");
            
            if(itemMenu!=null){
            itemMenu.setId(resultSet.getInt("id"));
            itemMenu.setNombre(resultSet.getString("nombre"));
            itemMenu.setDescripcion(resultSet.getString("descripcion"));
            itemMenu.setPrecio(resultSet.getDouble("precio"));
            String categoriaDesc= resultSet.getString("categoria");
            Categoria categoria = CategoriaMemory.getInstance().obtenerTodos().stream().filter(e-> e.getDescripcion().equals(categoriaDesc)).
                                        findFirst().orElseThrow(() -> new RuntimeException("Error de mapeo"));
            itemMenu.setCategoria(categoria);
            itemMenu.setCalorias(resultSet.getInt("calorias"));
            itemMenu.setAptoCeliaco(resultSet.getBoolean("aptoceliaco"));
            itemMenu.setAptoVegano(resultSet.getBoolean("aptovegano"));    
            itemMenu.setAptoVegetariano(resultSet.getBoolean("aptovegetariano"));   
            itemMenu.setVendedor(VendedorMySQLDaoImpl.getInstance().obtenerPorId(resultSet.getInt("id_vendedor")));
            }
            
            if(itemMenu instanceof Plato p){
                p.setPeso(resultSet.getDouble("peso"));
                
                return p;
            } else if(itemMenu instanceof Bebida b){
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
    
    protected String getChildrenTableName(ItemMenu entity){
        if(entity instanceof Plato){
            return "plato";
        }
        else if(entity instanceof Bebida){
            return "bebida";
        }
        else return null;
    }

    @Override
    public ItemMenu crear(ItemMenu entity) {
        String sqlItemMenu = "INSERT INTO " + getTableName() + "(nombre, descripcion, precio, categoria, calorias, aptoCeliaco, aptoVegetariano, aptoVegano, id_vendedor) VALUES (?,?,?,?,?,?,?,?,?);";
        String sqlPlato = "INSERT INTO " + getChildrenTableName(entity) + " VALUES (?,?);";
        String sqlBebida = "INSERT INTO " + getChildrenTableName(entity) + " VALUES (?,?,?);";

        try (Connection connection = getConnection()) {
            // Inicia una transacción
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sqlItemMenu, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, entity.getNombre());
                statement.setString(2, entity.getDescripcion());
                statement.setDouble(3, entity.getPrecio());
                statement.setString(4, entity.getCategoria().toString());
                statement.setDouble(5, entity.getCalorias());
                statement.setBoolean(6, entity.isAptoCeliaco());
                statement.setBoolean(7, entity.isAptoVegetariano());
                statement.setBoolean(8, entity.isAptoVegano());
                statement.setInt(9, entity.getVendedor().getId());

                
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            entity.setId(generatedKeys.getInt(1)); // Obtener el id generado
                        }
                    }
                }
            }

            
            if (entity instanceof Plato plato) {
                try (PreparedStatement statement = connection.prepareStatement(sqlPlato)) {
                    statement.setInt(1, plato.getId());
                    statement.setDouble(2, plato.getPeso());
                    statement.executeUpdate();
                }
            } else if (entity instanceof Bebida bebida) {
                try (PreparedStatement statement = connection.prepareStatement(sqlBebida)) {
                    statement.setInt(1, bebida.getId());
                    statement.setDouble(2, bebida.getGraduacionAlcoholica());
                    statement.setDouble(3, bebida.getVolumenEnMl());
                    statement.executeUpdate();
                }
            }

            //Commit
            connection.commit();
        } catch (SQLException e) {
          
            Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al crear item menu", e);
            try (Connection connection = getConnection()) {
                connection.rollback();  // Revertir los cambios
            } catch (SQLException rollbackEx) {
                Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al revertir la transacción", rollbackEx);
            }
            return null;
        }

        return entity;
    }

    @Override
    public ItemMenu actualizar(ItemMenu entity) {
        String sql = "UPDATE " + getTableName() + 
            " SET nombre = ?, descripcion = ?, precio = ?, categoria = ?, calorias = ?, aptoCeliaco = ?, aptoVegetariano = ?, aptoVegano = ? " +
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
            Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar ItemMenu", ex);
            return null;
        }
        
                if (entity instanceof Plato plato) {
                sql = "UPDATE " + getChildrenTableName(entity) + " SET peso = ? WHERE id = ?";
                try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setDouble(1, plato.getPeso());
                    statement.setInt(2, plato.getId());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar Plato", e);
                }
            } else if (entity instanceof Bebida bebida) {
                sql = "UPDATE " + getChildrenTableName(entity) + " SET graduacionAlcoholica = ?, volumenEnMl = ? WHERE id = ?";
                try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setDouble(1, bebida.getGraduacionAlcoholica());
                    statement.setDouble(2, bebida.getVolumenEnMl());
                    statement.setInt(3, bebida.getId());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    Logger.getLogger(ItemMenuMySQLDaoImpl.class.getName()).log(Level.SEVERE, "Error al actualizar Bebida", e);
                }
            }

        return entity;
    }
    
   
}
