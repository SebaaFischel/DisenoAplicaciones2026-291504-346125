package uy.edu.ort.malapata.modelo.clasesDeCalculo;
import uy.edu.ort.malapata.modelo.interfaces.CalculadorPremio;

public class CalculadorPremioSuper implements CalculadorPremio {
    public double calcular(double montoApostado, int dividendo) {
        double factor = (dividendo >= 2) ? 3 : 4;
        return montoApostado * dividendo * factor;
    }
}