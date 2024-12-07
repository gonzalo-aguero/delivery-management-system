/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller.springboot;


import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.dto.ItemMenuDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.PedidoDTO;
import isi.deso.g10.deliverymanagementsystem.service.PedidoService;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
@RequestMapping("/pedido")
public class PedidoController {
    
    @Autowired
    PedidoService pedidoService;
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedido(@PathVariable(value = "id") Integer id){
        
        try{
            PedidoDTO pedido = pedidoService.getById(id);
            return ResponseEntity.ok(pedido);
          }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
          }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
          }
    }
    
    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getPedidos(){
        try{
        List<PedidoDTO> pedidos = pedidoService.getAll();
        return ResponseEntity.ok(pedidos);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedidoDTO){
        try {
            PedidoDTO nuevoPedido = pedidoService.savePedido(pedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable("id") Integer id){
         try{
             pedidoService.deleteById(id);
             return ResponseEntity.status(HttpStatus.OK).build();
         }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@RequestBody PedidoDTO pedidoDTO){
        try{
            PedidoDTO pedido = pedidoService.updatePedido(pedidoDTO);
            return ResponseEntity.status(HttpStatus.OK).body(pedido);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }
    

}
