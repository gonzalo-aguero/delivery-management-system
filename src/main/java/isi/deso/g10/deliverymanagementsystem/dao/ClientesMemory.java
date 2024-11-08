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
import java.util.Iterator;

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
    
    private int lastId(){
        return clientes.getLast().getId();
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return clientes;
    }
    
    @Override
    public List<Cliente> obtenerClientesPorNombre(String cadena) {
        List<Cliente> resultados = new ArrayList<>();
        
        if (cadena == null || cadena.isEmpty()) {
            return resultados;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getNombre().toLowerCase().contains(cadena.toLowerCase())) {
                resultados.add(cliente);
            }
        }
        
        return resultados;
    }

    @Override
    public Cliente crear(Cliente cliente) {
        int id = lastId()+1;
        cliente.setId(id);
        clientes.add(cliente);
        return cliente;
    }

    @Override
    public Cliente actualizar(Cliente cliente) {
          Cliente cli = this.obtenerPorId(cliente.getId());
          cli.setNombre(cliente.getNombre());
          cli.setCuit(cliente.getCuit());
          cli.setEmail(cliente.getEmail());
          cli.setDireccion(cliente.getDireccion());
          cli.setCoordenadas(cliente.getCoordenadas()); 
       return cli;
    }

    @Override
    public boolean eliminar(int id) {
        boolean ret = false;
        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente cli = iterator.next();
            if (cli.getId() == id) {
                iterator.remove();
                ret = true;
                break;  // Salimos del bucle si se encuentra y elimina el cliente
            }
        }
        return ret;
    }
    @Override
    public Cliente obtenerPorId(int id) {
        int index = -1;
         for(Cliente cli : clientes){
             if(cli.getId()==id){
                 index = clientes.indexOf(cli);
             }
         }
         return clientes.get(index);
    }
}
