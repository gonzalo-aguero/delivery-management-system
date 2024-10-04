/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.builder.BebidaBuilder;
import isi.deso.g10.deliverymanagementsystem.builder.PlatoBuilder;
import isi.deso.g10.deliverymanagementsystem.exception.ItemNoEncontradoException;
import isi.deso.g10.deliverymanagementsystem.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

/**
 *
 * @author gonzalo90fa
 */
public class ItemsPedidoMemoryTest {

    private ArrayList<Vendedor> vendedores;
    private Plato milanesaPollo, pizzaMuzza, papasFritas, sandwichMilanesa, ensaladaCesar, picadaClasica, empanadaCarne, empanadaJamonQueso, hamburguesaDoble, pancho, pizzaEspecial, sandwichVegano, tablaQuesos, pizzaVegana;
    private Categoria minutas, picadas, pizzas, sandwiches, bebidaAlcoholica, bebidaNoAlcoholica;
    private Bebida cocaCola, fernetBranca, cervezaArtesanal, aguaMineral, vinoTinto, sprite;
    private Vendedor vendedor1;
    private ArrayList<ItemMenu> items;
    private Pedido pedido;

    public ItemsPedidoMemory itemsMemory = new ItemsPedidoMemory();

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

//        //CREACIÓN DE COMIDAS Y BEBIDAS
        milanesaPollo = new Plato(200, 1, "Milanesa de Pollo", "Milanesa de Pollo", 4500, minutas, 400, false, false, false);
        pizzaMuzza = new Plato(350, 2, "Pizza de Muzza", "Pizza con queso mozzarella", 3000, pizzas, 800, false, false, false);
        papasFritas = new Plato(250, 3, "Papas Fritas", "Papas fritas crocantes", 1500, minutas, 600, false, true, true);
        cocaCola = new Bebida(0, 500, 4, "Coca Cola", "Gaseosa Coca Cola 500ml", 500, bebidaNoAlcoholica, 200, true, true, true);
        fernetBranca = new Bebida(14, 750, 5, "Fernet Branca", "Fernet Branca 750ml", 3500, bebidaAlcoholica, 500, true, true, true);
        sandwichMilanesa = new Plato(300, 6, "Sandwich de Milanesa", "Sandwich de milanesa completo", 2500, sandwiches, 600, false, false, false);
        ensaladaCesar = new Plato(180, 7, "Ensalada Caesar", "Ensalada con pollo y crutones", 2200, minutas, 350, false, true, false);
        picadaClasica = new Plato(400, 8, "Picada Clásica", "Picada con jamón, queso, aceitunas", 3200, picadas, 900, false, true, false);
        cervezaArtesanal = new Bebida(5, 500, 9, "Cerveza Artesanal", "Cerveza IPA 500ml", 800, bebidaAlcoholica, 250, true, true, true);
        empanadaCarne = new Plato(120, 10, "Empanada de Carne", "Empanada de carne cortada a cuchillo", 200, minutas, 350, false, false, false);
        empanadaJamonQueso = new Plato(120, 11, "Empanada de Jamón y Queso", "Empanada con jamón y queso", 200, minutas, 400, false, false, false);
        aguaMineral = new Bebida(0, 500, 12, "Agua Mineral", "Agua mineral sin gas 500ml", 300, bebidaNoAlcoholica, 0, true, true, true);
        hamburguesaDoble = new Plato(300, 13, "Hamburguesa Doble", "Hamburguesa doble con queso", 2800, minutas, 700, false, false, false);
        pancho = new Plato(150, 14, "Pancho", "Pancho con aderezos a elección", 600, minutas, 450, false, false, false);
        pizzaEspecial = new Plato(400, 15, "Pizza Especial", "Pizza con jamón, morrones y huevo", 3500, pizzas, 900, false, false, false);
        vinoTinto = new Bebida(12, 750, 16, "Vino Tinto Malbec", "Botella de vino tinto Malbec 750ml", 2500, bebidaAlcoholica, 400, true, true, true);
        sandwichVegano = new Plato(200, 17, "Sandwich Vegano", "Sandwich con vegetales y hummus", 1800, sandwiches, 300, true, true, true);
        sprite = new Bebida(0, 500, 18, "Sprite", "Gaseosa Sprite 500ml", 500, bebidaNoAlcoholica, 210, true, true, true);
        tablaQuesos = new Plato(350, 19, "Tabla de Quesos", "Tabla con varios tipos de quesos", 4000, picadas, 750, false, true, false);
        pizzaVegana = new Plato(350, 20, "Pizza Vegana", "Pizza con queso vegano y vegetales", 3200, pizzas, 700, true, true, true);
        
        
        milanesaPollo = new PlatoBuilder().setId(1).setNombre("Milanesa de Pollo").setDescripcion("Milanesa de Pollo").setPrecio(4500).setCategoria(minutas).setCalorias(400).setAptoCeliaco(false).setAptoVegetariano(false).setAptoVegano(false).setPeso(200).build();
        pizzaMuzza = new PlatoBuilder().setId(2).setNombre("Pizza de Muzza").setDescripcion("Pizza con queso mozzarella").setPrecio(3000).setCategoria(pizzas).setCalorias(800).setAptoCeliaco(false).setAptoVegetariano(false).setAptoVegano(false).setPeso(350).build();
        papasFritas = new PlatoBuilder().setId(3).setNombre("Papas Fritas").setDescripcion("Papas fritas crocantes").setPrecio(1500).setCategoria(minutas).setCalorias(600).setAptoCeliaco(false).setAptoVegetariano(true).setAptoVegano(true).setPeso(250).build();
        cocaCola = new BebidaBuilder().setId(4).setNombre("Coca Cola").setDescripcion("Gaseosa Coca Cola 500ml").setPrecio(500).setCategoria(bebidaNoAlcoholica).setCalorias(200).setAptoCeliaco(true).setAptoVegetariano(true).setAptoVegano(true).setGraduacionAlcoholica(0).setVolumenEnMl(500).build();
        fernetBranca = new BebidaBuilder().setId(5).setNombre("Fernet Branca").setDescripcion("Fernet Branca 750ml").setPrecio(3500).setCategoria(bebidaAlcoholica).setCalorias(500).setAptoCeliaco(true).setAptoVegetariano(true).setAptoVegano(true).setGraduacionAlcoholica(14).setVolumenEnMl(750).build();
        sandwichMilanesa = new PlatoBuilder().setId(6).setNombre("Sandwich de Milanesa").setDescripcion("Sandwich de milanesa completo").setPrecio(2500).setCategoria(sandwiches).setCalorias(600).setAptoCeliaco(false).setAptoVegetariano(false).setAptoVegano(false).setPeso(300).build();
        ensaladaCesar = new PlatoBuilder().setId(7).setNombre("Ensalada Caesar").setDescripcion("Ensalada con pollo y crutones").setPrecio(2200).setCategoria(minutas).setCalorias(350).setAptoCeliaco(false).setAptoVegetariano(true).setAptoVegano(false).setPeso(180).build();
        picadaClasica = new PlatoBuilder().setId(8).setNombre("Picada Clásica").setDescripcion("Picada con jamón, queso, aceitunas").setPrecio(3200).setCategoria(picadas).setCalorias(900).setAptoCeliaco(false).setAptoVegetariano(true).setAptoVegano(false).setPeso(400).build();
        cervezaArtesanal = new BebidaBuilder().setId(9).setNombre("Cerveza Artesanal").setDescripcion("Cerveza IPA 500ml").setPrecio(800).setCategoria(bebidaAlcoholica).setCalorias(250).setAptoCeliaco(true).setAptoVegetariano(true).setAptoVegano(true).setGraduacionAlcoholica(5).setVolumenEnMl(500).build();
        empanadaCarne = new PlatoBuilder().setId(10).setNombre("Empanada de Carne").setDescripcion("Empanada de carne cortada a cuchillo").setPrecio(200).setCategoria(minutas).setCalorias(350).setAptoCeliaco(false).setAptoVegetariano(false).setAptoVegano(false).setPeso(120).build();
        empanadaJamonQueso = new PlatoBuilder().setId(11).setNombre("Empanada de Jamón y Queso").setDescripcion("Empanada con jamón y queso").setPrecio(200).setCategoria(minutas).setCalorias(400).setAptoCeliaco(false).setAptoVegetariano(false).setAptoVegano(false).setPeso(120).build();
        aguaMineral = new BebidaBuilder().setId(12).setNombre("Agua Mineral").setDescripcion("Agua mineral sin gas 500ml").setPrecio(300).setCategoria(bebidaNoAlcoholica).setCalorias(0).setAptoCeliaco(true).setAptoVegetariano(true).setAptoVegano(true).setGraduacionAlcoholica(0).setVolumenEnMl(500).build();
        hamburguesaDoble = new PlatoBuilder().setId(13).setNombre("Hamburguesa Doble").setDescripcion("Hamburguesa doble con queso").setPrecio(2800).setCategoria(minutas).setCalorias(700).setAptoCeliaco(false).setAptoVegetariano(false).setAptoVegano(false).setPeso(300).build();
        pancho = new PlatoBuilder().setId(14).setNombre("Pancho").setDescripcion("Pancho con aderezos a elección").setPrecio(600).setCategoria(minutas).setCalorias(450).setAptoCeliaco(false).setAptoVegetariano(false).setAptoVegano(false).setPeso(150).build();
        pizzaEspecial = new PlatoBuilder().setId(15).setNombre("Pizza Especial").setDescripcion("Pizza con jamón, morrones y huevo").setPrecio(3500).setCategoria(pizzas).setCalorias(900).setAptoCeliaco(false).setAptoVegetariano(false).setAptoVegano(false).setPeso(400).build();
        vinoTinto = new BebidaBuilder().setId(16).setNombre("Vino Tinto Malbec").setDescripcion("Botella de vino tinto Malbec 750ml").setPrecio(2500).setCategoria(bebidaAlcoholica).setCalorias(400).setAptoCeliaco(true).setAptoVegetariano(true).setAptoVegano(true).setGraduacionAlcoholica(12).setVolumenEnMl(750).build();
        sandwichVegano = new PlatoBuilder().setId(17).setNombre("Sandwich Vegano").setDescripcion("Sandwich con vegetales y hummus").setPrecio(1800).setCategoria(sandwiches).setCalorias(300).setAptoCeliaco(true).setAptoVegetariano(true).setAptoVegano(true).setPeso(200).build();
        sprite = new BebidaBuilder().setId(18).setNombre("Sprite").setDescripcion("Gaseosa Sprite 500ml").setPrecio(500).setCategoria(bebidaNoAlcoholica).setCalorias(210).setAptoCeliaco(true).setAptoVegetariano(true).setAptoVegano(true).setGraduacionAlcoholica(0).setVolumenEnMl(500).build();
        tablaQuesos = new PlatoBuilder().setId(19).setNombre("Tabla de Quesos").setDescripcion("Tabla con varios tipos de quesos").setPrecio(4000).setCategoria(picadas).setCalorias(750).setAptoCeliaco(false).setAptoVegetariano(true).setAptoVegano(false).setPeso(350).build();
        pizzaVegana = new PlatoBuilder().setId(20).setNombre("Pizza Vegana").setDescripcion("Pizza con queso vegano y vegetales").setPrecio(3200).setCategoria(pizzas).setCalorias(700).setAptoCeliaco(true).setAptoVegetariano(true).setAptoVegano(true).setPeso(350).build();

