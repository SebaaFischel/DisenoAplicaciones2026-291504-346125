package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;

public class EstadoCerrada extends EstadoCarrera {

    public EstadoCerrada(Carrera carrera) {
        super(carrera);
    }

    @Override
    public void abrir() throws MalaPataException {
        throw new MalaPataException("Una carrera cerrada no puede volver a abrirse.");
    }

    @Override
    public void cerrar() throws MalaPataException {
        throw new MalaPataException("La carrera ya está cerrada.");
    }

    @Override
    public void finalizar(int numeroGanador) throws MalaPataException {
        Participacion pg = getCarrera().buscarParticipacion(numeroGanador);
        if (pg == null)
            throw new MalaPataException("Debe indicar el caballo ganador de la carrera.");
        getCarrera().ejecutarFinalizacion(pg);
        getCarrera().cambiarEstado(new EstadoFinalizada(getCarrera()));
    }

    @Override
    public void actualizarEstado() {
    }

    @Override
    public boolean permiteApuestas() {
        return false;
    }

    @Override
    public boolean esFinalizada() {
        return false;
    }

    @Override
    public boolean esFinalOCerrada() {
        return true;
    }

    @Override
    public String getValor() {
        return "CERRADA";
    }
}