/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ItemMenuDao;
import isi.deso.g10.deliverymanagementsystem.model.*;
import isi.deso.g10.deliverymanagementsystem.exception.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author giuli
 */
public class ItemMenuMemory implements ItemMenuDao {

    private ArrayList<ItemMenu> items;
    private static ItemMenuMemory self;
    
    
    private ItemMenuMemory() {
        this.items = new ArrayList<>();
        this.generarItems();
        self = this;
    }
        
    public static ItemMenuMemory getInstance(){
        if(self == null){
            self = new ItemMenuMemory();
        }
        return self;
    }
    
    private void generarItems() {
        Categoria minutas = new Categoria(1, "minuta", Categoria.TipoItem.COMIDA);
        Categoria bebida = new Categoria(1, "bebida", Categoria.TipoItem.BEBIDA);
        Plato milanesaPollo = new Plato(200, 1, "Milanesa de Pollo", "Milanesa de Pollo", 4500, minutas, 400, false, false, false);
        items.add(milanesaPollo);
        Plato pizzaMuzza = new Plato(350, 2, "Pizza de Muzza", "Pizza con queso mozzarella", 3000, minutas, 800, false, false, false);
        items.add(pizzaMuzza);
        Plato papasFritas = new Plato(250, 3, "Papas Fritas", "Papas fritas crocantes", 1500, minutas, 600, false, true, true);
        items.add(papasFritas);
        Bebida cocaCola = new Bebida(0, 500, 4, "Coca Cola", "Gaseosa Coca Cola 500ml", 500, bebida, 200, true, true, true);
        items.add(cocaCola);
        //double graduacionAlcoholica, double volumenEnMl, int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano
        //double peso, int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano
        Bebida agua = new Bebida(0, 500, 5, "Agua mineral", "Agua sin gas 500ml", 300.00, bebida, 100, true, true, true);
        items.add(agua);
        Bebida cerveza = new Bebida(5.70, 700, 6, "Cerveza DUFF", "Cerveza DUFF 700ml", 600.00, bebida, 500, true, true, true);
        items.add(cerveza);
    }

    @Override
    public List<ItemMenu> obtenerItemsMenu() {
        return items;
    }

    @Override
    public ItemMenu buscarItemMenuPorId(int id) {
        try {
            ItemMenu itemRet = null;
            for (ItemMenu item : items) {
                if (item.getId() == id) {
                    itemRet = item;
                }
            }
            if (itemRet == null) {
                throw new ItemNoEncontradoException("No se han encontrado el item menu que deseaba");
            } else {
                return itemRet;
            }
        } catch (ItemNoEncontradoException item) {
            return null;
        }
    }

    @Override
    public boolean eliminarItemMenu(int id) {
        boolean borrado = false;
        for (ItemMenu item : items) {
            if (item.getId() == id) {
                items.remove(item);
                borrado = true;
            }
        }
        return borrado;
    }

    @Override
    public boolean actualizarItemMenu(String descripcion, double precio, int id) {
        boolean modificado = false;
        for (ItemMenu item : items) {
            if (item.getId() == id) {
                item.setDescripcion(descripcion);
                item.setPrecio(precio);
                modificado = true;
            }
        }
        return modificado;
    }

    @Override
    public ItemMenu crearNuevoItemMenu(String tipo, Categoria cat, double peso, double graduacionAlcoholica, double volumenEnMl, String nombre, String descripcion, double precio, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano) {
        if (tipo.equals("Bebida")) {
            Random random = new Random(System.currentTimeMillis());
            int intRan = random.nextInt();
            if (intRan < 0) {
                intRan = intRan * (-1);
            }
            return new Bebida(graduacionAlcoholica, volumenEnMl, intRan, nombre, descripcion, precio, cat, calorias, aptoCeliaco, aptoVegetariano, aptoVegano);
        } else {
            Random random = new Random(System.currentTimeMillis());
            int intRan = random.nextInt();
            if (intRan < 0) {
                intRan = intRan * (-1);
            }
            return new Plato(peso, intRan, nombre, descripcion, precio, cat, calorias, aptoCeliaco, aptoVegetariano, aptoVegano);
        }
    }

    @Override
    public ItemMenu addItemMenu(ItemMenu itemMenu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<ItemMenu> buscarVendedor(Vendedor vendedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
