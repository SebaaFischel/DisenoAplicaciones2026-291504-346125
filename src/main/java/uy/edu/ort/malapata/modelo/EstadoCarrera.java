package uy.edu.ort.malapata.modelo;

public class EstadoCarrera {

    public static final String DEFINIDA = "DEFINIDA";
    public static final String ABIERTA = "ABIERTA";
    public static final String ESTABLE = "ESTABLE";
    public static final String CERRADA = "CERRADA";
    public static final String FINALIZADA = "FINALIZADA";

    private String valor;

    public EstadoCarrera(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public boolean esAbiertaOEstable() {
        return valor.equals(ABIERTA) || valor.equals(ESTABLE);
    }

    @Override
    public String toString() {
        return valor;
    }
}