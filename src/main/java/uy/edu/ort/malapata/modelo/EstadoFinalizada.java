package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;

public class EstadoFinalizada extends EstadoCarrera {

    public EstadoFinalizada(Carrera carrera) {
        super(carrera);
    }

    @Override
    public void abrir() throws MalaPataException {
        throw new MalaPataException("La carrera ya fue finalizada.");
    }

    @Override
    public void cerrar() throws MalaPataException {
        throw new MalaPataException("La carrera ya fue finalizada.");
    }

    @Override
    public void finalizar(int numeroGanador) throws MalaPataException {
        throw new MalaPataException("La carrera ya fue finalizada.");
    }

    @Override public void actualizarEstado() {}

    @Override public boolean permiteApuestas() { return false; }
    @Override public boolean esFinalizada()    { return true;  }
    @Override public boolean esFinalOCerrada() { return true;  }
    @Override public String  getValor()        { return "FINALIZADA"; }
}