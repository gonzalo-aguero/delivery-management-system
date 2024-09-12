/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.Dao;

import isi.deso.g10.deliverymanagementsystem.Classes.Categoria;
import isi.deso.g10.deliverymanagementsystem.Classes.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.Classes.Vendedor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author giuli
 */
public class ItemsPedidoMemory implements ItemsPedidoDao {

    
    private File itemsFile;
    private File categoriasFile;
    private List<Categoria> categorias;
    private List<ItemMenu> items;
    
    public ItemsPedidoMemory(String itemsDir,String categoriasDir) throws IOException{
        categoriasFile= new File(categoriasDir);
        
        try(BufferedReader br= new BufferedReader(new FileReader(categoriasFile))){
              //Toma una linea 
            String line;
              while ((line = br.readLine()) != null) {
                // Separar los valores de la línea por coma
                String[] atributos = line.split(",");
                // Procesar los valores
                Categoria categoria = new Categoria(Integer.parseInt(atributos[0]),atributos[1],atributos[2]);
                
            }
        }
        catch(IOException e){
            
        }
      
        itemsFile= new File(itemsDir);
        
        try(BufferedReader br= new BufferedReader(new FileReader(itemsFile))){
            //Toma una linea 
            String line;
              while ((line = br.readLine()) != null) {
                // Separar los valores de la línea por coma
                String[] values = line.split(",");
                // Procesar los valores
                for (String value : values) {
                    
                }
            }
        }
        catch(IOException e) {
             
        }
    }
    
    
    @Override
    public ItemMenu buscarNombre(String nombre) {
       
    }

    @Override
    public ItemMenu buscarId(int id) {
       
    }

    @Override
    public ItemMenu buscarPorRangoPrecio(double minimo, double maximo) {
        
    }

    @Override
    public ItemMenu buscarPorCategoria(Categoria categoria) {
        
    }

    @Override
    public ItemMenu buscarComidas(Categoria categoria) {
        
    }

    @Override
    public ItemMenu buscarBebidas(Categoria categoria) {
        
    }

    @Override
    public ItemMenu buscarPorRestaurante(Vendedor vendedor) {
        
    }
    
}
