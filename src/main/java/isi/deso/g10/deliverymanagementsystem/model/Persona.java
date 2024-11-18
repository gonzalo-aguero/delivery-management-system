/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 *
 * @author giuli
 */
@Entity
@Table(name="persona")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Persona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonManagedReference
    @OneToOne(mappedBy="persona",cascade = CascadeType.ALL,orphanRemoval = true)
    private Coordenada coordenadas;
    
    Persona(){};
    
    Persona(Coordenada coordenada){
        this.coordenadas = coordenada;
    }
    
    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenada coordenadas) {
        this.coordenadas = coordenadas;
    }

    
    public int getId() {
        return id;
    }
    
    
    
}
