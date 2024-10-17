/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.strategy;



/**
 *
 * @author giuli
 */
public class FormaTransferencia implements FormaPagoI {
    private  String cuitCliente;
    private  String cbuCliente;
    private double montoFinal;
    private final double RECARGO_TRANSFERENCIA = 0.02;

    public FormaTransferencia(String cuitCliente, String cbuCliente) {
        this.cuitCliente = cuitCliente;
        this.cbuCliente = cbuCliente;
    }

    @Override
    public double totalizar(double montoInicial){
        montoFinal = montoInicial + montoInicial * RECARGO_TRANSFERENCIA;
        return montoFinal;
    }
    
}
