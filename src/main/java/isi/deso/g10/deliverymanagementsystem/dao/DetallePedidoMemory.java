package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.exception.ItemNoEncontradoException;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.DetallePedidoDao;
import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;

/**
 *
 * @author gonzalo90fa
 */
public class DetallePedidoMemory implements DetallePedidoDao {

    /**
     * En "itemsPedidos" iriamos a guardar las instancias de cada "ItemPedido",
     * es decir, el detalle de pedido de todos los pedidos y vendedores juntos.
     */
    private static DetallePedidoMemory self;
    private ArrayList<DetallePedido> detallesPedidos;

    private DetallePedidoMemory() {
        this.detallesPedidos = new ArrayList<>();
        self = this;
    }

    public DetallePedidoMemory getInstance() {
        if (self == null) {
            self = new DetallePedidoMemory();
        }
        return self;
    }

    @Override
    public DetallePedido agregarDetallePedido(DetallePedido detallePedido) {
        this.detallesPedidos.add(detallePedido);
        return detallePedido;
    }

    @Override
    public List<DetallePedido> buscarPorIdVendedor(int idVendedor) throws ItemNoEncontradoException {
        List<DetallePedido> resultados = this.detallesPedidos.stream()
                .filter(detallePedido -> detallePedido.getItems().get(0).getVendedor().getId() == idVendedor)
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("Detalles de pedidos del vendedor con id " + idVendedor + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<DetallePedido> buscarPorNombreVendedor(String nombreVendedor) throws ItemNoEncontradoException {
        List<DetallePedido> resultados = detallesPedidos.stream()
                .filter(detallePedido -> detallePedido.getItems().get(0).getVendedor().getNombre().equalsIgnoreCase(nombreVendedor))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("Detalles de pedidos del vendedor con nombre " + nombreVendedor + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<DetallePedido> buscarPorNombreCliente(String nombreCliente) throws ItemNoEncontradoException {
        List<DetallePedido> resultados = detallesPedidos.stream()
                .filter(detallePedido -> detallePedido.getPedido().getCliente().getNombre().equalsIgnoreCase(nombreCliente))
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("Detalles de pedidos del cliente con nombre " + nombreCliente + " no encontrados.");
        }

        return resultados;
    }

    @Override
    public List<DetallePedido> buscarPorRangoMontoTotal(double montoMinimo, double montoMaximo) throws ItemNoEncontradoException {
        List<DetallePedido> resultados = detallesPedidos.stream()
                .filter(detallePedido -> {
                    double montototal = detallePedido.calcularMontoTotal();
                    return montototal >= montoMinimo && montototal <= montoMaximo;
                })
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron detalles de pedidos.");
        }

        return resultados;
    }

    @Override
    public List<DetallePedido> ordenarPorNombreVendedor(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<DetallePedido> resultados = detallesPedidos.stream()
                .sorted((item1, item2) -> {
                    int comparison = item1.getItems().getFirst().getVendedor().getNombre().compareToIgnoreCase(item2.getItems().getFirst().getVendedor().getNombre());
                    return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
                })
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron detalles de pedidos.");
        }

        return resultados;
    }

    @Override
    public List<DetallePedido> ordenarPorNombreCliente(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<DetallePedido> resultados = detallesPedidos.stream()
                .sorted((item1, item2) -> {
                    int comparison = item1.getPedido().getCliente().getNombre().compareToIgnoreCase(item2.getPedido().getCliente().getNombre());
                    return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
                })
                .collect(Collectors.toList());
        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron detalles de pedidos.");
        }

        return resultados;
    }

    @Override
    public List<DetallePedido> ordenarPorMontoTotal(TipoOrdenamiento ordenamiento) throws ItemNoEncontradoException {
        List<DetallePedido> resultados = detallesPedidos.stream()
                .sorted((item1, item2) -> {
                    int comparison = Double.compare(item1.calcularMontoTotal(), item2.calcularMontoTotal());
                    return ordenamiento == TipoOrdenamiento.ASC ? comparison : -comparison;
                })
                .collect(Collectors.toList());

        if (resultados.isEmpty()) {
            throw new ItemNoEncontradoException("No se encontraron detalles de pedidos.");
        }

        return resultados;
    }

    @Override
    public List<DetallePedido> obtenerDetallesPedidos() {
        return detallesPedidos;
    }

    @Override
    public DetallePedido actualizarDetallePedido(DetallePedido detallePedido) {
        for (int i = 0; i < detallesPedidos.size(); i++) {
            DetallePedido p = detallesPedidos.get(i);
            if (p.getId() == detallePedido.getId()) {
                // Reemplaza el objeto existente por el nuevo
                detallesPedidos.set(i, detallePedido);
                return detallePedido; // Retorna el nuevo detallePedido
            }
        }
        return null; // Retorna null si no se encontró el detallePedido
    }

    @Override
    public boolean eliminarDetallePedido(int id) {
        for (DetallePedido detallePedido : detallesPedidos) {
            if (detallePedido.getId() == id) {
                detallesPedidos.remove(detallePedido);
                return true; // Retorna true si el pedido fue encontrado y eliminado
            }
        }
        return false; // Retorna false si no se encontró el pedido
    }

    @Override
    public DetallePedido buscarDetallePedidoPorId(int id) {
        for (DetallePedido detallePedido : detallesPedidos) {
            if (detallePedido.getId() == id) {
                return detallePedido; // Retorna el pedido encontrado
            }
        }
        return null; // Retorna null si no se encuentra el pedido
    }

}
