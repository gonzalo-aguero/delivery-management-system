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
@RequestMapping("/pedido")
public class PedidoController {
    
    @Autowired
    PedidoService pedidoService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedido(@PathVariable(value = "id") Integer id){
        Optional<Pedido> pedido = pedidoService.getById(id);
        if (pedido.isPresent()) {
              return ResponseEntity.ok(pedido.get());
          } else {
              return ResponseEntity.notFound().build();
          }
    }
    
    @GetMapping
    public ResponseEntity<List<Pedido>> getPedidos(){
        List<Pedido> pedidos = pedidoService.getAll();
        return ResponseEntity.ok(pedidos);
    }
    
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoDTO pedidoDTO){
        Pedido nuevoPedido = pedidoService.savePedido(pedidoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItemMenu(@PathVariable("id") Integer id){
         try{
             pedidoService.deleteById(id);
             return ResponseEntity.status(HttpStatus.OK).build();
         }catch(RuntimeException ex){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Pedido> updateItemMenu(@RequestBody PedidoDTO pedidoDTO){
        try{
            Pedido pedido = pedidoService.updatePedido(pedidoDTO);
            return ResponseEntity.status(HttpStatus.OK).body(pedido);
        }catch(RuntimeException ex){
            return ResponseEntity.notFound().build();
        }
    }
    

}
