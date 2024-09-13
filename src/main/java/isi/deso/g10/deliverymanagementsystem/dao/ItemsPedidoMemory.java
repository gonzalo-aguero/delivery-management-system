package isi.deso.g10.deliverymanagementsystem.dao;

import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Comida;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
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
    public ItemMenu buscarId(int id, List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        return Vendedores.stream()
                .flatMap(vendedor -> vendedor.getMenu().stream()) // Combina todos los menús de todos los vendedores
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ItemNoEncontradoException("Item con id " + id + " no encontrado."));
    }

    @Override
    public ItemMenu buscarNombre(String nombre, List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        return Vendedores.stream()
                .flatMap(vendedor -> vendedor.getMenu().stream()) // Combina todos los menús de todos los vendedores
                .filter(item -> item.getNombre() == nombre)
                .findFirst()
                .orElseThrow(() -> new ItemNoEncontradoException("Item con nombre " + nombre + " no encontrado."));
    }

    @Override
    public List<ItemMenu> buscarPorRangoPrecio(double minimo, double maximo, List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemMenu> buscarPorCategoria(Categoria categoria, List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Comida> buscarComidas(List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Bebida> buscarBebidas(List<Vendedor> Vendedores) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemMenu> buscarPorRestaurante(Vendedor vendedor) throws ItemNoEncontradoException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
