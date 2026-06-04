package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;

public class EstadoAbierta extends EstadoCarrera {

    public EstadoAbierta(Carrera carrera) {
        super(carrera);
    }

    @Override
    public void abrir() throws MalaPataException {
        throw new MalaPataException("La carrera ya está abierta.");
    }

    @Override
    public void cerrar() throws MalaPataException {
        throw new MalaPataException("No se puede cerrar: hay caballos con dividendo inválido.");
    }

    @Override
    public void finalizar(int numeroGanador) throws MalaPataException {
        throw new MalaPataException("No se puede finalizar: la carrera debe estar Cerrada.");
    }

    @Override
    public void actualizarEstado() {
        for (Participacion p : getCarrera().getParticipaciones()) {
            if (!p.isDividendoValido())
                return;
        }
        getCarrera().cambiarEstado(new EstadoEstable(getCarrera()));
    }

    @Override
    public boolean permiteApuestas() {
        return true;
    }

    @Override
    public boolean esFinalizada() {
        return false;
    }

    @Override
    public boolean esFinalOCerrada() {
        return false;
    }

    @Override
    public String getValor() {
        return "ABIERTA";
    }
}