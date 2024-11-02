/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.ClientesMemory;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ClientesDao;
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.utils.FieldAnalyzer;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import isi.deso.g10.deliverymanagementsystem.view.crear.CrearClienteDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author giuli
 */
public class ClienteController implements Controller {

    private DefaultTableModel tableModel;

    //DAO
    private final ClientesDao clientesDao;

    private List<Cliente> clientes;

    private final PantallaPrincipal menu;

    public ClienteController(PantallaPrincipal menu) {
        this.menu = menu;
        clientesDao = ClientesMemory.getInstance();
    }

    @Override
    public void addFrameListeners() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setTable() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Cuit");
        modelo.addColumn("Nombre");
        modelo.addColumn("Email");
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

        clientes = clientesDao.obtenerClientes();

        //Llena la tabla de vendedores
        for (Cliente cliente : clientes) {

            // Agregamos la fila a la tabla
            modelo.addRow(new Object[]{cliente.getId(),
                cliente.getCuit(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion(),
                "[" + cliente.getCoordenadas().getLatitud() + ";" + cliente.getCoordenadas().getLongitud() + "]"
            });
        }
    }

    private void editarButtonHandler(int row) {
        Object id = this.tableModel.getValueAt(row, 0);
        JOptionPane.showMessageDialog(this.menu.getParent(), "Editar en fila: " + row + " ID: " + id.toString());
    }

    private void eliminarButtonHandler(int row) {
        Object id = this.tableModel.getValueAt(row, 0);
        JOptionPane.showMessageDialog(this.menu.getParent(), "Eliminar en fila: " + row + " ID: " + id.toString());
    }

    @Override
    public void crear() {
        CrearClienteDialog crearCliente= new CrearClienteDialog(null,true);
        crearCliente.setLocationRelativeTo(null);
        crearCliente.getNombreField().requestFocusInWindow();
 
        crearCliente.getCrearButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 Cliente clienteCreado;
                try{
                    FieldAnalyzer.todosLosCamposLlenos(crearCliente);
                    
                    Coordenada coordenadas = new Coordenada(Double.parseDouble(crearCliente.getLatitudField().getText()),
                            Double.parseDouble(crearCliente.getLatitudField().getText())
                            );
                    Cliente cliente = new Cliente(-1,
                            crearCliente.getCuitField().getText(),
                            crearCliente.getNombreField().getText(),
                            crearCliente.getEmailField().getText(),
                            crearCliente.getDireccionField().getText(),
                            coordenadas
                    );
                    
                   clienteCreado = clientesDao.agregarCliente(cliente);
                }catch(RuntimeException ex){
                    JOptionPane.showMessageDialog(crearCliente,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(crearCliente, "Cliente creado con id: " + clienteCreado.getId(), "Creación exitosa", JOptionPane.INFORMATION_MESSAGE);
                crearCliente.dispose();
            }
        });
        crearCliente.getCancelarButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                crearCliente.dispose();
            }
            });
            
        
        crearCliente.setVisible(true);
    }

}
