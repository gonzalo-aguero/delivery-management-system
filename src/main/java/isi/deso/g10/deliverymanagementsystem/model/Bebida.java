/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

/**
 *
 * @author lucas
 */
public class Bebida extends ItemMenu {

    //atributos propios de las bebidas
    private double graduacionAlcoholica;
    //sustituye al atributo tamanio del diagrama de clases del tp
    private double volumenEnMl;
    
    public void setGraduacionAlcoholica(double graduacionAlcoholica) {
        this.graduacionAlcoholica = graduacionAlcoholica;
    }

    public void setVolumenEnMl(double volumenEnMl) {
        this.volumenEnMl = volumenEnMl;
    }
    
    //constructor (eliminar y dejar el completo)
    public Bebida(double graduacionAlcoholica, double volumenEnMl, int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano) {
        super(id, nombre, descripcion, precio, categoria, calorias, aptoCeliaco, aptoVegetariano, aptoVegano);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.volumenEnMl = volumenEnMl;
    }
    
    /**
     * Constructor con todos los atributos.
     * @param id
     * @param nombre
     * @param descripcion
     * @param precio
     * @param categoria
     * @param calorias
     * @param aptoCeliaco
     * @param aptoVegetariano
     * @param aptoVegano
     * @param vendedor
     * @param graduacionAlcoholica
     * @param volumenEnMl 
     */
    public Bebida(int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano, Vendedor vendedor, double graduacionAlcoholica, double volumenEnMl) {
        super(id, nombre, descripcion, precio, categoria, calorias, aptoCeliaco, aptoVegetariano, aptoVegano, vendedor);
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.volumenEnMl = volumenEnMl;
    }

    //definicion de los metodos abstractos de ItemMenu
    @Override
    public double peso() {
        if (graduacionAlcoholica == 0) {
            double pesoBebida = volumenEnMl * 1.04;
            return pesoBebida + pesoBebida * 0.20;
        } else {
            double pesoBebidaAlcoholica = volumenEnMl * 0.99;
            return pesoBebidaAlcoholica + pesoBebidaAlcoholica * 0.20;
        }
    }

    @Override
    public boolean esComida() {
        return false;
    }

    @Override
    public boolean esBebida() {
        return true;
    }

    @Override
    public boolean aptoVegano() {
        return aptoVegano;
    }

    @Override
    public boolean aptoVegetariano() {
        return aptoVegetariano;
    }

    @Override
    public boolean aptoCeliaco() {
        return aptoCeliaco;
    }

    //getters
    public double getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    public double getVolumenEnMl() {
        return volumenEnMl;
    }
}
