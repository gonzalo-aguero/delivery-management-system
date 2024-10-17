/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.builder;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
//borrar para mi esta todo mal aplicado esto.
/**
 * 
 * @author gonzalo90fa
 */
public class BebidaBuilder {
    
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private int calorias;
    private boolean aptoCeliaco;
    private boolean aptoVegetariano;
    private boolean aptoVegano;
    private Vendedor vendedor;
    private double graduacionAlcoholica;
    private double volumenEnMl;

    // Métodos para setear cada atributo con retorno del builder para permitir encadenamiento
    public BebidaBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public BebidaBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public BebidaBuilder setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public BebidaBuilder setPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public BebidaBuilder setCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public BebidaBuilder setCalorias(int calorias) {
        this.calorias = calorias;
        return this;
    }

    public BebidaBuilder setAptoCeliaco(boolean aptoCeliaco) {
        this.aptoCeliaco = aptoCeliaco;
        return this;
    }

    public BebidaBuilder setAptoVegetariano(boolean aptoVegetariano) {
        this.aptoVegetariano = aptoVegetariano;
        return this;
    }

    public BebidaBuilder setAptoVegano(boolean aptoVegano) {
        this.aptoVegano = aptoVegano;
        return this;
    }

    public BebidaBuilder setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        return this;
    }

    public BebidaBuilder setGraduacionAlcoholica(double graduacionAlcoholica) {
        this.graduacionAlcoholica = graduacionAlcoholica;
        return this;
    }

    public BebidaBuilder setVolumenEnMl(double volumenEnMl) {
        this.volumenEnMl = volumenEnMl;
        return this;
    }

    // Método para construir el objeto Bebida
    public Bebida build() {
        return new Bebida(id, nombre, descripcion, precio, categoria, calorias, aptoCeliaco, aptoVegetariano, aptoVegano, vendedor, graduacionAlcoholica, volumenEnMl);
    }
}