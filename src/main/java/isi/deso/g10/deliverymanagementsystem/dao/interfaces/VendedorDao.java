/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.List;

/**
 *
 * @author giuli
 */
public interface VendedorDao {

    List<Vendedor> obtenerVendedores();

    Vendedor agregarVendedor(Vendedor vendedor);

    Vendedor actualizarVendedor(Vendedor vendedor);

    boolean eliminarVendedor(int id);

    Vendedor buscarVendedorPorId(int id);

}
