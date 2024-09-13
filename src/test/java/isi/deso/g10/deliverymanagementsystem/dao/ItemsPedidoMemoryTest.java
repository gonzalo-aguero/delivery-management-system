/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Comida;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author gonzalo90fa
 */
public class ItemsPedidoMemoryTest {

    private ArrayList<Vendedor> vendedores;
    private Comida milanesaPollo, pizzaMuzza, papasFritas, sandwichMilanesa, ensaladaCesar, picadaClasica, empanadaCarne, empanadaJamonQueso, hamburguesaDoble, pancho, pizzaEspecial, sandwichVegano, tablaQuesos, pizzaVegana;
    private Categoria minutas, picadas, pizzas, sandwiches, bebidaAlcoholica, bebidaNoAlcoholica;
    private Bebida cocaCola, fernetBranca, cervezaArtesanal, aguaMineral, vinoTinto, sprite;

    public ItemsPedidoMemory items = new ItemsPedidoMemory();

    public ItemsPedidoMemoryTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {

        System.out.println("* UtilsJUnit5Test: setUp() method");

        //CREACIÓN DE CATEGORÍAS
        minutas = new Categoria(1, "minuta", Categoria.TipoItem.COMIDA);
        picadas = new Categoria(2, "picadas", Categoria.TipoItem.COMIDA);
        pizzas = new Categoria(3, "pizzas", Categoria.TipoItem.COMIDA);
        sandwiches = new Categoria(4, "sandwiches", Categoria.TipoItem.COMIDA);
        bebidaAlcoholica = new Categoria(5, "bebidaAlcoholica", Categoria.TipoItem.BEBIDA);
        bebidaNoAlcoholica = new Categoria(6, "bebidaNoAlcoholica", Categoria.TipoItem.BEBIDA);

        //CREACIÓN DE COMIDAS Y BEBIDAS
        milanesaPollo = new Comida(200, 1, "Milanesa de Pollo", "Milanesa de Pollo", 4500, minutas, 400, false, false, false);
        pizzaMuzza = new Comida(350, 2, "Pizza de Muzza", "Pizza con queso mozzarella", 3000, pizzas, 800, false, false, false);
        papasFritas = new Comida(250, 3, "Papas Fritas", "Papas fritas crocantes", 1500, minutas, 600, false, true, true);
        cocaCola = new Bebida(0, 500, 4, "Coca Cola", "Gaseosa Coca Cola 500ml", 500, bebidaNoAlcoholica, 200, true, true, true);
        fernetBranca = new Bebida(14, 750, 5, "Fernet Branca", "Fernet Branca 750ml", 3500, bebidaAlcoholica, 500, true, true, true);
        sandwichMilanesa = new Comida(300, 6, "Sandwich de Milanesa", "Sandwich de milanesa completo", 2500, sandwiches, 600, false, false, false);
        ensaladaCesar = new Comida(180, 7, "Ensalada Caesar", "Ensalada con pollo y crutones", 2200, minutas, 350, false, true, false);
        picadaClasica = new Comida(400, 8, "Picada Clásica", "Picada con jamón, queso, aceitunas", 3200, picadas, 900, false, true, false);
        cervezaArtesanal = new Bebida(5, 500, 9, "Cerveza Artesanal", "Cerveza IPA 500ml", 800, bebidaAlcoholica, 250, true, true, true);
        empanadaCarne = new Comida(120, 10, "Empanada de Carne", "Empanada de carne cortada a cuchillo", 200, minutas, 350, false, false, false);
        empanadaJamonQueso = new Comida(120, 11, "Empanada de Jamón y Queso", "Empanada con jamón y queso", 200, minutas, 400, false, false, false);
        aguaMineral = new Bebida(0, 500, 12, "Agua Mineral", "Agua mineral sin gas 500ml", 300, bebidaNoAlcoholica, 0, true, true, true);
        hamburguesaDoble = new Comida(300, 13, "Hamburguesa Doble", "Hamburguesa doble con queso", 2800, minutas, 700, false, false, false);
        pancho = new Comida(150, 14, "Pancho", "Pancho con aderezos a elección", 600, minutas, 450, false, false, false);
        pizzaEspecial = new Comida(400, 15, "Pizza Especial", "Pizza con jamón, morrones y huevo", 3500, pizzas, 900, false, false, false);
        vinoTinto = new Bebida(12, 750, 16, "Vino Tinto Malbec", "Botella de vino tinto Malbec 750ml", 2500, bebidaAlcoholica, 400, true, true, true);
        sandwichVegano = new Comida(200, 17, "Sandwich Vegano", "Sandwich con vegetales y hummus", 1800, sandwiches, 300, true, true, true);
        sprite = new Bebida(0, 500, 18, "Sprite", "Gaseosa Sprite 500ml", 500, bebidaNoAlcoholica, 210, true, true, true);
        tablaQuesos = new Comida(350, 19, "Tabla de Quesos", "Tabla con varios tipos de quesos", 4000, picadas, 750, false, true, false);
        pizzaVegana = new Comida(350, 20, "Pizza Vegana", "Pizza con queso vegano y vegetales", 3200, pizzas, 700, true, true, true);

        // CREACIÓN DE VENDEDORES
        vendedores = new ArrayList<Vendedor>();
        vendedores.add(new Vendedor(1, "MilfCoocker", "25 de mayo 3399", new Coordenada(13, 24)));
        vendedores.get(0).setMenu(new ArrayList<>(Arrays.asList(
                milanesaPollo, pizzaMuzza, papasFritas, cocaCola, fernetBranca, sandwichMilanesa, ensaladaCesar, picadaClasica,
                cervezaArtesanal, empanadaCarne, empanadaJamonQueso, aguaMineral, hamburguesaDoble, pancho, pizzaEspecial,
                vinoTinto, sandwichVegano, sprite, tablaQuesos)));
        vendedores.add(new Vendedor(2, "Restaurante Comer Sano", "9 de Julio 3399", new Coordenada(50, 100)));
        vendedores.get(1).setMenu(new ArrayList<>(Arrays.asList(ensaladaCesar, aguaMineral, vinoTinto, sandwichVegano)));

    }

