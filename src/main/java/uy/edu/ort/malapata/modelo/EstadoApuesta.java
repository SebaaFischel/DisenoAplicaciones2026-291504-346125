package uy.edu.ort.malapata.modelo;

public class EstadoApuesta {

    public static final String PENDIENTE = "PENDIENTE";
    public static final String LIQUIDADA = "LIQUIDADA";

    private String valor;

    public EstadoApuesta(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public boolean estaLiquidada() {
        return valor.equals(LIQUIDADA);
    }

    @Override
    public String toString() {
        return valor;
    }
}