/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import java.util.ArrayList;
import java.util.List;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ClienteDao;

/**
 *
 * @author giuli
 */
public class ClientesMemory implements ClienteDao {

    private static ClientesMemory self;
    private ArrayList<Cliente> clientes;
    
    public ClientesMemory(){
        generarClientes();
        self=this;
    };
    
    public static ClientesMemory getInstance(){
    if(self == null){
        
        self = new ClientesMemory();
    }
    return self;
}
    
    private void generarClientes() {
        clientes = new ArrayList<>(); 
        // Crear 5 instancias de Cliente
        clientes.add(new Cliente(1, "20-12345678-9", "Cliente Uno", "clienteuno@example.com", "Direccion Uno", new Coordenada(40.7128, -74.0060)));
        clientes.add(new Cliente(2, "20-23456789-0", "Cliente Dos", "clientedos@example.com", "Direccion Dos", new Coordenada(34.0522, -118.2437)));
        clientes.add(new Cliente(3, "20-34567890-1", "Cliente Tres", "clientetres@example.com", "Direccion Tres", new Coordenada(51.5074, -0.1278)));
        clientes.add(new Cliente(4, "20-45678901-2", "Cliente Cuatro", "clientecuatro@example.com", "Direccion Cuatro", new Coordenada(48.8566, 2.3522)));
        clientes.add(new Cliente(5, "20-56789012-3", "Cliente Cinco", "clientecinco@example.com", "Direccion Cinco", new Coordenada(35.6895, 139.6917)));
    }

    @Override
    public List<Cliente> obtenerClientes() {
        return clientes;
    }

    @Override
    public Cliente agregarCliente(Cliente cliente) {
        clientes.add(cliente);
        return cliente;
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
          int index = clientes.indexOf(cliente);
          clientes.add(index, cliente);
       return cliente;
    }

    @Override
    public boolean eliminarCliente(int id) {
        boolean ret = false;
        for(Cliente cli : clientes){
            if(cli.getId() == id){
                clientes.remove(cli);
                ret = true;
            }
        }
        return ret;
    }

    @Override
    public Cliente buscarClientePorId(int id) {
        int index = -1;
         for(Cliente cli : clientes){
             if(cli.getId()==id){
                 index = clientes.indexOf(cli);
             }
         }
         return clientes.get(index);
    }
}
