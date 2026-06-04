package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;

public class EstadoDefinida extends EstadoCarrera {

    public EstadoDefinida(Carrera carrera) {
        super(carrera);
    }

    @Override
    public void abrir() throws MalaPataException {
        for (Participacion p : getCarrera().getParticipaciones())
            p.invalidarDividendo();
        getCarrera().cambiarEstado(new EstadoAbierta(getCarrera()));
    }

    @Override
    public void cerrar() throws MalaPataException {
        throw new MalaPataException("No se puede cerrar: la carrera debe estar Estable.");
    }

    @Override
    public void finalizar(int numeroGanador) throws MalaPataException {
        throw new MalaPataException("No se puede finalizar: la carrera debe estar Cerrada.");
    }

    @Override public void actualizarEstado() {}

    @Override public boolean permiteApuestas() { return false; }
    @Override public boolean esFinalizada()    { return false; }
    @Override public boolean esFinalOCerrada() { return false; }
    @Override public String  getValor()        { return "DEFINIDA"; }
}