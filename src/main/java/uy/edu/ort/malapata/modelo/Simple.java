package uy.edu.ort.malapata.modelo;

public class Simple extends ModalidadApuesta {

    public Simple() {
        super("Simple");
    }

    @Override
    public double calcularCosto(double montoBase) {
        return montoBase;
    }

    @Override
    public double calcularPago(double montoBase, double dividendo, double totalApostadoAlCaballo) {
        return montoBase * dividendo;
    }
}