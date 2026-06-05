package uy.edu.ort.malapata.modelo;

public class ModalidadTriple extends Modalidad {
    public String getNombre() { return "Triple"; }
    
    public Apuesta crearApuesta(Jugador jugador, double monto, Participacion participacion) {
        return new ApuestaTriple(jugador, monto);
    }
}