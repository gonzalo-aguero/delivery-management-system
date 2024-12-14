/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.dto.ItemMenuDTO;
import isi.deso.g10.deliverymanagementsystem.service.ItemMenuService;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author giuli
 */
@RestController
@CrossOrigin
@RequestMapping("/itemmenu")
public class ItemMenuController {
    
    @Autowired
    ItemMenuService itemMenuService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuDTO> getItemMenu(@PathVariable(value = "id") Integer id){
        try{
           ItemMenuDTO itemMenu= itemMenuService.getById(id);
           return ResponseEntity.ok(itemMenu);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
              
        }
    }
    
    @GetMapping
    public ResponseEntity<List<ItemMenuDTO>> getItemsMenu(){
        try{
           List<ItemMenuDTO> itemMenu= itemMenuService.getAll();
           return ResponseEntity.ok(itemMenu);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              //return ResponseEntity.internalServerError().build();
              throw new RuntimeException(e.getMessage());
              //
        }
    }
    
    @GetMapping("/by-vendedor")
    public ResponseEntity<List<ItemMenuDTO>> getItemsByVendedorId(@RequestParam(name = "vendedorId", required = true) Integer vendedorId){
        try{
            List<ItemMenuDTO> itemMenus = itemMenuService.getByVendedorId(vendedorId);
            return ResponseEntity.ok(itemMenus);
        } catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        } catch(RuntimeException e){
            Logger.getLogger(ItemMenuController.class.getName()).log(Level.SEVERE,
                    "Ha ocurrido un error al obtener los items de menu por vendedor", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<ItemMenuDTO> crearItemMenu(@RequestBody ItemMenuDTO itemMenuDTO){
        ItemMenuDTO nuevoItemMenu = itemMenuService.saveItemMenu(itemMenuDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoItemMenu);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItemMenu(@PathVariable("id") Integer id){
         try{
             itemMenuService.deleteById(id);
             return ResponseEntity.status(HttpStatus.OK).build();
         }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping
    public ResponseEntity<ItemMenuDTO> updateItemMenu(@RequestBody ItemMenuDTO itemMenuDTO){
        try{
            ItemMenuDTO nuevoItemMenu = itemMenuService.updateItemMenu(itemMenuDTO);
            return ResponseEntity.status(HttpStatus.OK).body(nuevoItemMenu);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }
    
    
    
    
}
