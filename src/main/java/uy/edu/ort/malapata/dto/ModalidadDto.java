package uy.edu.ort.malapata.dto;

import java.util.ArrayList;
import java.util.List;

public class ModalidadDto {

    private String nombre;

    public ModalidadDto(String nombre) {
        this.nombre = nombre;
    }

    public static List<ModalidadDto> listaDtos(List<String> lista) {
        List<ModalidadDto> dtos = new ArrayList<>();
        for (String nombre : lista) dtos.add(new ModalidadDto(nombre));
        return dtos;
    }

    public String getNombre() { return nombre; }
}