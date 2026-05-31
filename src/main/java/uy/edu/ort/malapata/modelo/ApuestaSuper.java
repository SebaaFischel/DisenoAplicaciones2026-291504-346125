package uy.edu.ort.malapata.modelo;
import uy.edu.ort.malapata.modelo.clasesDeCalculo.CalculadorDescuentoSuper;
import uy.edu.ort.malapata.modelo.clasesDeCalculo.CalculadorPremioSuper;

public class ApuestaSuper extends Apuesta {

    public ApuestaSuper(Jugador jugador, double montoApostado) {
        super(jugador, montoApostado,
              new CalculadorDescuentoSuper(),
              new CalculadorPremioSuper());
    }

    public String getNombre() { return "Super"; }
}