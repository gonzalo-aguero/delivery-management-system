package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.List;

public class ItemsPedidoMemory implements ItemsPedidoDao {

    @Override
    public ItemMenu buscarId(int id, List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        return Vendedores.stream()
                .flatMap(vendedor -> vendedor.getMenu().stream()) // Combina todos los menús de todos los vendedores
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ItemNoEncontradoException("Item con id " + id + " no encontrado."));
    }

    @Override
    public ItemMenu buscarNombre(String nombre, List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        return Vendedores.stream()
                .flatMap(vendedor -> vendedor.getMenu().stream()) // Combina todos los menús de todos los vendedores
                .filter(item -> item.getNombre().equals(nombre))
                .findFirst()
                .orElseThrow(() -> new ItemNoEncontradoException("Item con nombre " + nombre + " no encontrado."));
    }

    @Override
    public List<ItemMenu> buscarPorRangoPrecio(double minimo, double maximo, List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemMenu> buscarPorCategoria(Categoria categoria, List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Plato> buscarComidas(List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Bebida> buscarBebidas(List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemMenu> buscarPorRestaurante(Vendedor vendedor) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
