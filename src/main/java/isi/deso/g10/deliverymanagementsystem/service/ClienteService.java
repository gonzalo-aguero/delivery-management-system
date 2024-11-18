/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.dto.ClienteDTO;
import isi.deso.g10.deliverymanagementsystem.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author giuli
 */
@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;
    
    public List<Cliente> getAll() {
       return clienteRepository.findAll();
    }

    public Optional<Cliente> getById(int id) {
        return clienteRepository.findById(id);  
    }

    public Cliente saveCliente(ClienteDTO clienteDTO) {
        Coordenada coordenadas = new Coordenada(clienteDTO.getCoordenadas().getLatitud(),clienteDTO.getCoordenadas().getLongitud());
        Cliente cliente = new Cliente(clienteDTO.getCuit(),
                clienteDTO.getNombre(),
                clienteDTO.getEmail(),
                clienteDTO.getDireccion(),
                coordenadas);
        coordenadas.setPersona(cliente);
        try{
            cliente = clienteRepository.save(cliente);
            return cliente;
        }catch(RuntimeException ex){
            throw new RuntimeException("No se pudo guardar el cliente", ex);
        }
    }
    
}
