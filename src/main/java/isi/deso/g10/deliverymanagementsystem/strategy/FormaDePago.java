/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.strategy;



/**
 *
 * @author giuli
 */
public class FormaDePago {
    double montoFinal;
    double recargo=0;
    public double totalizar(double monto){
        //Calcula recargo
        montoFinal = monto + monto*(recargo/100);
        return montoFinal;
    }

    public double getMontoFinal() {
        return montoFinal;
    }

    public double getRecargo() {
        return recargo;
    }

    public String getNombre() {
        return this.getClass().getSimpleName();
    }
    
    
    
}
