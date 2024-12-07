package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.exception.VendedorNotFoundException;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.model.dto.VendedorDTO;
import isi.deso.g10.deliverymanagementsystem.service.VendedorService;
import java.util.List;
import java.util.Optional;
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
 * Controlador para manejar las operaciones relacionadas con Vendedor.
 */
@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/{id}")
    public ResponseEntity<VendedorDTO> getVendedor(@PathVariable(value = "id") Integer id) {
        try{
            VendedorDTO vendedor = vendedorService.getById(id);
            return ResponseEntity.ok(vendedor);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<VendedorDTO>> getVendedores() {
        try{
            List<VendedorDTO> vendedores = vendedorService.getAll();
            return ResponseEntity.ok(vendedores);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<VendedorDTO> createVendedor(@RequestBody VendedorDTO vendedorDTO) {
        VendedorDTO nuevoVendedor = vendedorService.saveVendedor(vendedorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVendedor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable("id") Integer id) {
        try{
            vendedorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VendedorDTO> updateVendedor(@RequestBody VendedorDTO vendedorDTO) {
        try {
            VendedorDTO nuevoVendedor = vendedorService.updateVendedor(vendedorDTO);
            return ResponseEntity.status(HttpStatus.OK).body(nuevoVendedor);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }catch(RuntimeException e){
              return ResponseEntity.internalServerError().build();
        }
    }
}