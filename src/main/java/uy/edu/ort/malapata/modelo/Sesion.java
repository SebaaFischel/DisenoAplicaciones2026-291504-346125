package uy.edu.ort.malapata.modelo;

import java.util.Date;

public class Sesion {

    private Date fechaIngreso = new Date();
    private Usuario usuario ;

    public Sesion(Usuario  usuario) {
        this.usuario  = usuario ;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public Usuario getUsuario() {
        return usuario ;
    }
}