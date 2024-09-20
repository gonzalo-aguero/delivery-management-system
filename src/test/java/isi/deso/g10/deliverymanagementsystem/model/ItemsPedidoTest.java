/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import isi.deso.g10.deliverymanagementsystem.builder.BebidaBuilder;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gonzalo90fa
 */
public class ItemsPedidoTest {

    private ItemsPedido itemsPedido;
    
    public ItemsPedidoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        // Crear algunos items de ejemplo
        ArrayList<ItemMenu> items = new ArrayList<>();
        
        // Creamos items y los agregamos
        items.add(new BebidaBuilder().setPrecio(35).build()); 
        items.add(new BebidaBuilder().setPrecio(70).build()); 
        items.add(new BebidaBuilder().setPrecio(35).build());
        
        // Crear un pedido
        Pedido pedido = new Pedido();
        
        // Inicializar ItemsPedido con los ítems y el pedido
        itemsPedido = new ItemsPedido(items, pedido);

    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of calcularMontoTotal method, of class ItemsPedido.
     */
    @Test
    public void testCalcularMontoTotal() {
        double expectedTotal = 140;
        
        double actualTotal = itemsPedido.calcularMontoTotal();
        
        assertEquals(expectedTotal, actualTotal, "El monto total calculado no es el correcto.");
    }

}
