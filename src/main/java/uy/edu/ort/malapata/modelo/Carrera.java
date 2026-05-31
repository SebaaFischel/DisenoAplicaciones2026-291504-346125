package uy.edu.ort.malapata.modelo;

import uy.edu.ort.malapata.excepciones.MalaPataException;
import java.time.LocalTime;
import java.util.ArrayList;
import uy.edu.ort.malapata.dto.ApuestaDto;
import uy.edu.ort.malapata.observador.Observable;

public class Carrera extends Observable {

    public enum Eventos { cambioEstado }

    private int numero;
    private String nombre;
    private EstadoCarrera estado;
    private ArrayList<Participacion> participaciones = new ArrayList<>();
    private Participacion ganador;
    private LocalTime horaFinalizacion;

    public Carrera(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
        this.estado = new EstadoCarrera(EstadoCarrera.DEFINIDA);
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

    public void agregarParticipacion(Participacion p) {
        participaciones.add(p);
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

    public ArrayList<Apuesta> getApuestasDelJugador(String usuarioJugador) {
        ArrayList<Apuesta> resultado = new ArrayList<>();
        for (Participacion p : participaciones) {
            resultado.addAll(p.getApuestasDelJugador(usuarioJugador));
        }
        return resultado;
    }

    public ArrayList<ApuestaDto> getApuestasDtoDelJugador(String usuarioJugador, String fechaJornada) {
        ArrayList<ApuestaDto> resultado = new ArrayList<>();
        for (Participacion p : participaciones) {
            resultado.addAll(p.getApuestasDtoDelJugador(
                    usuarioJugador,
                    nombre,
                    numero,
                    fechaJornada,
                    estado.getValor()));
        }
        return resultado;
    }

    public void abrir() throws MalaPataException {
        if (!estado.puedeAbrirse())
            throw new MalaPataException("No se puede abrir esta carrera.");
        estado = new EstadoCarrera(EstadoCarrera.ABIERTA);
        for (Participacion p : participaciones)
            p.invalidarDividendo();
        avisar(Eventos.cambioEstado);
    }

    public void cerrar() throws MalaPataException {
        if (!estado.puedeCerrarse())
            throw new MalaPataException("No es posible cerrar esta carrera");
        estado = new EstadoCarrera(EstadoCarrera.CERRADA);
        avisar(Eventos.cambioEstado);
    }

    public void finalizar(int numeroGanador) throws MalaPataException {
        if (!estado.puedeFinalizarse())
            throw new MalaPataException("No se puede finalizar esta carrera.");
        Participacion pg = buscarParticipacion(numeroGanador);
        if (pg == null)
            throw new MalaPataException("Debe indicar el caballo ganador de la carrera");
        ganador = pg;
        horaFinalizacion = LocalTime.now();
        estado = new EstadoCarrera(EstadoCarrera.FINALIZADA);
        liquidarApuestas(pg);
        avisar(Eventos.cambioEstado);
    }

    public void registrarApuesta(Apuesta apuesta, Participacion participacion, double comision)
            throws MalaPataException {
        if (!permiteApuestas())
            throw new MalaPataException("Esta carrera ya no recibe apuestas");
        participacion.agregarApuesta(apuesta);
        recalcularDividendos(comision);
        actualizarEstado();
        avisar(Eventos.cambioEstado);
    }

    public Participacion buscarParticipacion(int numero) {
        for (Participacion p : participaciones) {
            if (p.getNumero() == numero)
                return p;
        }
        return null;
    }

    public boolean permiteApuestas() {
        return estado.permiteApuestas();
    }

    private void recalcularDividendos(double comision) {
        for (Participacion p : participaciones) {
            p.recalcularDividendo(getTotalApostado(), comision);
        }
    }

    private void actualizarEstado() {
    if (estado.esFinalOCerrada())
        return;
    boolean todosValidos = true;
for (Participacion p : participaciones) {
    if (!p.isDividendoValido()) {
        todosValidos = false;
        break;
    }
}
    if (todosValidos && estado.esAbierta())
        estado = new EstadoCarrera(EstadoCarrera.ESTABLE);
    if (!todosValidos && estado.esEstable())
        estado = new EstadoCarrera(EstadoCarrera.ABIERTA);
}

    private void liquidarApuestas(Participacion pg) {
        double totalAlGanador = pg.getTotalApostadoEnParticipacion();
        double dividendoCierre = pg.getValorDividendo();
        for (Apuesta a : pg.getApuestas()) {
            a.liquidar(dividendoCierre, totalAlGanador);
        }
    }

public double getTotalPagado() {
    if (!esFinalizada() || ganador == null)
        return 0;
    return ganador.getTotalPagado();
}
}