/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

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
}
