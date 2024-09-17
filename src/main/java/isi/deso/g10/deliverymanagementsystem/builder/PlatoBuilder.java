/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.builder;

import isi.deso.g10.deliverymanagementsystem.model.Plato;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;

/**
 *
 * @author gonzalo90fa
 */
public class PlatoBuilder {
    
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
    private double peso;

    // Métodos para setear cada atributo con retorno del builder para permitir encadenamiento
    public PlatoBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PlatoBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public PlatoBuilder setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public PlatoBuilder setPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public PlatoBuilder setCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public PlatoBuilder setCalorias(int calorias) {
        this.calorias = calorias;
        return this;
    }

    public PlatoBuilder setAptoCeliaco(boolean aptoCeliaco) {
        this.aptoCeliaco = aptoCeliaco;
        return this;
    }

    public PlatoBuilder setAptoVegetariano(boolean aptoVegetariano) {
        this.aptoVegetariano = aptoVegetariano;
        return this;
    }

    public PlatoBuilder setAptoVegano(boolean aptoVegano) {
        this.aptoVegano = aptoVegano;
        return this;
    }

    public PlatoBuilder setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
        return this;
    }

    public PlatoBuilder setPeso(double peso) {
        this.peso = peso;
        return this;
    }

    // Método para construir el objeto Plato
    public Plato build() {
        return new Plato(id, nombre, descripcion, precio, categoria, calorias, aptoCeliaco, aptoVegetariano, aptoVegano, vendedor, peso);
    }
}