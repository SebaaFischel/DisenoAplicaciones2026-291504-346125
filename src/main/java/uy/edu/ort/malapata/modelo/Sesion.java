package uy.edu.ort.malapata.modelo;

import java.util.Date;

public class Sesion {

    private Date fechaIngreso = new Date();
    private Administrador administrador;

    public Sesion(Administrador administrador) {
        this.administrador = administrador;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public Administrador getAdministrador() {
        return administrador;
    }
}