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
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaTransferencia;
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
    private final PedidosDao pedidoDao;
    private final VendedorDao vendedorDao;
    private final ItemMenuMemory itemMenuDao;
    private FormaPagoI formaDePago;

    private ArrayList<Pedido> pedidos;

    private final PantallaPrincipal menu;

    public PedidoController(PantallaPrincipal menu) {
        this.menu = menu;
        this.pedidoDao = PedidoMemory.getInstance();
        this.vendedorDao = VendedorMemory.getInstance();
        this.itemMenuDao = ItemMenuMemory.getInstance();
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

        pedidos = (ArrayList) pedidoDao.obtenerPedidos();

        for (Pedido pedido : pedidos) {
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
        //Editar pedido
        int id = (int) this.tableModel.getValueAt(row, 0);
        Pedido pedido = pedidoDao.buscarPedidoPorId(id);
        //editar(pedido);

        //Actualizar tabla
        setTable();
    }

    private void eliminarButtonHandler(int row) {
        //Eliminar pedido
        int id = (int) this.tableModel.getValueAt(row, 0);
        pedidoDao.eliminarPedido(id);

        //Actualizar tabla
        setTable();
        JOptionPane.showMessageDialog(this.menu.getParent(), "Pedido con ID " + id + " eliminado.");
    }

    @Override
    public void crear() {
        CrearPedidoDialog crearPedido = new CrearPedidoDialog(menu, true);
        double totalFinal;

        ArrayList<Vendedor> vendedores = (ArrayList) vendedorDao.obtenerVendedores();

        ArrayList<ItemMenu> itemMenuSeleccionados = new ArrayList();

        DefaultTableModel menuModel = (DefaultTableModel) crearPedido.getTablaMenu().getModel();
        DefaultTableModel pedidoModel = (DefaultTableModel) crearPedido.getTablaPedido().getModel();

        for (Vendedor vendedor : vendedores) {
            crearPedido.getVendedorBox().addItem(vendedor);
        }

        //Agrega los item menu a la tabla
        crearPedido.getVendedorBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarVendedor((Vendedor) crearPedido.getVendedorBox().getSelectedItem());

                menuModel.setRowCount(0);

                for (ItemMenu itemMenu : itemsMenu) {
                    menuModel.addRow(new Object[]{itemMenu.getId(), itemMenu.getDescripcion()});
                }

                itemMenuSeleccionados.clear();
                updateTablePedido(pedidoModel, itemMenuSeleccionados);
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

        crearPedido.getSeleccionarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = crearPedido.getTablaMenu().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(crearPedido, "Seleccione una fila");
                    throw new RuntimeException("Seleccione una fila");
                }
                if (crearPedido.getSeleccionarButton().getText() == "Seleccionar") {
                    String value = String.valueOf(crearPedido.getTablaMenu().getValueAt(selectedRow, 0)); // Usa el índice de columna adecuado
                    itemMenuSeleccionados.add(obtenerItemMenu(value));
                } else if (crearPedido.getSeleccionarButton().getText() == "Eliminar") {
                    // !!! CORREGIR: no se elimina el correcto
                    String value = String.valueOf(crearPedido.getTablaMenu().getValueAt(selectedRow, 0));
                    itemMenuSeleccionados.remove(obtenerItemMenu(value));
                }
                
                updateTablePedido((DefaultTableModel) crearPedido.getTablaPedido().getModel(), itemMenuSeleccionados);
            }
        });

        crearPedido.getCancelarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPedido.dispose();
            }
        });

        crearPedido.getPedirButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formaPago(itemMenuSeleccionados,Double.parseDouble(crearPedido.getTotalField().getText()));
                setTable();
                crearPedido.dispose();
            }
        });
        
        crearPedido.setVisible(true);
    }

    private void buscarVendedor(Vendedor vendedor) {
        itemsMenu = itemMenuDao.buscarVendedor(vendedor);
    }

    private void formaPago(ArrayList<ItemMenu> itemMenuSeleccionados, double total) {
        FormaDePagoDialog formaPagoDialog = new FormaDePagoDialog(null, true);

        formaPagoDialog.getPedirButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aliasCbu = formaPagoDialog.getCbuField().getText();
                String cuit = formaPagoDialog.getCuitField().getText();
                
                if (formaPagoDialog.getFormaBox().getSelectedItem().equals("Mercado Pago")) {
                    formaDePago = new FormaMercadoPago(aliasCbu);
                } else if (formaPagoDialog.getFormaBox().getSelectedItem().equals("Transferencia")) {
                    formaDePago = new FormaTransferencia(cuit, aliasCbu);
                } else {
                    JOptionPane.showMessageDialog(null, "Elija una forma de pago");
                    throw new RuntimeException("Elija una forma de pago");
                }
                
                double subtotal = formaDePago.totalizar(total);
                formaPagoDialog.getSubTotalField().setText(subtotal + "");
                
                 // Crear y guardar el pedido
                Cliente cliente = obtenerCliente();  // cambiar
                

                Pedido pedido = new Pedido(-1, itemMenuSeleccionados, cliente, formaDePago);

                     try {
                        pedidoDao.agregarPedido(pedido);
                        JOptionPane.showMessageDialog(null, "Pedido guardado exitosamente.");
                    } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar el pedido: " + ex.getMessage());
                throw new RuntimeException("Error al guardar el pedido", ex);
            }

            formaPagoDialog.dispose();
            }
        });
        
        formaPagoDialog.setVisible(true);
    }

    private ItemMenu obtenerItemMenu(String id) {
        return itemsMenu.stream().filter(e -> e.getId() == Integer.parseInt(id)).findFirst().orElse(null);
    }

    private ItemMenu obtenerItemPedido(String id) {
        return itemsMenu.stream().filter(e -> e.getId() == Integer.parseInt(id)).findFirst().orElse(null);
    }

    private void updateTablePedido(DefaultTableModel tablaPedido, ArrayList<ItemMenu> itemMenuSeleccionados) {
        tablaPedido.setRowCount(0);
        for (ItemMenu itemMenu : itemMenuSeleccionados) {
            tablaPedido.addRow(new Object[]{itemMenu.getDescripcion()});
        }
    }
}
