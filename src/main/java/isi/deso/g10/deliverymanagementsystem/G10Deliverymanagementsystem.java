/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package isi.deso.g10.deliverymanagementsystem;

/**
 *
 * @author gonzalo-aguero
 */
public class G10Deliverymanagementsystem {

    public static void main(String[] args) {
        
        Coordenada coordenada1= new Coordenada(3.43,4.43);
        Coordenada coordenada2= new Coordenada(1.23,3.223);
        Coordenada coordenada3= new Coordenada(1.654,6.888);
        
        Vendedor vendedor1= new Vendedor(1,"Juan","Azcuenaga 3000",coordenada1);
        Vendedor vendedor2= new Vendedor(2,"Manuel","San Martin 610",coordenada2);
        Vendedor vendedor3= new Vendedor(3,"Ian","Rivadavia 2032",coordenada3);
        
        Vendedor[] vendedores = new Vendedor[3];
        
        vendedores[0]=vendedor1;
        vendedores[1]=vendedor2;
        vendedores[2]=vendedor3;
        
        
        
    }
    
        private Vendedor buscar(Vendedor[] vendedores,int id){
            
            Vendedor vendedor=null;
            int i = 0;
            while(vendedor==null || i < vendedores.length ){
                if(vendedores[i].getId()==id){
                    vendedor= vendedores[i];
                }
                i++;
            }
            return vendedor;
        }
        private Vendedor buscar(Vendedor[] vendedores,String nombre){
            
            Vendedor vendedor=null;
            int i = 0;
            while(vendedor==null || i < vendedores.length ){
                if(vendedores[i].getNombre()==nombre){
                    vendedor= vendedores[i];
                }
                i++;
            }
            return vendedor;
        }
}
