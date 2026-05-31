package uy.edu.ort.malapata.modelo;
import uy.edu.ort.malapata.modelo.clasesDeCalculo.CalculadorDescuentoSimple;
import uy.edu.ort.malapata.modelo.clasesDeCalculo.CalculadorPremioSimple;

public class ApuestaSimple extends Apuesta {

    public ApuestaSimple(Jugador jugador, double montoApostado) {
        super(jugador, montoApostado,
              new CalculadorDescuentoSimple(),
              new CalculadorPremioSimple());
    }

    public String getNombre() { return "Simple"; }
}