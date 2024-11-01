/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.controller;

import isi.deso.g10.deliverymanagementsystem.dao.PedidosMemory;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.ClientesDao;
import isi.deso.g10.deliverymanagementsystem.dao.interfaces.PedidosDao;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanel;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelEditor;
import isi.deso.g10.deliverymanagementsystem.view.ButtonsPanelRenderer;
import isi.deso.g10.deliverymanagementsystem.view.PantallaPrincipal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author giuli
 */
public class PedidoController implements Controller {

    private DefaultTableModel tableModel;
    
    //DAO
    private final PedidosDao pedidosDao;
    
    private ArrayList<Pedido> pedidos;
    
    private final PantallaPrincipal menu;

    public PedidoController(PantallaPrincipal menu) {
        this.menu = menu;
        this.pedidosDao = new PedidosMemory();
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
        
       table.getColumn("Acciones").setCellRenderer(new ButtonsPanelRenderer());
       table.getColumn("Acciones").setCellEditor(new ButtonsPanelEditor(new ButtonsPanel()));
       
       pedidos = pedidosDao.getPedidos();
       
       for(Pedido pedido: pedidos){
           modelo.addRow(new Object[]{
           pedido.getId(),
           pedido.getCliente().getId(),
           pedido.getEstado(),
           pedido.getDetallePedido().calcularMontoTotal(),
           pedido.getDatosPago().getFormaPago(),
           new ButtonsPanel()
           });
       
       }
       
    }
    
}
