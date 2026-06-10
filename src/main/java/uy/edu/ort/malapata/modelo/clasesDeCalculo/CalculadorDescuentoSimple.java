package uy.edu.ort.malapata.modelo.clasesDeCalculo;
import uy.edu.ort.malapata.modelo.interfaces.CalculadorDescuento;

public class CalculadorDescuentoSimple implements CalculadorDescuento {
    public double calcular(double montoApostado) {
        return montoApostado;
    }
}
