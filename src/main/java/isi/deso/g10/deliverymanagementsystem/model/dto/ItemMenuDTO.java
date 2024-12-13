/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giuli
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMenuDTO {
    
    public enum Tipo{
        PLATO,
        BEBIDA
    }
    
    private int id;
    private Tipo tipo;
    private String nombre;
    private String descripcion;
    private double precio;
    private CategoriaDTO categoria;
    private int categoriaId;
    private int calorias;
    private boolean aptoCeliaco;
    private boolean aptoVegetariano;
    private boolean aptoVegano;
    private VendedorDTO vendedor;
    private int vendedorId;
    private double peso;
    private double graduacionAlcoholica;
    private double volumenEnMl;
    
    public ItemMenuDTO(ItemMenu itemMenu) {
    this.id = itemMenu.getId();
    this.nombre = itemMenu.getNombre();
    this.descripcion = itemMenu.getDescripcion();
    this.precio = itemMenu.getPrecio();
    this.categoria = new CategoriaDTO(itemMenu.getCategoria());
    this.calorias = itemMenu.getCalorias();
    this.aptoCeliaco = itemMenu.isAptoCeliaco();
    this.aptoVegetariano = itemMenu.isAptoVegetariano();
    this.aptoVegano = itemMenu.isAptoVegano();
    this.vendedor = new VendedorDTO(itemMenu.getVendedor());
    this.vendedorId = this.vendedor.getId();
    this.categoriaId = this.categoria.getId();


    if (itemMenu instanceof Plato) {
        Plato plato = (Plato) itemMenu;
        this.tipo = Tipo.PLATO;
        this.peso = plato.getPeso();
    } else if (itemMenu instanceof Bebida) {
        Bebida bebida = (Bebida) itemMenu;
        this.tipo = Tipo.BEBIDA;
        this.graduacionAlcoholica = bebida.getGraduacionAlcoholica();
        this.volumenEnMl = bebida.getVolumenEnMl();
    }
}
}
