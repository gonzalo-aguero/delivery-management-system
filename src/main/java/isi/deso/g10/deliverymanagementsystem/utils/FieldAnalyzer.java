/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.utils;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JTextField;

/**
 *
 * @author giuli
 */
public class FieldAnalyzer {
    
    public static boolean todosLosCamposLlenos(Container container) throws RuntimeException {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField textField) {
                if (textField.getText().trim().isEmpty()) {
                    throw new RuntimeException("Complete todos los campos");
                }
            } else if (component instanceof Container) {
                // Si el componente es un contenedor, revisar sus componentes hijos de forma recursiva
                todosLosCamposLlenos((Container) component);
            }
        }
        return true;  // Todos los campos tienen contenido
    }
}
