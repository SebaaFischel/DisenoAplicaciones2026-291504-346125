package uy.edu.ort.malapata.fachada;

import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import uy.edu.ort.malapata.dto.ApuestaDto;
import uy.edu.ort.malapata.dto.CarreraDto;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.observador.Observable;
import uy.edu.ort.malapata.modelo.*;
import uy.edu.ort.malapata.sistemas.SistemaApuestas;
import uy.edu.ort.malapata.sistemas.SistemaCarreras;
import uy.edu.ort.malapata.sistemas.SistemaUsuarios;

@Service
public class Fachada extends Observable {

    public enum Eventos {
        cambioEstadoCarrera
    }

    private final SistemaUsuarios sistemaUsuarios;
    private final SistemaCarreras sistemaCarreras;
    private final SistemaApuestas sistemaApuestas;

    public Fachada() {
        this.sistemaUsuarios = new SistemaUsuarios();
        this.sistemaCarreras = new SistemaCarreras();
        this.sistemaApuestas = new SistemaApuestas();
    }

    public void agregarAdministrador(Administrador admin) {
        sistemaUsuarios.agregarAdministrador(admin);
    }

    public void agregarJugador(Jugador jugador) {
        sistemaUsuarios.agregarJugador(jugador);
    }

    public Jornada crearJornada(LocalDate fecha) {
        return sistemaCarreras.crearJornada(fecha);
    }

    public Carrera crearCarrera(Jornada jornada, String nombre) {
        return sistemaCarreras.crearCarrera(jornada, nombre);
    }

    public void agregarParticipacion(Carrera carrera, Participacion participacion) {
        sistemaCarreras.agregarParticipacion(carrera, participacion);
    }

    public Administrador loginAdministrador(String usuario, String contrasena) throws MalaPataException {
        return sistemaUsuarios.loginAdministrador(usuario, contrasena);
    }

    public Jugador loginJugador(String usuario, String contrasena) throws MalaPataException {
        return sistemaUsuarios.loginJugador(usuario, contrasena);
    }

    public void logoutAdministrador(String usuario) {
        sistemaUsuarios.logoutAdministrador(usuario);
    }

    public void logoutJugador(String usuario) {
        sistemaUsuarios.logoutJugador(usuario);
    }

    public Jugador buscarJugador(String usuario) {
        return sistemaUsuarios.buscarJugador(usuario);
    }

    public ArrayList<Jornada> getJornadas() {
        return sistemaCarreras.getJornadas();
    }

    public Jornada getJornadaActual() {
        return sistemaCarreras.getJornadaActual();
    }

    public Jornada getJornadaAnterior(Jornada jornada) {
        return sistemaCarreras.getJornadaAnterior(jornada);
    }

    public Jornada getJornadaSiguiente(Jornada jornada) {
        return sistemaCarreras.getJornadaSiguiente(jornada);
    }

    public int getIdJornada(Jornada jornada) {
        return sistemaCarreras.getIdJornada(jornada);
    }

    public Carrera buscarCarrera(Jornada jornada, int numeroCarrera) {
        return sistemaCarreras.buscarCarrera(jornada, numeroCarrera);
    }

    public void abrirCarrera(Jornada jornada, int numeroCarrera) throws MalaPataException {
        sistemaCarreras.abrirCarrera(jornada, numeroCarrera);
        avisar(Eventos.cambioEstadoCarrera);
    }

    public void cerrarCarrera(Jornada jornada, int numeroCarrera) throws MalaPataException {
        sistemaCarreras.cerrarCarrera(jornada, numeroCarrera);
        avisar(Eventos.cambioEstadoCarrera);
    }

    public void finalizarCarrera(Jornada jornada, int numeroCarrera, int numeroGanador) throws MalaPataException {
        sistemaCarreras.finalizarCarrera(jornada, numeroCarrera, numeroGanador);
        avisar(Eventos.cambioEstadoCarrera);
    }

    public ArrayList<CarreraDto> getCarrerasDtoDisponiblesParaApostar() {
        return sistemaCarreras.getCarrerasDtoDisponiblesParaApostar(getJornadas());
    }

    public double getComision() {
        return ComisionHipodromo.getInstancia().getPorcentaje();
    }

    public ArrayList<String> getModalidades() {
        return sistemaApuestas.getModalidades();
    }

    public Apuesta iniciarApuesta(String usuarioJugador, int idJornada, int numeroCarrera,
            int numeroParticipacion, String nombreModalidad,
            double monto) throws MalaPataException {
        Jugador jugador = sistemaUsuarios.buscarJugador(usuarioJugador);
        if (jugador == null)
            throw new MalaPataException("Jugador no encontrado.");

        ArrayList<Jornada> jornadas = sistemaCarreras.getJornadas();
        if (idJornada < 0 || idJornada >= jornadas.size())
            throw new MalaPataException("Jornada no encontrada.");

        Carrera carrera = jornadas.get(idJornada).buscarCarrera(numeroCarrera);
        if (carrera == null || !carrera.permiteApuestas())
            throw new MalaPataException("Esta carrera ya no recibe apuestas.");

        Participacion participacion = carrera.buscarParticipacion(numeroParticipacion);
        if (participacion == null)
            throw new MalaPataException("Participación no encontrada.");

        return sistemaApuestas.iniciarApuesta(jugador, carrera, participacion, nombreModalidad, monto);
    }

    public void confirmarApuesta(Apuesta apuesta, Participacion participacion,
            Carrera carrera, String contrasena) throws MalaPataException {
        sistemaApuestas.confirmarApuesta(apuesta, participacion, carrera, contrasena);
        avisar(Eventos.cambioEstadoCarrera);
    }

    public ArrayList<ApuestaDto> getApuestasDtoDelJugador(String usuarioJugador) {
        return sistemaApuestas.getApuestasDtoDelJugador(usuarioJugador, getJornadas());
    }

    public ApuestaDto getApuestaDtoConContexto(Apuesta apuesta, Participacion part) {
        return sistemaApuestas.getApuestaDtoConContexto(apuesta, part, getJornadas());
    }
}