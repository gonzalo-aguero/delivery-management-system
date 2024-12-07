/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giuli
 */
@Data
@NoArgsConstructor
public class DetallePedidoDTO {
    private int id;
    private PedidoDTO pedido;  // Relación con PedidoDTO
    private ItemMenuDTO item;  // Relación con ItemMenuDTO
    private int cantidad;
    
    public DetallePedidoDTO(DetallePedido detallePedido) {
    this.id = detallePedido.getId();
    this.cantidad = detallePedido.getCantidad();
    this.pedido = detallePedido.getPedido() != null ? new PedidoDTO(detallePedido.getPedido()) : null;
    this.item = detallePedido.getItem() != null ? new ItemMenuDTO(detallePedido.getItem()) : null;
    }
}
