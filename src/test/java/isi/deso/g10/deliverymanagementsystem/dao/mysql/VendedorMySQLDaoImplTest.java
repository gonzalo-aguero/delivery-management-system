package isi.deso.g10.deliverymanagementsystem.dao.mysql;

import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import org.junit.jupiter.api.*;

import java.sql.Time;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/*
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VendedorMySQLDaoImplTest {

    private static VendedorMySQLDaoImpl vendedorDao;

    @BeforeAll
    public static void setUp() {
        vendedorDao = VendedorMySQLDaoImpl.getInstance();
        System.out.println("===== Inicio de pruebas para VendedorMySQLDaoImpl =====");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("===== Fin de pruebas para VendedorMySQLDaoImpl =====");
    }

    @BeforeEach
    public void cleanUp() {
        // Clean up the database before each test
        vendedorDao.eliminarTodos();
    }

    @Test
    @Order(1)
    public void testCrearVendedor() {
        Vendedor vendedor = new Vendedor(-1, "Juan R", "Calle Falsa 123", new Coordenada(1, 1));
        Vendedor creado = vendedorDao.crear(vendedor);
        assertNotNull(creado, "No se ha podido crear el vendedor");
        System.out.println("Creación exitosa");
    }

    @Test
    @Order(2)
    public void testActualizarVendedor() {
        Vendedor vendedor = new Vendedor(-1, "Juan R", "Calle Falsa 123", new Coordenada(1, 1));
        Vendedor creado = vendedorDao.crear(vendedor);
        assertNotNull(creado, "No se ha podido crear el vendedor para actualizar");

        // new Time() para siempre poder verificar el cambio de nombre en la base de datos
        creado.setNombre("Juan Ramírez " + new Time(System.currentTimeMillis()));
        Vendedor actualizado = vendedorDao.actualizar(creado);
        assertNotNull(actualizado, "No se ha podido actualizar el vendedor");
        assertEquals(creado.getNombre(), actualizado.getNombre(), "El nombre del vendedor no se actualizó correctamente");
        System.out.println("Actualización exitosa");
    }

    @Test
    @Order(3)
    public void testEliminarVendedor() {
        Vendedor vendedor = new Vendedor(-1, "Juan R", "Calle Falsa 123", new Coordenada(1, 1));
        Vendedor creado = vendedorDao.crear(vendedor);
        assertNotNull(creado, "No se ha podido crear el vendedor para eliminar");

        boolean eliminado = vendedorDao.eliminar(creado.getId());
        assertTrue(eliminado, "No se ha podido eliminar el vendedor");
        System.out.println("Eliminación exitosa");
    }

    @Test
    @Order(4)
    public void testObtenerVendedorPorId() {
        Vendedor vendedor = new Vendedor(-1, "Juan R", "Calle Falsa 123", new Coordenada(1, 1));
        Vendedor creado = vendedorDao.crear(vendedor);
        assertNotNull(creado, "No se ha podido crear el vendedor a obtener");

        Vendedor vendedorObtenido = vendedorDao.obtenerPorId(creado.getId());
        assertNotNull(vendedorObtenido, "No se ha encontrado el vendedor");
        assertEquals(creado.getId(), vendedorObtenido.getId(), "El ID del vendedor obtenido no coincide");
        System.out.println("Obtención exitosa");
        System.out.println("Vendedor encontrado:");
        System.out.println(vendedorObtenido.getId() + "  |  " + vendedorObtenido.getNombre() + "  |  " + vendedorObtenido.getDireccion());
    }

    @Test
    @Order(5)
    public void testObtenerTodosLosVendedores() {
        Vendedor vendedor1 = new Vendedor(-1, "Juan R", "Calle Falsa 123", new Coordenada(1, 1));
        Vendedor vendedor2 = new Vendedor(-1, "Pedro P", "Calle Verdadera 456", new Coordenada(2, 2));
        vendedorDao.crear(vendedor1);
        vendedorDao.crear(vendedor2);

        ArrayList<Vendedor> vendedoresObtenidos = (ArrayList<Vendedor>) vendedorDao.obtenerTodos();
        assertFalse(vendedoresObtenidos.isEmpty(), "No se han encontrado vendedores");
        assertEquals(2, vendedoresObtenidos.size(), "El número de vendedores obtenidos no es correcto");
        System.out.println("Obtención exitosa");
        System.out.println("Vendedores encontrados:");
        vendedoresObtenidos.forEach(v -> System.out.println(v.getId() + "  |  " + v.getNombre() + "  |  " + v.getDireccion()));
    }
}*/