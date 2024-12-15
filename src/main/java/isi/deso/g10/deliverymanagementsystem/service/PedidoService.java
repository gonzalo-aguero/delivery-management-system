/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pago;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.dto.PedidoDTO;
import isi.deso.g10.deliverymanagementsystem.repository.ClienteRepository;
import isi.deso.g10.deliverymanagementsystem.repository.DetallePedidoRepository;
import isi.deso.g10.deliverymanagementsystem.repository.ItemMenuRepository;
import isi.deso.g10.deliverymanagementsystem.repository.PedidoRepository;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaTransferencia;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    public PedidoDTO savePedido(PedidoDTO pedidoDTO) throws NotFoundException {
        Pedido pedido = new Pedido();
        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setCliente(clienteRepository.findById(pedidoDTO.getIdCliente())
                .orElseThrow(
                        () -> new RuntimeException("No se encontró el cliente con ID: " + pedidoDTO.getIdCliente())));

        if (pedidoDTO.getDatosPago() != null) {
            Pago pago = new Pago();
            pago.setMonto(pedidoDTO.getDatosPago().getMonto());
            pago.setFormaPago(pedidoDTO.getDatosPago().getFormaPago());
            pago.setFecha(pedidoDTO.getDatosPago().getFecha());
            pago.setNombreCliente(pedidoDTO.getDatosPago().getNombreCliente());
            pago.setCuitCliente(pedidoDTO.getDatosPago().getCuitCliente());
            pago.setPedido(pedido);
            pedido.setDatosPago(pago);
        }

        pedido.setDetallePedido(new ArrayList<>());
        pedidoDTO.getDetallePedido().forEach(detallePedidoDTO -> {
            DetallePedido detallePedido = new DetallePedido();
            ItemMenu item = itemMenuRepository.findById(detallePedidoDTO.getIdItemMenu())
                    .orElseThrow(() -> new RuntimeException(
                            "No se encontró el ItemMenu con ID: " + detallePedidoDTO.getIdItemMenu()));
            detallePedido.setItem(item);
            detallePedido.setCantidad(detallePedidoDTO.getCantidad());
            pedido.addDetallePedido(detallePedido);
        });

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return new PedidoDTO(pedidoGuardado);
    }

    @Transactional
    public void deleteById(Integer id) throws NotFoundException {
        if (!pedidoRepository.existsById(id)) {
            throw new NotFoundException();
        }
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public PedidoDTO updatePedido(PedidoDTO pedidoDTO) throws NotFoundException {
        Pedido pedido = pedidoRepository.findById(pedidoDTO.getId())
                .orElseThrow(() -> new NotFoundException());

        pedido.setEstado(pedidoDTO.getEstado());
        pedido.setCliente(clienteRepository.findById(pedidoDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró el cliente con ID: " + pedidoDTO.getIdCliente())));

        // Update detallePedido
        pedido.getDetallePedido().clear();
        pedidoDTO.getDetallePedido().forEach(detallePedidoDTO -> {
            DetallePedido detallePedido = new DetallePedido();
            detallePedido.setItem(itemMenuRepository.findById(detallePedidoDTO.getIdItemMenu())
                    .orElseThrow(() -> new RuntimeException(
                            "No se encontró el ItemMenu con ID: " + detallePedidoDTO.getIdItemMenu())));
            detallePedido.setCantidad(detallePedidoDTO.getCantidad());
            pedido.addDetallePedido(detallePedido);
        });

        if (pedidoDTO.getDatosPago() != null) {
            Pago pago = pedido.getDatosPago() != null ? pedido.getDatosPago() : new Pago();

            // Calcular nuevo monto
            Double nuevoMonto = (double) 0;
            for (DetallePedido detalle : pedido.getDetallePedido()) {
                nuevoMonto += detalle.getItem().getPrecio() *  detalle.getCantidad();
            }
            FormaPagoI formaPago;
            if (pedidoDTO.getDatosPago().getFormaPago().equals("Transferencia")) {
                formaPago = new FormaTransferencia();
            } else {
                formaPago = new FormaMercadoPago();
            }
            nuevoMonto = formaPago.totalizar(nuevoMonto);
            pago.setMonto(nuevoMonto);

            pago.setFormaPago(pedidoDTO.getDatosPago().getFormaPago());
            pago.setFecha(pedidoDTO.getDatosPago().getFecha());
            pago.setNombreCliente(pedidoDTO.getDatosPago().getNombreCliente());
            pago.setCuitCliente(pedidoDTO.getDatosPago().getCuitCliente());
            pedido.setDatosPago(pago);
            pago.setPedido(pedido);
        }



        Pedido pedidoActualizado = pedidoRepository.save(pedido);

        return new PedidoDTO(pedidoActualizado);
    }
}