/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

/**
 *
 * @author giuli
 */
public class CoordenadaDTO {
    
    private double latitud;
    private double longitud;

    // Constructor
    public CoordenadaDTO(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y setters
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
