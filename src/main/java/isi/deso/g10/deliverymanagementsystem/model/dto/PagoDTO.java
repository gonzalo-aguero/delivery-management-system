/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author giuli
 */
@Data
class PagoDTO {

    private int id;
    private PedidoDTO pedido;
    private double monto;
    private String formaPago;
    private Date fecha;
    private String nombreCliente;
    private String cuitCliente;
    
}
