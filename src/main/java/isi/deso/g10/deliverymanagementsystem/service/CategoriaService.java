/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.dto.CategoriaDTO;
import isi.deso.g10.deliverymanagementsystem.repository.CategoriaRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuli
 */
@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;
    
    public List<CategoriaDTO> getAll() throws NotFoundException {
       List<Categoria> categorias = categoriaRepository.findAll();
          if (categorias.isEmpty()) {
              throw new NotFoundException();
          }

          return categorias.stream()
                  .map(categoria -> {
                      CategoriaDTO categoriaDTO = new CategoriaDTO(categoria);
                      return categoriaDTO;
                  })
                  .collect(Collectors.toList());
    }
    
}
