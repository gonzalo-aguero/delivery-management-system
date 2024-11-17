/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author giuli
 */
@Entity
@Table(name="coordenada")
public class Coordenada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double latitud;
    private double longitud;
    
    @OneToOne
    @JoinColumn(name="cliente_id",nullable = true)
    private Cliente cliente;
    
    @OneToOne
    @JoinColumn(name="vendedor_id",nullable = true)  
    private Vendedor vendedor;
    
    
    

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
