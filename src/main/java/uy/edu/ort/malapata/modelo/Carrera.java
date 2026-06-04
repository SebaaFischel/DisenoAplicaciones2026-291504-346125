package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;
import java.time.LocalTime;
import java.util.ArrayList;
import uy.edu.ort.malapata.dto.ApuestaDto;

public class Carrera {

    private int numero;
    private String nombre;
    private EstadoCarrera estado;
    private ArrayList<Participacion> participaciones = new ArrayList<>();
    private Participacion ganador;
    private LocalTime horaFinalizacion;

    public Carrera(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
        this.estado = new EstadoDefinida(this);
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public EstadoCarrera getEstado() {
        return estado;
    }

    public ArrayList<Participacion> getParticipaciones() {
        return participaciones;
    }

    public Participacion getGanador() {
        return ganador;
    }

    public LocalTime getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public boolean esFinalizada() {
        return estado.esFinalizada();
    }

    public boolean permiteApuestas() {
        return estado.permiteApuestas();
    }

    protected void cambiarEstado(EstadoCarrera nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void abrir() throws MalaPataException {
        estado.abrir();
    }

    public void cerrar() throws MalaPataException {
        estado.cerrar();
    }

    public void finalizar(int numeroGanador) throws MalaPataException {
        estado.finalizar(numeroGanador);
    }

    protected void ejecutarFinalizacion(Participacion pg) {
        ganador = pg;
        horaFinalizacion = LocalTime.now();
        liquidarApuestas(pg);
    }

    public void agregarParticipacion(Participacion p) {
        participaciones.add(p);
    }

    public Participacion buscarParticipacion(int numero) {
        for (Participacion p : participaciones)
            if (p.getNumero() == numero)
                return p;
        return null;
    }

    public void registrarApuesta(Apuesta apuesta, Participacion participacion, double comision)
            throws MalaPataException {
        if (!permiteApuestas())
            throw new MalaPataException("Esta carrera ya no recibe apuestas");
        participacion.agregarApuesta(apuesta);
        recalcularDividendos(comision);
        estado.actualizarEstado();
    }

    public double getTotalApostado() {
        double total = 0;
        for (Participacion p : participaciones)
            total += p.getTotalApostadoEnParticipacion();
        return total;
    }

    public int getCantidadApuestas() {
        int total = 0;
        for (Participacion p : participaciones)
            total += p.getCantidadApuestas();
        return total;
    }

    public double getTotalPagado() {
        if (!esFinalizada() || ganador == null)
            return 0;
        return ganador.getTotalPagado();
    }

    public ArrayList<Apuesta> getApuestasDelJugador(String usuarioJugador) {
        ArrayList<Apuesta> resultado = new ArrayList<>();
        for (Participacion p : participaciones)
            resultado.addAll(p.getApuestasDelJugador(usuarioJugador));
        return resultado;
    }

    public ArrayList<ApuestaDto> getApuestasDtoDelJugador(String usuarioJugador, String fechaJornada) {
        ArrayList<ApuestaDto> resultado = new ArrayList<>();
        for (Participacion p : participaciones)
            resultado.addAll(p.getApuestasDtoDelJugador(
                    usuarioJugador, nombre, numero, fechaJornada, estado.getValor()));
        return resultado;
    }

    private void recalcularDividendos(double comision) {
        for (Participacion p : participaciones)
            p.recalcularDividendo(getTotalApostado(), comision);
    }

    private void liquidarApuestas(Participacion pg) {
        double totalAlGanador = pg.getTotalApostadoEnParticipacion();
        double dividendoCierre = pg.getValorDividendo();
        for (Apuesta a : pg.getApuestas())
            a.liquidar(dividendoCierre, totalAlGanador);
    }
}