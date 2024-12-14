/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

import isi.deso.g10.deliverymanagementsystem.model.Pago;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giuli
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private int id;
    // private PedidoDTO pedido;
    private Double monto;
    private String formaPago;
    private Date fecha;
    private String nombreCliente;
    private String cuitCliente;

    // Mapeo de entidad Pago a PagoDTO
    public PagoDTO(Pago pago) {
        this.id = pago.getId();
        this.monto = pago.getMonto();
        this.formaPago = pago.getFormaPago();
        this.fecha = pago.getFecha();
        this.nombreCliente = pago.getNombreCliente();
        this.cuitCliente = pago.getCuitCliente();
    }
}
