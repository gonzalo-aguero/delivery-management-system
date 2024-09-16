/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

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
    private ArrayList<ItemMenu> menu;

    //constructor
    public Vendedor(int id, String nombre, String direccion, Coordenada coordenadas) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
        this.menu = new ArrayList();
    }

    //geters y setters
    public ArrayList<ItemMenu> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<ItemMenu> menu) {
        this.menu = menu;
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
    public double distancia(Cliente cliente) {
        //le puse final porque el radio de la tierra es cte
        final double R = 6378;

        //Latitudes
        double latV = toRadians(this.coordenadas.getLatitud());
        double latC = toRadians(cliente.getCoordenadas().getLatitud());

        //Longitudes
        double lngV = toRadians(this.coordenadas.getLongitud());
        double lngC = toRadians(cliente.getCoordenadas().getLongitud());

        //Deltas
        double dlat = latC - latV;
        double dlng = lngC - lngV;

        double a = pow(sin(dlat) / 2, 2) + cos(latV) * cos(latC) * pow(sin(dlng / 2), 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        double distancia = R * c;

        return distancia;
    }

    //metodos etapa 2 
    public ArrayList<Bebida> getItemsBebida() {
        ArrayList<Bebida> bebidas = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esBebida()) {
                bebidas.add((Bebida)item);
            }
        }
        return bebidas;
    }

    public ArrayList<Comida> getItemsComida() {
        ArrayList<Comida> comidas = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esComida()) {
                comidas.add((Comida)item);
            }
        }
        return comidas;
    }

    public ArrayList<Comida> getItemsComidaVegana() {
        ArrayList<Comida> comidasVeganas = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esComida() && item.aptoVegano()) {
                comidasVeganas.add((Comida)item);
            }
        }
        return comidasVeganas;
    }
    public ArrayList<Bebida> getItemsBebidasVeganas() {
        ArrayList<Bebida> bebidasVeganas = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esBebida() && item.aptoVegano()) {
                bebidasVeganas.add((Bebida)item);
            }
        }
        return bebidasVeganas;
    }
    
        public ArrayList<Comida> getItemsComidasVegetarianas() {
        ArrayList<Comida> comidasVegetarianas = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esComida() && item.aptoVegetariano()) {
                comidasVegetarianas.add((Comida)item);
            }
        }
        return comidasVegetarianas;
    }
        
     public ArrayList<Bebida> getItemsBebidasVegetarianas() {
        ArrayList<Bebida> bebidasVegetarianas = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esBebida() && item.aptoVegetariano()) {
                bebidasVegetarianas.add((Bebida)item);
            }
        }
        return bebidasVegetarianas;
    }
     
    public ArrayList<Comida> getItemsComidasAptoCeliaco() {
        ArrayList<Comida> comidasCeliaco = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esComida() && item.aptoCeliaco()) {
                comidasCeliaco.add((Comida)item);
            }
        }
        return comidasCeliaco;
    }
     
    public ArrayList<Bebida> getItemsBebidasAptoCeliaco() {
        ArrayList<Bebida> bebidasCeliaco = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esBebida() && item.aptoCeliaco()) {
                bebidasCeliaco.add((Bebida)item);
            }
        }
        return bebidasCeliaco;
    }
    
    public ArrayList<Bebida> getItemsBebidaSinAlcohol() {
        ArrayList<Bebida> bebidasSinAlcohol = new ArrayList();
        for (ItemMenu item : menu) {
            if (item.esBebida() && ((Bebida) item).getGraduacionAlcoholica() == 0) {
                bebidasSinAlcohol.add((Bebida)item);
            }
        }
        return bebidasSinAlcohol;
    }
}
