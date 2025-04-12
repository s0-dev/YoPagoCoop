package com.yopagocoop.yopagocoop_backend.dto.Escuelas;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespuestaEscuelasDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private String cuit;
    private String email;
    private LocalDateTime fechaCreacion;

}
