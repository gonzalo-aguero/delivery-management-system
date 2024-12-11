/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Categoria.TipoItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author giuli
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private int id;
    private String descripcion;
    private TipoItem tipoItem;

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.descripcion = categoria.getDescripcion();
        this.tipoItem = categoria.getTipoItem();
    }
}
