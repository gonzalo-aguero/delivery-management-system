/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.dto.ClienteDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.CoordenadaDTO;
import isi.deso.g10.deliverymanagementsystem.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
    
        public List<ClienteDTO> getAll() throws NotFoundException {
          List<Cliente> clientes = clienteRepository.findAll();
          if (clientes.isEmpty()) {
              throw new NotFoundException();
          }

          return clientes.stream()
                  .map(cliente -> {
                      ClienteDTO clienteDTO = new ClienteDTO();
                      clienteDTO.setId(cliente.getId());
                      clienteDTO.setDireccion(cliente.getDireccion());
                      clienteDTO.setNombre(cliente.getNombre());
                      clienteDTO.setCuit(cliente.getCuit());
                      clienteDTO.setEmail(cliente.getEmail());

               
                      if (cliente.getCoordenadas() != null) {
                          CoordenadaDTO coordenadaDTO = new CoordenadaDTO();
                          coordenadaDTO.setLatitud(cliente.getCoordenadas().getLatitud());
                          coordenadaDTO.setLongitud(cliente.getCoordenadas().getLongitud());
                          clienteDTO.setCoordenadas(coordenadaDTO);
                      }

                      return clienteDTO;
                  })
                  .collect(Collectors.toList());
      }

    public ClienteDTO getById(int id) throws NotFoundException {
        return clienteRepository.findById(id)
            .map(cliente -> {
                ClienteDTO clienteDTO = new ClienteDTO();
                clienteDTO.setId(cliente.getId());
                clienteDTO.setDireccion(cliente.getDireccion());
                clienteDTO.setNombre(cliente.getNombre());
                clienteDTO.setCuit(cliente.getCuit());
                clienteDTO.setEmail(cliente.getEmail());
                
               
                if (cliente.getCoordenadas() != null) {
                    CoordenadaDTO coordenadaDTO = new CoordenadaDTO();
                    coordenadaDTO.setLatitud(cliente.getCoordenadas().getLatitud());
                    coordenadaDTO.setLongitud(cliente.getCoordenadas().getLongitud());
                    clienteDTO.setCoordenadas(coordenadaDTO);
                }

                return clienteDTO;
            })
            .orElseThrow(NotFoundException::new);
    }

    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        Coordenada coordenadas = new Coordenada(clienteDTO.getCoordenadas().getLatitud(),clienteDTO.getCoordenadas().getLongitud());
        Cliente cliente = new Cliente();
        cliente.setCuit(clienteDTO.getCuit()); 
        cliente.setNombre(clienteDTO.getNombre());      
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setCoordenadas(coordenadas);
        coordenadas.setPersona(cliente);
        try{
            cliente = clienteRepository.save(cliente);
            clienteDTO.setId(cliente.getId());
            return clienteDTO;
        }catch(RuntimeException ex){
            throw new RuntimeException("No se pudo guardar el cliente", ex);
        }
    }

    public void deleteById(Integer id) {
       clienteRepository.deleteById(id);
    }

    public ClienteDTO updateCliente(ClienteDTO clienteDTO) throws NotFoundException {
        int id = clienteDTO.getId();
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if(clienteOpt.isPresent()){
            Cliente cliente = clienteOpt.get();
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setCuit(clienteDTO.getCuit());
            cliente.setDireccion(clienteDTO.getDireccion());
            cliente.setEmail(clienteDTO.getEmail());
           
            Coordenada coordenadas = cliente.getCoordenadas();
            if(coordenadas == null){
                coordenadas = new Coordenada();
            }
            coordenadas.setLatitud(clienteDTO.getCoordenadas().getLatitud());
            coordenadas.setLongitud(clienteDTO.getCoordenadas().getLongitud());            
            coordenadas.setPersona(cliente);
            
            try{
                cliente = clienteRepository.save(cliente);
                clienteDTO.setId(cliente.getId());
                return clienteDTO;
            }catch(RuntimeException ex){
                throw new RuntimeException("No se pudo guardar el cliente", ex);
            }
        }else{
            throw new NotFoundException();
        }
    }
    
}
