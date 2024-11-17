/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

import isi.deso.g10.deliverymanagementsystem.controller.swing.MenuController;
import isi.deso.g10.deliverymanagementsystem.utils.DatabaseInitializer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author giuli
 */
public class Etapa6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        PantallaPrincipal menu = new PantallaPrincipal();
        MenuController controller = new MenuController(menu);
        
    }
    
}
