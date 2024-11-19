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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping("/{id}")
      public ResponseEntity<Cliente> getCliente(@PathVariable(value = "id") Integer id) {
          Optional<Cliente> cliente = clienteService.getById(id);
          if (cliente.isPresent()) {
              return ResponseEntity.ok(cliente.get());
          } else {
              return ResponseEntity.notFound().build();
          }
      }
    
    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes(){
        List<Cliente> clientes= clienteService.getAll();
        return ResponseEntity.ok(clientes);
    }
    
      
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente nuevoCliente = clienteService.saveCliente(clienteDTO);
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
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody ClienteDTO clienteDTO){
        try{
            Cliente nuevoCliente = clienteService.updateCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.OK).body(nuevoCliente);
        }catch(RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
