/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import java.util.Date;

import isi.deso.g10.deliverymanagementsystem.model.Pedido.EstadoPedido;
import isi.deso.g10.deliverymanagementsystem.observer.PedidoObserver;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author giuli
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
@EqualsAndHashCode(callSuper = false)
public class Cliente extends Persona{

    @Column(unique = true)
    private String cuit;
    private String email;
    
  
    @OneToMany(mappedBy= "cliente",cascade = CascadeType.ALL,orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
    
   
    private Pago generarPago(Pedido pedido) {
       System.out.println("Generando pago para el pedido " + pedido.getId() + "...");
        if(pedido.getFormapago().getClass() == FormaMercadoPago.class) {
            Pago pago = new Pago(
                    pedido,
                    new Date(),
                    super.nombre,
                    this.cuit,
                    pedido.costoFinal(),
                    "Mercado Pago"
            );
            pago.printPagoInfo();
            return pago;
        }else{
            Pago pago = new Pago(
                    pedido,
                    new Date(),
                    super.nombre,
                    this.cuit,
                    pedido.costoFinal(),
                    "Transferencia"
            );
            pago.printPagoInfo();
            return pago;
        }
    }
}
