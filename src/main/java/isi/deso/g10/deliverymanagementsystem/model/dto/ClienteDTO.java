/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

/**
 *
 * @author giuli
 */
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private int id;
    private String cuit;
    private String nombre;
    private String email;
    private String direccion;
    private CoordenadaDTO coordenadas;
    
    public ClienteDTO(Cliente cliente) {
    this.id = cliente.getId();
    this.nombre = cliente.getNombre();
    this.direccion = cliente.getDireccion();
    this.cuit = cliente.getCuit();
    this.email = cliente.getEmail();
    this.coordenadas = cliente.getCoordenadas() != null ? new CoordenadaDTO(cliente.getCoordenadas()) : null;
    }

}