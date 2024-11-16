/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.strategy;

/**
 *
 * @author giuli
 */
public class FormaMercadoPago implements FormaPagoI{

    private int id;
    private String alias;
    private double montoFinal;
    private final double RECARGO_MP = 0.04;

    public FormaMercadoPago(String alias) {
        this.alias = alias;
    }

    @Override
    public double totalizar(double montoInicial){
        montoFinal = montoInicial + montoInicial * RECARGO_MP;
        return montoFinal;
    }
    
    @Override
    public String toString(){
     return "Mercado Pago";
    }

    public static String getTipo(){
        return "Mercado Pago";
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
