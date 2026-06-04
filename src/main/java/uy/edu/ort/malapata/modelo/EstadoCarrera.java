package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;

public abstract class EstadoCarrera {

    private Carrera carrera;

    public EstadoCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public abstract void abrir()                      throws MalaPataException;
    public abstract void cerrar()                     throws MalaPataException;
    public abstract void finalizar(int numeroGanador) throws MalaPataException;
    public abstract void actualizarEstado();
    public abstract boolean permiteApuestas();
    public abstract boolean esFinalizada();
    public abstract boolean esFinalOCerrada();
    public abstract String getValor();
}