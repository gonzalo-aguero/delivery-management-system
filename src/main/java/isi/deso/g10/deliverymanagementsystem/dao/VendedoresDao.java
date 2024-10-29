/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.HashSet;

/**
 *
 * @author giuli
 */
public interface VendedoresDao {

    public HashSet<Vendedor> getVendedores();
}
