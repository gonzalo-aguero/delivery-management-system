/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import isi.deso.g10.deliverymanagementsystem.observer.Observable;
import isi.deso.g10.deliverymanagementsystem.observer.PedidoObserver;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 *
 * @author gonzalo90fa
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pedido")
public class Pedido{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne()
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @Enumerated(EnumType.STRING)
    @Column(name="estado")
    private EstadoPedido estado;
    
    @OneToMany(mappedBy="pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detallePedido;
    
    @OneToOne(mappedBy="pedido", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Pago datosPago;
    
    @Column(name = "forma_pago_tipo")
    private String formaPagoTipo;
    
    @Transient
    private FormaPagoI formapago;
    
    
    @Transient
    private List<PedidoObserver> observers;

    
    public enum EstadoPedido {
       RECIBIDO, EN_ENVIO, EN_PROCESO, PENDIENTE_DE_PAGO, ENTREGADO, FINALIZADO
    }


     public FormaPagoI getFormapago() {
        if (formapago == null && formaPagoTipo != null) {
            // Recupera la implementación correspondiente
            try {
                // Usamos reflexión o una fábrica para instanciar la implementación
                formapago = (FormaPagoI) Class.forName(formaPagoTipo).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return formapago;
    }

    public void setFormapago(FormaPagoI formapago) {
        this.formapago = formapago;
        // Guardar el nombre de la clase de la implementación
        if (formapago != null) {
            this.formaPagoTipo = formapago.getClass().getName();
        }
    }
    public double costoFinal() {
        if(formapago == null) {
            System.out.println("No se ha ingresado una forma de pago aun :(");
            return 0d;
        }else{
            double total=0;
            for(DetallePedido dp : detallePedido ){
               total+= dp.calcularMontoTotal();
            }
            
            return getFormapago().totalizar(total);
        }
    }
/*
    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        //Esto evita que se notifique a los observadores si el estado no cambia
        if (this.estado != estado) {
            this.estado = estado;
            notifyObservers();
        }
    }

    public ArrayList<ItemMenu> getItemsPedido() {
        ArrayList<ItemMenu> itemsMenu = new ArrayList();
        detallePedido.forEach(e -> itemsMenu.add(e.getItem()));
        
        return itemsMenu;
    }

    public List<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }

*/

    /**
     * Agrega un detalle de pedido a la lista de detalles del pedido,
     * asegurandose de crear la id compuesta correctamente.
     *
     * @param detalle El detalle de pedido que se va a agregar.
     */
    public void addDetallePedido(DetallePedido detalle) {
        DetallePedidoId detalleId = new DetallePedidoId();
        detalleId.setPedidoId(this.id);
        detalleId.setDetallePedidoId(detallePedido.size() + 1);
        detalle.setId(detalleId);
        detalle.setPedido(this);
        detallePedido.add(detalle);
    }
}
