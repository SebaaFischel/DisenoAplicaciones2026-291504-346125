package uy.edu.ort.malapata.excepciones;

public class MalaPataException extends RuntimeException {
    public MalaPataException(String mensaje) {
        super(mensaje);
    }
}