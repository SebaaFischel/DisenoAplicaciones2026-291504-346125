package uy.edu.ort.malapata.modelo;

public abstract class ModalidadApuesta {

    private String nombre;

    public ModalidadApuesta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public abstract double calcularCosto(double montoBase);

    public abstract double calcularPago(double montoBase, double dividendo, double totalApostadoAlCaballo);
}