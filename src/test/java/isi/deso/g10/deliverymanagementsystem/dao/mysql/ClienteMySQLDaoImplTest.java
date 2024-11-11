package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import org.junit.jupiter.api.*;

import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteMySQLDaoImplTest {

    private static ClienteMySQLDaoImpl clienteDao;

    @BeforeAll
    public static void setUp() {
        clienteDao = ClienteMySQLDaoImpl.getInstance();
        System.out.println("===== Inicio de pruebas para ClienteMySQLDaoImpl =====");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("===== Fin de pruebas para ClienteMySQLDaoImpl =====");
    }


    @BeforeEach
    public void cleanUp() {
        // Clean up the database before each test
        clienteDao.eliminarTodos();
    }

    @Test
    @Order(1)
    public void testCrearCliente() {
        Cliente cliente = new Cliente(-1, "Juan R", "Calle Falsa 123", "123456789", "juan@example.com", new Coordenada(1, 1));
        Cliente creado = clienteDao.crear(cliente);
        assertNotNull(creado, "No se ha podido crear el cliente");
        System.out.println("Creación exitosa");
    }

    @Test
    @Order(2)
    public void testActualizarCliente() {
        Cliente cliente = new Cliente(-1, "Juan R", "Calle Falsa 123", "123456789", "juan@example.com", new Coordenada(1, 1));
        Cliente creado = clienteDao.crear(cliente);
        assertNotNull(creado, "No se ha podido crear el cliente para actualizar");

        // new Time() para siempre poder verificar el cambio de nombre en la base de datos
        creado.setNombre("Juan Ramírez " + new Time(System.currentTimeMillis()));
        Cliente actualizado = clienteDao.actualizar(creado);
        assertNotNull(actualizado, "No se ha podido actualizar el cliente");
        assertEquals(creado.getNombre(), actualizado.getNombre(), "El nombre del cliente no se actualizó correctamente");
        System.out.println("Actualización exitosa");
    }

    @Test
    @Order(3)
    public void testEliminarCliente() {
        Cliente cliente = new Cliente(-1, "Juan R", "Calle Falsa 123", "123456789", "juan@example.com", new Coordenada(1, 1));
        Cliente creado = clienteDao.crear(cliente);
        assertNotNull(creado, "No se ha podido crear el cliente para eliminar");

        boolean eliminado = clienteDao.eliminar(creado.getId());
        assertTrue(eliminado, "No se ha podido eliminar el cliente");
        System.out.println("Eliminación exitosa");
    }

    @Test
    @Order(4)
    public void testObtenerClientePorId() {
        Cliente cliente = new Cliente(-1, "Juan R", "Calle Falsa 123", "123456789", "juan@example.com", new Coordenada(1, 1));
        Cliente creado = clienteDao.crear(cliente);
        assertNotNull(creado, "No se ha podido crear el cliente a obtener");

        Cliente clienteObtenido = clienteDao.obtenerPorId(creado.getId());
        assertNotNull(clienteObtenido, "No se ha encontrado el cliente");
        assertEquals(creado.getId(), clienteObtenido.getId(), "El ID del cliente obtenido no coincide");
        System.out.println("Obtención exitosa");
        System.out.println("Cliente encontrado:");
        System.out.println(clienteObtenido.getId() + "  |  " + clienteObtenido.getNombre() + "  |  " + clienteObtenido.getDireccion());
    }

    @Test
    @Order(5)
    public void testObtenerTodosLosClientes() {
        Cliente cliente1 = new Cliente(-1, "Juan R", "Calle Falsa 123", "123456789", "juan@example.com", new Coordenada(1, 1));
        Cliente cliente2 = new Cliente(-1, "Pedro P", "Calle Verdadera 456", "987654321", "pedro@example.com", new Coordenada(2, 2));
        clienteDao.crear(cliente1);
        clienteDao.crear(cliente2);

        ArrayList<Cliente> clientesObtenidos = (ArrayList<Cliente>) clienteDao.obtenerTodos();
        assertFalse(clientesObtenidos.isEmpty(), "No se han encontrado clientes");
        assertEquals(2, clientesObtenidos.size(), "El número de clientes obtenidos no es correcto");
        System.out.println("Obtención exitosa");
        System.out.println("Clientes encontrados:");
        clientesObtenidos.forEach(c -> System.out.println(c.getId() + "  |  " + c.getNombre() + "  |  " + c.getDireccion()));
    }
}