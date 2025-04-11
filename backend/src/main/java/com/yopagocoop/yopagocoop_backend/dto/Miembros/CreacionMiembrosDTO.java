package com.yopagocoop.yopagocoop_backend.dto.Miembros;

import lombok.Data;

@Data
public class CreacionMiembrosDTO {

    private Long idEscuela;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String celular;

}
