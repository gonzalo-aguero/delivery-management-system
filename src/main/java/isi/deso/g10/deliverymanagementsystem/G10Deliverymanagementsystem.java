/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package isi.deso.g10.deliverymanagementsystem;

import isi.deso.g10.deliverymanagementsystem.model.*;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author gonzalo-aguero
 */
public class G10Deliverymanagementsystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Coordenadas para Vendedores
        Coordenada coordenada1 = new Coordenada(3.43, 4.43);
        Coordenada coordenada2 = new Coordenada(1.23, 3.223);

        // Vendedores
        Vendedor vendedor1 = new Vendedor(1, "Juan", "Azcuenaga 3000", coordenada1);
        Vendedor vendedor2 = new Vendedor(2, "Manuel", "San Martin 610", coordenada2);

        // Array de Vendedores
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        vendedores.add(vendedor1);
        vendedores.add(vendedor2);

        // Crear coordenadas para clientes
        Coordenada coordenada4 = new Coordenada(4.56, 7.89);
        Coordenada coordenada5 = new Coordenada(2.34, 5.67);
        Coordenada coordenada6 = new Coordenada(7.89, 1.23);

        // Crear instancias de Cliente
        Cliente cliente1 = new Cliente(1, "20-12345678-9", "Jorge", "cliente1@example.com", "Calle Falsa 123", coordenada4);

        // Crear array de clientes
        Cliente[] clientes = new Cliente[3];
        clientes[0] = cliente1;

        // Etapa 4
        Categoria minutas = new Categoria(1, "minuta", Categoria.TipoItem.COMIDA);
        Categoria bebida = new Categoria(1, "bebida", Categoria.TipoItem.BEBIDA);

        Plato milanesaPollo = new Plato(200, 1, "Milanesa de Pollo", "Milanesa de Pollo", 4500, minutas, 400, false, false, false);
        Plato pizzaMuzza = new Plato(350, 2, "Pizza de Muzza", "Pizza con queso mozzarella", 3000, minutas, 800, false, false, false);
        Plato papasFritas = new Plato(250, 3, "Papas Fritas", "Papas fritas crocantes", 1500, minutas, 600, false, true, true);
        Bebida cocaCola = new Bebida(0, 500, 4, "Coca Cola", "Gaseosa Coca Cola 500ml", 500, bebida, 200, true, true, true);

        ArrayList<ItemMenu> menuVendedor1 = new ArrayList<ItemMenu>();
        ArrayList<ItemMenu> menuVendedor2 = new ArrayList<ItemMenu>();

        menuVendedor1.add(milanesaPollo);
        menuVendedor1.add(pizzaMuzza);
        menuVendedor2.add(papasFritas);
        menuVendedor2.add(cocaCola);


        vendedor1.setMenu(menuVendedor1);
        vendedor2.setMenu(menuVendedor2);

        // Cadena de ejecucion

        System.out.println("Seleccionar Vendedor");
        System.out.println("1. "+vendedor1.getNombre());
        System.out.println("2. "+vendedor2.getNombre());
        Integer numVendedor = Integer.parseInt(scanner.nextLine())-1;
        for(ItemMenu item :  vendedores.get(numVendedor).getMenu()) {
            System.out.println(item.getNombre());
        }

        String resp = scanner.nextLine();
        String[] partes = resp.split(",");
        ArrayList<ItemMenu> itemsPedidos = new ArrayList<ItemMenu>();
        for (String parte : partes) {
            itemsPedidos.add(vendedores.get(numVendedor).getMenu().get(Integer.parseInt(parte)-1));
        }
        Pedido pedido = new Pedido(itemsPedidos, cliente1);

        System.out.println(pedido.getItemsPedido().getFirst().getNombre());

        System.out.println("Seleccione Forma de pago");
        // mostrar formas de pago
        String formaDePago = scanner.nextLine();
        pedido.setFormaDePago(formaDePago);
    }

    private static Vendedor buscarVendedor(Vendedor[] vendedores, int idVendedor) {
        Vendedor vendedor = null;
        int i = 0;
        while (vendedor == null && i < vendedores.length) {
            if (vendedores[i].getId() == idVendedor) {
                vendedor = vendedores[i];
            }
            i++;
        }
        return vendedor;
    }

    private static Vendedor buscarVendedor(Vendedor[] vendedores, String nombreVendedor) {
        Vendedor vendedor = null;
        int i = 0;
        while (vendedor == null && i < vendedores.length) {
            if (vendedores[i].getNombre().equals(nombreVendedor)) {
                vendedor = vendedores[i];
            }
            i++;
        }
        return vendedor;
    }

    private static Vendedor[] eliminarVendedor(Vendedor[] vendedores, int idVendedor) {
        if (vendedores == null || vendedores.length == 0) {
            return vendedores;
        }

        //Contar cantidad vendedores a eliminar
        int count = 0;
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getId() == idVendedor) {
                count++;
            }
        }

        //Vendedor no presente en array
        if (count == 0) {
            return vendedores;
        }

        //Vendedor presente en el array una o mas veces
        Vendedor[] newVendedores = new Vendedor[vendedores.length - count];
        int j = 0;
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getId() != idVendedor) {
                newVendedores[j] = vendedor;
                j++;
            }
        }

        return newVendedores;
    }

    private static void mostrarVendedores(Vendedor[] vendedores) {
        for (Vendedor vendedor : vendedores) {
            System.out.println("ID: " + vendedor.getId()
                    + ", Nombre: " + vendedor.getNombre()
                    + ", Dirección: " + vendedor.getDireccion()
                    + ", Coordenadas: (" + vendedor.getCoordenadas().getLatitud() + ", "
                    + vendedor.getCoordenadas().getLongitud() + ")");
        }
    }

    private static Cliente buscarCliente(Cliente[] clientes, int idCliente) {
        Cliente cliente = null;
        int i = 0;
        while (cliente == null && i < clientes.length) {
            if (clientes[i].getId() == idCliente) {
                cliente = clientes[i];
            }
            i++;
        }
        return cliente;
    }

    private static Cliente buscarCliente(Cliente[] clientes, String nombreCliente) {
        Cliente cliente = null;
        int i = 0;
        while (cliente == null && i < clientes.length) {
            if (clientes[i].getNombre().equals(nombreCliente)) {
                cliente = clientes[i];
            }
            i++;
        }
        return cliente;
    }

    private static void mostrarClientes(Cliente[] clientes) {
        for (Cliente cliente : clientes) {
            System.out.println("ID: " + cliente.getId()
                    + ", CUIT: " + cliente.getCuit()
                    + ", Nombre: " + cliente.getNombre()
                    + ", Email: " + cliente.getEmail()
                    + ", Dirección: " + cliente.getDireccion()
                    + ", Coordenadas: (" + cliente.getCoordenadas().getLatitud() + ", "
                    + cliente.getCoordenadas().getLongitud() + ")");
        }
    }

    private static Cliente[] eliminarCliente(Cliente[] clientes, int idCliente) {

        if (clientes == null || clientes.length == 0) {
            return clientes;
        }

        //Contar cantidad vendedores a eliminar
        int count = 0;
        for (Cliente cliente : clientes) {
            if (cliente.getId() == idCliente) {
                count++;
            }
        }

        //Vendedor no presente en array
        if (count == 0) {
            return clientes;
        }

        //Vendedor presente en el array una o mas veces
        Cliente[] newClientes = new Cliente[clientes.length - count];
        int j = 0;
        for (Cliente cliente : clientes) {
            if (cliente.getId() != idCliente) {
                newClientes[j] = cliente;
                j++;
            }
        }

        return newClientes;
    }

    
    /**
    * Este método realiza una serie de pruebas sobre los arreglos de vendedores y clientes y muestra los resultados por la terminal.
    * <p>Las pruebas realizadas incluyen:</p>
    * <ul>
    *   <li>Búsqueda de un vendedor por su ID.</li>
    *   <li>Búsqueda de un vendedor por su nombre.</li>
    *   <li>Eliminación de un vendedor por su ID.</li>
    *   <li>Búsqueda de un cliente por su ID.</li>
    *   <li>Búsqueda de un cliente por su nombre.</li>
    *   <li>Eliminación de un cliente por su ID.</li>
    *   <li>Cálculo de la distancia entre un vendedor y un cliente específicos.</li>
    * </ul>
    *
    * @param vendedores un arreglo de objetos {@code Vendedor} sobre los que se realizarán las pruebas.
    * @param clientes un arreglo de objetos {@code Cliente} sobre los que se realizarán las pruebas.
    */
    private static void testearMetodos(Vendedor[] vendedores, Cliente[] clientes) {
        System.out.println("\n==================== Realización de pruebas Etapa 1 ====================");
        System.out.println("A continuación se detallan los resultados luego de ejecutar los métodos correspondientes a cada prueba.");
        
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

        //Prueba Eliminación de Cliente
        System.out.println("\nPrueba de eliminación de Cliente:");
        Cliente[] clientesActualizados = eliminarCliente(clientes, 2);
        mostrarClientes(clientesActualizados);
        
        //Prueba Calculo de la distancia
        System.out.println("\nPrueba de cálculo de la distancia:");
        Vendedor vendedor2 = vendedores[1];
        Cliente cliente3 = clientes[2];
        System.out.println("Distancia Vendedor 2 ("+ vendedor2.getNombre() +") y Cliente 3 ("+ cliente3.getNombre() +"): " + vendedor2.distancia(cliente3));
    }
}
