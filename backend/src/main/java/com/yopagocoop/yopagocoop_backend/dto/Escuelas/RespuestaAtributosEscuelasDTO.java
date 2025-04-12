package com.yopagocoop.yopagocoop_backend.dto.Escuelas;

import lombok.Data;

@Data
public class RespuestaAtributosEscuelasDTO {
    private Long id;
    private String nombreAtributo;
    private String tipoDato;
    private Boolean esRequerido;
}
