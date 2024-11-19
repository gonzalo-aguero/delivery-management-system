package isi.deso.g10.deliverymanagementsystem.repository;

import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {

}