package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido getPedidoById(int id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido createPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido updatePedido(int id, Pedido pedidoModificado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setEstado(pedidoModificado.getEstado());
            pedido.setDetallePedido(pedidoModificado.getDetallePedido());
            pedido.setDatosPago(pedidoModificado.getDatosPago());
            pedido.setFormapago(pedidoModificado.getFormapago());
            return pedidoRepository.save(pedido);
        }).orElse(null);
    }

    public boolean deletePedido(int id) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedidoRepository.delete(pedido);
            return true;
        }).orElse(false);
    }
}