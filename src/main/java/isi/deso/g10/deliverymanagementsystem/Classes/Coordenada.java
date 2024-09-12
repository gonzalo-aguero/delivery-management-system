/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.Classes;

/**
 *
 * @author giuli
 */
public class Coordenada {
    
    private double latitud;
    private double longitud;

    public Coordenada(double lat,double lng){
        this.latitud=lat;
        this.longitud=lng;
    }
    
    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
    
    
    
}
