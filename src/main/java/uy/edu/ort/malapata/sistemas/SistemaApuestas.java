package uy.edu.ort.malapata.sistemas;

import java.util.ArrayList;
import uy.edu.ort.malapata.dto.ApuestaDto;
import uy.edu.ort.malapata.excepciones.MalaPataException;
import uy.edu.ort.malapata.modelo.*;

public class SistemaApuestas {

    private ArrayList<ModalidadApuesta> modalidades = new ArrayList<>();

    public SistemaApuestas() {
        modalidades.add(new Simple());
        modalidades.add(new Triple());
        modalidades.add(new Super());
    }

    public ArrayList<ModalidadApuesta> getModalidades() { return modalidades; }

    public ModalidadApuesta getModalidad(String nombre) {
        for (ModalidadApuesta m : modalidades) {
            if (m.getNombre().equalsIgnoreCase(nombre)) return m;
        }
        return null;
    }

    public Apuesta iniciarApuesta(Jugador jugador, Carrera carrera,
                                  Participacion participacion, String nombreModalidad,
                                  double monto) throws MalaPataException {
        ModalidadApuesta modalidad = getModalidad(nombreModalidad);
        if (modalidad == null) throw new MalaPataException("Modalidad no encontrada.");

        Apuesta apuesta = new Apuesta(jugador, monto, modalidad);
        apuesta.validarContra(jugador, carrera);
        return apuesta;
    }

    public void confirmarApuesta(Apuesta apuesta, Participacion participacion,
                                 Carrera carrera, String contrasena) throws MalaPataException {
        if (apuesta == null) throw new MalaPataException("No hay apuesta en curso.");
        if (!apuesta.getJugador().validarContrasena(contrasena))
            throw new MalaPataException("Contraseña incorrecta.");
        if (!carrera.permiteApuestas())
            throw new MalaPataException("Esta carrera ya no recibe apuestas.");
        apuesta.getJugador().descontarSaldo(apuesta.calcularCosto());
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
            if (dto != null) return dto;
        }
        return new ApuestaDto(apuesta, part.getCaballo().getNombre(), "", -1, "", "");
    }
}