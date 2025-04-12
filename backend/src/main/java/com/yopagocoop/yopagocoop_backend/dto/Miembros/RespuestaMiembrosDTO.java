package com.yopagocoop.yopagocoop_backend.dto.Miembros;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespuestaMiembrosDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private LocalDateTime fechaCreacion;
}
