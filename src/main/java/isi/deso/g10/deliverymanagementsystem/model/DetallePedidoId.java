package isi.deso.g10.deliverymanagementsystem.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DetallePedidoId implements Serializable {

    private int detallePedidoId;
    private int pedidoId;

    public DetallePedidoId() {}

    public DetallePedidoId(int detallePedidoId, int pedidoId) {
        this.detallePedidoId = detallePedidoId;
        this.pedidoId = pedidoId;
    }

    // Getters and Setters
    public int getDetallePedidoId() {
        return detallePedidoId;
    }

    public void setDetallePedidoId(int detallePedidoId) {
        this.detallePedidoId = detallePedidoId;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetallePedidoId that = (DetallePedidoId) o;
        return detallePedidoId == that.detallePedidoId &&
               pedidoId == that.pedidoId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(detallePedidoId, pedidoId);
    }
}