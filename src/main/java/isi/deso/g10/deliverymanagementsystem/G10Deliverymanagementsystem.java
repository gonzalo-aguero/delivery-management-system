/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package isi.deso.g10.deliverymanagementsystem;

/**
 *
 * @author gonzalo-aguero
 */
public class G10Deliverymanagementsystem {

    public static void main(String[] args) {
        
        Coordenada coordenada1= new Coordenada(3.43,4.43);
        Coordenada coordenada2= new Coordenada(1.23,3.223);
        Coordenada coordenada3= new Coordenada(1.654,6.888);
        
        Vendedor vendedor1= new Vendedor(1,"Juan","Azcuenaga 3000",coordenada1);
        Vendedor vendedor2= new Vendedor(2,"Manuel","San Martin 610",coordenada2);
        Vendedor vendedor3= new Vendedor(3,"Ian","Rivadavia 2032",coordenada3);
        
        Vendedor[] vendedores = new Vendedor[3];
        
        vendedores[0]=vendedor1;
        vendedores[1]=vendedor2;
        vendedores[2]=vendedor3;

         // Crear coordenadas para clientes
        Coordenada coordenada4 = new Coordenada(4.56, 7.89);
        Coordenada coordenada5 = new Coordenada(2.34, 5.67);
        Coordenada coordenada6 = new Coordenada(7.89, 1.23);
        
        // Crear instancias de Cliente
        Cliente cliente1 = new Cliente(1, "20-12345678-9", "Jorge", "cliente1@example.com", "Calle Falsa 123", coordenada4);
        Cliente cliente2 = new Cliente(2, "20-87654321-0", "Maria", "cliente2@example.com", "Avenida Siempre Viva 742", coordenada5);
        Cliente cliente3 = new Cliente(3, "20-13579246-1", "Paul", "cliente3@example.com", "Boulevard de los Sueños Rotos 10", coordenada6);

        // Crear array de clientes
        Cliente[] clientes = new Cliente[3];
        clientes[0] = cliente1;
        clientes[1] = cliente2;
        clientes[2] = cliente3;

        // Mostrar vendedores
        System.out.println("\nVendedores:");
        mostrarVendedores(vendedores);
        
        // Mostrar clientes
        System.out.println("Clientes:");
        mostrarClientes(clientes);
        
        
         // Ejecutar pruebas
        //testearMetodos(vendedores, clientes);
    }
    
    private static Vendedor buscarVendedor(Vendedor[] vendedores, int idVendedor){
        Vendedor vendedor=null;
        int i = 0;
        while(vendedor==null && i < vendedores.length ){
            if(vendedores[i].getId()==idVendedor){
                vendedor= vendedores[i];
            }
            i++;
        }
        return vendedor;
    }
    
    private static Vendedor buscarVendedor(Vendedor[] vendedores, String nombreVendedor){
        Vendedor vendedor=null;
        int i = 0;
        while(vendedor==null && i < vendedores.length ){
            if(vendedores[i].getNombre().equals(nombreVendedor)){
                vendedor= vendedores[i];
            }
            i++;
        }
        return vendedor;
    }
    
    private static Vendedor[] eliminarVendedor(Vendedor[] vendedores, int idVendedor){ 
        if(vendedores == null || vendedores.length == 0) return vendedores;
        
        //Contar cantidad vendedores a eliminar
        int count = 0;
        for (int i = 0; i < vendedores.length; i++) {
            if(vendedores[i].getId()== idVendedor) count++;
        }
        
        //Vendedor no presente en array
        if(count == 0) return vendedores;
        
        //Vendedor presente en el array una o mas veces
        Vendedor[] newVendedores = new Vendedor[vendedores.length - count];
        int j = 0;
        for (int i = 0; i < vendedores.length; i++) {
            if(vendedores[i].getId() != idVendedor){
                newVendedores[j] = vendedores[i];
                j++;
            }
        }
        
        return newVendedores;
    }
    
