/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.memory;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ItemMenuDao;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;
import isi.deso.g10.deliverymanagementsystem.model.*;
import isi.deso.g10.deliverymanagementsystem.exception.*;
import isi.deso.g10.deliverymanagementsystem.model.Categoria.TipoItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author giuli
 */
public class ItemMenuMemory implements ItemMenuDao {

    private ArrayList<ItemMenu> items;
    private static ItemMenuMemory self;
    private ArrayList<Categoria> categorias;
    private final VendedorDao vendedorDao;

    private ItemMenuMemory() {
        this.vendedorDao = VendedorMemory.getInstance();
        this.items = new ArrayList<>();
        self = this;
        this.categorias = new ArrayList<Categoria>();
        this.categorias.add(new Categoria(1, "Carnes", TipoItem.COMIDA));
        this.categorias.add(new Categoria(2, "Pastas", TipoItem.COMIDA));
        this.categorias.add(new Categoria(3, "Cervezas", TipoItem.BEBIDA));
        this.categorias.add(new Categoria(4, "Vinos", TipoItem.BEBIDA));
        this.generarItems(vendedorDao.obtenerTodos());
    }
    
    @Override
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public static ItemMenuMemory getInstance() {
        if (self == null) {
            self = new ItemMenuMemory();
        }
        return self;
    }

    private void generarItems(List<Vendedor> vendedores) {
        Plato milanesaPollo = new Plato(200, 1, "Milanesa de Pollo", "Milanesa de Pollo", 4500, this.categorias.getFirst(), 400, false, false, false);
        milanesaPollo.setVendedor(vendedores.get(0));
        items.add(milanesaPollo);
        Plato pizzaMuzza = new Plato(350, 2, "Pizza de Muzza", "Pizza con queso mozzarella", 3000, this.categorias.getFirst(), 800, false, false, false);
        pizzaMuzza.setVendedor(vendedores.get(1));
        items.add(pizzaMuzza);
        Plato papasFritas = new Plato(250, 3, "Papas Fritas", "Papas fritas crocantes", 1500, this.categorias.getFirst(), 600, false, true, true);
        papasFritas.setVendedor(vendedores.get(2));
        items.add(papasFritas);
        Bebida cocaCola = new Bebida(0, 500, 4, "Coca Cerveza", "Cerveza Coca Cola 500ml", 500, this.categorias.get(2), 200, true, true, true);
        cocaCola.setVendedor(vendedores.get(3));
        items.add(cocaCola);
        //double graduacionAlcoholica, double volumenEnMl, int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano
        //double peso, int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano
        Bebida agua = new Bebida(0, 500, 5, "Cerveza mineral", "Cerveza sin gas 500ml", 300.00, this.categorias.get(2), 100, true, true, true);
        agua.setVendedor(vendedores.get(2));
        items.add(agua);
        Bebida cerveza = new Bebida(5.70, 700, 6, "Cerveza DUFF", "Cerveza DUFF 700ml", 600.00, this.categorias.get(2), 500, true, true, true);
        cerveza.setVendedor(vendedores.get(1));
        items.add(cerveza);
        
        
    }

    @Override
    public List<ItemMenu> obtenerTodos() {
        return items;
    }

    @Override
    public ItemMenu obtenerPorId(int id) {
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
    public List<ItemMenu> buscarItemsPorNombre(String cadena) {
        List<ItemMenu> resultados = new ArrayList<>();
        
        if (cadena == null || cadena.isEmpty()) {
            return resultados;
        }

        for (ItemMenu item : items) {
            if (item.getNombre().toLowerCase().contains(cadena.toLowerCase())) {
                resultados.add(item);
            }
        }
        
        return resultados;
    }

    @Override
    public boolean eliminar(int id) {
        Iterator<ItemMenu> iterator = items.iterator();
        while (iterator.hasNext()) {
            ItemMenu item = iterator.next();
            if (item.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemMenu actualizar(ItemMenu itemMenu) {
        String descripcion = itemMenu.getDescripcion();
        double precio = itemMenu.getPrecio();
        int id = itemMenu.getId();
        
        for (ItemMenu item : items) {
            if (item.getId() == id) {
                item.setDescripcion(descripcion);
                item.setNombre(itemMenu.getNombre());
                item.setPrecio(precio);
                item.setAptoCeliaco(itemMenu.aptoCeliaco());
                item.setAptoVegetariano(itemMenu.aptoVegetariano());
                item.setAptoVegano(itemMenu.aptoVegano());
                item.setCategoria(item.getCategoria());
                item.setCalorias(item.getCalorias());
                item.setVendedor(item.getVendedor());
                
                return item;
            }
        }
        
        return null;
    }

    @Override
    public ItemMenu crear(ItemMenu itemMenu) {
        itemMenu.setId(this.items.getLast().getId() + 1);
        items.add(itemMenu);
        return itemMenu;
    }

    @Override
    public ArrayList<ItemMenu> buscarVendedor(Vendedor vendedor) {
    return items.stream()
                .filter(e -> e.getVendedor().equals(vendedor))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
