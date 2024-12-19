/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.repository;

import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author giuli
 */
public interface PedidoRepository extends JpaRepository<Pedido,Integer> {
    
}
