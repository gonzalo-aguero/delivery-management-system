/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.ArrayList;

/**
 *
 * @author giuli
 */
public interface VendedorDao {

    public ArrayList<Vendedor> getVendedores();

    public Vendedor addVendedor(Vendedor vendedor);
}
