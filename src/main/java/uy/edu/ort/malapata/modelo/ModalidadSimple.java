package uy.edu.ort.malapata.modelo;

public class ModalidadSimple extends Modalidad {
    public String getNombre() { return "Simple"; }
    
    public Apuesta crearApuesta(Jugador jugador, double monto, Participacion participacion) {
        return new ApuestaSimple(jugador, monto);
    }
}