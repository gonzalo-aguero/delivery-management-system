package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.ItemsPedido;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author gonzalo90fa
 */
public class ItemsPedidoMemory implements ItemsPedidoDao {

    /**
     * Por lo que entiendo, en "itemsPedidos" iriamos a guardar las instancias
     * de cada "ItemsPedido", es decir, el detalle de pedido de todos los
     * pedidos y vendedores juntos.
     */
    private ArrayList<ItemsPedido> itemsPedidos;

    public ItemsPedidoMemory() {
        this.itemsPedidos = new ArrayList<>();
    }

    @Override
    public List<ItemsPedido> buscarPorIdVendedor(int idVendedor) throws ItemNoEncontradoException {
        List<ItemsPedido> resultados = itemsPedidos.stream()
                .filter(itemsPedido -> itemsPedido.getVendedor().getId() == idVendedor)
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de vendedor con id " + idVendedor + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<ItemsPedido> buscarPorNombreVendedor(String nombreVendedor) throws ItemNoEncontradoException {
        List<ItemsPedido> resultados = itemsPedidos.stream()
                .filter(itemsPedido -> itemsPedido.getVendedor().getNombre().equalsIgnoreCase(nombreVendedor))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de vendedor con nombre " + nombreVendedor + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<ItemsPedido> buscarPorNombreCliente(String nombreCliente) throws ItemNoEncontradoException {
        List<ItemsPedido> resultados = itemsPedidos.stream()
                .filter(itemsPedido -> itemsPedido.getPedido().getCliente().getNombre().equalsIgnoreCase(nombreCliente))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de cliente con nombre " + nombreCliente + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<ItemsPedido> buscarPorRangoMontoTotal(double montoMinimo, double montoMaximo) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemsPedido> ordenarPorNombreVendedor(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<ItemsPedido> resultados = itemsPedidos.stream()
                .sorted((item1, item2) -> {
                    int comparison = item1.getVendedor().getNombre().compareToIgnoreCase(item2.getVendedor().getNombre());
                    return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
                })
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron ItemsPedidos.");
        }

        return resultados;
    }

    @Override
    public List<ItemsPedido> ordenarPorNombreCliente(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemsPedido> ordenarPorMontoTotal(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<ItemsPedido> resultados = itemsPedidos.stream()
                .sorted((item1, item2) -> {
                    int comparison = Double.compare(item1.calcularMontoTotal(), item2.calcularMontoTotal());
                    return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
                })
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron ItemsPedidos.");
        }

        return resultados;
    }

}
