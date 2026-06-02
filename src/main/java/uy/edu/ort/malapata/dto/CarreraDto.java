package uy.edu.ort.malapata.dto;

import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.malapata.modelo.Carrera;
import uy.edu.ort.malapata.modelo.Jornada;
import uy.edu.ort.malapata.modelo.Participacion;

public class CarreraDto {

    private int numero;
    private String nombre;
    private String estado;
    private double totalApostado;
    private int cantidadApuestas;
    private String ganador;
    private String horaFinalizacion;
    private String fechaJornada;
    private int idJornada;
    private List<ParticipacionDto> participaciones = new ArrayList<>();
    private int cantidadCaballos;
    private double dividendoFinalGanador;

    public CarreraDto(Carrera carrera, Jornada jornada, int idJornada) {
        this.numero = carrera.getNumero();
        this.nombre = carrera.getNombre();
        this.estado = carrera.getEstado().getValor();
        this.totalApostado = carrera.getTotalApostado();
        this.cantidadApuestas = carrera.getCantidadApuestas();
        this.fechaJornada = jornada.getFecha().toString();
        this.idJornada = idJornada;
        this.cantidadCaballos = carrera.getParticipaciones().size();

        if (carrera.getGanador() != null)
            this.ganador = carrera.getGanador().getCaballo().getNombre();

        if (carrera.getHoraFinalizacion() != null)
            this.horaFinalizacion = carrera.getHoraFinalizacion().toString();

        for (Participacion p : carrera.getParticipaciones())
            participaciones.add(new ParticipacionDto(p));

        if (carrera.getGanador() != null)
            this.dividendoFinalGanador = carrera.getGanador().getValorDividendo();
    }

    public static List<CarreraDto> listaDtos(List<Carrera> lista) {
        List<CarreraDto> dtos = new ArrayList<>();
        for (Carrera c : lista)
            dtos.add(new CarreraDto(c, null, -1));
        return dtos;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEstado() {
        return estado;
    }

    public double getTotalApostado() {
        return totalApostado;
    }

    public int getCantidadApuestas() {
        return cantidadApuestas;
    }

    public String getGanador() {
        return ganador;
    }

    public String getHoraFinalizacion() {
        return horaFinalizacion;
    }

    public String getFechaJornada() {
        return fechaJornada;
    }

    public int getIdJornada() {
        return idJornada;
    }

    public List<ParticipacionDto> getParticipaciones() {
        return participaciones;
    }

    public int getCantidadCaballos() {
        return cantidadCaballos;
    }

    public double getDividendoFinalGanador() {
        return dividendoFinalGanador;
    }
}