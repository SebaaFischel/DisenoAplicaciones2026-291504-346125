package uy.edu.ort.malapata.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import uy.edu.ort.malapata.dto.ApuestaDto;
import uy.edu.ort.malapata.dto.CarreraDto;

public class Jornada {

    private LocalDate fecha;
    private ArrayList<Carrera> carreras = new ArrayList<>();

    public Jornada(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public ArrayList<Carrera> getCarreras() {
        return carreras;
    }

    public void agregarCarrera(Carrera carrera) {
        carreras.add(carrera);
    }

    public int proximoNumeroCarrera() {
        return carreras.size() + 1;
    }

    public double getTotalApostado() {
        double total = 0;
        for (Carrera c : carreras)
            total += c.getTotalApostado();
        return total;
    }

    public double getTotalPagado() {
        double total = 0;
        for (Carrera c : carreras)
            total += c.getTotalPagado();
        return total;
    }

    public double getTotalComisiones() {
        double comision = ComisionHipodromo.getInstancia().getPorcentaje();
        return getTotalApostado() * (comision / 100.0);
    }

    public ArrayList<Carrera> getCarrerasDisponiblesParaApostar() {
        ArrayList<Carrera> ret = new ArrayList<>();
        for (Carrera c : carreras) {
            if (c.permiteApuestas()) {
                ret.add(c);
            }
        }
        return ret;
    }

    public ArrayList<CarreraDto> getCarrerasDtoDisponiblesParaApostar(int idJornada) {
        ArrayList<CarreraDto> ret = new ArrayList<>();
        for (Carrera c : carreras) {
            if (c.permiteApuestas()) {
                ret.add(new CarreraDto(c, this, idJornada));
            }
        }
        return ret;
    }

    public ArrayList<Apuesta> getApuestasDelJugador(String usuarioJugador) {
        ArrayList<Apuesta> ret = new ArrayList<>();
        for (Carrera c : carreras) {
            ret.addAll(c.getApuestasDelJugador(usuarioJugador));
        }
        return ret;
    }

    public ArrayList<ApuestaDto> getApuestasDtoDelJugador(String usuarioJugador) {
        ArrayList<ApuestaDto> ret = new ArrayList<>();
        String fechaJornada = fecha.toString();
        for (Carrera c : carreras) {
            ret.addAll(c.getApuestasDtoDelJugador(usuarioJugador, fechaJornada));
        }
        return ret;
    }

public ApuestaDto getApuestaDtoConContexto(Apuesta apuesta, Participacion participacion) {
    String fechaJornada = fecha.toString();
    for (Carrera c : carreras) {
        if (c.getParticipaciones().contains(participacion)) {
            return new ApuestaDto(
                    apuesta,
                    participacion.getCaballo().getNombre(),
                    participacion.getNumero(),
                    c.getNombre(),
                    c.getNumero(),
                    fechaJornada,
                    c.getEstado().getValor());
        }
    }
    return null;
}

public int getCantidadFinalizadas() {
    return getCarrerasFinalizadas().size();
}

    public ArrayList<Carrera> getCarrerasFinalizadas() {
        ArrayList<Carrera> ret = new ArrayList<>();
        for (Carrera c : carreras) {
            if (c.esFinalizada())
                ret.add(0, c);
        }
        return ret;
    }

    public Carrera buscarCarrera(int numero) {
        for (Carrera c : carreras) {
            if (c.getNumero() == numero)
                return c;
        }
        return null;
    }

    public ArrayList<Carrera> getProximasCarreras() {
        ArrayList<Carrera> ret = new ArrayList<>();
        for (Carrera c : carreras) {
            if (!c.esFinalizada())
                ret.add(c);
        }
        return ret;
    }
}