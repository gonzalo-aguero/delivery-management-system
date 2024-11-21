package isi.deso.g10.deliverymanagementsystem.controller.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "CRUD de Pedidos", description = "Operaciones relacionadas con los Pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Ver la lista de pedidos", description = "Obtiene una lista de todos los pedidos disponibles")
    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @Operation(summary = "Obtener un pedido por Id", description = "Obtiene un pedido por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(
            @Parameter(description = "ID del pedido a recuperar", required = true) @PathVariable int id) {
        Pedido pedido = pedidoService.getPedidoById(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido")
    @PostMapping
    public Pedido createPedido(
            @Parameter(description = "Objeto Pedido a crear", required = true) @RequestBody Pedido pedido) {
        return pedidoService.createPedido(pedido);
    }

    @Operation(summary = "Actualizar un pedido existente", description = "Actualiza un pedido existente")
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(
            @Parameter(description = "ID del pedido a actualizar", required = true) @PathVariable int id,
            @Parameter(description = "Objeto pedido actualizado", required = true) @RequestBody Pedido pedido) {
        Pedido updatedPedido = pedidoService.updatePedido(id, pedido);
        if (updatedPedido != null) {
            return ResponseEntity.ok(updatedPedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(
            @Parameter(description = "ID del pedido a eliminar", required = true) @PathVariable int id) {
        boolean isDeleted = pedidoService.deletePedido(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

