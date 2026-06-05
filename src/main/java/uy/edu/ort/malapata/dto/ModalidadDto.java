package uy.edu.ort.malapata.dto;

import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.malapata.modelo.Modalidad;

public class ModalidadDto {

    private String nombre;

    public ModalidadDto(Modalidad modalidad) {
        this.nombre = modalidad.getNombre();
    }

    public static List<ModalidadDto> listaDtos(List<Modalidad> lista) {
        List<ModalidadDto> dtos = new ArrayList<>();
        for (Modalidad m : lista)
            dtos.add(new ModalidadDto(m));
        return dtos;
    }

    public String getNombre() { return nombre; }
}