package isi.deso.g10.deliverymanagementsystem.strategy;

public interface FormaPagoI {
    double totalizar(double monto);

    int getId();
}