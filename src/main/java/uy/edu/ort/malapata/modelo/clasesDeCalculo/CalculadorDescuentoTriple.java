package uy.edu.ort.malapata.modelo.clasesDeCalculo;
import uy.edu.ort.malapata.modelo.interfaces.CalculadorDescuento;

public class CalculadorDescuentoTriple implements CalculadorDescuento {
    public double calcular(double montoApostado, double totalApostadoAlCaballo) {
        return montoApostado * 1.5;
    }
}