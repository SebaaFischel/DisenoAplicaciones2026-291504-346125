package uy.edu.ort.malapata.modelo;

public abstract class Usuario {

    private String usuario;
    private String contrasena;
    private String nombreCompleto;

    public Usuario(String usuario, String contrasena, String nombreCompleto) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public boolean validarContrasena(String contrasena) {
        return this.contrasena != null && this.contrasena.equals(contrasena);
    }
}