/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

/**
 *
 * @author lucas
 */
public class Comida  extends ItemMenu{
    //atributos especificos de la clase
    private double peso;
    
    //constructor 

    public Comida(double peso, int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano) {
        super(id, nombre, descripcion, precio, categoria, calorias, aptoCeliaco, aptoVegetariano, aptoVegano);
        this.peso = peso;
    }
    

    //definicion de metodos abstractos de la clase ItemMenu
    @Override
    public double peso() {
        return peso + peso * 0.1;
    }
    @Override
    public boolean esComida(){
        return true;
    }
    @Override
    public boolean esBebida(){
        return false;
    }
    @Override
    public boolean aptoVegano(){
        return aptoVegano;
    }
    @Override
    public boolean aptoVegetariano(){
        return aptoVegetariano;
    }
    @Override
    public boolean aptoCeliaco(){
        return aptoCeliaco;
    }
    //getters
    public double getPeso() {
        return peso;
    }
        
}
