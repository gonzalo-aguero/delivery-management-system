package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.exception.VendedorNotFoundException;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.dto.CoordenadaDTO;
import isi.deso.g10.deliverymanagementsystem.model.dto.VendedorDTO;
import isi.deso.g10.deliverymanagementsystem.repository.VendedorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

/**
 * Servicio para manejar las operaciones relacionadas con Vendedor.
 */
@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<VendedorDTO> getAll() throws NotFoundException {
         List<Vendedor> vendedores = vendedorRepository.findAll(); 
         if (vendedores.isEmpty()) {
                throw new NotFoundException();
            }

            return vendedores.stream()
                    .map(vendedor -> {
                        VendedorDTO vendedorDTO = new VendedorDTO();
                        vendedorDTO.setId(vendedor.getId());
                        vendedorDTO.setDireccion(vendedor.getDireccion());
                        vendedorDTO.setNombre(vendedor.getNombre());

                        if (vendedor.getCoordenadas() != null) {
                            CoordenadaDTO coordenadaDTO = new CoordenadaDTO();
                            coordenadaDTO.setLatitud(vendedor.getCoordenadas().getLatitud());
                            coordenadaDTO.setLongitud(vendedor.getCoordenadas().getLongitud());
                            vendedorDTO.setCoordenadas(coordenadaDTO);
                        }

                        return vendedorDTO;
                    })
                    .collect(Collectors.toList());
    }

    public VendedorDTO getById(int id) throws NotFoundException {
    return vendedorRepository.findById(id)
            .map(vendedor -> {
                VendedorDTO vendedorDTO = new VendedorDTO();
                vendedorDTO.setId(vendedor.getId());
                vendedorDTO.setDireccion(vendedor.getDireccion());
                vendedorDTO.setNombre(vendedor.getNombre());

            
                if (vendedor.getCoordenadas() != null) {
                    CoordenadaDTO coordenadaDTO = new CoordenadaDTO();
                    coordenadaDTO.setLatitud(vendedor.getCoordenadas().getLatitud());
                    coordenadaDTO.setLongitud(vendedor.getCoordenadas().getLongitud());
                    vendedorDTO.setCoordenadas(coordenadaDTO);
                }

                return vendedorDTO;
            })
            .orElseThrow(NotFoundException::new);
}

    public VendedorDTO saveVendedor(VendedorDTO vendedorDTO) {
        Coordenada coordenadas = new Coordenada(vendedorDTO.getCoordenadas().getLatitud(), vendedorDTO.getCoordenadas().getLongitud());
        Vendedor vendedor = new Vendedor();
        vendedor.setNombre(vendedorDTO.getNombre());
        vendedor.setDireccion(vendedorDTO.getDireccion());
        vendedor.setCoordenadas(coordenadas);
        coordenadas.setPersona(vendedor);
        try {
            vendedor = vendedorRepository.save(vendedor);
            vendedorDTO.setId(vendedor.getId());
            return vendedorDTO;
        } catch (RuntimeException ex) {
            throw new RuntimeException("No se pudo guardar el vendedor", ex);
        }
    }

    public void deleteById(Integer id) throws NotFoundException {
        if(!vendedorRepository.existsById(id)) {
            throw new NotFoundException();
        }
        vendedorRepository.deleteById(id);
    }

    public VendedorDTO updateVendedor(VendedorDTO vendedorDTO) throws NotFoundException {
        int id = vendedorDTO.getId();
        Optional<Vendedor> vendedorOpt = vendedorRepository.findById(id);
        if (vendedorOpt.isPresent()) {
            Vendedor vendedor = vendedorOpt.get();
            vendedor.setNombre(vendedorDTO.getNombre());
            vendedor.setDireccion(vendedorDTO.getDireccion());

            Coordenada coordenadas = vendedor.getCoordenadas();
            if (coordenadas == null) {
                coordenadas = new Coordenada();
            }
            coordenadas.setLatitud(vendedorDTO.getCoordenadas().getLatitud());
            coordenadas.setLongitud(vendedorDTO.getCoordenadas().getLongitud());
            coordenadas.setPersona(vendedor);
            vendedor.setCoordenadas(coordenadas);
    
            try {
                vendedor = vendedorRepository.save(vendedor);
                vendedorDTO.setId(vendedor.getId());
                return vendedorDTO;
            } catch (RuntimeException ex) {
                throw new RuntimeException("No se pudo guardar el vendedor", ex);
            }
        } else {
            throw new NotFoundException();
        }
    }
}