package com.yopagocoop.yopagocoop_backend.dto.Miembros;

import java.util.Map;
import lombok.Data;

@Data
public class CreacionMiembrosDTO {

    private Long escuelaId;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private Map<String, String> atributos;

}