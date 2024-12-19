/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.dto.ClienteDTO;
import isi.deso.g10.deliverymanagementsystem.service.ClienteService;
import java.util.List;

import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author giuli
 */
@RestController
@CrossOrigin
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping("/{id}")
      public ResponseEntity<ClienteDTO> getCliente(@PathVariable(value = "id") Integer id) {
        try{
              ClienteDTO cliente = clienteService.getById(id);
              return ResponseEntity.ok(cliente);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
      }
    
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getClientes(){
        try{
        List<ClienteDTO> clientes = clienteService.getAll();
        return ResponseEntity.ok(clientes);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
             return ResponseEntity.internalServerError().build();
        }
    }
    
      
    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO nuevoCliente = clienteService.saveCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable("id") Integer id) {
        
            try{
                clienteService.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).build();
            }catch(RuntimeException ex){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }
    
    @PutMapping
    public ResponseEntity<ClienteDTO> updateCliente(@RequestBody ClienteDTO clienteDTO){
        try{
            ClienteDTO nuevoCliente = clienteService.updateCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.OK).body(nuevoCliente);
        }catch(NotFoundException ex) {
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }
}
