/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
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
public class CoordenadaDTO {
    
    private double latitud;
    private double longitud;

    public CoordenadaDTO(Coordenada coordenada) {
    this.latitud = coordenada.getLatitud();
    this.longitud = coordenada.getLongitud();
}   
}
