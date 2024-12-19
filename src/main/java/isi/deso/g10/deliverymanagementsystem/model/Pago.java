package isi.deso.g10.deliverymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {
    
    @Id
    private int id;
    
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Pedido pedido;

    private double monto;
    private String formaPago;
    private Date fecha;
    private String nombreCliente;
    private String cuitCliente;

    public Pago(Pedido pedido, Date fecha, String nombreCliente, String cuitCliente, double monto, String formaPago) {
        this.pedido = pedido;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.cuitCliente = cuitCliente;
        this.monto = monto;
        this.formaPago = formaPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCuitCliente() {
        return cuitCliente;
    }

    public void setCuitCliente(String cuitCliente) {
        this.cuitCliente = cuitCliente;
    }

    public void printPagoInfo() {
        System.out.println("--------------------------- Pago Generado ---------------------------");
        System.out.println("ID Pedido: " + pedido);
        System.out.println("Fecha: " + fecha);
        System.out.println("Nombre Cliente: " + nombreCliente);
        System.out.println("CUIT Cliente: " + cuitCliente);
        System.out.println("Monto: " + monto);
        System.out.println("Forma de Pago: " + formaPago);
        System.out.println("---------------------------------------------------------------------");
    }
}