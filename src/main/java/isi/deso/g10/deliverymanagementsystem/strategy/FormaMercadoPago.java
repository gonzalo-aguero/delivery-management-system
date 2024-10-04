/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.strategy;



/**
 *
 * @author giuli
 */
public class FormaMercadoPago extends FormaDePago {
    
    private String alias;
    
    public FormaMercadoPago(){
        super.recargo= 2;
    }
    
    public String getAlias(){
        return alias;
    }
    public void setAlias(String alias){
        this.alias=alias;
    }
}
