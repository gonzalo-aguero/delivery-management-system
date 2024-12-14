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
    private Integer idItemMenu;
    private Integer cantidad;
    
    public DetallePedidoDTO(DetallePedido detallePedido) {
        this.cantidad = detallePedido.getCantidad();
        this.idItemMenu = detallePedido.getItem() != null ? detallePedido.getItem().getId() : null;
    }
}
