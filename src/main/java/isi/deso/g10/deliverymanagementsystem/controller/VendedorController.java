/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.VendedorMemory;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.utils.FieldAnalyzer;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import isi.deso.g10.deliverymanagementsystem.view.crear.CrearVendedorDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;

/**
 *
 * @author giuli
 */
public class VendedorController implements Controller {

    PantallaPrincipal menu;
    DefaultTableModel tableModel;

    //DAOS
    VendedorDao vendedoresDao;

    List<Vendedor> vendedores;

    public VendedorController(PantallaPrincipal menu) {
        this.menu = menu;
        vendedoresDao = VendedorMemory.getInstance();
    }

    @Override
    public void addFrameListeners() {
        //Aca debería haber listeners del frame de creacion, edición y eliminación, creo que la busqueda se podría hacer en el MenuController tranquilamente
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void setTableFiltradaPorNombre(String cadena){
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Coordenadas");
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

        vendedores = vendedoresDao.buscarVendedorPorNombre(cadena);

        //Llena la tabla de vendedores
        for (Vendedor vendedor : vendedores) {
            modelo.addRow(new Object[]{
                vendedor.getId(),
                vendedor.getNombre(),
                vendedor.getDireccion(),
                "[" + vendedor.getCoordenadas().getLatitud() + ";" + vendedor.getCoordenadas().getLongitud() + "]",
                new ButtonsPanel()
            });
        }
    }

    @Override
    public void setTable() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Dirección");
        modelo.addColumn("Coordenadas");
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

        vendedores = vendedoresDao.obtenerTodos();

        //Llena la tabla de vendedores
        for (Vendedor vendedor : vendedores) {
            modelo.addRow(new Object[]{
                vendedor.getId(),
                vendedor.getNombre(),
                vendedor.getDireccion(),
                "[" + vendedor.getCoordenadas().getLatitud() + ";" + vendedor.getCoordenadas().getLongitud() + "]",
                new ButtonsPanel()
            });
        }

    }

    private void editarButtonHandler(int row) {
        //Editar vendedor
        int id = (int) this.tableModel.getValueAt(row, 0);
        Vendedor vendedor = vendedoresDao.obtenerPorId(id);
        editar(vendedor);
        
        //Actualizar tabla
        setTable();
    }

    private void eliminarButtonHandler(int row) {
        //Eliminar vendedor
        int id = (int) this.tableModel.getValueAt(row, 0);
        vendedoresDao.eliminar(id);
        
        //Actualizar tabla
        setTable();
        JOptionPane.showMessageDialog(this.menu.getParent(), "Vendedor con ID " + id + " eliminado.");
    }

    @Override
    public void crear() {
        CrearVendedorDialog crearVendedor = new CrearVendedorDialog(menu, true);
        crearVendedor.setLocationRelativeTo(null);

        crearVendedor.getCrearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vendedor vendedorCreado;
                try {
                    FieldAnalyzer.todosLosCamposLlenos(crearVendedor);

                    Coordenada coordenadas = new Coordenada(
                            Double.parseDouble(crearVendedor.getLatitudField().getText()),
                            Double.parseDouble(crearVendedor.getLongitudField().getText())
                    );

                    Vendedor vendedor = new Vendedor(
                            -1,
                            crearVendedor.getNombreField().getText(),
                            crearVendedor.getDireccionField().getText(),
                            coordenadas
                    );

                    vendedorCreado = vendedoresDao.crear(vendedor);
                    JOptionPane.showMessageDialog(crearVendedor, "Vendedor creado con id: " + vendedorCreado.getId(), "Creación exitosa", JOptionPane.INFORMATION_MESSAGE);
                    crearVendedor.dispose();
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(crearVendedor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                //Actualizar tabla
                setTable();
            }
        });

        crearVendedor.getCancelarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearVendedor.dispose();
            }
        });

        crearVendedor.setVisible(true);
    }
    
    public void editar(Vendedor vendedor) {
        CrearVendedorDialog editarVendedor = new CrearVendedorDialog(menu, true);
        editarVendedor.setLocationRelativeTo(null);
        editarVendedor.setEditorMode(vendedor);

        editarVendedor.getCrearButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Vendedor vendedorEditado;
                try {
                    FieldAnalyzer.todosLosCamposLlenos(editarVendedor);

                    Coordenada coordenadas = new Coordenada(
                            Double.parseDouble(editarVendedor.getLatitudField().getText()),
                            Double.parseDouble(editarVendedor.getLongitudField().getText())
                    );
                    
                    vendedorEditado = new Vendedor(
                            vendedor.getId(),
                            editarVendedor.getNombreField().getText(),
                            editarVendedor.getDireccionField().getText(),
                            coordenadas
                    );

                    vendedoresDao.actualizar(vendedorEditado);
                    JOptionPane.showMessageDialog(editarVendedor, "Vendedor con ID " + vendedorEditado.getId() + " editado correctamente.", "Edición exitosa", JOptionPane.INFORMATION_MESSAGE);
                    editarVendedor.dispose();
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(editarVendedor, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                //Actualizar tabla
                setTable();
            }
        });

        editarVendedor.getCancelarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarVendedor.dispose();
            }
        });

        editarVendedor.setVisible(true);
    }

}
