/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

import static java.lang.Math.*;
import java.util.ArrayList;
/**
 *
 * @author giuli
 */
public class Vendedor {
    
    //atributos
    private int id;
    private String nombre;
    private String direccion;
    private Coordenada coordenadas;
    private ArrayList<ItemMenu> listaComidas;
    
    //constructor
    public Vendedor(int id,String nombre,String direccion,Coordenada coordenadas){
        this.id=id;
        this.nombre=nombre;
        this.direccion=direccion;
        this.coordenadas=coordenadas;
        this.listaComidas = new ArrayList();
    }

    //geters y setters
    public ArrayList<ItemMenu> getListaComidas() {
        return listaComidas;
    }

    public void setListaComidas(ArrayList<ItemMenu> listaComidas) {
        this.listaComidas = listaComidas;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    //el metodo creo que esta bien
    public double distancia(Cliente cliente){
        //le puse final porque el radio de la tierra es cte
        final  double R=6378;
        
        
        //Latitudes
        double latV= toRadians(this.coordenadas.getLatitud());
        double latC= toRadians(cliente.getCoordenadas().getLatitud());
        
        
        //Longitudes
        double lngV= toRadians(this.coordenadas.getLongitud());
        double lngC= toRadians(cliente.getCoordenadas().getLongitud());
        
        //Deltas
        double dlat= latC-latV;
        double dlng= lngC-lngV;
        
        double a= pow(sin(dlat)/2,2)+ cos(latV)* cos(latC) * pow(sin(dlng/2),2);
        double c= 2* atan2(sqrt(a), sqrt(1-a));
        
        double distancia= R * c;
        
        return distancia;
    }
    
    
    
}
