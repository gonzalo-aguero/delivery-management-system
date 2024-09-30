package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.exception.ItemNoEncontradoException;
import isi.deso.g10.deliverymanagementsystem.model.ItemPedido;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author gonzalo90fa
 */
public class ItemsPedidoMemory implements ItemsPedidoDao {

    /**
     * En "itemsPedidos" iriamos a guardar las instancias
 de cada "ItemPedido", es decir, el detalle de pedido de todos los
 pedidos y vendedores juntos.
     */
    private ArrayList<ItemPedido> itemsPedidos;

    public ItemsPedidoMemory() {
        this.itemsPedidos = new ArrayList<>();
    }

    /**
     * Agrega el itemsPedido (Detalle del pedido) al array de itemsPedidos.  
     * @param itemsPedido 
     */
    public void agregarPedido(ItemPedido itemsPedido) {
        this.itemsPedidos.add(itemsPedido);
    }

    @Override
    public List<ItemPedido> buscarPorIdVendedor(int idVendedor) throws ItemNoEncontradoException {
        List<ItemPedido> resultados = itemsPedidos.stream()
                .filter(itemsPedido -> itemsPedido.getVendedor().getId() == idVendedor)
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de vendedor con id " + idVendedor + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<ItemPedido> buscarPorNombreVendedor(String nombreVendedor) throws ItemNoEncontradoException {
        List<ItemPedido> resultados = itemsPedidos.stream()
                .filter(itemsPedido -> itemsPedido.getVendedor().getNombre().equalsIgnoreCase(nombreVendedor))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de vendedor con nombre " + nombreVendedor + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<ItemPedido> buscarPorNombreCliente(String nombreCliente) throws ItemNoEncontradoException {
        List<ItemPedido> resultados = itemsPedidos.stream()
                .filter(itemsPedido -> itemsPedido.getPedido().getCliente().getNombre().equalsIgnoreCase(nombreCliente))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de cliente con nombre " + nombreCliente + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<ItemPedido> buscarPorRangoMontoTotal(double montoMinimo, double montoMaximo) throws ItemNoEncontradoException {
       List<ItemPedido> resultados = itemsPedidos.stream()
               .filter(itemPedido -> {
                       double montototal = itemPedido.calcularMontoTotal();
                       return montototal >= montoMinimo && montototal <= montoMaximo;
               })
               .collect(Collectors.toList());
       
       if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron ItemsPedidos.");
        }
       
       return resultados;
    }

    @Override
    public List<ItemPedido> ordenarPorNombreVendedor(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<ItemPedido> resultados = itemsPedidos.stream()
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
    public List<ItemPedido> ordenarPorNombreCliente(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
       List<ItemPedido> resultados = itemsPedidos.stream()
               .sorted((item1,item2) -> {
                   int comparison =item1.getPedido().getCliente().getNombre().compareToIgnoreCase(item2.getPedido().getCliente().getNombre());
                   return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
               })
               .collect(Collectors.toList());
       if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron ItemsPedidos.");
        }
       
       return resultados;
    }

    @Override
    public List<ItemPedido> ordenarPorMontoTotal(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<ItemPedido> resultados = itemsPedidos.stream()
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
