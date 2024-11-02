    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package isi.deso.g10.deliverymanagementsystem.controller;

    import isi.deso.g10.deliverymanagementsystem.dao.ItemMenuMemory;
    import isi.deso.g10.deliverymanagementsystem.dao.VendedorMemory;
    import isi.deso.g10.deliverymanagementsystem.dao.interfaces.CategoriaDao;
    import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ItemMenuDao;
    import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;
    import isi.deso.g10.deliverymanagementsystem.model.Bebida;
    import isi.deso.g10.deliverymanagementsystem.model.Categoria;
    import isi.deso.g10.deliverymanagementsystem.model.Categoria.TipoItem;
    import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
    import isi.deso.g10.deliverymanagementsystem.model.Plato;
    import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
    import isi.deso.g10.deliverymanagementsystem.utils.FieldAnalyzer;
    import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
    import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
    import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
    import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
    import isi.deso.g10.deliverymanagementsystem.view.crear.CrearItemMenuDialog;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.function.Consumer;
    import javax.swing.JOptionPane;
    import javax.swing.JTable;
    import javax.swing.table.DefaultTableModel;

    /**
     *
     * @author giuli
     */
    public class ItemMenuController implements Controller {

        private DefaultTableModel tableModel;

        //DAO
        private final ItemMenuDao itemMenuDao;
        private final VendedorDao vendedorDao;

        private ArrayList<ItemMenu> itemsMenu;

        private final PantallaPrincipal menu;

        public ItemMenuController(PantallaPrincipal menu) {
            this.itemMenuDao = ItemMenuMemory.getInstance();
            this.vendedorDao = VendedorMemory.getInstance();
            this.menu = menu;

        }

        @Override
        public void addFrameListeners() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        @Override
        public void setTable() {
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Precio");
            modelo.addColumn("Categoria");
            modelo.addColumn("Calorias");
            modelo.addColumn("Apto para");
            modelo.addColumn("Acciones");

            JTable table = menu.getTabla();
            table.setModel(modelo);
            this.tableModel = modelo;

            // CONFIGURACION BOTONES EDITAR Y ELIMINAR
            table.getColumn("Acciones").setCellRenderer(new ButtonsPanelRenderer());

            // Creamos y configuramos los botones para cada elemento
            ButtonsPanel buttonsPanel = new ButtonsPanel();

            // Define acciones personalizadas para cada botón
            Consumer<Integer> editAction = (row) -> editarButtonHandler(row);
            Consumer<Integer> deleteAction = (row) -> eliminarButtonHandler(row);

            // Crea el editor de la celda pasando las acciones como parámetros
            ButtonsPanelEditor buttonsPanelEditor = new ButtonsPanelEditor(buttonsPanel, editAction, deleteAction);

            table.getColumn("Acciones").setCellEditor(buttonsPanelEditor);

            itemsMenu = (ArrayList) itemMenuDao.obtenerItemsMenu();

            for (ItemMenu item : itemsMenu) {
                modelo.addRow(new Object[]{
                    item.getId(),
                    item.getNombre(),
                    item.getPrecio(),
                    item.getCategoria().getTipoItem(),
                    item.getCalorias(),
                    (item.isAptoCeliaco() ? "celiaco" : "") + (item.isAptoVegano() ? " vegano" : "") + (item.isAptoVegetariano() ? " vegetariano" : "")
                });
            }
        }

        private void editarButtonHandler(int row) {
            int id = (int) this.tableModel.getValueAt(row, 0);
            ItemMenu item = itemMenuDao.buscarItemMenuPorId(id);
            editar(item);

            //Actualizar tabla
            setTable();
        }
        
        private void editar(ItemMenu item) {
            CrearItemMenuDialog crearIM = new CrearItemMenuDialog(menu, true);
            crearIM.setLocationRelativeTo(null);
            
            try {
                ArrayList<Vendedor> vendedores = (ArrayList) vendedorDao.obtenerVendedores();
                for (Vendedor vendedor : vendedores) {
                    crearIM.getVendedoresBox().addItem(vendedor);
                }
                
                ArrayList<Categoria> categorias = (ArrayList) itemMenuDao.getCategorias();

                for (Categoria categoria : categorias) {
                    crearIM.getCategoriaBox().addItem(categoria);
                }


            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(crearIM, ex.getMessage());
                throw new RuntimeException(ex.getMessage());      
            }
            
            // Setear valores previos

            crearIM.getVendedoresBox().setSelectedItem(item.getVendedor());
            crearIM.getCategoriaBox().setSelectedItem(item.getCategoria());
            crearIM.getCeliacoCheck().setSelected(item.aptoCeliaco());
            crearIM.getVeganoCheck().setSelected(item.aptoVegano());
            crearIM.getVegetarianoCheck().setSelected(item.aptoVegetariano());
            crearIM.getCaloriasField().setText(item.getCalorias()+"");
            crearIM.getNombreField().setText(item.getNombre()+"");
            crearIM.getPrecioField().setText(item.getPrecio()+"");
            crearIM.getDescripcionTextPane().setText(item.getDescripcion()+"");
            
            if (item instanceof Plato plato){
                crearIM.getPesoField().setText(plato.getPeso()+"");
                crearIM.getTipoBox().setSelectedItem("Plato");
            } else if (item instanceof Bebida bebida){
                crearIM.getTipoBox().setSelectedItem("Bebida");
                crearIM.getGraduacionField().setText(bebida.getGraduacionAlcoholica()+"");
                crearIM.getVolumenField().setText(bebida.getVolumenEnMl()+"");
                crearIM.getPesoField().setEditable(false);
                crearIM.getVolumenField().setEditable(true);
                crearIM.getGraduacionField().setEditable(true);
            }

           crearIM.getCrearButton().addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e) {
                  try{
                  Categoria categoria = (Categoria) crearIM.getCategoriaBox().getSelectedItem();
                  Vendedor vendedor= (Vendedor) crearIM.getVendedoresBox().getSelectedItem();

                  ItemMenu itemMenu = crearIM.getTipoBox().getSelectedItem() == "Plato"? 
                          new Plato(
                            item.getId(),
                            crearIM.getNombreField().getText(), // nombre
                            crearIM.getDescripcionTextPane().getText(), // descripcion (TextPane)
                            Double.parseDouble(crearIM.getPrecioField().getText()), // precio
                            categoria, // Categoria
                            Integer.parseInt(crearIM.getCaloriasField().getText()), // calorias
                            crearIM.getCeliacoCheck().isSelected(), // aptoCeliaco
                            crearIM.getVegetarianoCheck().isSelected(), // aptoVegetariano
                            crearIM.getVeganoCheck().isSelected(), // aptoVegano
                            vendedor, // Vendedor
                            Double.parseDouble(crearIM.getPesoField().getText()) // peso
                        ) 
                          : 
                          new Bebida(
                            item.getId(),
                            crearIM.getNombreField().getText(), // nombre
                            crearIM.getDescripcionTextPane().getText(), // descripcion (TextPane)
                            Double.parseDouble(crearIM.getPrecioField().getText()), // precio
                            categoria, // Categoria
                            Integer.parseInt(crearIM.getCaloriasField().getText()), // calorias
                            crearIM.getCeliacoCheck().isSelected(), // aptoCeliaco
                            crearIM.getVegetarianoCheck().isSelected(), // aptoVegetariano
                            crearIM.getVeganoCheck().isSelected(), // aptoVegano
                            vendedor, // Vendedor
                            Double.parseDouble(crearIM.getGraduacionField().getText()), // graduacionAlcoholica
                            Double.parseDouble(crearIM.getVolumenField().getText()) // volumenEnMl
                    );

                  itemMenuDao.actualizarItemMenu(itemMenu);
                  }catch(RuntimeException ex){
                      JOptionPane.showMessageDialog(crearIM,ex.getMessage());
                      throw new RuntimeException(ex.getMessage());
                  }

                  JOptionPane.showMessageDialog(crearIM,"ItemMenu editado con id:" + item.getId(),"Éxito",JOptionPane.INFORMATION_MESSAGE);
                  
                  setTable();
                  crearIM.dispose();
               }
           });

           crearIM.getTipoBox().addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e) {
                  if(crearIM.getTipoBox().getSelectedItem()== "Plato"){
                      crearIM.getPesoField().setEditable(true);
                      crearIM.getVolumenField().setEditable(false);
                      crearIM.getGraduacionField().setEditable(false);
                  }
                  else {
                      crearIM.getPesoField().setEditable(false);
                      crearIM.getVolumenField().setEditable(true);
                      crearIM.getGraduacionField().setEditable(true);
                  }
               }
           });
           
           crearIM.getCancelarButton().addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e) {
                   crearIM.dispose();
               }
           });

           crearIM.setVisible(true);
        }
        
        

        private void eliminarButtonHandler(int row) {
            int confirm = JOptionPane.showConfirmDialog(this.menu.getParent(),
                "¿Estás seguro de que deseas eliminar este elemento?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(this.tableModel.getValueAt(row, 0) + "");
                this.itemMenuDao.eliminarItemMenu(id);
                JOptionPane.showMessageDialog(this.menu.getParent(), "Eliminar en fila: " + row + " ID: " + id);
                setTable();
            }
        }

        @Override
        public void crear() {
            CrearItemMenuDialog crearIM = new CrearItemMenuDialog(menu, true);
            crearIM.setLocationRelativeTo(null);

            try {
                ArrayList<Vendedor> vendedores = (ArrayList) vendedorDao.obtenerVendedores();
                for (Vendedor vendedor : vendedores) {
                    crearIM.getVendedoresBox().addItem(vendedor);
                }

                ArrayList<Categoria> categorias = new ArrayList<Categoria>();
                categorias.add(new Categoria(1, "Carnes", TipoItem.COMIDA));
                categorias.add(new Categoria(2, "Pastas", TipoItem.COMIDA));
                categorias.add(new Categoria(3, "Cervezas", TipoItem.BEBIDA));
                categorias.add(new Categoria(4, "Vinos", TipoItem.BEBIDA));

                for (Categoria categoria : categorias) {
                    crearIM.getCategoriaBox().addItem(categoria);
                }


            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(crearIM, ex.getMessage());
                throw new RuntimeException(ex.getMessage());      
            }
            
            crearIM.getCancelarButton().addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e) {
                   crearIM.dispose();
               }
           });

           crearIM.getCrearButton().addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e) {
                  ItemMenu nuevoItemMenu;
                  try{

                  Categoria categoria = (Categoria) crearIM.getCategoriaBox().getSelectedItem();
                  Vendedor vendedor= (Vendedor) crearIM.getVendedoresBox().getSelectedItem();

                  ItemMenu itemMenu= crearIM.getTipoBox().getSelectedItem()=="Plato"? 
                          new Plato(
                            -1, // id
                            crearIM.getNombreField().getText(), // nombre
                            crearIM.getDescripcionTextPane().getText(), // descripcion (TextPane)
                            Double.parseDouble(crearIM.getPrecioField().getText()), // precio
                            categoria, // Categoria
                            Integer.parseInt(crearIM.getCaloriasField().getText()), // calorias
                            crearIM.getCeliacoCheck().isSelected(), // aptoCeliaco
                            crearIM.getVegetarianoCheck().isSelected(), // aptoVegetariano
                            crearIM.getVeganoCheck().isSelected(), // aptoVegano
                            vendedor, // Vendedor
                            Double.parseDouble(crearIM.getPesoField().getText()) // peso
                        ) 
                          : 

                          new Bebida(
                            -1, // id
                            crearIM.getNombreField().getText(), // nombre
                            crearIM.getDescripcionTextPane().getText(), // descripcion (TextPane)
                            Double.parseDouble(crearIM.getPrecioField().getText()), // precio
                            categoria, // Categoria
                            Integer.parseInt(crearIM.getCaloriasField().getText()), // calorias
                            crearIM.getCeliacoCheck().isSelected(), // aptoCeliaco
                            crearIM.getVegetarianoCheck().isSelected(), // aptoVegetariano
                            crearIM.getVeganoCheck().isSelected(), // aptoVegano
                            vendedor, // Vendedor
                            Double.parseDouble(crearIM.getGraduacionField().getText()), // graduacionAlcoholica
                            Double.parseDouble(crearIM.getVolumenField().getText()) // volumenEnMl
                    );

                  nuevoItemMenu = itemMenuDao.agregarItemMenu(itemMenu);

                  }catch(RuntimeException ex){
                      JOptionPane.showMessageDialog(crearIM,ex.getMessage());
                      throw new RuntimeException(ex.getMessage());
                  }

                  JOptionPane.showMessageDialog(crearIM,"ItemMenu creado con id:" + nuevoItemMenu.getId(),"Éxito",JOptionPane.INFORMATION_MESSAGE);
                  setTable();
                  crearIM.dispose();
               }
           });

           crearIM.getTipoBox().addActionListener(new ActionListener(){
               @Override
               public void actionPerformed(ActionEvent e) {
                  if(crearIM.getTipoBox().getSelectedItem()== "Plato"){
                      crearIM.getPesoField().setEditable(true);
                      crearIM.getVolumenField().setEditable(false);
                      crearIM.getGraduacionField().setEditable(false);
                  }
                  else {
                      crearIM.getPesoField().setEditable(false);
                      crearIM.getVolumenField().setEditable(true);
                      crearIM.getGraduacionField().setEditable(true);
                  }
               }

           });

           crearIM.setVisible(true);
        }

    }
