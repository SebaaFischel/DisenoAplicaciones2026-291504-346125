package uy.edu.ort.malapata.modelo;

public abstract class Modalidad {
    public abstract String getNombre();
    
    public abstract Apuesta crearApuesta(Jugador jugador, double monto, Participacion participacion);
}