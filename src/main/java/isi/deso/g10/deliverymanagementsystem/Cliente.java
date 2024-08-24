/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

/**
 *
 * @author giuli
 */
public class Cliente {

    private int id;
    private String cuit;
    private String nombre;
    private String email;
    private String direccion;
    private Coordenada coordenadas;
    
    public Cliente(int id, String cuit, String nombre, String email, String direccion, Coordenada coordenadas) {
        this.id = id;
        this.cuit = cuit;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }
    
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

    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Coordenada coordenadas) {
        this.coordenadas = coordenadas;
    }    

    
    
}
