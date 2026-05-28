package uy.edu.ort.malapata.presentador;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import uy.edu.ort.malapata.dto.*;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.fachada.Fachada;
import uy.edu.ort.malapata.modelo.*;
import uy.edu.ort.malapata.observador.Observable;
import uy.edu.ort.malapata.observador.Observador;

@RestController
@Scope("session")
@RequestMapping("/jugador")
public class PresentadorJugador implements Observador {

    private final Fachada fachada;
    private Apuesta apuestaEnCurso = null;
    private Participacion participacionEnCurso = null;
    private Carrera carreraEnCurso = null;

    public PresentadorJugador(Fachada fachada) {
        this.fachada = fachada;
        this.fachada.agregarObservador(this);
    }

    @PostMapping("/vistaConectada")
    public Commands vistaConectada(
            @SessionAttribute(name = "jugador", required = false) Jugador jugador) {
        if (jugador == null)
            return Commands.create(new Command("usuarioNoAutenticado", "index.html"));
        return Commands.create(
                jugador(jugador),
                carrerasDisponibles(),
                modalidades(),
                misApuestas(jugador));
    }

    @PostMapping("/iniciarApuesta")
    public Commands iniciarApuesta(
            @SessionAttribute(name = "jugador", required = false) Jugador jugador,
            @RequestParam int idJornada,
            @RequestParam int numeroCarrera,
            @RequestParam int numeroParticipacion,
            @RequestParam String modalidad,
            @RequestParam double monto) throws MalaPataException {
        validarSesion(jugador);
        if (apuestaEnCurso != null)
            throw new MalaPataException("Debe confirmar o descartar la apuesta anterior");

        apuestaEnCurso = fachada.iniciarApuesta(jugador.getUsuario(), idJornada,
                numeroCarrera, numeroParticipacion, modalidad, monto);
        carreraEnCurso = fachada.getJornadas().get(idJornada).buscarCarrera(numeroCarrera);
        participacionEnCurso = carreraEnCurso.buscarParticipacion(numeroParticipacion);

        return Commands.create(
                mostrarConfirmacion(apuestaEnCurso, participacionEnCurso),
                jugador(jugador));
    }

    @PostMapping("/confirmarApuesta")
    public Commands confirmarApuesta(
            @SessionAttribute(name = "jugador", required = false) Jugador jugador,
            @RequestParam String contrasena) throws MalaPataException {
        validarSesion(jugador);
        if (apuestaEnCurso == null)
            throw new MalaPataException("No hay apuesta en curso");
        fachada.confirmarApuesta(apuestaEnCurso, participacionEnCurso, carreraEnCurso, contrasena);
        apuestaEnCurso = null;
        participacionEnCurso = null;
        carreraEnCurso = null;
        Jugador actualizado = fachada.buscarJugador(jugador.getUsuario());
        return Commands.create(
                ocultarConfirmacion(),
                jugador(actualizado),
                carrerasDisponibles(),
                misApuestas(actualizado),
                mensaje("Apuesta confirmada correctamente."));
    }

    @PostMapping("/descartarApuesta")
    public Commands descartarApuesta(
            @SessionAttribute(name = "jugador", required = false) Jugador jugador)
            throws MalaPataException {
        validarSesion(jugador);
        if (apuestaEnCurso == null)
            throw new MalaPataException("No hay apuesta en curso.");
        apuestaEnCurso = null;
        participacionEnCurso = null;
        carreraEnCurso = null;
        return Commands.create(ocultarConfirmacion(), mensaje("Apuesta descartada."));
    }

    private Command jugador(Jugador jugador) {
        return new Command("jugador", new JugadorDto(jugador));
    }

    private Command carrerasDisponibles() {
        List<CarreraDto> dtos = fachada.getCarrerasDtoDisponiblesParaApostar();
        return new Command("carrerasDisponibles", dtos);
    }

    private Command modalidades() {
        return new Command("modalidades", ModalidadDto.listaDtos(fachada.getModalidades()));
    }

    private Command misApuestas(Jugador jugador) {
        List<ApuestaDto> dtos = fachada.getApuestasDtoDelJugador(jugador.getUsuario());
        return new Command("misApuestas", dtos);
    }

    private Command mostrarConfirmacion(Apuesta apuesta, Participacion part) {
        return new Command("confirmacion", fachada.getApuestaDtoConContexto(apuesta, part));
    }

    private Command ocultarConfirmacion() {
        return new Command("ocultarConfirmacion", true);
    }

    private Command mensaje(String texto) {
        return new Command("mensaje", texto);
    }

    private void validarSesion(Jugador jugador) throws MalaPataException {
        if (jugador == null)
            throw new MalaPataException("Sesión no iniciada.");
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (Fachada.Eventos.cambioEstadoCarrera.equals(evento))
            System.out.println("Cambio de estado detectado.");
    }
}