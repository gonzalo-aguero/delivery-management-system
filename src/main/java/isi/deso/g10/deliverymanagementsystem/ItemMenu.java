/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

/**
 *
 * @author lucas
 */
public abstract  class ItemMenu {
    private int id;
    private String nombre;
    private String descripcion;
    private  double precio;
    private Categoria categoria;
    private int  calorias;
    protected boolean aptoCeliaco;
    protected boolean aptoVegetariano;
    protected boolean aptoVegano;

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public int getCalorias() {
        return calorias;
    }
    public boolean isAptoCeliaco() {
        return aptoCeliaco;
    }

    public boolean isAptoVegetariano() {
        return aptoVegetariano;
    }

    public boolean isAptoVegano() {
        return aptoVegano;
    }

    //constructor
    public ItemMenu(int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.calorias = calorias;
        this.aptoCeliaco = aptoCeliaco;
        this.aptoVegetariano = aptoVegetariano;
        this.aptoVegano = aptoVegano;
    }
    
    
    //metodos abstractos 
    public abstract  double peso();
    public abstract boolean esComida();
    public abstract boolean esBebida();
    public abstract boolean aptoVegano();
    public abstract boolean aptoVegetariano();
    public abstract boolean aptoCeliaco();
}
