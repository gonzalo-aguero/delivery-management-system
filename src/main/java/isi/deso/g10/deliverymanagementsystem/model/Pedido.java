/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model;

/**
 *
 * @author gonzalo90fa
 */
public class Pedido {
    public enum EstadoPedido {
        EN_PROCESO, PENDIENTE_DE_PAGO, ENTREGADO, FINALIZADO
    }
    
    private Cliente cliente;
    private EstadoPedido estado;
}
