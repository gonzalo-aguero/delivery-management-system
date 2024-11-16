package isi.deso.g10.deliverymanagementsystem.dao.mysql.mappers;

import isi.deso.g10.deliverymanagementsystem.dao.memory.CategoriaMemory;
import isi.deso.g10.deliverymanagementsystem.model.Bebida;
import isi.deso.g10.deliverymanagementsystem.model.Categoria;
import isi.deso.g10.deliverymanagementsystem.model.Cliente;
import isi.deso.g10.deliverymanagementsystem.model.Coordenada;
import isi.deso.g10.deliverymanagementsystem.model.DetallePedido;
import isi.deso.g10.deliverymanagementsystem.model.ItemMenu;
import isi.deso.g10.deliverymanagementsystem.model.Pedido;
import isi.deso.g10.deliverymanagementsystem.model.Pedido.EstadoPedido;
import isi.deso.g10.deliverymanagementsystem.model.Plato;
import isi.deso.g10.deliverymanagementsystem.model.Vendedor;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaMercadoPago;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaPagoI;
import isi.deso.g10.deliverymanagementsystem.strategy.FormaTransferencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoMapper {
    public PedidoMapper(){

    }

    public Pedido mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(resultSet.getInt("id"));

        // Get and set Cliente
        Cliente cliente = mapResultSetToCliente(resultSet);
        pedido.setCliente(cliente);

        // Get and set EstadoPedido
        EstadoPedido estado;
        try {
            estado = EstadoPedido.valueOf(resultSet.getString("estado_pedido"));
        } catch (IllegalArgumentException e) {
            estado = null;
        }
        pedido.setEstado(estado);

        // Get and set Forma de Pago
        FormaPagoI formaPago = mapResultSetToFormaPago(resultSet);
        pedido.setFormapago(formaPago);

        return pedido;
    }

    public Cliente mapResultSetToCliente(ResultSet resultSet) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(resultSet.getInt("clienteId"));
        cliente.setCuit(resultSet.getString("cuit_cliente"));
        cliente.setNombre(resultSet.getString("nombre_cliente"));
        cliente.setEmail(resultSet.getString("email_cliente"));
        cliente.setDireccion(resultSet.getString("direccion_cliente"));
        cliente.setCoordenadas(
                new Coordenada(resultSet.getDouble("latitud_cliente"), resultSet.getDouble("longitud_cliente")));
        return cliente;
    }

    public FormaPagoI mapResultSetToFormaPago(ResultSet resultSet) throws SQLException {
        FormaPagoI formaPago;
        String tipoFormaPago = resultSet.getString("tipoFormaPago");
        if ("MercadoPago".equals(tipoFormaPago)) {
            formaPago = new FormaMercadoPago(resultSet.getString("aliasVendedor"));
        } else if ("Transferencia".equals(tipoFormaPago)) {
            formaPago = new FormaTransferencia(
                    resultSet.getString("cuitVendedor"),
                    resultSet.getString("cbuVendedor"));
        } else {
            formaPago = null;
        }
        formaPago.setId(resultSet.getInt("id_FormaPago"));
        return formaPago;
    }

    public ItemMenu mapResultSetToItemMenu(ResultSet resultSet) throws SQLException {
        ItemMenu itemMenu;

        if (resultSet.getString("graduacionAlcoholica") != null) {
            itemMenu = new Bebida();
            ((Bebida) itemMenu).setGraduacionAlcoholica(resultSet.getDouble("graduacionAlcoholica"));
            ((Bebida) itemMenu).setVolumenEnMl(resultSet.getDouble("volumenEnMl"));
        } else if (resultSet.getString("peso") != null) {
            itemMenu = new Plato();
            ((Plato) itemMenu).setPeso(resultSet.getDouble("peso"));
        } else {
            throw new RuntimeException("El resultado no coincide con alguno de los dos tipos (Plato/Bebida)");
        }

        itemMenu.setId(resultSet.getInt("id_item"));
        itemMenu.setNombre(resultSet.getString("nombre"));
        itemMenu.setDescripcion(resultSet.getString("descripcion"));
        itemMenu.setPrecio(resultSet.getDouble("precio"));

        // Get and set Categoria
        Categoria categoria = CategoriaMemory.getInstance().obtenerPorDescripcion(resultSet.getString("categoria"));
        itemMenu.setCategoria(categoria);

        itemMenu.setCalorias(resultSet.getInt("calorias"));
        itemMenu.setAptoCeliaco(resultSet.getBoolean("aptoCeliaco"));
        itemMenu.setAptoVegetariano(resultSet.getBoolean("aptoVegetariano"));
        itemMenu.setAptoVegano(resultSet.getBoolean("aptoVegano"));

        // Get and set Vendedor
        // Si se quiere obtener el vendedor, se debe hacer una consulta a la base de datos
        // o agregar un JOIN en la consulta de los itemsMenu
        // Vendedor vendedor = new Vendedor(
        //         resultSet.getInt("id_vendedor"),
        //         resultSet.getString("nombre_vendedor"),
        //         resultSet.getString("direccion_vendedor"),
        //         new Coordenada(
        //                 resultSet.getDouble("latitud_vendedor"),
        //                 resultSet.getDouble("longitud_vendedor")));
        // itemMenu.setVendedor(vendedor);

        return itemMenu;
    }
}