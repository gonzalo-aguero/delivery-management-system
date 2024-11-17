/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

import isi.deso.g10.deliverymanagementsystem.controller.swing.MenuController;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;
import isi.deso.g10.deliverymanagementsystem.dao.mysql.VendedorMySQLDaoImpl;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.utils.DatabaseInitializer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;

/**
 *
 * @author gonzalo90fa
 */
public class DesarrolloBDD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
         * Esta clase es para ejecutar y probar directamente los dao de mysql 
         * sin tener que cargar toda la interfaz.
         *
         * Una vez que funcionen bien todos los daos volvemos a usar la clase 
         * Etapa6 para arrancar la aplicacion con la interfaz y actualizar los 
         * daos en los controllers...
         */

        // Inicializamos la base de datos
        DatabaseInitializer.initialize();

        // Insertamos los datos de prueba
        DatabaseInitializer.insertTestData();
        
        PantallaPrincipal menu = new PantallaPrincipal();
        MenuController controller = new MenuController(menu);
        
    }
    
}
