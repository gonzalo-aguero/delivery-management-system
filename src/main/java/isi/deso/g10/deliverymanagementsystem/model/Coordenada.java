/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import isi.deso.g10.deliverymanagementsystem.model.dto.CoordenadaDTO;
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
    
    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    private Persona persona;
    
    
    public Coordenada(){}

    public Coordenada(double lat,double lng){
        this.latitud=lat;
        this.longitud=lng;
    }
    public Coordenada(double lat,double lng,Persona persona){
        this.latitud=lat;
        this.longitud=lng;
    }

   
    
    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    
    
}
