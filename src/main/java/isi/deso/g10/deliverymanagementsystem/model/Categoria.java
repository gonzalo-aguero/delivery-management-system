/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 *
 * @author lucas
 */
@Entity
@Table(name = "categoria")
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_item")
    private TipoItem tipoItem;
    
    @OneToMany(cascade = CascadeType.REFRESH, fetch= FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private List<ItemMenu> itemMenu;
    
    
    public enum TipoItem {
        COMIDA, BEBIDA
    }

    public Categoria(int id, String descripcion, TipoItem tipoItem) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoItem = tipoItem;
    }

    

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    
    
    
    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }
    
    @Override
    public String toString(){
        return descripcion;
    }
    
}
