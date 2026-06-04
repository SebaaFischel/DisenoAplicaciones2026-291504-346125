package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;

public class EstadoEstable extends EstadoCarrera {

    public EstadoEstable(Carrera carrera) {
        super(carrera);
    }

    @Override
    public void abrir() throws MalaPataException {
        throw new MalaPataException("La carrera ya está abierta.");
    }

    @Override
    public void cerrar() throws MalaPataException {
        getCarrera().cambiarEstado(new EstadoCerrada(getCarrera()));
    }

    @Override
    public void finalizar(int numeroGanador) throws MalaPataException {
        throw new MalaPataException("No se puede finalizar: la carrera debe estar Cerrada.");
    }

    @Override
    public void actualizarEstado() {
        for (Participacion p : getCarrera().getParticipaciones()) {
            if (!p.isDividendoValido()) {
                getCarrera().cambiarEstado(new EstadoAbierta(getCarrera()));
                return;
            }
        }
    }

    @Override public boolean permiteApuestas() { return true;  }
    @Override public boolean esFinalizada()    { return false; }
    @Override public boolean esFinalOCerrada() { return false; }
    @Override public String  getValor()        { return "ESTABLE"; }
}