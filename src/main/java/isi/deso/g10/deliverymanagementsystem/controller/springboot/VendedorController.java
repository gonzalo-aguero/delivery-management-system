package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import isi.deso.g10.deliverymanagementsystem.exception.VendedorNotFoundException;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.model.dto.VendedorDTO;
import isi.deso.g10.deliverymanagementsystem.service.VendedorService;
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
 * Controlador para manejar las operaciones relacionadas con Vendedor.
 */
@RestController
@RequestMapping("/vendedor")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> getVendedor(@PathVariable(value = "id") Integer id) {
        Optional<Vendedor> vendedor = vendedorService.getById(id);
        if (vendedor.isPresent()) {
            return ResponseEntity.ok(vendedor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Vendedor>> getVendedores() {
        List<Vendedor> vendedores = vendedorService.getAll();
        return ResponseEntity.ok(vendedores);
    }

    @PostMapping
    public ResponseEntity<Vendedor> createVendedor(@RequestBody VendedorDTO vendedorDTO) {
        Vendedor nuevoVendedor = vendedorService.saveVendedor(vendedorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoVendedor);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteVendedor(@PathVariable("id") Integer id) {
        try{
            vendedorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (VendedorNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Vendedor> updateVendedor(@RequestBody VendedorDTO vendedorDTO) {
        try {
            Vendedor nuevoVendedor = vendedorService.updateVendedor(vendedorDTO);
            return ResponseEntity.status(HttpStatus.OK).body(nuevoVendedor);
        } catch (VendedorNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}