/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.ArrayList;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;
import java.util.List;

/**
 *
 * @author giuli
 */
public class VendedorMemory implements VendedorDao {

    private static VendedorMemory self;
    private ArrayList<Vendedor> vendedores;

    private VendedorMemory() {
        this.vendedores = generarVendedores();
        self = this;
    }

    public static VendedorMemory getInstance() {
        if (self == null) {
            self = new VendedorMemory();
        }
        return self;
    }

    public ArrayList<Vendedor> generarVendedores() {
        ArrayList<Vendedor> vendedores = new ArrayList();

        Coordenada coordenada1 = new Coordenada(10, 20);
        Coordenada coordenada2 = new Coordenada(15, 25);
        Coordenada coordenada3 = new Coordenada(20, 30);
        Coordenada coordenada4 = new Coordenada(25, 35);
        Coordenada coordenada5 = new Coordenada(30, 40);

        Vendedor vendedor1 = new Vendedor(1, "Vendedor 1", "Direccion 1", coordenada1);
        Vendedor vendedor2 = new Vendedor(2, "Vendedor 2", "Direccion 2", coordenada2);
        Vendedor vendedor3 = new Vendedor(3, "Vendedor 3", "Direccion 3", coordenada3);
        Vendedor vendedor4 = new Vendedor(4, "Vendedor 4", "Direccion 4", coordenada4);
        Vendedor vendedor5 = new Vendedor(5, "Vendedor 5", "Direccion 5", coordenada5);

        vendedores.add(vendedor1);
        vendedores.add(vendedor2);
        vendedores.add(vendedor3);
        vendedores.add(vendedor4);
        vendedores.add(vendedor5);

        this.vendedores = vendedores;

        return vendedores;
    }

    @Override
    public List<Vendedor> obtenerVendedores() {
        return vendedores;
    }

    @Override
    public Vendedor agregarVendedor(Vendedor vendedor) {
        vendedores.add(vendedor); // Agrega el vendedor a la lista
        return vendedor; // Retorna el vendedor agregado
    }

    @Override
    public Vendedor actualizarVendedor(Vendedor vendedor) {
        for (Vendedor v : vendedores) {
            if (v.getId() == vendedor.getId()) { // Compara IDs
                int index = vendedores.indexOf(v); // Obtiene el índice del vendedor
                vendedores.set(index, vendedor); // Actualiza el vendedor en la lista
                return vendedor; // Retorna el vendedor actualizado
            }
        }
        return null; // Retorna null si no se encontró el vendedor para actualizar
    }

    @Override
    public boolean eliminarVendedor(int id) {
        for (Vendedor v : vendedores) {
            if (v.getId() == id) { // Compara IDs
                vendedores.remove(v); // Elimina el vendedor encontrado
                return true; // Retorna true si el vendedor fue eliminado
            }
        }
        return false; // Retorna false si no se encontró el vendedor
    }

    @Override
    public Vendedor buscarVendedorPorId(int id) {
        for (Vendedor v : vendedores) {
            if (v.getId() == id) { // Compara IDs
                return v; // Retorna el vendedor encontrado
            }
        }
        return null; // Retorna null si no se encuentra el vendedor
    }
}
