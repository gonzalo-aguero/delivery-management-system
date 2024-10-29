/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author giuli
 */
public class ButtonsPanelEditor extends DefaultCellEditor {
    private ButtonsPanel buttonsPanel;

    public ButtonsPanelEditor(ButtonsPanel buttonsPanel) {
        super(new JCheckBox());  // Dummy editor
        this.buttonsPanel = buttonsPanel;

        // Acciones de los botones
        buttonsPanel.getEditarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = ((JTable) buttonsPanel.getParent()).getSelectedRow();
                JOptionPane.showMessageDialog(buttonsPanel, "Editar en fila: " + row);
                fireEditingStopped();
            }
        });

        buttonsPanel.getEliminarButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = ((JTable) buttonsPanel.getParent()).getSelectedRow();
                JOptionPane.showMessageDialog(buttonsPanel, "Eliminar en fila: " + row);
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return buttonsPanel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
