/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

/**
 *
 * @author lucas
 */
public class Categoria {
    public enum TipoItem {
        COMIDA, BEBIDA
    }

    public Categoria(int id, String descripcion, TipoItem tipoItem) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoItem = tipoItem;
    }

    private int id;
    private String descripcion;
    private TipoItem tipoItem;
}
