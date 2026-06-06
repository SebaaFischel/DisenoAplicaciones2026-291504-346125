package uy.edu.ort.malapata.datosPrueba;

import java.time.LocalDate;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.fachada.Fachada;
import uy.edu.ort.malapata.modelo.*;
import java.util.ArrayList;

public class DatosPrueba {

    public static void cargar(Fachada fachada) throws MalaPataException {
        cargarUsuarios(fachada);
        cargarCarreras(fachada);
    }

    private static void cargarUsuarios(Fachada fachada) {
        fachada.agregarAdministrador(new Administrador("a1", "a1", "Usuario Administrador"));
        fachada.agregarAdministrador(new Administrador("a2", "a2", "Administrador Dos"));

        fachada.agregarJugador(new Jugador("j1", "j1", "Usuario Jugador", 2000));
        fachada.agregarJugador(new Jugador("j2", "j2", "Jugador Dos", 50000));
        fachada.agregarJugador(new Jugador("j3", "j3", "Jugador Tres", 80000));
        fachada.agregarJugador(new Jugador("j4", "j4", "Jugador Cuatro", 60000));
        fachada.agregarJugador(new Jugador("j5", "j5", "Jugador Cinco", 40000));
        fachada.agregarJugador(new Jugador("j6", "j6", "Jugador Seis", 30000));
        fachada.agregarJugador(new Jugador("j7", "j7", "Jugador Siete", 70000));
        fachada.agregarJugador(new Jugador("j8", "j8", "Jugador Ocho", 90000));
        fachada.agregarJugador(new Jugador("j9", "j9", "Jugador Nueve", 55000));
        fachada.agregarJugador(new Jugador("j10", "j10", "Jugador Diez", 45000));
        fachada.agregarJugador(new Jugador("j11", "j11", "Jugador Once", 35000));
        fachada.agregarJugador(new Jugador("j12", "j12", "Jugador Doce", 65000));
    }

    private static void cargarCarreras(Fachada fachada) throws MalaPataException {
        Caballo relampago  = new Caballo("Relámpago");
        Caballo tornado    = new Caballo("Tornado");
        Caballo ventisca   = new Caballo("Ventisca");
        Caballo centelleo  = new Caballo("Centelleo");
        Caballo borrascoso = new Caballo("Borrascoso");
        Caballo fugaz      = new Caballo("Fugaz");

        cargarCarrerasHoy(fachada, relampago, tornado, ventisca, centelleo, borrascoso, fugaz);
        cargarCarrerasPasadas(fachada, relampago, tornado, ventisca, centelleo, borrascoso, fugaz);
        cargarCarreraFutura(fachada, centelleo, fugaz);
    }

    private static void cargarCarrerasHoy(Fachada fachada,
            Caballo relampago, Caballo tornado, Caballo ventisca,
            Caballo centelleo, Caballo borrascoso, Caballo fugaz) {
        Jornada hoy = fachada.crearJornada(LocalDate.now());

        Carrera c1 = fachada.crearCarrera(hoy, "Gran Premio");
        fachada.agregarParticipacion(c1, new Participacion(1, relampago));
        fachada.agregarParticipacion(c1, new Participacion(2, tornado));
        fachada.agregarParticipacion(c1, new Participacion(3, ventisca));

        Carrera c2 = fachada.crearCarrera(hoy, "Copa de Oro");
        fachada.agregarParticipacion(c2, new Participacion(1, centelleo));
        fachada.agregarParticipacion(c2, new Participacion(2, borrascoso));

        Carrera c3 = fachada.crearCarrera(hoy, "Clásico Sur");
        fachada.agregarParticipacion(c3, new Participacion(1, fugaz));
        fachada.agregarParticipacion(c3, new Participacion(2, relampago));
        fachada.agregarParticipacion(c3, new Participacion(3, tornado));
    }

    private static void cargarCarrerasPasadas(Fachada fachada,
            Caballo relampago, Caballo tornado, Caballo ventisca,
            Caballo centelleo, Caballo borrascoso, Caballo fugaz)
            throws MalaPataException {
        Jornada pasada = fachada.crearJornada(LocalDate.now().minusWeeks(1));

        Carrera c4 = fachada.crearCarrera(pasada, "Premio Clásico");
        fachada.agregarParticipacion(c4, new Participacion(1, relampago));
        fachada.agregarParticipacion(c4, new Participacion(2, tornado));
        fachada.agregarParticipacion(c4, new Participacion(3, ventisca));
        cargarApuestasYCerrar(c4, fachada);

        Carrera c5 = fachada.crearCarrera(pasada, "Gran Clásico Norte");
        fachada.agregarParticipacion(c5, new Participacion(1, centelleo));
        fachada.agregarParticipacion(c5, new Participacion(2, borrascoso));
        fachada.agregarParticipacion(c5, new Participacion(3, fugaz));
        cargarApuestasYCerrar(c5, fachada);
    }

    private static void cargarCarreraFutura(Fachada fachada,
            Caballo centelleo, Caballo fugaz) {
        Jornada futura = fachada.crearJornada(LocalDate.now().plusWeeks(1));
        Carrera c6 = fachada.crearCarrera(futura, "Copa Futura");
        fachada.agregarParticipacion(c6, new Participacion(1, centelleo));
        fachada.agregarParticipacion(c6, new Participacion(2, fugaz));
    }

    private static void cargarApuestasYCerrar(Carrera carrera, Fachada fachada)
            throws MalaPataException {
        carrera.abrir();
        ArrayList<Jugador> jugadores = obtenerJugadores(fachada);
        cargarApuestasPorParticipacion(carrera, jugadores);
        carrera.cerrar();
    }

    private static ArrayList<Jugador> obtenerJugadores(Fachada fachada) {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        for (int i = 2; i <= 12; i++)
            jugadores.add(fachada.buscarJugador("j" + i));
        return jugadores;
    }

    private static void cargarApuestasPorParticipacion(Carrera carrera,
            ArrayList<Jugador> jugadores) throws MalaPataException {
        double comision = ComisionHipodromo.getInstancia().getPorcentaje();
        int[] cantidades = { 11, 10, 10 };
        double[] montos  = { 3000, 2000, 1500 };

        for (int i = 0; i < carrera.getParticipaciones().size(); i++) {
            Participacion p = carrera.getParticipaciones().get(i);
            int cant  = cantidades[Math.min(i, cantidades.length - 1)];
            double m  = montos[Math.min(i, montos.length - 1)];
            for (int j = 0; j < cant; j++) {
                Jugador jug = jugadores.get(j % jugadores.size());
                Apuesta a = new ApuestaSimple(jug, m);
                jug.descontarSaldo(a.calcularDescuento());
                carrera.registrarApuesta(a, p, comision);
            }
        }
    }
}