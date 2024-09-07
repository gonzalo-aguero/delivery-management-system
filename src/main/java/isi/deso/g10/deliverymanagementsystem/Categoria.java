/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem;

/**
 *
 * @author lucas
 */
public class Categoria {
    public enum TipoItem {
        COMIDA, BEBIDA
    }
    
    private int id;
    private String descripcion;
    private TipoItem tipoItem;
}
