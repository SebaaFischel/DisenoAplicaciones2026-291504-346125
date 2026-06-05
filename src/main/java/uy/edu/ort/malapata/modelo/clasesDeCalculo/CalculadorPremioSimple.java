package uy.edu.ort.malapata.modelo.clasesDeCalculo;
import uy.edu.ort.malapata.modelo.interfaces.CalculadorPremio;

public class CalculadorPremioSimple implements CalculadorPremio {
    public double calcular(double montoApostado, double dividendo, double totalApostadoAlCaballo) {
        return montoApostado * dividendo;
    }
}