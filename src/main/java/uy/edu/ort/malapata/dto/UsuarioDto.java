package uy.edu.ort.malapata.dto;

import uy.edu.ort.malapata.modelo.Administrador;
import uy.edu.ort.malapata.modelo.Jugador;

public class UsuarioDto {

    private String usuario;
    private String nombreCompleto;
    private String rol;
    private double saldo;

    public UsuarioDto(Administrador admin) {
        this.usuario = admin.getUsuario();
        this.nombreCompleto = admin.getNombreCompleto();
        this.rol = "ADMINISTRADOR";
    }

    public UsuarioDto(Jugador jugador) {
        this.usuario = jugador.getUsuario();
        this.nombreCompleto = jugador.getNombreCompleto();
        this.rol = "JUGADOR";
        this.saldo = jugador.getSaldo();
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getRol() {
        return rol;
    }

    public double getSaldo() {
        return saldo;
    }
}