package isi.deso.g10.deliverymanagementsystem.Dao;

import isi.deso.g10.deliverymanagementsystem.Classes.Categoria;
import isi.deso.g10.deliverymanagementsystem.Classes.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.Classes.Vendedor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ItemsPedidoMemory implements ItemsPedidoDao {

    /*
    private File itemsFile;
    private File categoriasFile;
    private List<Categoria> categorias;
    private List<ItemMenu> items;
    */
    /*
    public ItemsPedidoMemory(String itemsDir, String categoriasDir) throws IOException{
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
    */
    @Override
    public ItemMenu buscarNombre(String nombre, List<ItemMenu> items) throws ItemNoEncontradoException{
    return items.stream()
                .filter(item -> item.getNombre().equals(nombre))
                .findFirst()
                .orElseThrow(() -> new ItemNoEncontradoException("Item con nombre " + nombre + " no encontrado"));
    }

    @Override
    public ItemMenu buscarId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ItemMenu buscarPorRangoPrecio(double minimo, double maximo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ItemMenu buscarPorCategoria(Categoria categoria) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ItemMenu buscarComidas(Categoria categoria) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ItemMenu buscarBebidas(Categoria categoria) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ItemMenu buscarPorRestaurante(Vendedor vendedor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
