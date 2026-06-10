package uy.edu.ort.malapata.sistemas;

import java.util.ArrayList;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.modelo.Administrador;
import uy.edu.ort.malapata.modelo.Jugador;
import uy.edu.ort.malapata.modelo.Sesion;

public class SistemaUsuarios {

    private ArrayList<Administrador> administradores;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Sesion> sesiones;

    public SistemaUsuarios() {
        this.administradores = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.sesiones = new ArrayList<>();
    }

    public void agregarAdministrador(Administrador admin) {
        administradores.add(admin);
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public Administrador loginAdministrador(String usuario, String contrasena) throws MalaPataException {
        Administrador admin = buscarAdministrador(usuario);
        if (admin == null || !admin.validarContrasena(contrasena))
            throw new MalaPataException("Acceso denegado");
        if (buscarSesion(usuario) != null)
            throw new MalaPataException("El usuario ya tiene una sesión activa.");
        sesiones.add(new Sesion(admin));
        return admin;
    }

    public Jugador loginJugador(String usuario, String contrasena) throws MalaPataException {
        Jugador jugador = buscarJugador(usuario);
        if (jugador == null || !jugador.validarContrasena(contrasena))
            throw new MalaPataException("Acceso denegado");
        if (buscarSesion(usuario) != null)
            throw new MalaPataException("El usuario ya tiene una sesión activa.");
        sesiones.add(new Sesion(jugador));
        return jugador;
    }

    public void logoutAdministrador(String usuario) {
        Sesion sesion = buscarSesion(usuario);
        if (sesion != null) sesiones.remove(sesion);
    }

        public void logoutJugador(String usuario) {
        Sesion sesion = buscarSesion(usuario);
        if (sesion != null) sesiones.remove(sesion);
    }

    public Administrador buscarAdministrador(String usuario) {
        for (Administrador admin : administradores) {
            if (admin.getUsuario().equals(usuario)) return admin;
        }
        return null;
    }

    public Jugador buscarJugador(String usuario) {
        for (Jugador j : jugadores) {
            if (j.getUsuario().equals(usuario)) return j;
        }
        return null;
    }

    private Sesion buscarSesion(String usuario) {
        for (Sesion s : sesiones) {
            if (s.getUsuario().getUsuario().equals(usuario)) return s;
        }
        return null;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public ArrayList<Sesion> getSesiones() {
        return sesiones;
    }
}