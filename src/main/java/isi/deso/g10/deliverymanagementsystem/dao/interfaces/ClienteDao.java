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
public interface ClienteDao {

    public List<Cliente> obtenerClientes();

    public Cliente agregarCliente(Cliente cliente);

    public Cliente actualizarCliente(Cliente cliente);

    public boolean eliminarCliente(int id);

    public Cliente buscarClientePorId(int id);

}
