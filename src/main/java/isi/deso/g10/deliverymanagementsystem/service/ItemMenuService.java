/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Categoria.TipoItem;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.model.dto.ItemMenuDTO;
import isi.deso.g10.deliverymanagementsystem.repository.CategoriaRepository;
import isi.deso.g10.deliverymanagementsystem.repository.ItemMenuRepository;
import isi.deso.g10.deliverymanagementsystem.repository.VendedorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author giuli
 */
@Service
public class ItemMenuService {

    @Autowired
    ItemMenuRepository itemMenuRepository;
    @Autowired
    VendedorRepository vendedorRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    
    
    public Optional<ItemMenu> getById(Integer id) {
       return itemMenuRepository.findById(id);
    }

    public List<ItemMenu> getAll() {
       return itemMenuRepository.findAll();
    }

    public ItemMenu saveItemMenu(ItemMenuDTO itemMenuDTO) {
        Optional<Categoria> categoria = categoriaRepository.findById(itemMenuDTO.getCategoriaId());
        Optional<Vendedor> vendedor = vendedorRepository.findById(itemMenuDTO.getVendedorId());
        if(vendedor.isEmpty()){throw new RuntimeException("No existe vendedor con id:" + itemMenuDTO.getVendedorId());}
        if(categoria.isEmpty()){throw new RuntimeException("No existe categoria con id:" + itemMenuDTO.getCategoriaId());}

        /*ItemMenu nuevoItemMenu;
        if(categoria.get().getTipoItem() == TipoItem.COMIDA){
            nuevoItemMenu = new Plato();
        }else{}
        
        try{
            
        }
        COMENTO ACA PORQUE CREO QUE SE PUEDE DE HACER MEJOR FORMA DESPUES VEO*/
        
    }

    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ItemMenu updateItemMenu(ItemMenuDTO itemMenuDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
