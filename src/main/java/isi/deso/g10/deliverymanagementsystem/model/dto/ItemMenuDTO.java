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
    
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int categoriaId;
    private int calorias;
    private boolean aptoCeliaco;
    private boolean aptoVegetariano;
    private boolean aptoVegano;
    private int vendedorId;
    private double peso;
    private double graduacionAlcoholica;
    private double volumenEnMl;
    
    public ItemMenuDTO(ItemMenu itemMenu) {
    this.id = itemMenu.getId();
    this.nombre = itemMenu.getNombre();
    this.descripcion = itemMenu.getDescripcion();
    this.precio = itemMenu.getPrecio();
    this.categoriaId = itemMenu.getCategoria().getId();
    this.calorias = itemMenu.getCalorias();
    this.aptoCeliaco = itemMenu.isAptoCeliaco();
    this.aptoVegetariano = itemMenu.isAptoVegetariano();
    this.aptoVegano = itemMenu.isAptoVegano();
    this.vendedorId = itemMenu.getVendedor().getId();

    // Manejar campos específicos de Plato o Bebida
    if (itemMenu instanceof Plato) {
        Plato plato = (Plato) itemMenu;
        this.peso = plato.getPeso();
    } else if (itemMenu instanceof Bebida) {
        Bebida bebida = (Bebida) itemMenu;
        this.graduacionAlcoholica = bebida.getGraduacionAlcoholica();
        this.volumenEnMl = bebida.getVolumenEnMl();
    }
}
}
