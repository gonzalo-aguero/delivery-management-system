/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.memory;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.CategoriaDao;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author giuli
 */
public class CategoriaMemory implements CategoriaDao {
    
    List<Categoria> categorias;
    
    private static CategoriaMemory instance;
    
    private CategoriaMemory(){
        categorias = new ArrayList<Categoria>();
        categorias.add(new Categoria(1, "Carnes", Categoria.TipoItem.COMIDA));
        categorias.add(new Categoria(2, "Pastas", Categoria.TipoItem.COMIDA));
        categorias.add(new Categoria(3, "Cervezas", Categoria.TipoItem.BEBIDA));
        categorias.add(new Categoria(4, "Vinos", Categoria.TipoItem.BEBIDA));
        categorias.add(new Categoria(5,"Plato Principal",Categoria.TipoItem.COMIDA));
        categorias.add(new Categoria(6,"Bebida",Categoria.TipoItem.BEBIDA));
        
    }
    
    public static CategoriaMemory getInstance(){
        if(instance == null){
            instance = new CategoriaMemory();
        }
        return instance;
    }
    

    @Override
    public Categoria crear(Categoria entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Categoria obtenerPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Categoria> obtenerTodos() {
        return categorias;
    }

    @Override
    public Categoria actualizar(Categoria entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Categoria obtenerPorDescripcion(String descripcion) {
        return categorias.stream()
            .filter(categoria -> categoria.getDescripcion().equalsIgnoreCase(descripcion))
            .findFirst()
            .orElse(null);
    }
    
    
}
