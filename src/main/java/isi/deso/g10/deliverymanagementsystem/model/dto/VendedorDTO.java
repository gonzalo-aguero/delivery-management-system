package isi.deso.g10.deliverymanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la entidad Vendedor.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendedorDTO {

    private int id;
    private String nombre;
    private String direccion;
    private CoordenadaDTO coordenadas;

}