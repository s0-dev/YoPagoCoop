package com.yopagocoop.yopagocoop_backend.dto;

import lombok.Data;

@Data
public class RespuestaAtributosMiembrosDTO {

    private Long id;
    private Long idMiembro;
    private Long idAtributoEspecificoEscuela;
    private String valorAtributo;

}
