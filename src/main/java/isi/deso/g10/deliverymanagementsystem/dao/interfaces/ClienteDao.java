/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao.interfaces;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import java.util.List;

/**
 *
 * @author giuli
 */
public interface ClienteDao extends GenericDao<Cliente>{

    // Métodos específicos de la entidad
    
    public List<Cliente> obtenerClientesPorNombre(String cadena);

}
