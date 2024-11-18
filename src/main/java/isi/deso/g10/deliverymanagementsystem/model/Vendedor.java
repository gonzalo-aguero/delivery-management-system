/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import static java.lang.Math.*;
import java.util.ArrayList;

/**
 *
 * @author giuli
 */
@Entity
@Table(name="vendedor")
public class Vendedor extends Persona{

    
    private String nombre;
    private String direccion;
    
    
    @OneToMany(mappedBy="vendedor")
    private ArrayList<ItemMenu> menu;

    //constructor
    public Vendedor( String nombre, String direccion, Coordenada coordenadas) {
        
        super(coordenadas);
        this.nombre = nombre;
        this.direccion = direccion;
        this.menu = new ArrayList<ItemMenu>();
    }

    public Vendedor() {
        
    }

    //geters y setters
    public ArrayList<ItemMenu> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<ItemMenu> menu) {
        this.menu = menu;
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

  

    public double distancia(Cliente cliente) {
        final double R = 6378;

        //Latitudes
        double latV = toRadians(getCoordenadas().getLatitud());
        double latC = toRadians(cliente.getCoordenadas().getLatitud());

        //Longitudes
        double lngV = toRadians(getCoordenadas().getLongitud());
        double lngC = toRadians(cliente.getCoordenadas().getLongitud());

        //Deltas
        double dlat = latC - latV;
        double dlng = lngC - lngV;

        double a = pow(sin(dlat) / 2, 2) + cos(latV) * cos(latC) * pow(sin(dlng / 2), 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        double distancia = R * c;

        return distancia;
    }

    
    
    /**
     * ----------- Métodos Etapa 2 -----------
     */
    
    
    public ArrayList<Bebida> getItemsBebidas() {
        ArrayList<Bebida> bebidas = new ArrayList<Bebida>();
        for (ItemMenu item : menu) {
            if (item.esBebida()) {
                bebidas.add((Bebida)item);
            }
        }
        return bebidas;
    }

    public ArrayList<Plato> getItemsComidas() {
        ArrayList<Plato> comidas = new ArrayList<Plato>();
        for (ItemMenu item : menu) {
            if (item.esComida()) {
                comidas.add((Plato)item);
            }
        }
        return comidas;
    }

    public ArrayList<Plato> getItemsComidasVeganas() {
        ArrayList<Plato> comidasVeganas = new ArrayList<Plato>();
        for (ItemMenu item : menu) {
            if (item.esComida() && item.aptoVegano()) {
                comidasVeganas.add((Plato)item);
            }
        }
        return comidasVeganas;
    }
    
    public ArrayList<Bebida> getItemsBebidasVeganas() {
        ArrayList<Bebida> bebidasVeganas = new ArrayList<Bebida>();
        for (ItemMenu item : menu) {
            if (item.esBebida() && item.aptoVegano()) {
                bebidasVeganas.add((Bebida)item);
            }
        }
        return bebidasVeganas;
    }
    
    public ArrayList<Plato> getItemsComidasVegetarianas() {
        ArrayList<Plato> comidasVegetarianas = new ArrayList<Plato>();
        for (ItemMenu item : menu) {
            if (item.esComida() && item.aptoVegetariano()) {
                comidasVegetarianas.add((Plato)item);
            }
        }
        return comidasVegetarianas;
    }
        
    public ArrayList<Bebida> getItemsBebidasVegetarianas() {
        ArrayList<Bebida> bebidasVegetarianas = new ArrayList<Bebida>();
        for (ItemMenu item : menu) {
            if (item.esBebida() && item.aptoVegetariano()) {
                bebidasVegetarianas.add((Bebida)item);
            }
        }
        return bebidasVegetarianas;
    }
    
    public ArrayList<Plato> getItemsComidasAptoCeliaco() {
        ArrayList<Plato> comidasCeliaco = new ArrayList<Plato>();
        for (ItemMenu item : menu) {
            if (item.esComida() && item.aptoCeliaco()) {
                comidasCeliaco.add((Plato)item);
            }
        }
        return comidasCeliaco;
    }
    
    public ArrayList<Bebida> getItemsBebidasAptoCeliaco() {
        ArrayList<Bebida> bebidasCeliaco = new ArrayList<Bebida>();
        for (ItemMenu item : menu) {
            if (item.esBebida() && item.aptoCeliaco()) {
                bebidasCeliaco.add((Bebida)item);
            }
        }
        return bebidasCeliaco;
    }
    
    public ArrayList<Bebida> getItemsBebidaSinAlcohol() {
        ArrayList<Bebida> bebidasSinAlcohol = new ArrayList<Bebida>();
        for (ItemMenu item : menu) {
            if (item.esBebida() && ((Bebida) item).getGraduacionAlcoholica() == 0) {
                bebidasSinAlcohol.add((Bebida)item);
            }
        }
        return bebidasSinAlcohol;
    }
    
    //Esto permite agregarlo a un combobox y mantener la referencia
    @Override
    public String toString() {
        return nombre; // Esto se mostrará en el JComboBox
    }
}
