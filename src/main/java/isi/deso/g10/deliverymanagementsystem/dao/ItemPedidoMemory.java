package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.exception.ItemNoEncontradoException;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ItemPedidoDao;

/**
 *
 * @author gonzalo90fa
 */
public class ItemPedidoMemory implements ItemPedidoDao {

    /**
     * En "itemsPedidos" iriamos a guardar las instancias
 de cada "ItemPedido", es decir, el detalle de pedido de todos los
 pedidos y vendedores juntos.
     */
    
    private static ItemPedidoMemory self;
    private ArrayList<Pedido> pedidos;
    

    private ItemPedidoMemory() {
        this.pedidos = new ArrayList<>();
        self = this;
    }
    
    public ItemPedidoMemory getInstance(){
        if(self == null){
            self = new ItemPedidoMemory();
        }
        return self;
    }

    /**
     * Agrega el itemsPedido (Detalle del pedido) al array de itemsPedidos.
     * @param itemsPedido
     */
    public void agregarPedido(Pedido itemsPedido) {
        this.pedidos.add(itemsPedido);
    }

    @Override
    public List<Pedido> buscarPorIdVendedor(int idVendedor) throws ItemNoEncontradoException {
        List<Pedido> resultados = this.pedidos.stream()
                .filter(itemsPedido -> itemsPedido.getDetallePedido().getItems().get(0).getVendedor().getId() == idVendedor)
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de vendedor con id " + idVendedor + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<Pedido> buscarPorNombreVendedor(String nombreVendedor) throws ItemNoEncontradoException {
        List<Pedido> resultados = pedidos.stream()
                .filter(itemsPedido -> itemsPedido.getDetallePedido().getItems().get(0).getVendedor().getNombre().equalsIgnoreCase(nombreVendedor))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de vendedor con nombre " + nombreVendedor + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<Pedido> buscarPorNombreCliente(String nombreCliente) throws ItemNoEncontradoException {
        List<Pedido> resultados = pedidos.stream()
                .filter(itemsPedido -> itemsPedido.getCliente().getNombre().equalsIgnoreCase(nombreCliente))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("ItemsPedidos de cliente con nombre " + nombreCliente + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<Pedido> buscarPorRangoMontoTotal(double montoMinimo, double montoMaximo) throws ItemNoEncontradoException {
       List<Pedido> resultados = pedidos.stream()
               .filter(pedido -> {
                       double montototal = pedido.getDetallePedido().calcularMontoTotal();
                       return montototal >= montoMinimo && montototal <= montoMaximo;
               })
               .collect(Collectors.toList());
       
       if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron ItemsPedidos.");
        }
       
       return resultados;
    }

    @Override
    public List<Pedido> ordenarPorNombreVendedor(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<Pedido> resultados = pedidos.stream()
                .sorted((item1, item2) -> {
                    int comparison = item1.getItemsPedido().getFirst().getVendedor().getNombre().compareToIgnoreCase(item2.getItemsPedido().getFirst().getVendedor().getNombre());
                    return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
                })
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron ItemsPedidos.");
        }

        return resultados;
    }

    @Override
    public List<Pedido> ordenarPorNombreCliente(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
       List<Pedido> resultados = pedidos.stream()
               .sorted((item1,item2) -> {
                   int comparison =item1.getCliente().getNombre().compareToIgnoreCase(item2.getCliente().getNombre());
                   return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
               })
               .collect(Collectors.toList());
       if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron ItemsPedidos.");
        }
       
       return resultados;
    }

    @Override
    public List<Pedido> ordenarPorMontoTotal(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<Pedido> resultados = pedidos.stream()
                .sorted((item1, item2) -> {
                    int comparison = Double.compare(item1.getDetallePedido().calcularMontoTotal(), item2.getDetallePedido().calcularMontoTotal());
                    return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
                })
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron ItemsPedidos.");
        }

        return resultados;
    }

    
}
