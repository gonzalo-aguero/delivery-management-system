/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.dto.PedidoDTO;
import isi.deso.g10.deliverymanagementsystem.repository.ClienteRepository;
import isi.deso.g10.deliverymanagementsystem.repository.PedidoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author giuli
 */
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;
    
    @Autowired
    ClienteService clienteService;
    
    public PedidoDTO getById(Integer id) {
       Optional<Pedido> pedidoOPT = pedidoRepository.findById(id);
       if(pedidoOPT.isEmpty()){
           return null;
       }else{
           PedidoDTO pedidoDTO = new PedidoDTO();
           Pedido pedido = pedidoOPT.get();
           
           pedidoDTO.setCliente(clienteService.getById(pedido.getCliente().getId()));
           pedidoDTO.se     
       }
    }
    
    public List<Pedido> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Pedido savePedido(PedidoDTO pedidoDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Pedido updatePedido(PedidoDTO pedidoDTO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
