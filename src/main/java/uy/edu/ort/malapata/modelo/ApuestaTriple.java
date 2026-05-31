package uy.edu.ort.malapata.modelo;
import uy.edu.ort.malapata.modelo.clasesDeCalculo.CalculadorDescuentoTriple;
import uy.edu.ort.malapata.modelo.clasesDeCalculo.CalculadorPremioTriple;

public class ApuestaTriple extends Apuesta {

    public ApuestaTriple(Jugador jugador, double montoApostado, double totalApostadoAlCaballo) {
        super(jugador, montoApostado,
              new CalculadorDescuentoTriple(),
              new CalculadorPremioTriple(totalApostadoAlCaballo));
    }
    public String getNombre() { return "Triple"; }
}
