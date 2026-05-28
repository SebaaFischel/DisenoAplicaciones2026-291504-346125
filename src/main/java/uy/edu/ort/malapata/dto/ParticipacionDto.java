package uy.edu.ort.malapata.dto;

import uy.edu.ort.malapata.modelo.Participacion;

public class ParticipacionDto {

    private int numero;
    private String caballo;
    private double dividendo;
    private boolean dividendoValido;
    private double totalApostado;
    private int cantidadApuestas;

    public ParticipacionDto(Participacion p) {
        this.numero = p.getNumero();
        this.caballo = p.getCaballo().getNombre();
        this.dividendo = p.getValorDividendo();
        this.dividendoValido = p.isDividendoValido();
        this.totalApostado = p.getTotalApostadoEnParticipacion();
        this.cantidadApuestas = p.getCantidadApuestas();
    }

    public int getNumero() {
        return numero;
    }

    public String getCaballo() {
        return caballo;
    }

    public double getDividendo() {
        return dividendo;
    }

    public boolean isDividendoValido() {
        return dividendoValido;
    }

    public double getTotalApostado() {
        return totalApostado;
    }

    public int getCantidadApuestas() {
        return cantidadApuestas;
    }
}