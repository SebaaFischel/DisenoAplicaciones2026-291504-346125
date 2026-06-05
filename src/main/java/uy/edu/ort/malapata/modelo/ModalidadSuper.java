package uy.edu.ort.malapata.modelo;

public class ModalidadSuper extends Modalidad {
    public String getNombre() { return "Super"; }
    
    public Apuesta crearApuesta(Jugador jugador, double monto, Participacion participacion) {
        return new ApuestaSuper(jugador, monto);
    }
}