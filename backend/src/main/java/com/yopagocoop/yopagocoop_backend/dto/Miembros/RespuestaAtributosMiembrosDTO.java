package com.yopagocoop.yopagocoop_backend.dto.Miembros;

import lombok.Data;

@Data
public class RespuestaAtributosMiembrosDTO {

    private Long id;
    private Long miembroId;
    private String nombreMiembro;
    private Long atributoEscuelaId;
    private String nombreAtributoEscuela;
    private String valorAtributo;

}