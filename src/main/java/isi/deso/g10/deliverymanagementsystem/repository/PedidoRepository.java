package isi.deso.g10.deliverymanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
    
}
