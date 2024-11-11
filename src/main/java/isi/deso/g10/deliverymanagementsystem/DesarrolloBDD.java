/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;
import isi.deso.g10.deliverymanagementsystem.dao.mysql.VendedorMySQLDaoImpl;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;

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
        
        VendedorMySQLDaoImpl vendedorDao = VendedorMySQLDaoImpl.getInstance();
        
        // CREAMOS EL VENDEDOR (comprobar en bdd)
        Vendedor vendedor = new Vendedor(-1, "Juan R", "Calle Falsa 123", new Coordenada(1, 1));
        if(vendedorDao.crear(vendedor) == null){
            System.out.println("No se ha podido crear");
        }else {
            System.out.println("Creado correctamente");
        }
        
        // ACTUALIZAMOS EL VENDEDOR (comprobar en bdd)
        vendedor = new Vendedor(5, "Juan R", "Calle Falsa 123", new Coordenada(1, 1));
        vendedor.setNombre("Juan Ramírez " + new Time(System.currentTimeMillis()));
        if(vendedorDao.actualizar(vendedor) == null){
            System.out.println("No se ha podido actualizar");
        }else {
            System.out.println("Actualizado correctamente");
        }

        // ELIMINAMOS AL VENDEDOR CON ID 2
        if(vendedorDao.eliminar(3)){
            System.out.println("Eliminado correctamente");
        }else {
            System.out.println("No se ha podido eliminar");
        }
        
        // OBTENEMOS EL VENDEDOR CON ID 1
        Vendedor vendedorObtenido = vendedorDao.obtenerPorId(10120);
        if(vendedorObtenido == null){
            System.out.println("No se ha encontrado el vendedor");
        }else {
            System.out.println(vendedorObtenido.toString());
        }
        
        // OBTENEMOS TODOS LOS VENDEDORES
        ArrayList<Vendedor> vendedoresObtenidos = (ArrayList<Vendedor>) vendedorDao.obtenerTodos();
        if(vendedoresObtenidos.isEmpty()){
            System.out.println("No se han encontrado vendedores");
        }else {
            System.out.println("Vendedores encontrados:");
            vendedoresObtenidos.forEach(v -> System.out.println(v.getId() +"  |  "+ v.getNombre() +"  |  "+ v.getDireccion()));
        }

        System.out.println("Fin de la ejecución");
    }
    
}
