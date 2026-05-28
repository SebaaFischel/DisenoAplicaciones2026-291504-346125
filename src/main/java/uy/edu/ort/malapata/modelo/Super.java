package uy.edu.ort.malapata.modelo;

public class Super extends ModalidadApuesta {

    public Super() {
        super("Super");
    }

    @Override
    public double calcularCosto(double montoBase) {
        return montoBase * 2;
    }

    @Override
    public double calcularPago(double montoBase, double dividendo, double totalApostadoAlCaballo) {
        double factor = (dividendo >= 2) ? 3 : 4;
        return montoBase * dividendo * factor;
    }
}