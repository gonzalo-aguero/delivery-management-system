/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pago;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import isi.deso.g10.deliverymanagementsystem.model.dto.ClienteDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.DetallePedidoDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.ItemMenuDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.PagoDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.PedidoDTO;
import isi.deso.g10.deliverymanagementsystem.repository.ClienteRepository;
import isi.deso.g10.deliverymanagementsystem.repository.DetallePedidoRepository;
import isi.deso.g10.deliverymanagementsystem.repository.ItemMenuRepository;
import isi.deso.g10.deliverymanagementsystem.repository.PedidoRepository;
import isi.deso.g10.deliverymanagementsystem.repository.VendedorRepository;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuli
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemMenuRepository itemMenuRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public List<PedidoDTO> getAll() throws NotFoundException {
        List<Pedido> pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) {
            throw new NotFoundException();
        }

        return pedidos.stream()
                .map(pedido -> new PedidoDTO(pedido))
                .collect(Collectors.toList());
    }

    public PedidoDTO getById(int id) throws NotFoundException {
        return pedidoRepository.findById(id)
                .map(pedido -> new PedidoDTO(
                        pedido))
                .orElseThrow(() -> new NotFoundException());
    }

    public PedidoDTO savePedido(PedidoDTO pedidoDTO) throws NotFoundException {
        Pedido pedido = new Pedido();
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setCliente(clienteRepository.findById(pedidoDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró el cliente con ID: " + pedidoDTO.getCliente().getId())));

        if (pedidoDTO.getDatosPago() != null) {
            Pago pago = new Pago();
            pago.setMonto(pedidoDTO.getDatosPago().getMonto());
            pago.setFormaPago(pedidoDTO.getDatosPago().getFormaPago());
            pago.setFecha(pedidoDTO.getDatosPago().getFecha());
            pago.setNombreCliente(pedidoDTO.getDatosPago().getNombreCliente());
            pago.setCuitCliente(pedidoDTO.getDatosPago().getCuitCliente());
            pedido.setDatosPago(pago);
            pago.setPedido(pedido);
        }

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        pedidoDTO.getDetallePedido().forEach(detallePedidoDTO -> {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setCantidad(detallePedidoDTO.getCantidad());
            detallePedido.setItem(itemMenuRepository.findById(detallePedidoDTO.getItem().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "No se encontró el ItemMenu con ID: " + detallePedidoDTO.getItem().getId())));
            detallePedido.setPedido(pedidoGuardado);
            detallePedidoRepository.save(detallePedido);
        });

        return new PedidoDTO(pedidoGuardado);
    }

    public void deleteById(Integer id) throws NotFoundException {
        if (!pedidoRepository.existsById(id)) {
            throw new NotFoundException();
        }
        pedidoRepository.deleteById(id);
    }

    public PedidoDTO updatePedido(PedidoDTO pedidoDTO) throws NotFoundException {
        Pedido pedido = pedidoRepository.findById(pedidoDTO.getId())
                .orElseThrow(() -> new NotFoundException());

        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setCliente(clienteRepository.findById(pedidoDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró el cliente con ID: " + pedidoDTO.getCliente().getId())));

        if (pedidoDTO.getDatosPago() != null) {
            Pago pago = pedido.getDatosPago() != null ? pedido.getDatosPago() : new Pago();
            pago.setMonto(pedidoDTO.getDatosPago().getMonto());
            pago.setFormaPago(pedidoDTO.getDatosPago().getFormaPago());
            pago.setFecha(pedidoDTO.getDatosPago().getFecha());
            pago.setNombreCliente(pedidoDTO.getDatosPago().getNombreCliente());
            pago.setCuitCliente(pedidoDTO.getDatosPago().getCuitCliente());
            pedido.setDatosPago(pago);
            pago.setPedido(pedido);
        }

        Pedido pedidoActualizado = pedidoRepository.save(pedido);

        pedidoDTO.getDetallePedido().forEach(detallePedidoDTO -> {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setCantidad(detallePedidoDTO.getCantidad());
            detallePedido.setItem(itemMenuRepository.findById(detallePedidoDTO.getItem().getId())
                    .orElseThrow(() -> new RuntimeException(
                            "No se encontró el ItemMenu con ID: " + detallePedidoDTO.getItem().getId())));
            detallePedido.setPedido(pedidoActualizado);
            detallePedidoRepository.save(detallePedido);
        });

        return new PedidoDTO(pedidoActualizado);
    }
}