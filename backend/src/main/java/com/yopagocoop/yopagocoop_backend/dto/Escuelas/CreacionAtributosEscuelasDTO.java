package com.yopagocoop.yopagocoop_backend.dto;

import lombok.Data;

@Data
public class CreacionAtributosEscuelasDTO {

    private Long idEscuela;
    private String nombreAtributo;
    private String tipoDato;
    private Boolean esRequerido;

}
