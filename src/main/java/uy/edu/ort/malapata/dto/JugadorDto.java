package uy.edu.ort.malapata.dto;

import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.malapata.modelo.Jugador;

public class JugadorDto {

    private String usuario;
    private String nombreCompleto;
    private double saldo;
    private double totalApostado;
    private double totalGanado;

    public JugadorDto(Jugador jugador) {
        this.usuario = jugador.getUsuario();
        this.nombreCompleto = jugador.getNombreCompleto();
        this.saldo = jugador.getSaldo();
        this.totalApostado = jugador.getTotalApostado();
        this.totalGanado = jugador.getTotalGanado();
    }

    public static List<JugadorDto> listaDtos(List<Jugador> lista) {
        List<JugadorDto> dtos = new ArrayList<>();
        for (Jugador j : lista)
            dtos.add(new JugadorDto(j));
        return dtos;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getTotalApostado() {
        return totalApostado;
    }

    public double getTotalGanado() {
        return totalGanado;
    }
}