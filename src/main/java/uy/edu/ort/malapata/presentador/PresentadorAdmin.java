package uy.edu.ort.malapata.presentador;

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
@RequestMapping("/admin")
public class PresentadorAdmin implements Observador {

    private final Fachada fachada;
    private Jornada jornadaActual       = null;
    private Carrera carreraSeleccionada = null;

    public PresentadorAdmin(Fachada fachada) {
        this.fachada = fachada;
        this.fachada.agregarObservador(this);
    }

    @PostMapping("/vistaConectada")
    public Commands vistaConectada(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin) {
        if (admin == null)
            return Commands.create(new Command("usuarioNoAutenticado", "index.html"));
        jornadaActual = fachada.getJornadaActual();
        return Commands.create(
                jornada(jornadaActual),
                habilitarNavegacion(fachada.getJornadaAnterior(jornadaActual) != null,
                        fachada.getJornadaSiguiente(jornadaActual) != null));
    }

    @PostMapping("/jornadaAnterior")
    public Commands jornadaAnterior(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin)
            throws MalaPataException {
        validarSesion(admin);
        Jornada anterior = fachada.getJornadaAnterior(jornadaActual);
        if (anterior == null)
            throw new MalaPataException("No hay jornada anterior.");
        jornadaActual       = anterior;
        carreraSeleccionada = null;
        return Commands.create(
                jornada(jornadaActual),
                habilitarNavegacion(fachada.getJornadaAnterior(jornadaActual) != null,
                        fachada.getJornadaSiguiente(jornadaActual) != null),
                ocultarDetalleCarrera());
    }

    @PostMapping("/jornadaSiguiente")
    public Commands jornadaSiguiente(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin)
            throws MalaPataException {
        validarSesion(admin);
        Jornada siguiente = fachada.getJornadaSiguiente(jornadaActual);
        if (siguiente == null)
            throw new MalaPataException("No hay jornada siguiente.");
        jornadaActual       = siguiente;
        carreraSeleccionada = null;
        return Commands.create(
                jornada(jornadaActual),
                habilitarNavegacion(fachada.getJornadaAnterior(jornadaActual) != null,
                        fachada.getJornadaSiguiente(jornadaActual) != null),
                ocultarDetalleCarrera());
    }

    @PostMapping("/seleccionarCarrera")
    public Commands seleccionarCarrera(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin,
            @RequestParam int numero) throws MalaPataException {
        validarSesion(admin);
        carreraSeleccionada = fachada.buscarCarrera(jornadaActual, numero);
        if (carreraSeleccionada == null)
            throw new MalaPataException("Carrera no encontrada.");
        return Commands.create(detalleCarrera(carreraSeleccionada));
    }

    @PostMapping("/abrirCarrera")
    public Commands abrirCarrera(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin)
            throws MalaPataException {
        validarSesion(admin);
        validarCarreraSeleccionada();
        fachada.abrirCarrera(jornadaActual, carreraSeleccionada.getNumero());
        return Commands.create(
                jornada(jornadaActual),
                detalleCarrera(fachada.buscarCarrera(jornadaActual, carreraSeleccionada.getNumero())),
                mensaje("Carrera abierta correctamente."));
    }

    @PostMapping("/cerrarCarrera")
    public Commands cerrarCarrera(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin)
            throws MalaPataException {
        validarSesion(admin);
        validarCarreraSeleccionada();
        fachada.cerrarCarrera(jornadaActual, carreraSeleccionada.getNumero());
        return Commands.create(
                jornada(jornadaActual),
                detalleCarrera(fachada.buscarCarrera(jornadaActual, carreraSeleccionada.getNumero())),
                mensaje("Carrera cerrada correctamente."));
    }

    @PostMapping("/finalizarCarrera")
    public Commands finalizarCarrera(
            @SessionAttribute(name = "usuarioAdmin", required = false) Administrador admin,
            @RequestParam int numeroGanador) throws MalaPataException {
        validarSesion(admin);
        validarCarreraSeleccionada();
        fachada.finalizarCarrera(jornadaActual, carreraSeleccionada.getNumero(), numeroGanador);
        return Commands.create(
                jornada(jornadaActual),
                detalleCarrera(fachada.buscarCarrera(jornadaActual, carreraSeleccionada.getNumero())),
                mensaje("Carrera finalizada correctamente."));
    }


    private Command ocultarDetalleCarrera() {
        return new Command("ocultarDetalleCarrera", true);
    }

    private Command jornada(Jornada j) {
        int idJornada = fachada.getIdJornada(j);
        return new Command("jornada", j != null ? new JornadaDto(j, idJornada) : null);
    }

    private Command detalleCarrera(Carrera c) {
        int idJornada = fachada.getIdJornada(jornadaActual);
        return new Command("detalleCarrera", new CarreraDto(c, jornadaActual, idJornada));
    }

    private Command habilitarNavegacion(boolean hayAnterior, boolean haySiguiente) {
        return new Command("navegacion", new boolean[]{ hayAnterior, haySiguiente });
    }

    private Command mensaje(String texto) {
        return new Command("mensaje", texto);
    }

    private void validarSesion(Administrador admin) throws MalaPataException {
        if (admin == null) throw new MalaPataException("Sesión no iniciada.");
    }

    private void validarCarreraSeleccionada() throws MalaPataException {
        if (carreraSeleccionada == null)
            throw new MalaPataException("No hay carrera seleccionada.");
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if (Fachada.Eventos.cambioEstadoCarrera.equals(evento))
            System.out.println("Cambio de estado de carrera detectado.");
    }
}