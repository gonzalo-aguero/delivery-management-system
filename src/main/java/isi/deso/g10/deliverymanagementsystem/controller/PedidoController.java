/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.ClientesMemory;
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
import isi.deso.g10.deliverymanagementsystem.model.Pedido.EstadoPedido;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaTransferencia;
import isi.deso.g10.deliverymanagementsystem.view.EditarPedidoDialog;
import isi.deso.g10.deliverymanagementsystem.view.crear.FormaDePagoDialog;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
    private CrearPedidoDialog crearPedidoDialog;

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
    public void setTableFiltradaPorNombre(String cadena){
        throw new UnsupportedOperationException("No se puede buscar por pedido.");
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
                pedido.getFormapago().totalizar(pedido.getDetallePedido().calcularMontoTotal()),
                pedido.getFormapago()
            });

        }

    }

    private void editarButtonHandler(int row) {
        //Editar pedido
        int id = (int) this.tableModel.getValueAt(row, 0);
        Pedido pedido = pedidoDao.buscarPedidoPorId(id);
        editar(pedido);

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
        crearPedidoDialog = new CrearPedidoDialog(menu, true);

        ArrayList<Vendedor> vendedores = (ArrayList) vendedorDao.obtenerVendedores();

        ArrayList<ItemMenu> itemsSeleccionados = new ArrayList();

        DefaultTableModel menuModel = (DefaultTableModel) crearPedidoDialog.getTablaMenu().getModel();
        DefaultTableModel pedidoModel = (DefaultTableModel) crearPedidoDialog.getTablaPedido().getModel();

        for (Vendedor vendedor : vendedores) {
            crearPedidoDialog.getVendedorBox().addItem(vendedor);
        }

        //Agrega los item menu a la tabla
        crearPedidoDialog.getVendedorBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarVendedor((Vendedor) crearPedidoDialog.getVendedorBox().getSelectedItem());

                menuModel.setRowCount(0);

                for (ItemMenu itemMenu : itemsMenu) {
                    menuModel.addRow(new Object[]{itemMenu.getId(), itemMenu.getDescripcion()});
                }

                itemsSeleccionados.clear();
                updateTablePedido(pedidoModel, itemsSeleccionados);
                crearPedidoDialog.getTotalField().setText(String.valueOf(0));
            }
        });

        // Listener para la selección en tablaMenu
        crearPedidoDialog.getTablaMenu().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && crearPedidoDialog.getTablaMenu().getSelectedRow() != -1) {
                crearPedidoDialog.getSeleccionarButton().setText("Seleccionar");
                crearPedidoDialog.getSeleccionarButton().setBackground(Color.decode("#42B0FF"));
            }
        });

        // Listener para la selección en tablaPedido
        crearPedidoDialog.getTablaPedido().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && crearPedidoDialog.getTablaPedido().getSelectedRow() != -1) {
                crearPedidoDialog.getSeleccionarButton().setText("Eliminar");
                crearPedidoDialog.getSeleccionarButton().setBackground(Color.decode("#CD281E"));
            }
        });

        // Listener para cuando se agrega un item al pedido
        crearPedidoDialog.getSeleccionarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = crearPedidoDialog.getTablaMenu().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(crearPedidoDialog, "Seleccione una fila");
                    throw new RuntimeException("Seleccione una fila");
                }

                if ("Seleccionar".equals(crearPedidoDialog.getSeleccionarButton().getText())) {
                    int idPedido = (int) crearPedidoDialog.getTablaMenu().getValueAt(selectedRow, 0); // Usa el índice de columna adecuado
                    itemsSeleccionados.add(obtenerItemMenu(idPedido));
                } else if ("Eliminar".equals(crearPedidoDialog.getSeleccionarButton().getText())) {
                    int selectedRowTablaPedido = crearPedidoDialog.getTablaPedido().getSelectedRow();
                    int idPedido = (int) crearPedidoDialog.getTablaPedido().getValueAt(selectedRowTablaPedido, 0);

                    // Eliminar de la lista de items seleccionados
                    itemsSeleccionados.remove(obtenerItemPedido(idPedido));

                }

                // Actualizar tabla de items selecionados
                updateTablePedido((DefaultTableModel) crearPedidoDialog.getTablaPedido().getModel(), itemsSeleccionados);

                // Actualizar subtotal (sumatoria de items)
                double subtotal = obtenerSubtotalDeItems(itemsSeleccionados);
                crearPedidoDialog.getTotalField().setText(String.valueOf(subtotal));
            }
        });

        crearPedidoDialog.getCancelarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPedidoDialog.dispose();
            }
        });

        crearPedidoDialog.getPedirButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formaPago(itemsSeleccionados);
                setTable();
                crearPedidoDialog.dispose();
            }
        });

        crearPedidoDialog.setVisible(true);
    }
    
    public void editar(Pedido pedido){
        EditarPedidoDialog editarPedido = new EditarPedidoDialog(menu,true);
        editarPedido.setLocationRelativeTo(null);
        
        for (EstadoPedido estadoPedido : EstadoPedido.values()) {
            editarPedido.getEstadoBox().addItem(estadoPedido);
        }
        editarPedido.getEstadoBox().setSelectedItem(pedido.getEstado());
        
        editarPedido.getFormaBox().addItem("Mercado Pago");
        editarPedido.getFormaBox().addItem("Transferencia");
        
        if(pedido.getFormapago() instanceof FormaMercadoPago) editarPedido.getFormaBox().setSelectedItem("Mercado Pago");
        else editarPedido.getFormaBox().setSelectedItem("Transferencia");
        
        
        editarPedido.getFormaBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aliasCbu = editarPedido.getCbuField().getText();
                String cuit = editarPedido.getCuitField().getText();

                if (editarPedido.getFormaBox().getSelectedItem().equals("Mercado Pago")) {
                    formaDePago = new FormaMercadoPago(aliasCbu);
                } else if (editarPedido.getFormaBox().getSelectedItem().equals("Transferencia")) {
                    formaDePago = new FormaTransferencia(cuit, aliasCbu);
                } else {
                    JOptionPane.showMessageDialog(null, "Elija una forma de pago");
                    throw new RuntimeException("Elija una forma de pago");
                }

                double total = obtenerSubtotalDeItems(pedido.getDetallePedido().getItems());
                double subtotal = formaDePago.totalizar(total);
                editarPedido.getSubTotalField().setText(String.valueOf(subtotal));
            }
        });

        editarPedido.getEditarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstadoPedido estadoSeleccionado = (EstadoPedido) editarPedido.getEstadoBox().getSelectedItem();
                pedido.setEstado(estadoSeleccionado);

                
                String aliasCbu = editarPedido.getCbuField().getText();
                String cuit = editarPedido.getCuitField().getText();
                if (editarPedido.getFormaBox().getSelectedItem().equals("Mercado Pago")) {
                    pedido.setFormapago(new FormaMercadoPago(aliasCbu));
                } else if (editarPedido.getFormaBox().getSelectedItem().equals("Transferencia")) {
                    pedido.setFormapago(new FormaTransferencia(cuit, aliasCbu));
                }

                
                try {
                    pedidoDao.actualizarPedido(pedido);
                    JOptionPane.showMessageDialog(editarPedido, "Pedido actualizado exitosamente.");
                    editarPedido.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(editarPedido, "Error al actualizar el pedido: " + ex.getMessage());
                    throw new RuntimeException("Error al actualizar el pedido", ex);
                }
            }
    });

    // Cancelar edición
    editarPedido.getCancelarButton().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            editarPedido.dispose();
        }
    });

    editarPedido.setVisible(true);
        
    }

    private void buscarVendedor(Vendedor vendedor) {
        itemsMenu = itemMenuDao.buscarVendedor(vendedor);
    }

    private void formaPago(ArrayList<ItemMenu> itemMenuSeleccionados) {
        final FormaDePagoDialog formaDePagoDialog = new FormaDePagoDialog(null, true);

        // Cada vez que se selecciona una forma de pago se actualiza el monto
        formaDePagoDialog.getFormaBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aliasCbu = formaDePagoDialog.getCbuField().getText();
                String cuit = formaDePagoDialog.getCuitField().getText();

                if (formaDePagoDialog.getFormaBox().getSelectedItem().equals("Mercado Pago")) {
                    formaDePago = new FormaMercadoPago(aliasCbu);
                } else if (formaDePagoDialog.getFormaBox().getSelectedItem().equals("Transferencia")) {
                    formaDePago = new FormaTransferencia(cuit, aliasCbu);
                } else {
                    JOptionPane.showMessageDialog(null, "Elija una forma de pago");
                    throw new RuntimeException("Elija una forma de pago");
                }

                // Totalizar de acuerdo a la forma de pago
                double total = obtenerSubtotalDeItems(itemMenuSeleccionados);
                double subtotal = formaDePago.totalizar(total);
                formaDePagoDialog.getSubTotalField().setText(String.valueOf(subtotal));
            }
        });

        // Se toman los valores de los campos cuit y cbu/alias
        formaDePagoDialog.getPedirButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String aliasCbu = formaDePagoDialog.getCbuField().getText();
                String cuit = formaDePagoDialog.getCuitField().getText();

                if (formaDePagoDialog.getFormaBox().getSelectedItem().equals("Mercado Pago")) {
                    formaDePago = new FormaMercadoPago(aliasCbu);
                } else if (formaDePagoDialog.getFormaBox().getSelectedItem().equals("Transferencia")) {
                    formaDePago = new FormaTransferencia(cuit, aliasCbu);
                } else {
                    JOptionPane.showMessageDialog(null, "Elija una forma de pago");
                    throw new RuntimeException("Elija una forma de pago");
                }

                // Totalizar de acuerdo a la forma de pago
                double subtotal = obtenerSubtotalDeItems(itemMenuSeleccionados);
                double total = formaDePago.totalizar(subtotal);
                formaDePagoDialog.getSubTotalField().setText(String.valueOf(total));

                Cliente cliente = obtenerCliente();

                // Crear y guardar el pedido
                Pedido pedido = new Pedido(-1, itemMenuSeleccionados, cliente, formaDePago);
                pedido.setEstado(Pedido.EstadoPedido.RECIBIDO);

                try {
                    pedidoDao.agregarPedido(pedido);
                    JOptionPane.showMessageDialog(formaDePagoDialog, "Pedido guardado exitosamente.");
                    crearPedidoDialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(formaDePagoDialog, "Error al guardar el pedido: " + ex.getMessage());
                    throw new RuntimeException("Error al guardar el pedido", ex);
                }

                formaDePagoDialog.dispose();
            }
        });

        formaDePagoDialog.setVisible(true);
    }

    private ItemMenu obtenerItemMenu(int id) {
        return itemsMenu.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    private ItemMenu obtenerItemPedido(int id) {
        return itemsMenu.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    private double obtenerSubtotalDeItems(ArrayList<ItemMenu> items) {
        double total = 0.0;

        for (ItemMenu item : items) {
            total += item.getPrecio();
        }

        return total;
    }

    /**
     * Retorna el primer cliente para que realice el pedido.
     *
     * @return
     */
    private Cliente obtenerCliente() {
        return ClientesMemory.getInstance().obtenerClientes().getFirst();
    }

    private void updateTablePedido(DefaultTableModel tablaPedido, ArrayList<ItemMenu> itemMenuSeleccionados) {
        tablaPedido.setRowCount(0);
        for (ItemMenu itemMenu : itemMenuSeleccionados) {
            tablaPedido.addRow(new Object[]{
                itemMenu.getId(),
                itemMenu.getDescripcion()
            });
        }
    }
}