        // CREACIÓN DE VENDEDORES
        vendedor1 = new Vendedor(1, "MilfCoocker", "25 de mayo 3399", new Coordenada(13, 24));
        vendedor1.setMenu(new ArrayList<>(Arrays.asList(
                milanesaPollo, pizzaMuzza, papasFritas, cocaCola, fernetBranca, sandwichMilanesa, ensaladaCesar, picadaClasica,
                cervezaArtesanal, empanadaCarne, empanadaJamonQueso, aguaMineral, hamburguesaDoble, pancho, pizzaEspecial,
                vinoTinto, sandwichVegano, sprite, tablaQuesos)));

        // Creacion y agregado de los items del pedido
        this.items = new ArrayList<>();

        milanesaPollo.setVendedor(vendedor1);
        items.add(milanesaPollo);
        cervezaArtesanal.setVendedor(vendedor1);
        items.add(cervezaArtesanal);
        sandwichVegano.setVendedor(vendedor1);
        items.add(sandwichVegano);

        // Crear un pedido
        pedido = new Pedido();
        pedido.setCliente(new Cliente(1, "20-45341666-0", "Miguel Centurion", "mc@hot.com", "25 de mayo 3350", new Coordenada(10, 10)));

        // Inicializar ItemsPedido con los ítems y el pedido
        pedido.setDetallePedido(new DetallePedido(items));

