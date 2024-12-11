/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.model.dto.CategoriaDTO;
import isi.deso.g10.deliverymanagementsystem.service.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author giuli
 */
@RestController
@CrossOrigin
@RequestMapping("/categoria")
public class CategoriaController {
    
    
    @Autowired
    CategoriaService categoriaService;
    
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getCategorias(){
        try{
        List<CategoriaDTO> categorias = categoriaService.getAll();
        return ResponseEntity.ok(categorias);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
             return ResponseEntity.internalServerError().build();
        }
    }
    
}
