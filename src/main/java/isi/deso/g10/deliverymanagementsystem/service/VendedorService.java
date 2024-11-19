package isi.deso.g10.deliverymanagementsystem.service;

import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.exception.VendedorNotFoundException;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.dto.VendedorDTO;
import isi.deso.g10.deliverymanagementsystem.repository.VendedorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para manejar las operaciones relacionadas con Vendedor.
 */
@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> getAll() {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> getById(int id) {
        return vendedorRepository.findById(id);
    }

    public Vendedor saveVendedor(VendedorDTO vendedorDTO) {
        Coordenada coordenadas = new Coordenada(vendedorDTO.getCoordenadas().getLatitud(), vendedorDTO.getCoordenadas().getLongitud());
        Vendedor vendedor = new Vendedor(vendedorDTO.getNombre(), vendedorDTO.getDireccion(), coordenadas);
        coordenadas.setPersona(vendedor);
        try {
            vendedor = vendedorRepository.save(vendedor);
            return vendedor;
        } catch (RuntimeException ex) {
            throw new RuntimeException("No se pudo guardar el vendedor", ex);
        }
    }

    public void deleteById(Integer id) {
        if(!vendedorRepository.existsById(id)) {
            throw new VendedorNotFoundException("No se encontró el vendedor con id " + id);
        }
        vendedorRepository.deleteById(id);
    }

    public Vendedor updateVendedor(VendedorDTO vendedorDTO) {
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
                return vendedor;
            } catch (RuntimeException ex) {
                throw new RuntimeException("No se pudo guardar el vendedor", ex);
            }
        } else {
            throw new VendedorNotFoundException("No se encontró el vendedor con id " + id);
        }
    }
}