package uy.edu.ort.malapata.dto;

import uy.edu.ort.malapata.modelo.Participacion;

public class ParticipacionDto {

    private int numero;
    private String caballo;
    private double dividendo;
    private String dividendoValido;  
    private double totalApostado;
    private int cantidadApuestas;

    public ParticipacionDto(Participacion p) {
        this.numero           = p.getNumero();
        this.caballo          = p.getCaballo().getNombre();
        this.dividendo        = redondear(p.getValorDividendo());
        this.dividendoValido  = p.isDividendoValido() ? "Sí" : "No";
        this.totalApostado    = redondear(p.getTotalApostadoEnParticipacion());
        this.cantidadApuestas = p.getCantidadApuestas();
    }

    private double redondear(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public int getNumero()           { return numero; }
    public String getCaballo()       { return caballo; }
    public double getDividendo()     { return dividendo; }
    public String getDividendoValido(){ return dividendoValido; }
    public double getTotalApostado() { return totalApostado; }
    public int getCantidadApuestas() { return cantidadApuestas; }
}