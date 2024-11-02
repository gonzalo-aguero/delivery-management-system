/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.dao.interfaces.CategoriaDao;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import java.util.ArrayList;

/**
 *
 * @author giuli
 */
public class CategoriaMemory implements CategoriaDao{
    private static CategoriaMemory self;
    
    private CategoriaMemory(){
        self=this;
    }
    
    public static CategoriaMemory getInstance(){
        if(self == null){
        self = new CategoriaMemory();
        }
        return self;
    }

    @Override
    public ArrayList<Categoria> getCategorias() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
