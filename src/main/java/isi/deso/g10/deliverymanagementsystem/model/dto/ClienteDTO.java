/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

/**
 *
 * @author giuli
 */
import java.util.List;

public class ClienteDTO {

    private int id;
    private String cuit;
    private String nombre;
    private String email;
    private String direccion;
    private CoordenadaDTO coordenadas;

    // Constructor
    public ClienteDTO(int id, String cuit, String nombre, String email, String direccion, CoordenadaDTO coordenadas) {
        this.id = id;
        this.cuit = cuit;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
        
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CoordenadaDTO getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(CoordenadaDTO coordenadas) {
        this.coordenadas = coordenadas;
    }

    
}