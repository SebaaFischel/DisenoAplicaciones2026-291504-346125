package uy.edu.ort.malapata.datosPrueba;

import java.time.LocalDate;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.fachada.Fachada;
import uy.edu.ort.malapata.modelo.*;

public class DatosPrueba {

    public static void cargar(Fachada fachada) throws MalaPataException {
        cargarUsuarios(fachada);
        cargarCaballosYCarreras(fachada);
    }

    private static void cargarUsuarios(Fachada fachada) {
        fachada.agregarAdministrador(new Administrador("a1", "a1", "Usuario Administrador"));
        fachada.agregarAdministrador(new Administrador("a2", "a2", "Administrador Dos"));

        fachada.agregarJugador(new Jugador("j1", "j1", "Usuario Jugador", 2000));
        fachada.agregarJugador(new Jugador("j2", "j2", "Jugador Dos", 5000));
        fachada.agregarJugador(new Jugador("j3", "j3", "Jugador Tres", 8000));
    }

    private static void cargarCaballosYCarreras(Fachada fachada) throws MalaPataException {
        Caballo relampago  = new Caballo("Relámpago");
        Caballo tornado    = new Caballo("Tornado");
        Caballo ventisca   = new Caballo("Ventisca");
        Caballo centelleo  = new Caballo("Centelleo");
        Caballo borrascoso = new Caballo("Borrascoso");
        Caballo fugaz      = new Caballo("Fugaz");

        // 3 carreras hoy en estado Definida
        Jornada jornadaHoy = fachada.crearJornada(LocalDate.now());

        Carrera c1 = fachada.crearCarrera(jornadaHoy, "Gran Premio");
        fachada.agregarParticipacion(c1, new Participacion(1, relampago));
        fachada.agregarParticipacion(c1, new Participacion(2, tornado));
        fachada.agregarParticipacion(c1, new Participacion(3, ventisca));

        Carrera c2 = fachada.crearCarrera(jornadaHoy, "Copa de Oro");
        fachada.agregarParticipacion(c2, new Participacion(1, centelleo));
        fachada.agregarParticipacion(c2, new Participacion(2, borrascoso));

        Carrera c3 = fachada.crearCarrera(jornadaHoy, "Clásico Sur");
        fachada.agregarParticipacion(c3, new Participacion(1, fugaz));
        fachada.agregarParticipacion(c3, new Participacion(2, relampago));
        fachada.agregarParticipacion(c3, new Participacion(3, tornado));

        // 2 carreras semana pasada en estado Cerrada con apuestas
        Jornada jornadaPasada = fachada.crearJornada(LocalDate.now().minusWeeks(1));

        Carrera c4 = fachada.crearCarrera(jornadaPasada, "Premio Clásico");
        fachada.agregarParticipacion(c4, new Participacion(1, relampago));
        fachada.agregarParticipacion(c4, new Participacion(2, tornado));
        fachada.agregarParticipacion(c4, new Participacion(3, ventisca));
        cargarApuestasYCerrar(c4, fachada);

        Carrera c5 = fachada.crearCarrera(jornadaPasada, "Gran Clásico Norte");
        fachada.agregarParticipacion(c5, new Participacion(1, centelleo));
        fachada.agregarParticipacion(c5, new Participacion(2, borrascoso));
        fachada.agregarParticipacion(c5, new Participacion(3, fugaz));
        cargarApuestasYCerrar(c5, fachada);

        // 1 carrera semana próxima en estado Definida
        Jornada jornadaFutura = fachada.crearJornada(LocalDate.now().plusWeeks(1));
        Carrera c6 = fachada.crearCarrera(jornadaFutura, "Copa Futura");
        fachada.agregarParticipacion(c6, new Participacion(1, centelleo));
        fachada.agregarParticipacion(c6, new Participacion(2, fugaz));
    }
private static void cargarApuestasYCerrar(Carrera carrera, Fachada fachada)
        throws MalaPataException {
    carrera.abrir();

    Jugador[] jugadores = {
        new Jugador("jt1",  "jt1",  "Jugador jt1",  999999),
        new Jugador("jt2",  "jt2",  "Jugador jt2",  999999),
        new Jugador("jt3",  "jt3",  "Jugador jt3",  999999),
        new Jugador("jt4",  "jt4",  "Jugador jt4",  999999),
        new Jugador("jt5",  "jt5",  "Jugador jt5",  999999),
        new Jugador("jt6",  "jt6",  "Jugador jt6",  999999),
        new Jugador("jt7",  "jt7",  "Jugador jt7",  999999),
        new Jugador("jt8",  "jt8",  "Jugador jt8",  999999),
        new Jugador("jt9",  "jt9",  "Jugador jt9",  999999),
        new Jugador("jt10", "jt10", "Jugador jt10", 999999),
        new Jugador("jt11", "jt11", "Jugador jt11", 999999),
        new Jugador("jt12", "jt12", "Jugador jt12", 999999)
    };

    int[] cantidades = { 12, 10, 10 };
    double[] montos  = { 3000, 2000, 1500 };

    for (int i = 0; i < carrera.getParticipaciones().size(); i++) {
        Participacion p = carrera.getParticipaciones().get(i);
        int cant = cantidades[Math.min(i, cantidades.length - 1)];
        double m = montos[Math.min(i, montos.length - 1)];
        for (int j = 0; j < cant; j++) {
            Jugador jug = jugadores[j % jugadores.length];
            Apuesta a = new ApuestaSimple(jug, m);
            jug.descontarSaldo(a.calcularDescuento());
            carrera.registrarApuesta(a, p, ComisionHipodromo.getInstancia().getPorcentaje());
        }
    }

    carrera.cerrar();
}
}