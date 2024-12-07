/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.g10.deliverymanagementsystem.model.dto;

/**
 *
 * @author giuli
 */
class DetallePedidoDTO {
    private int id;
    private PedidoDTO pedido;  // Relación con PedidoDTO
    private ItemMenuDTO item;  // Relación con ItemMenuDTO
    private int cantidad;
}
