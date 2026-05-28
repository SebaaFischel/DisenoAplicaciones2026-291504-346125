package uy.edu.ort.malapata.observador;

public interface Observador {

    public void actualizar(Object evento,Observable origen);
}