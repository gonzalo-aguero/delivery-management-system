/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author lucas
 */
@Entity
@Table(name="itemmenu")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ItemMenu {

    //atributos comunes a ambas subclases
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemmenu_seq")
    @SequenceGenerator(name = "itemmenu_seq", sequenceName = "itemmenu_sequence", allocationSize = 1)
    protected int id;
    protected String nombre;
    protected String descripcion;
    protected double precio;
    @ManyToOne()
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "FK_item_categoria"))
    protected Categoria categoria;
    protected int calorias;
    protected boolean aptoCeliaco;
    protected boolean aptoVegetariano;
    protected boolean aptoVegano;
    @ManyToOne()
    @JoinColumn(name = "vendedor_id")
    protected Vendedor vendedor;
    
    @OneToMany(mappedBy = "itemmenu", fetch = FetchType.LAZY)
    private List<DetallePedido> detallePedidos;
    
    public ItemMenu(){};

    //constructor (eliminar y dejar el completo)
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
     */
    public ItemMenu(int id, String nombre, String descripcion, double precio, Categoria categoria, int calorias, boolean aptoCeliaco, boolean aptoVegetariano, boolean aptoVegano, Vendedor vendedor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.calorias = calorias;
        this.aptoCeliaco = aptoCeliaco;
        this.aptoVegetariano = aptoVegetariano;
        this.aptoVegano = aptoVegano;
        this.vendedor = vendedor;
    }

    //getters
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

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
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

    //metodos abstractos 
    public abstract double peso();

    public abstract boolean esComida();

    public abstract boolean esBebida();

    public abstract boolean aptoVegano();

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public void setAptoCeliaco(boolean aptoCeliaco) {
        this.aptoCeliaco = aptoCeliaco;
    }

    public void setAptoVegetariano(boolean aptoVegetariano) {
        this.aptoVegetariano = aptoVegetariano;
    }

    public void setAptoVegano(boolean aptoVegano) {
        this.aptoVegano = aptoVegano;
    }

    public abstract boolean aptoVegetariano();

    public abstract boolean aptoCeliaco();
}
