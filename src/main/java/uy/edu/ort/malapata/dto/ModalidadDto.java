package uy.edu.ort.malapata.dto;

import java.util.ArrayList;
import java.util.List;
import uy.edu.ort.malapata.modelo.ModalidadApuesta;

public class ModalidadDto {

    private String nombre;

    public ModalidadDto(ModalidadApuesta modalidad) {
        this.nombre = modalidad.getNombre();
    }

    public static List<ModalidadDto> listaDtos(List<ModalidadApuesta> lista) {
        List<ModalidadDto> dtos = new ArrayList<>();
        for (ModalidadApuesta m : lista) dtos.add(new ModalidadDto(m));
        return dtos;
    }

    public String getNombre() { return nombre; }
}