package uy.edu.ort.malapata.dto;

import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.malapata.modelo.Carrera;
import uy.edu.ort.malapata.modelo.Jornada;

public class JornadaDto {

    private String fecha;
    private double totalApostado;
    private double totalPagado;
    private double totalComisiones;
    private long cantidadFinalizadas;
    private List<CarreraDto> proximasCarreras = new ArrayList<>();
    private List<CarreraDto> carrerasFinalizadas = new ArrayList<>();
    private int cantidadCarreras;
    private int cantidadCarrerasFaltanCorrer;
    private double balanceGeneral;

    public JornadaDto(Jornada jornada, int idJornada) {
        this.fecha = jornada.getFecha().toString();
        this.totalApostado = jornada.getTotalApostado();
        this.totalPagado = jornada.getTotalPagado();
        this.totalComisiones = jornada.getTotalComisiones();
        this.cantidadFinalizadas = jornada.getCantidadFinalizadas();
        this.cantidadCarreras = jornada.getCarreras().size();
        this.cantidadCarrerasFaltanCorrer = jornada.getProximasCarreras().size();
        this.balanceGeneral = jornada.getTotalApostado() - jornada.getTotalPagado();

        for (Carrera c : jornada.getProximasCarreras())
            proximasCarreras.add(new CarreraDto(c, jornada, idJornada));

        for (Carrera c : jornada.getCarrerasFinalizadas())
            carrerasFinalizadas.add(new CarreraDto(c, jornada, idJornada));
    }

    public String getFecha() {
        return fecha;
    }

    public double getTotalApostado() {
        return totalApostado;
    }

    public double getTotalPagado() {
        return totalPagado;
    }

    public double getTotalComisiones() {
        return totalComisiones;
    }

    public long getCantidadFinalizadas() {
        return cantidadFinalizadas;
    }

    public List<CarreraDto> getProximasCarreras() {
        return proximasCarreras;
    }

    public List<CarreraDto> getCarrerasFinalizadas() {
        return carrerasFinalizadas;
    }

    public int getCantidadCarreras() {
        return cantidadCarreras;
    }

    public int getCantidadFaltanCorrer() {
        return cantidadCarrerasFaltanCorrer;
    }

    public double getBalanceGeneral() {
        return balanceGeneral;
    }
}
