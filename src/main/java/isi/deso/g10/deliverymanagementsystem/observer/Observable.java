package isi.deso.g10.deliverymanagementsystem.observer;

public interface Observable {
    public void addObserver(PedidoObserver o);

    public boolean removeObserver(PedidoObserver o);

    public void notifyObservers();
}