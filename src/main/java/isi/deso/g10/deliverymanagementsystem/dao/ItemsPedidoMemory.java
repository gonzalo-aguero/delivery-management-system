package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.ItemsPedido;
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
     * de cada "ItemsPedido", es decir, el detalle de pedido de todos los
     * pedidos y vendedores juntos.
     */
    private ArrayList<ItemsPedido> itemsPedidos;

    public ItemsPedidoMemory() {
        this.itemsPedidos = new ArrayList<>();
    }

    /**
     * Agrega el itemsPedido (Detalle del pedido) al array de itemsPedidos.  
     * @param itemsPedido 
     */
    public void agregarPedido(ItemsPedido itemsPedido) {
        this.itemsPedidos.add(itemsPedido);
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
       List<ItemsPedido> resultados = itemsPedidos.stream()
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
       List<ItemsPedido> resultados = itemsPedidos.stream()
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
