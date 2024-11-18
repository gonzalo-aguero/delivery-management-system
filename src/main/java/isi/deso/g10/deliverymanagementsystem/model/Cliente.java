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
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author giuli
 */
@Entity
@Table(name = "cliente")
public class Cliente extends Persona implements PedidoObserver{

    private String cuit;
    private String nombre;
    private String email;
    private String direccion;
  
    @OneToMany(mappedBy= "cliente",cascade = CascadeType.ALL,orphanRemoval = false, fetch = FetchType.LAZY)
    private List<Pedido> pedidos;
    
    public Cliente() {
    }

    public Cliente(String cuit, String nombre, String email, String direccion, Coordenada coordenadas) {
        super(coordenadas);
        this.cuit = cuit;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        
    }
   

    

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    @Override
    public void update(Pedido pedido) {
        System.out.println("El pedido " + pedido.getId() + " ha cambiado de estado a " + pedido.getEstado());    
        if(pedido.getEstado().equals(EstadoPedido.EN_ENVIO)) {
            pedido.setDatosPago(generarPago(pedido));
        }
    }
    private Pago generarPago(Pedido pedido) {
       System.out.println("Generando pago para el pedido " + pedido.getId() + "...");
        if(pedido.getFormapago().getClass() == FormaMercadoPago.class) {
            Pago pago = new Pago(
                    pedido,
                    new Date(),
                    this.nombre,
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
                    this.nombre,
                    this.cuit,
                    pedido.costoFinal(),
                    "Transferencia"
            );
            pago.printPagoInfo();
            return pago;
        }
    }
}
