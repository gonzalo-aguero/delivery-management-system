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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author giuli
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vendedor")
public class Vendedor extends Persona {

    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "vendedor")
    private List<ItemMenu> menu;

    // constructor
    public Vendedor(String nombre, String direccion, Coordenada coordenadas) {
        super(coordenadas);
        this.nombre = nombre;
        this.direccion = direccion;
        this.menu = new ArrayList<>();
    }

    public double distancia(Cliente cliente) {
        final double R = 6378;

        // Latitudes
        double latV = toRadians(getCoordenadas().getLatitud());
        double latC = toRadians(cliente.getCoordenadas().getLatitud());

        // Longitudes
        double lngV = toRadians(getCoordenadas().getLongitud());
        double lngC = toRadians(cliente.getCoordenadas().getLongitud());

        // Deltas
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

    @JsonIgnore
    public List<Bebida> getItemsBebidas() {
        return menu.stream()
                .filter(ItemMenu::esBebida)
                .map(item -> (Bebida) item)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Plato> getItemsComidas() {
        return menu.stream()
                .filter(ItemMenu::esComida)
                .map(item -> (Plato) item)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Plato> getItemsComidasVeganas() {
        return menu.stream()
                .filter(item -> item.esComida() && item.aptoVegano())
                .map(item -> (Plato) item)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Bebida> getItemsBebidasVeganas() {
        return menu.stream()
                .filter(item -> item.esBebida() && item.aptoVegano())
                .map(item -> (Bebida) item)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Plato> getItemsComidasVegetarianas() {
        return menu.stream()
                .filter(item -> item.esComida() && item.aptoVegetariano())
                .map(item -> (Plato) item)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Bebida> getItemsBebidasVegetarianas() {
        return menu.stream()
                .filter(item -> item.esBebida() && item.aptoVegetariano())
                .map(item -> (Bebida) item)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Plato> getItemsComidasAptoCeliaco() {
        return menu.stream()
                .filter(item -> item.esComida() && item.aptoCeliaco())
                .map(item -> (Plato) item)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Bebida> getItemsBebidasAptoCeliaco() {
        return menu.stream()
                .filter(item -> item.esBebida() && item.aptoCeliaco())
                .map(item -> (Bebida) item)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Bebida> getItemsBebidaSinAlcohol() {
        return menu.stream()
                .filter(item -> item.esBebida() && ((Bebida) item).getGraduacionAlcoholica() == 0)
                .map(item -> (Bebida) item)
                .collect(Collectors.toList());
    }

    // Esto permite agregarlo a un combobox y mantener la referencia
    @Override
    public String toString() {
        return nombre; // Esto se mostrará en el JComboBox
    }
}
