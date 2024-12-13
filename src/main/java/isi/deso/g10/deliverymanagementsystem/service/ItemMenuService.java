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
import isi.deso.g10.deliverymanagementsystem.model.dto.CategoriaDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.ItemMenuDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.ItemMenuDTO.Tipo;
import isi.deso.g10.deliverymanagementsystem.repository.CategoriaRepository;
import isi.deso.g10.deliverymanagementsystem.repository.ItemMenuRepository;
import isi.deso.g10.deliverymanagementsystem.repository.VendedorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
    
    
    public ItemMenuDTO getById(Integer id) throws NotFoundException {
        try{
            Optional<ItemMenu> itemMenuOpt = itemMenuRepository.findById(id);
            if (itemMenuOpt.isEmpty()) {
                throw new NotFoundException();
            }
            ItemMenu itemMenu = itemMenuOpt.get();

            ItemMenuDTO itemMenuDTO = new ItemMenuDTO(itemMenu);

            return itemMenuDTO;
        }
        catch(Exception ex){ 
            throw new RuntimeException(ex.getMessage());
        }
    }

    public List<ItemMenuDTO> getAll() throws NotFoundException {
       List<ItemMenu> itemMenus = itemMenuRepository.findAll();
        if (itemMenus.isEmpty()) {
            throw new NotFoundException();
        }
        
        return itemMenus.stream()
                .map(itemMenu ->    
                    {
                    ItemMenuDTO itemMenuDTO = new ItemMenuDTO(itemMenu);
                    
                    return itemMenuDTO;
                })
                .collect(Collectors.toList());
    }

    public ItemMenuDTO saveItemMenu(ItemMenuDTO itemMenuDTO) {
        Optional<Categoria> categoria = categoriaRepository.findById(itemMenuDTO.getCategoriaId());
        Optional<Vendedor> vendedor = vendedorRepository.findById(itemMenuDTO.getVendedorId());
                if(vendedor.isEmpty()){throw new RuntimeException("No existe vendedor con id:" + itemMenuDTO.getVendedorId());}
                if(categoria.isEmpty()){throw new RuntimeException("No existe categoria con id:" + itemMenuDTO.getCategoriaId());}

                ItemMenu nuevoItemMenu;


                if(itemMenuDTO.getTipo() == Tipo.PLATO){
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
                    itemMenuDTO.setId(nuevoItemMenu.getId());
                    return itemMenuDTO;
                }catch(RuntimeException e){
                    throw new RuntimeException("No se pudo guardar el Item Menu", e);
                }
    }

    public void deleteById(Integer id) throws NotFoundException {
            if(!itemMenuRepository.existsById(id)) {
                throw new NotFoundException();
            }
            itemMenuRepository.deleteById(id);
        }

    public ItemMenuDTO updateItemMenu(ItemMenuDTO itemMenuDTO) throws NotFoundException {
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
                     return itemMenuDTO;
                     }catch(RuntimeException e){
                         throw new RuntimeException("Error al guardar");
                     }
                }else{
                        throw new NotFoundException();
                }
           }

}
