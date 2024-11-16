package isi.deso.g10.deliverymanagementsystem.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DatabaseInitializer {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String URLDESARROLLO = "jdbc:mysql://localhost:3306/tpdesarrollo";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void initialize() {

        // CREAR BASE DE DATOS
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {

            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS tpdesarrollo";
            stmt.executeUpdate(createDatabaseSQL);
            System.out.println("Base de datos 'tpdesarrollo' verificada o creada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // CREAR TABLAS
        try (Connection conn = DriverManager.getConnection(URLDESARROLLO, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {

            // Crear tabla vendedor
            String ejecutarSQL = "CREATE TABLE IF NOT EXISTS vendedor (" +
                    "    id_vendedor INT AUTO_INCREMENT PRIMARY KEY," +
                    "    nombre VARCHAR(100) NOT NULL," +
                    "    direccion VARCHAR(255)," +
                    "    latitud DOUBLE," +
                    "    longitud DOUBLE" +
                    ");";

            stmt.executeUpdate(ejecutarSQL);

            // Crear tabla cliente
            ejecutarSQL = "CREATE TABLE IF NOT EXISTS cliente (" +
                    "    id_cliente INT AUTO_INCREMENT PRIMARY KEY," +
                    "    cuit VARCHAR(20) NOT NULL UNIQUE," +
                    "    nombre VARCHAR(100) NOT NULL," +
                    "    email VARCHAR(100) NOT NULL," +
                    "    direccion VARCHAR(255)," +
                    "    latitud DOUBLE," +
                    "    longitud DOUBLE" +
                    ");";
            stmt.executeUpdate(ejecutarSQL);

            // Crear tabla item_menu
            ejecutarSQL = "CREATE TABLE IF NOT EXISTS ItemMenu (" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    nombre VARCHAR(100) NOT NULL," +
                    "    descripcion TEXT," +
                    "    precio DOUBLE NOT NULL," +
                    "    categoria VARCHAR(50)," + // Entrada, plato principal, postre, bebida, etc.
                    "    calorias INT," +
                    "    aptoCeliaco BOOLEAN," +
                    "    aptoVegetariano BOOLEAN," +
                    "    aptoVegano BOOLEAN," +
                    "    id_vendedor INT NOT NULL," +
                    "    FOREIGN KEY (id_vendedor) REFERENCES vendedor(id_vendedor)" +
                    ");";
            stmt.executeUpdate(ejecutarSQL);

            // Crear tabla Plato
            ejecutarSQL = "CREATE TABLE IF NOT EXISTS Plato (" +
                    "    id_item INT PRIMARY KEY," +
                    "    peso DOUBLE NOT NULL," +
                    "    FOREIGN KEY (id_item) REFERENCES ItemMenu(id)" +
                    ");";
            stmt.executeUpdate(ejecutarSQL);

            // Crear tabla Bebida
            ejecutarSQL = "CREATE TABLE IF NOT EXISTS Bebida (" +
                    "    id_item INT PRIMARY KEY," +
                    "    graduacionAlcoholica DOUBLE NOT NULL," +
                    "    volumenEnMl DOUBLE NOT NULL," +
                    "    FOREIGN KEY (id_item) REFERENCES ItemMenu(id)" +
                    ");";
            stmt.executeUpdate(ejecutarSQL);

            // Crear tabla FormaPago
            ejecutarSQL = "CREATE TABLE IF NOT EXISTS FormaPago (" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    id_vendedor INT," +
                    "    tipoFormaPago VARCHAR(50) NOT NULL," +
                    "    aliasVendedor VARCHAR(100)," +
                    "    cuitVendedor VARCHAR(20)," +
                    "    cbuVendedor VARCHAR(20)," +
                    "    FOREIGN KEY (id_vendedor) REFERENCES vendedor(id_vendedor)" +
                    ");";
            stmt.executeUpdate(ejecutarSQL);

            // Crear tabla Pedido
            ejecutarSQL = "CREATE TABLE IF NOT EXISTS Pedido (" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    clienteId INT," +
                    "    estado_pedido VARCHAR(20)," +
                    // " detallePedidoId INT," +
                    // " datosPagoId INT," +
                    "    formaPagoId INT," +
                    "    FOREIGN KEY (clienteId) REFERENCES cliente(id_cliente)," +
                    // " FOREIGN KEY (detallePedidoId) REFERENCES DetallePedido(id)," +
                    // " FOREIGN KEY (datosPagoId) REFERENCES Pago(id)," +
                    "    FOREIGN KEY (formaPagoId) REFERENCES FormaPago(id)" +
                    ");";
            stmt.executeUpdate(ejecutarSQL);

            // Crear tabla DetallePedido
            // ? Supuse que al igual que la clase DetallePedido, debería tener una lista
            // ? de items y un pedido asociado.
            ejecutarSQL = "CREATE TABLE IF NOT EXISTS DetallePedido (" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    id_pedido INT NOT NULL," +
                    "    id_item INT NOT NULL," +
                    "    FOREIGN KEY (id_item) REFERENCES ItemMenu(id)," +
                    "    FOREIGN KEY (id_pedido) REFERENCES Pedido(id)" +
                    ");";
            stmt.executeUpdate(ejecutarSQL);

            // Crear tabla Pago
            ejecutarSQL = "CREATE TABLE IF NOT EXISTS Pago (" +
                    "    id_pedido INT PRIMARY KEY," +
                    "    monto DOUBLE NOT NULL," +
                    "    formaPago VARCHAR(50) NOT NULL," +
                    "    fecha DATE NOT NULL," +
                    "    nombreCliente VARCHAR(100)," +
                    "    cuitCliente VARCHAR(20)," +
                    "    FOREIGN KEY (id_pedido) REFERENCES Pedido(id)" +
                    ");";
            stmt.executeUpdate(ejecutarSQL);

            System.out.println("Tablas creadas correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertTestData() {
        String sqlFilePath = "test_data.sql";

        try (Connection conn = DriverManager.getConnection(URLDESARROLLO, USER, PASSWORD);
                Statement stmt = conn.createStatement();
                BufferedReader br = new BufferedReader(new FileReader(sqlFilePath))) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line);
                if (line.trim().endsWith(";")) {
                    stmt.executeUpdate(sql.toString());
                    sql.setLength(0); // Clear the StringBuilder for the next statement
                }
            }

            System.out.println("Datos de prueba insertados correctamente.");
        } catch (IOException e) {
            Logger.getLogger(DatabaseInitializer.class.getName()).log(Level.SEVERE, "Error al insertar datos de prueba", e);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) { // SQL state code for duplicate entry
                Logger.getLogger(DatabaseInitializer.class.getName()).log(Level.WARNING, "Datos de prueba ya insertados anteriormente.");
            } else {
                Logger.getLogger(DatabaseInitializer.class.getName()).log(Level.SEVERE, "Error al insertar datos de prueba", e);
            }
        }
    }

}