package com.yopagocoop.yopagocoop_backend.dto.Escuelas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreacionAtributosEscuelasDTO {

    @NotBlank(message = "nombreAtributo: no puede estar vacio")
    private String nombreAtributo;
    @NotBlank(message = "tipoDato: no puede estar vacio")
    private String tipoDato;
    @NotNull(message = "esRequerido: El valor debe ser true o false")
    private Boolean esRequerido;

}
