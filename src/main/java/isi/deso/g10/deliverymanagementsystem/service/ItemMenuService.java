/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
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

        ItemMenu nuevoItemMenu;
        
        
        if(categoria.get().getTipoItem() == TipoItem.COMIDA){
            Plato plato = new Plato(itemMenuDTO.getPeso(),
                    0,
            itemMenuDTO.getNombre(),
            itemMenuDTO.getDescripcion(),
            itemMenuDTO.getPrecio(),
            categoria.get(),
            itemMenuDTO.getCalorias(),
            itemMenuDTO.isAptoCeliaco(),
            itemMenuDTO.isAptoVegetariano(),
            itemMenuDTO.isAptoVegano());
        
            nuevoItemMenu = plato;
        }
        else{
            Bebida bebida = new Bebida(itemMenuDTO.getGraduacionAlcoholica(),
                    itemMenuDTO.getVolumenEnMl(),
                    0,
            itemMenuDTO.getNombre(),
            itemMenuDTO.getDescripcion(),
            itemMenuDTO.getPrecio(),
            categoria.get(),
            itemMenuDTO.getCalorias(),
            itemMenuDTO.isAptoCeliaco(),
            itemMenuDTO.isAptoVegetariano(),
            itemMenuDTO.isAptoVegano());
            
            nuevoItemMenu = bebida;
        }
        
        try{
            nuevoItemMenu = itemMenuRepository.save(nuevoItemMenu);
            return nuevoItemMenu;
        }catch(RuntimeException e){
            throw new RuntimeException("No se pudo guardar el Item Menu", e);
        }
    }

    public void deleteById(Integer id) {
        if(!itemMenuRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el vendedor con id " + id);
        }
        itemMenuRepository.deleteById(id);
    }

    public ItemMenu updateItemMenu(ItemMenuDTO itemMenuDTO) {
         int id = itemMenuDTO.getId();
         
         Optional<ItemMenu> itemMenuOpt = itemMenuRepository.findById(id);
         
         
         if(itemMenuOpt.isPresent()){
             
            Optional<Categoria> categoria = categoriaRepository.findById(itemMenuDTO.getCategoriaId());
            Optional<Vendedor> vendedor = vendedorRepository.findById(itemMenuDTO.getVendedorId());
            if(vendedor.isEmpty()){throw new RuntimeException("No existe vendedor con id:" + itemMenuDTO.getVendedorId());}
            if(categoria.isEmpty()){throw new RuntimeException("No existe categoria con id:" + itemMenuDTO.getCategoriaId());}
             ItemMenu itemMenu = itemMenuOpt.get();
             
             itemMenu.setNombre(itemMenuDTO.getNombre());
             itemMenu.setDescripcion(itemMenuDTO.getDescripcion()); 
             itemMenu.setCategoria(categoria.get());
             itemMenu.setPrecio(itemMenuDTO.getPrecio());
             itemMenu.setCalorias(itemMenuDTO.getCalorias());
             itemMenu.setAptoCeliaco(itemMenuDTO.isAptoCeliaco());
             itemMenu.setAptoVegetariano(itemMenuDTO.isAptoVegetariano());
             itemMenu.setAptoVegano(itemMenuDTO.isAptoVegano());
             
              if (itemMenu instanceof Plato plato) {
                     plato.setPeso(itemMenuDTO.getPeso());
              } else if (itemMenu instanceof Bebida bebida) {
                        bebida.setGraduacionAlcoholica(itemMenuDTO.getGraduacionAlcoholica());
                        bebida.setVolumenEnMl(itemMenuDTO.getVolumenEnMl());
                } else {
                            throw new RuntimeException("Error: tipo desconocido de ItemMenu: " + itemMenu.getClass().getName());
                        }
              
              try{
              itemMenu = itemMenuRepository.save(itemMenu);
              return itemMenu;
              }catch(RuntimeException e){
                  throw new RuntimeException("Error al guardar");
              }
         }else{
                 throw new RuntimeException("No existe ItemMenu con id:" + id);
         }
    }
    
}
