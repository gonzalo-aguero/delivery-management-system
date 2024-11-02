/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.ItemMenuMemory;
import isi.deso.g10.deliverymanagementsystem.dao.PedidoMemory;
import isi.deso.g10.deliverymanagementsystem.dao.VendedorMemory;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.PedidosDao;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.VendedorDao;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import isi.deso.g10.deliverymanagementsystem.view.crear.CrearPedidoDialog;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ClienteDao;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import isi.deso.g10.deliverymanagementsystem.view.crear.FormaDePagoDialog;

/**
 *
 * @author giuli
 */
public class PedidoController implements Controller {

    private DefaultTableModel tableModel;
    //
    ArrayList<ItemMenu> itemsMenu;
    //DAO
    private final PedidosDao pedidosDao;
    private final VendedorDao vendedorDao;
    private final ItemMenuMemory itemMenuDao;
    
    private ArrayList<Pedido> pedidos;
    
    private final PantallaPrincipal menu;
    

    public PedidoController(PantallaPrincipal menu) {
        this.menu = menu;
        this.pedidosDao = PedidoMemory.getInstance();
        this.vendedorDao = VendedorMemory.getInstance();
        this.itemMenuDao= ItemMenuMemory.getInstance();
    }
    
    
    @Override
    public void addFrameListeners() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setTable() {
       DefaultTableModel modelo = new DefaultTableModel();
       modelo.addColumn("ID");
       modelo.addColumn("ID Cliente");
       modelo.addColumn("Estado");
       modelo.addColumn("Monto");
       modelo.addColumn("Forma de Pago");
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
       
       pedidos = (ArrayList) pedidosDao.obtenerPedidos();
       
       for(Pedido pedido: pedidos){
           modelo.addRow(new Object[]{
           pedido.getId(),
           pedido.getCliente().getId(),
           pedido.getEstado(),
           pedido.getDetallePedido().calcularMontoTotal(),
           pedido.getDatosPago().getFormaPago()
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
        CrearPedidoDialog crearPedido = new CrearPedidoDialog(menu,true);
        double totalFinal;
        
        ArrayList<Vendedor> vendedores= (ArrayList) vendedorDao.obtenerVendedores();
       
        
        ArrayList<ItemMenu> itemMenuSeleccionados = new ArrayList();
        
        DefaultTableModel menuModel = (DefaultTableModel) crearPedido.getTablaMenu().getModel();
        DefaultTableModel pedidoModel = (DefaultTableModel) crearPedido.getTablaPedido().getModel();
        
        for(Vendedor vendedor: vendedores){
            
            crearPedido.getVendedorBox().addItem(vendedor);
        }
        
        //Agrega los item menu a la tabla
        crearPedido.getVendedorBox().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarVendedor((Vendedor) crearPedido.getVendedorBox().getSelectedItem());
                
                menuModel.setRowCount(0);
                
                for(ItemMenu itemMenu : itemsMenu){
                      menuModel.addRow(new Object[]{itemMenu.getId(),itemMenu.getDescripcion()});
                }
                
                itemMenuSeleccionados.clear();
                updateTablePedido(pedidoModel,itemMenuSeleccionados);
            }
        });
        
        // Listener para la selección en tablaMenu
        crearPedido.getTablaMenu().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && crearPedido.getTablaMenu().getSelectedRow() != -1) {
            crearPedido.getSeleccionarButton().setText("Seleccionar");
            crearPedido.getSeleccionarButton().setBackground(Color.decode("#42B0FF"));
        }
        });

        // Listener para la selección en tablaPedido
        crearPedido.getTablaPedido().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && crearPedido.getTablaPedido().getSelectedRow() != -1) {
                crearPedido.getSeleccionarButton().setText("Eliminar");
                crearPedido.getSeleccionarButton().setBackground(Color.decode("#CD281E"));
            }
        });
        
        crearPedido.getSeleccionarButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = crearPedido.getTablaMenu().getSelectedRow();
                if(selectedRow == -1){ 
                    JOptionPane.showMessageDialog(crearPedido, "Seleccione una fila");
                    throw new RuntimeException("Seleccione una fila");
                }
                if(crearPedido.getSeleccionarButton().getText() == "Seleccionar"){
                    String value = String.valueOf(crearPedido.getTablaMenu().getValueAt(selectedRow, 0)); // Usa el índice de columna adecuado
                    itemMenuSeleccionados.add(obtenerItemMenu(value));
                }                           
                else if(crearPedido.getSeleccionarButton().getText() == "Eliminar"){
                    String value = String.valueOf(crearPedido.getTablaMenu().getValueAt(selectedRow, 0));
                    itemMenuSeleccionados.remove(obtenerItemMenu(value));
                 }
                updateTablePedido((DefaultTableModel)crearPedido.getTablaPedido().getModel(),itemMenuSeleccionados);
            }
        });
        
        crearPedido.getCancelarButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               crearPedido.dispose();
            }       
        });        
        
        crearPedido.getPedirButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                formaPago();
            }
        });
        
        crearPedido.setVisible(true);
    }
    
    private void buscarVendedor(Vendedor vendedor){
       itemsMenu= itemMenuDao.buscarVendedor(vendedor);
    }
    
    private void formaPago(){
        FormaDePagoDialog formaPago= new FormaDePagoDialog(null,true);
        
        formaPago.getPedirButton().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               if(formaPago.getFormaBox().getSelectedItem().equals("Mercado Pago")){
               
               }
               else if(formaPago.getFormaBox().getSelectedItem().equals("Transferencia")){
               
               } else{
                   JOptionPane.showMessageDialog(null,"Elija una forma de pago");
                   throw new RuntimeException("Elija una forma de pago");
               }
              
            }
        });
    }
    
    
    private ItemMenu obtenerItemMenu(String id){
        return itemsMenu.stream().filter(e -> e.getId() == Integer.parseInt(id)).findFirst().orElse(null);
    }
    private ItemMenu obtenerItemPedido(String id){
        return itemsMenu.stream().filter(e -> e.getId() == Integer.parseInt(id)).findFirst().orElse(null);
    }
    
    private void updateTablePedido(DefaultTableModel tablaPedido, ArrayList<ItemMenu> itemMenuSeleccionados){
        tablaPedido.setRowCount(0);
        for(ItemMenu itemMenu: itemMenuSeleccionados){
                  tablaPedido.addRow(new Object[]{itemMenu.getDescripcion()});
        }
    }
}
