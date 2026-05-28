package uy.edu.ort.malapata.dto;

import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.malapata.modelo.Apuesta;
import uy.edu.ort.malapata.modelo.Jornada;

public class ApuestaDto {

    private String caballo;
    private String carrera;
    private String modalidad;
    private double monto;
    private double costo;
    private double dividendoAlCierre;
    private double montoCobrado;
    private boolean liquidada;
    private int numeroCarrera;
    private String fechaJornada;
    private String estadoCarrera;

    public ApuestaDto(Apuesta apuesta, String nombreCaballo, String nombreCarrera,
            int numeroCarrera, String fechaJornada, String estadoCarrera) {
        this.caballo = nombreCaballo;
        this.carrera = nombreCarrera;
        this.modalidad = apuesta.getModalidad().getNombre();
        this.monto = apuesta.getMonto();
        this.costo = apuesta.calcularCosto();
        this.dividendoAlCierre = apuesta.getDividendoAlCierre();
        this.montoCobrado = apuesta.getMontoCobrado();
        this.liquidada = apuesta.isLiquidada();
        this.numeroCarrera = numeroCarrera;
        this.fechaJornada = fechaJornada;
        this.estadoCarrera = estadoCarrera;
    }

    public static List<ApuestaDto> listaDtos(List<Apuesta> lista,
            String nombreCaballo,
            String nombreCarrera) {
        List<ApuestaDto> dtos = new ArrayList<>();
        for (Apuesta a : lista)
            dtos.add(new ApuestaDto(a, nombreCaballo, nombreCarrera, -1, "", ""));
        return dtos;
    }

    public String getCaballo() {
        return caballo;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getModalidad() {
        return modalidad;
    }

    public double getMonto() {
        return monto;
    }

    public double getCosto() {
        return costo;
    }

    public double getDividendoAlCierre() {
        return dividendoAlCierre;
    }

    public double getMontoCobrado() {
        return montoCobrado;
    }

    public boolean isLiquidada() {
        return liquidada;
    }

    public int getNumeroCarrera() {
        return numeroCarrera;
    }

    public String getFechaJornada() {
        return fechaJornada;
    }

    public String getEstadoCarrera() {
        return estadoCarrera;
    }
}