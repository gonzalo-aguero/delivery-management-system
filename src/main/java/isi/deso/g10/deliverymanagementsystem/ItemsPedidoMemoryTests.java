package isi.deso.g10.deliverymanagementsystem;
import isi.deso.g10.deliverymanagementsystem.classes.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.classes.Coordenada;
import isi.deso.g10.deliverymanagementsystem.classes.Bebida;
import isi.deso.g10.deliverymanagementsystem.classes.Categoria;
import isi.deso.g10.deliverymanagementsystem.classes.Comida;
import isi.deso.g10.deliverymanagementsystem.classes.Vendedor;
import isi.deso.g10.deliverymanagementsystem.dao.ItemNoEncontradoException;
import isi.deso.g10.deliverymanagementsystem.dao.ItemsPedidoMemory;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author migue
 */
public class ItemsPedidoMemoryTests {
    private Vendedor vendedor1;
    private Comida milanesa, pizzaMuzza, papasFritas, sandwichMilanesa,ensaladaCaesar,picadaClasica,empanadaCarne,empanadaJamonQueso,hamburguesaDoble,pancho,pizzaEspecial,sandwichVegano,tablaQuesos,pizzaVegana;
    private Categoria minutas, picadas, pizzas, sandwiches, bebidaAlcoholica, bebidaNoAlcoholica;
    private Bebida cocaCola,fernetBranca,cervezaArtesanal,aguaMineral,vinoTinto,sprite;
    
    public ItemsPedidoMemory buscador = new ItemsPedidoMemory();
    
    @BeforeEach
    void setUp() {
        minutas = new Categoria(1, "minuta", Categoria.TipoItem.COMIDA);
        picadas = new Categoria(2, "picadas", Categoria.TipoItem.COMIDA);
        pizzas = new Categoria(3, "pizzas", Categoria.TipoItem.COMIDA);
        sandwiches = new Categoria(4, "sandwiches", Categoria.TipoItem.COMIDA);
        bebidaAlcoholica = new Categoria(5, "bebidaAlcoholica", Categoria.TipoItem.BEBIDA);
        bebidaNoAlcoholica = new Categoria(6, "bebidaNoAlcoholica", Categoria.TipoItem.BEBIDA);
        
        milanesa = new Comida(200, 1, "Milanesa de Pollo", "Milanesa de Pollo", 4500, minutas, 400, false, false, false);
        pizzaMuzza = new Comida(350, 2, "Pizza de Muzza", "Pizza con queso mozzarella", 3000, pizzas, 800, false, false, false);
        papasFritas = new Comida(250, 3, "Papas Fritas", "Papas fritas crocantes", 1500, minutas, 600, false, true, true);
        cocaCola = new Bebida(0, 500, 4, "Coca Cola", "Gaseosa Coca Cola 500ml", 500, bebidaNoAlcoholica, 200, true, true, true);
        fernetBranca = new Bebida(14, 750, 5, "Fernet Branca", "Fernet Branca 750ml", 3500, bebidaAlcoholica, 500, true, true, true);
        sandwichMilanesa = new Comida(300, 6, "Sandwich de Milanesa", "Sandwich de milanesa completo", 2500, sandwiches, 600, false, false, false);
        ensaladaCaesar = new Comida(180, 7, "Ensalada Caesar", "Ensalada con pollo y crutones", 2200, minutas, 350, false, true, false);
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

        // vendedor
        vendedor1 = new Vendedor(1, "MilfCoocker", "25 de mayo 3399", new Coordenada(13,24));
        vendedor1.setMenu(new ArrayList<ItemMenu>(Arrays.asList(
                milanesa, pizzaMuzza, papasFritas, cocaCola, fernetBranca, sandwichMilanesa, ensaladaCaesar, picadaClasica,
                cervezaArtesanal, empanadaCarne, empanadaJamonQueso, aguaMineral, hamburguesaDoble, pancho, pizzaEspecial, 
                vinoTinto, sandwichVegano,sprite,tablaQuesos)));

    }

    @Test
    void buscarNombre_DevuelveMilanesa_CuandoMilanesaPerteneceAUnVendedorValido() throws ItemNoEncontradoException {
        setUp();
        try{
            var ret = buscador.buscarNombre("Milanesa de Pollo", vendedor1.getMenu());
            System.out.println("Milanesa Se encontro correctamente");
            assertEquals(milanesa, ret);
            assertEquals("Milanesa de Pollo", ret.getNombre());
        } catch (ItemNoEncontradoException e){
            throw new ItemNoEncontradoException(e.getMessage());
        }
    }
    
    @Test
    void buscarNombre_DevuelveError_pizzaVeganaNoPerteneceAVendedorValido() throws ItemNoEncontradoException {
        setUp();
        try{
            var ret = buscador.buscarNombre("Pizza Vegana", vendedor1.getMenu());
            System.out.println("Pizza Vegana se encontro correctamente");
            assertEquals(pizzaVegana, ret);
            assertEquals("Pizza Vegana", ret.getNombre());
        } catch (ItemNoEncontradoException e){
            throw new ItemNoEncontradoException(e.getMessage());
        }
    }
    
    @Test
    void buscarId_DevuelvePapasFritas_CuandoId3PerteneceAUnVendedorValido() throws ItemNoEncontradoException {
        setUp();
        try{
            var ret = buscador.buscarId(3, vendedor1.getMenu());
            System.out.println("Item con ID 3 se encontro correctamente");
            assertEquals(papasFritas, ret);
            assertEquals(papasFritas.getId(), ret.getId());
        } catch (ItemNoEncontradoException e){
            throw new ItemNoEncontradoException(e.getMessage());
        }
    }
    
    @Test
    void buscarId_DevuelveError_Id20NoPerteneceAUnVendedorValido() throws ItemNoEncontradoException {
        setUp();
        try{
            var ret = buscador.buscarId(20, vendedor1.getMenu());
            System.out.println("Item con ID 20 se encontro correctamente");
            assertEquals(pizzaVegana, ret);
            assertEquals(pizzaVegana.getId(), ret.getId());
        } catch (ItemNoEncontradoException e){
            throw new ItemNoEncontradoException(e.getMessage());
        }
    }
}