    private static void mostrarVendedores(Vendedor[] vendedores) {
        for (Vendedor vendedor : vendedores) {
            System.out.println("ID: " + vendedor.getId() +
                ", Nombre: " + vendedor.getNombre() +
                ", Dirección: " + vendedor.getDireccion() +
                ", Coordenadas: (" + vendedor.getCoordenadas().getLat()+ ", " +
                vendedor.getCoordenadas().getLng() + ")");
        }
    }
    
    
    private static Cliente buscarCliente(Cliente[] clientes, int idCliente){
        Cliente cliente = null;
        int i = 0;
        while(cliente == null && i < clientes.length){
            if(clientes[i].getId()== idCliente){
                cliente = clientes[i];
            }
            i++;
        }
        return cliente;
    }
    
    private static Cliente buscarCliente(Cliente[] clientes, String nombreCliente){
        Cliente cliente = null;
        int i = 0;
        while(cliente == null && i < clientes.length){
            if(clientes[i].getNombre().equals(nombreCliente)){
                cliente = clientes[i];
            }
            i++;
        }
        return cliente;
    }
    
    private static void mostrarClientes(Cliente[] clientes) {
        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getId() +
                ", CUIT: " + cliente.getCuit() +
                ", Email: " + cliente.getEmail() +
                ", Dirección: " + cliente.getDireccion() +
                ", Coordenadas: (" + cliente.getCoordenadas().getLat() + ", " +
                cliente.getCoordenadas().getLng() + ")");
        }
    }
    
    
    /**
     * AI-generated
     * @param vendedores
     * @param clientes 
     */
    private static void testearMetodos(Vendedor[] vendedores, Cliente[] clientes) {
        // Prueba de búsqueda de vendedor por ID
        System.out.println("\nPrueba de búsqueda de Vendedor por ID:");
        Vendedor vendedorEncontrado = buscarVendedor(vendedores, 2);
        if (vendedorEncontrado != null) {
            System.out.println("Vendedor encontrado: ID " + vendedorEncontrado.getId() + ", Nombre " + vendedorEncontrado.getNombre());
        } else {
            System.out.println("Vendedor con ID 2 no encontrado.");
        }

        // Prueba de búsqueda de vendedor por nombre
        System.out.println("\nPrueba de búsqueda de Vendedor por nombre:");
        vendedorEncontrado = buscarVendedor(vendedores, "Juan");
        if (vendedorEncontrado != null) {
            System.out.println("Vendedor encontrado: ID " + vendedorEncontrado.getId() + ", Nombre " + vendedorEncontrado.getNombre());
        } else {
            System.out.println("Vendedor con nombre Juan no encontrado.");
        }

        // Prueba de eliminación de vendedor
        System.out.println("\nPrueba de eliminación de Vendedor:");
        Vendedor[] vendedoresActualizados = eliminarVendedor(vendedores, 2);
        mostrarVendedores(vendedoresActualizados);

        // Prueba de búsqueda de cliente por ID
        System.out.println("\nPrueba de búsqueda de Cliente por ID:");
        Cliente clienteEncontrado = buscarCliente(clientes, 2);
        if (clienteEncontrado != null) {
            System.out.println("Cliente encontrado: ID " + clienteEncontrado.getId() + ", Nombre " + clienteEncontrado.getNombre());
        } else {
            System.out.println("Cliente con ID 2 no encontrado.");
        }

        // Prueba de búsqueda de cliente por nombre
        System.out.println("\nPrueba de búsqueda de Cliente por nombre:");
        clienteEncontrado = buscarCliente(clientes, "Maria");
        if (clienteEncontrado != null) {
            System.out.println("Cliente encontrado: ID " + clienteEncontrado.getId() + ", Nombre " + clienteEncontrado.getNombre());
        } else {
            System.out.println("Cliente con nombre Maria no encontrado.");
        }
    }
}
