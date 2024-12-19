/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.EmbeddedId;

/**
 * Equivalente a la clase PedidoDetalle del diagrama de clases del enunciado.
 *
 * @author gonzalo90fa
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detallepedido")
public class DetallePedido {

    @EmbeddedId
    private DetallePedidoId id;
    
    @ManyToOne
    @MapsId("pedidoId")// mapea el atributo pedidoId de la clase DetallePedidoId
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "itemmenu_id")
    private ItemMenu item;
    
    private int cantidad;
    
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    

    public DetallePedido(ItemMenu item) {
        this.item = item;
    }

    public double calcularMontoTotal() {
        return item.getPrecio() * cantidad;
    }
    
    /**
     * @return Retorna el vendedor de los items basándose en el vendedor asociado al primer item de la lista
     */
    public Vendedor getVendedor(){
        return item.getVendedor();//Cada itemMenu tiene referencia a su vendedor
    }

    public ItemMenu getItem() {
        return item;
    }

}
