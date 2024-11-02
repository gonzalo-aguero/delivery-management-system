/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

/**
 *
 * @author giuli
 */
public interface Controller {
    
    //Agregar los listeners de los botones para ejecutar cada metodo propio del controller
    public void addFrameListeners();
    
    //Setea la tabla propia de cada modelo
    public void setTable();
    
    public void setTableFiltradaPorNombre(String cadena);
    
    public void crear();
    
   
    
}
