/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ItemMenuDao;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;
import isi.deso.g10.deliverymanagementsystem.model.*;
import isi.deso.g10.deliverymanagementsystem.exception.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author giuli
 */
public class ItemMenuMemory implements ItemMenuDao {

    private ArrayList<ItemMenu> items;
    private static VendedorDao vendedorDao;
    private static ItemMenuMemory self;

    private ItemMenuMemory() {
        this.items = new ArrayList<>();
        this.vendedorDao = VendedorMemory.getInstance();
        
        this.generarItems();
        self = this;
    }

    public static ItemMenuMemory getInstance() {
        if (self == null) {
            self = new ItemMenuMemory();
        }
        return self;
    }

    private void generarItems() {
        ArrayList<Vendedor> vendedores = (ArrayList) vendedorDao.obtenerVendedores();
        Categoria minutas = new Categoria(1, "minuta", Categoria.TipoItem.COMIDA);
        Categoria bebida = new Categoria(1, "bebida", Categoria.TipoItem.BEBIDA);
        
        Plato milanesaPollo = new Plato(1, "Milanesa de Pollo", "Milanesa de Pollo", 4500, minutas, 400, false, false, false, vendedores.get(0), 200);
        items.add(milanesaPollo);

        Plato pizzaMuzza = new Plato(2, "Pizza de Muzza", "Pizza con queso mozzarella", 3000, minutas, 800, false, false, false, vendedores.get(1), 350);
        items.add(pizzaMuzza);

        Plato papasFritas = new Plato(3, "Papas Fritas", "Papas fritas crocantes", 1500, minutas, 600, false, true, true, vendedores.get(2), 250);
        items.add(papasFritas);

        // Crear Bebidas con vendedores asignados mediante .get()
        Bebida cocaCola = new Bebida(4, "Coca Cola", "Gaseosa Coca Cola 500ml", 500, bebida, 200, true, true, true, vendedores.get(3), 0, 500);
        items.add(cocaCola);

        Bebida agua = new Bebida(5, "Agua mineral", "Agua sin gas 500ml", 300, bebida, 100, true, true, true, vendedores.get(4), 0, 500);
        items.add(agua);

        Bebida cerveza = new Bebida(6, "Cerveza DUFF", "Cerveza DUFF 700ml", 600, bebida, 500, true, true, true, vendedores.get(0), 5.70, 700);
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
    public boolean actualizarItemMenu(ItemMenu itemMenu) {
        String descripcion = itemMenu.getDescripcion();
        double precio = itemMenu.getPrecio();
        int id = itemMenu.getId();
        
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

    public ItemMenu agregarItemMenu(ItemMenu itemMenu) {
        items.add(itemMenu);
        return itemMenu;
    }

    public ArrayList<ItemMenu> buscarVendedor(Vendedor vendedor) {
    return items.stream()
                .filter(e -> e.getVendedor().equals(vendedor))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
