/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import isi.deso.g10.deliverymanagementsystem.observer.Observable;
import isi.deso.g10.deliverymanagementsystem.observer.PedidoObserver;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author gonzalo90fa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido implements Observable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPedido estado;
    
    @OneToMany(mappedBy = "pedido")
    private List<DetallePedido> detallePedido;
    
    @OneToOne(mappedBy = "pedido")
    private Pago datosPago;

    @Column(name = "forma_pago_tipo")
    private String formaPagoTipo;

    @Transient
    private FormaPagoI formapago;

    @JsonIgnore
    @Transient
    private List<PedidoObserver> observers;

    /*
     * HAY QUE VER ESTOS POR EL CAMBIO DE ESTRUCTURA
     * public Pedido(int idPedido ,ArrayList<ItemMenu> itemsPedido, Cliente cliente)
     * {
     * this.id = idPedido;
     * this.detallePedido = new DetallePedido(itemsPedido);
     * this.cliente = cliente;
     * this.observers = new ArrayList<>();
     * }
     * 
     * public Pedido(int idPedido ,ArrayList<ItemMenu> itemsPedido, Cliente cliente,
     * FormaPagoI formapago) {
     * this.id = idPedido;
     * this.detallePedido = new DetallePedido(itemsPedido);
     * this.cliente = cliente;
     * this.formapago = formapago;
     * this.observers = new ArrayList<>();
     * }
     */

    public enum EstadoPedido {
        RECIBIDO, EN_ENVIO, EN_PROCESO, PENDIENTE_DE_PAGO, ENTREGADO, FINALIZADO
    }

    public double costoFinal() {
        if (formapago == null) {
            System.out.println("No se ha ingresado una forma de pago aun :(");
            return 0d;
        } else {
            double total = 0;
            for (DetallePedido dp : detallePedido) {
                total += dp.calcularMontoTotal();
            }

            return getFormapago().totalizar(total);
        }
    }

    public void setEstado(EstadoPedido estado) {
        // Esto evita que se notifique a los observadores si el estado no cambia
        if (this.estado != estado) {
            this.estado = estado;
            notifyObservers();
        }
    }

    @Override
    public void addObserver(PedidoObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    @Override
    public boolean removeObserver(PedidoObserver o) {
        return observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (PedidoObserver observer : observers) {
            observer.update(this);
        }
    }

}
