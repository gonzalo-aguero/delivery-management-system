/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import java.util.List;

/**
 *
 * @author giuli
 */
public interface CategoriaDao {
    
    public List<Categoria> obtenerCategorias();
    
    public Categoria agregarCategoria(Categoria categoria);
    
    public Categoria actualizarCategoria(Categoria categoria);
    
    public boolean eliminarCategoria(int id);
    
    public Categoria buscarCategoriaPorId(int id);
    
}
