/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.Pedido.EstadoPedido;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giuli
 */
@Data
@NoArgsConstructor
public class PedidoDTO {
    private Integer id;
    private Integer idCliente;
    private EstadoPedido estado;
    private List<DetallePedidoDTO> detallePedido;
    private PagoDTO datosPago;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.idCliente = pedido.getCliente().getId();
        this.estado = pedido.getEstado();

        if(pedido.getDatosPago() != null){
            this.datosPago = new PagoDTO(pedido.getDatosPago());
        }
        
        this.detallePedido = pedido.getDetallePedido().stream()
            .map(DetallePedidoDTO::new)
            .collect(Collectors.toList());
    }
}
