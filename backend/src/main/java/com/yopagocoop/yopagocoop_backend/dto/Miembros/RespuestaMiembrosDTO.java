package com.yopagocoop.yopagocoop_backend.dto.Miembros;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class RespuestaMiembrosDTO {

    private Long id;
    private Long escuelaId;
    private String escuelaNombre;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private LocalDateTime fechaCreacion;
    private Map<String, String> atributos;

}