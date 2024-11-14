package isi.deso.g10.deliverymanagementsystem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String URLDESARROLLO = "jdbc:mysql://localhost:3306/tpdesarrollo";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void initialize() {
        
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         Statement stmt = conn.createStatement()) {

        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS tpdesarrollo";
        stmt.executeUpdate(createDatabaseSQL);
        System.out.println("Base de datos 'tpdesarrollo' verificada o creada.");

    } catch (Exception e) {
        e.printStackTrace();
        return;
    }
        
        try (Connection conn = DriverManager.getConnection(URLDESARROLLO, USER, PASSWORD);
            Statement stmt = conn.createStatement()) {

            String ejecutarSQL = 
            "CREATE TABLE IF NOT EXISTS vendedor (" +
            "    id_vendedor INT AUTO_INCREMENT PRIMARY KEY," +
            "    nombre VARCHAR(100) NOT NULL," +
            "    direccion VARCHAR(255)," +
            "    latitud DOUBLE," +
            "    longitud DOUBLE" +
            ");";
            
            stmt.executeUpdate(ejecutarSQL);
            ejecutarSQL = 
            "CREATE TABLE IF NOT EXISTS cliente (" +
            "    id_cliente INT AUTO_INCREMENT PRIMARY KEY," +
            "    cuit VARCHAR(20) NOT NULL," +
            "    nombre VARCHAR(100) NOT NULL," +
            "    email VARCHAR(100) NOT NULL," +
            "    direccion VARCHAR(255)," +
            "    latitud DOUBLE," +
            "    longitud DOUBLE" +
            ");";

            stmt.executeUpdate(ejecutarSQL);
                ejecutarSQL = 
            "CREATE TABLE IF NOT EXISTS FormaPago (" +
            "    id INT AUTO_INCREMENT PRIMARY KEY," +
            "    montoFinal DOUBLE NOT NULL," +
            "    tipoFormaPago VARCHAR(50) NOT NULL," +
            "    alias VARCHAR(100)," +
            "    cuitCliente VARCHAR(20)," +
            "    cbuCliente VARCHAR(20)" +
            ");";
                
            stmt.executeUpdate(ejecutarSQL);
            ejecutarSQL = 
            "CREATE TABLE IF NOT EXISTS DetallePedido (" +
            "    id INT AUTO_INCREMENT PRIMARY KEY" +
            ");";
            
            stmt.executeUpdate(ejecutarSQL);
            ejecutarSQL = 
            "CREATE TABLE IF NOT EXISTS Pago (" +
            "    id INT AUTO_INCREMENT PRIMARY KEY," +
            "    monto DOUBLE NOT NULL," +
            "    formaPago VARCHAR(50) NOT NULL," +
            "    fecha DATE NOT NULL," +
            "    nombreCliente VARCHAR(100)," +
            "    cuitCliente VARCHAR(20)" +
            ");";
        
            stmt.executeUpdate(ejecutarSQL);
            ejecutarSQL = 
            "CREATE TABLE IF NOT EXISTS Pedido (" +
            "    id INT AUTO_INCREMENT PRIMARY KEY," +
            "    clienteId INT," +
            "    estado_pedido VARCHAR(20)," +
            "    detallePedidoId INT," +
            "    datosPagoId INT," +
            "    formaPagoId INT," +
            "    FOREIGN KEY (clienteId) REFERENCES cliente(id_cliente)," +
            "    FOREIGN KEY (detallePedidoId) REFERENCES DetallePedido(id)," +
            "    FOREIGN KEY (datosPagoId) REFERENCES Pago(id)," +
            "    FOREIGN KEY (formaPagoId) REFERENCES FormaPago(id)" +
            ");";
            
            stmt.executeUpdate(ejecutarSQL);
            System.out.println("Tablas creadas y datos insertados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}