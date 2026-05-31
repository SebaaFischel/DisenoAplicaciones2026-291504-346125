package uy.edu.ort.malapata.modelo.clasesDeCalculo;
import uy.edu.ort.malapata.modelo.interfaces.CalculadorPremio;

public class CalculadorPremioTriple implements CalculadorPremio {
    private double totalApostadoAlCaballo;

    public CalculadorPremioTriple(double totalApostadoAlCaballo) {
        this.totalApostadoAlCaballo = totalApostadoAlCaballo;
    }

    public double calcular(double montoApostado, double dividendo) {
        double factor = (totalApostadoAlCaballo < 100000) ? 2 : 3;
        return montoApostado * dividendo * factor;
    }
}