        itemsMemory.agregarPedido(pedido);
    }

    @Test
    void buscarPorIdVendedor_DevuelveLosItemsPedidosDelVendedor_CuandoElVendedorExiste() throws Exception {
        // Action
        var ret = itemsMemory.buscarPorIdVendedor(1);

        // Assert
        assertEquals(ret.getFirst(), pedido);
    }

    @Test
    void buscarPorIdVendedor_LanzaItemNoEncontradoException_CuandoVendedorIdNoExisteONoTienePedidos() throws Exception {
        // Arrenge
        int idVendedor = 1000;

        // Assert
        assertThrows(ItemNoEncontradoException.class, () -> {
            itemsMemory.buscarPorIdVendedor(idVendedor);
        }, "ItemsPedidos de vendedor con id " + idVendedor + " no encontrados.");

    }

    @Test
    void buscarPorNombreVendedor_DevuelveLosItemsPedidosDelVendedor_CuandoNombrePerteneceAUnVendedorValido() throws ItemNoEncontradoException {
        // Action
        var ret = itemsMemory.buscarPorNombreVendedor(vendedor1.getNombre());

        // Assert
        assertEquals(ret.getFirst(), pedido);
    }

    @Test
    void buscarPorNombreVendedor_LanzaItemNoEncontradoException_CuandoNombreNoExiste() {
        // Arrenge
        var nombreVendedor = "VendedorNoExistente";

        // Assert
        assertThrows(ItemNoEncontradoException.class, () -> {
            itemsMemory.buscarPorNombreVendedor(nombreVendedor);
        });
    }

    @Test
    public void buscarPorNombreCliente_DevuelveLosItemsPedidosDelCliente_CuandoExisteCliente() throws Exception {
        // Action
        var itemsPedidos = itemsMemory.buscarPorNombreCliente("Miguel Centurion");

        // Asert
        assertEquals(itemsPedidos.getFirst(), pedido);
        assertEquals(itemsPedidos.getFirst().getCliente().getNombre(), "Miguel Centurion");
    }

    @Test
    public void buscarPorRangoMontoTotal_DevuelveItemsPedidos_CuandoExisteAlgunPedidoEnElRango() throws Exception {
        // Arrenge
        double minimo = 0;
        double maximo = pedido.getDetallePedido().calcularMontoTotal();

        // Action
        List<Pedido> res = itemsMemory.buscarPorRangoMontoTotal(minimo, maximo);
        assertEquals(res.getFirst(), pedido);
    }

    @Test
    public void buscarPorRangoMontoTotal_LanzaItemNoEncontradoException_CuandoNoExistePedidoEnElRango() throws Exception {
        // Arrenge
        double minimo = 0;
        double maximo = -1;

        // Action
        assertThrows(ItemNoEncontradoException.class, () -> {
            itemsMemory.buscarPorRangoMontoTotal(minimo, maximo);
        }, "No se encontraron ItemsPedidos.");
    }

}
