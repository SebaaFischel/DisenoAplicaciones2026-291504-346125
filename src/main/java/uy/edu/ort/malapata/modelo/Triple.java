package uy.edu.ort.malapata.modelo;

public class Triple extends ModalidadApuesta {

    public Triple() {
        super("Triple");
    }

    @Override
    public double calcularCosto(double montoBase) {
        return montoBase * 1.5;
    }

    @Override
    public double calcularPago(double montoBase, double dividendo, double totalApostadoAlCaballo) {
        double factor = (totalApostadoAlCaballo < 100000) ? 2 : 3;
        return montoBase * dividendo * factor;
    }
}