    @AfterEach
    public void tearDown() {
        System.out.println("* UtilsJUnit5Test: tearDown() method");
    }

    /**
     * Tests of buscarId method, of class ItemsPedidoMemory.
     */
    @Test
    void buscarId_DevuelveItemCorrecto_CuandoIdPerteneceAUnVendedor() throws Exception {
        System.out.println("buscarId (Devuelve Item)");
        var ret = items.buscarId(3, vendedores);
        assertEquals(papasFritas, ret);
        assertEquals(papasFritas.getId(), ret.getId());
    }

    @Test
    void buscarId_DevuelveError_CuandoIdNoExiste() throws Exception {
        System.out.println("buscarId (ID inexistente)");
        // Verifica que se lanza la excepción "ItemNoEncontradoException"
        assertThrows(ItemNoEncontradoException.class, () -> {
            items.buscarId(1000, vendedores); // Ejecuta la operación que debería lanzar la excepción
        }, "Se esperaba que se lanzara ItemNoEncontradoException para el ID 20");
    }

    /**
     * Tests of buscarNombre method, of class ItemsPedidoMemory.
     */
    @Test
    void buscarNombre_DevuelveItemCorrecto_CuandoNombrePerteneceAUnVendedorValido() throws ItemNoEncontradoException {
        System.out.println("buscarNombre (Devuelve Item)");
        var ret = items.buscarNombre("Milanesa de Pollo", vendedores);
        assertEquals(milanesaPollo, ret);
        assertEquals("Milanesa de Pollo", ret.getNombre());
    }

    @Test
    void buscarNombre_DevuelveError_CuandoNombreNoExiste() {
        System.out.println("buscarNombre (Nombre inexistente)");
        assertThrows(ItemNoEncontradoException.class, () -> {
            items.buscarNombre("Pizza con Ananá", vendedores);
        });
    }

    /**
     * Test of buscarPorRangoPrecio method, of class ItemsPedidoMemory.
     */
    @Disabled("Test pendiente de implementación")
    @Test
    public void testBuscarPorRangoPrecio() throws Exception {
        System.out.println("buscarPorRangoPrecio");
        double minimo = 0.0;
        double maximo = 0.0;
        List<Vendedor> Vendedores = null;
        ItemsPedidoMemory instance = new ItemsPedidoMemory();
        List<ItemMenu> expResult = null;
        List<ItemMenu> result = instance.buscarPorRangoPrecio(minimo, maximo, Vendedores);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarPorCategoria method, of class ItemsPedidoMemory.
     */
    @Disabled("Test pendiente de implementación")
    @Test
    public void testBuscarPorCategoria() throws Exception {
        System.out.println("buscarPorCategoria");
        Categoria categoria = null;
        List<Vendedor> Vendedores = null;
        ItemsPedidoMemory instance = new ItemsPedidoMemory();
        List<ItemMenu> expResult = null;
        List<ItemMenu> result = instance.buscarPorCategoria(categoria, Vendedores);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarComidas method, of class ItemsPedidoMemory.
     */
    @Disabled("Test pendiente de implementación")
    @Test
    public void testBuscarComidas() throws Exception {
        System.out.println("buscarComidas");
        List<Vendedor> Vendedores = null;
        ItemsPedidoMemory instance = new ItemsPedidoMemory();
        List<Comida> expResult = null;
        List<Comida> result = instance.buscarComidas(Vendedores);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarBebidas method, of class ItemsPedidoMemory.
     */
    @Disabled("Test pendiente de implementación")
    @Test
    public void testBuscarBebidas() throws Exception {
        System.out.println("buscarBebidas");
        List<Vendedor> Vendedores = null;
        ItemsPedidoMemory instance = new ItemsPedidoMemory();
        List<Bebida> expResult = null;
        List<Bebida> result = instance.buscarBebidas(Vendedores);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buscarPorRestaurante method, of class ItemsPedidoMemory.
     */
    @Disabled("Test pendiente de implementación")
    @Test
    public void testBuscarPorRestaurante() throws Exception {
        System.out.println("buscarPorRestaurante");
        Vendedor vendedor = null;
        ItemsPedidoMemory instance = new ItemsPedidoMemory();
        List<ItemMenu> expResult = null;
        List<ItemMenu> result = instance.buscarPorRestaurante(vendedor);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
