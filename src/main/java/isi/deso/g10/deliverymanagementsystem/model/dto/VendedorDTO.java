package isi.deso.g10.deliverymanagementsystem.model.dto;

import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
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
    
    public VendedorDTO(Vendedor vendedor){
        this.id = vendedor.getId();
        this.nombre = vendedor.getNombre();
        this.direccion = vendedor.getDireccion();
        this.coordenadas = new CoordenadaDTO(vendedor.getCoordenadas());
    }
}