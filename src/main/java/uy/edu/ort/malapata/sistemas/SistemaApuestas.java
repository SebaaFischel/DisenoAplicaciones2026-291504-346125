package uy.edu.ort.malapata.sistemas;

import java.util.ArrayList;
import uy.edu.ort.malapata.dto.ApuestaDto;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.modelo.*;

public class SistemaApuestas {

    private ArrayList<Modalidad> modalidades = new ArrayList<>();

    public SistemaApuestas() {
        modalidades.add(new ModalidadSimple());
        modalidades.add(new ModalidadTriple());
        modalidades.add(new ModalidadSuper());
    }

    public ArrayList<Modalidad> getModalidades() {
        return modalidades;
    }

    public Apuesta iniciarApuesta(String usuarioJugador, int idJornada, int numeroCarrera,
            int numeroParticipacion, String nombreModalidad, double monto,
            SistemaUsuarios sistemaUsuarios, SistemaCarreras sistemaCarreras)
            throws MalaPataException {

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

        Modalidad modalidad = buscarModalidad(nombreModalidad);
        Apuesta apuesta = modalidad.crearApuesta(jugador, monto, participacion);
        apuesta.validar(jugador, carrera);
        return apuesta;
    }

        private Modalidad buscarModalidad(String nombre) throws MalaPataException {
        for (Modalidad m : modalidades) {
            if (m.getNombre().equals(nombre))
                return m;
        }
        throw new MalaPataException("Modalidad no encontrada.");
    }

    public void confirmarApuesta(Apuesta apuesta, Participacion participacion,
            Carrera carrera, String contrasena) throws MalaPataException {
        if (apuesta == null)
            throw new MalaPataException("No hay apuesta en curso.");
        if (!apuesta.getJugador().validarContrasena(contrasena))
            throw new MalaPataException("Contraseña incorrecta.");
        if (!carrera.permiteApuestas())
            throw new MalaPataException("Esta carrera ya no recibe apuestas.");
        apuesta.getJugador().descontarSaldo(apuesta.calcularDescuento());
        double comision = ComisionHipodromo.getInstancia().getPorcentaje();
        carrera.registrarApuesta(apuesta, participacion, comision);
    }

    public ArrayList<ApuestaDto> getApuestasDtoDelJugador(String usuarioJugador,
            ArrayList<Jornada> jornadas) {
        ArrayList<ApuestaDto> dtos = new ArrayList<>();
        for (Jornada j : jornadas)
            dtos.addAll(j.getApuestasDtoDelJugador(usuarioJugador));
        return dtos;
    }

    public ApuestaDto getApuestaDtoConContexto(Apuesta apuesta, Participacion part,
            ArrayList<Jornada> jornadas) {
        for (Jornada j : jornadas) {
            ApuestaDto dto = j.getApuestaDtoConContexto(apuesta, part);
            if (dto != null)
                return dto;
        }
        return new ApuestaDto(apuesta, part.getCaballo().getNombre(), part.getNumero(), "", -1, "", "");
    }
}