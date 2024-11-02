package isi.deso.g10.deliverymanagementsystem.model;

import java.util.Date;

public class Pago {
    private double monto;
    private String formaPago;
    private Date fecha;
    private int idPedido;
    private String nombreCliente;
    private String cuitCliente;

    public Pago(int idPedido, Date fecha, String nombreCliente, String cuitCliente, double monto, String formaPago) {
        this.idPedido = idPedido;
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

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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
        System.out.println("ID Pedido: " + idPedido);
        System.out.println("Fecha: " + fecha);
        System.out.println("Nombre Cliente: " + nombreCliente);
        System.out.println("CUIT Cliente: " + cuitCliente);
        System.out.println("Monto: " + monto);
        System.out.println("Forma de Pago: " + formaPago);
        System.out.println("---------------------------------------------------------------------");
    }
}