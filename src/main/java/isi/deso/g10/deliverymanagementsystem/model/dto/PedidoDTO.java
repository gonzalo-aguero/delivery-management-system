/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

import isi.deso.g10.deliverymanagementsystem.model.Pedido.EstadoPedido;
import java.util.List;
import lombok.Data;

/**
 *
 * @author giuli
 */
@Data
public class PedidoDTO {
    
    private int id;
    private ClienteDTO cliente;
    private EstadoPedido estado;
    private List<DetallePedidoDTO> detallePedido;
    private PagoDTO datosPago;
    private String formaPagoTipo;
}
