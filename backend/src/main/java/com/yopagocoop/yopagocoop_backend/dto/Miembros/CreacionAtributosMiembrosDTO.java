package com.yopagocoop.yopagocoop_backend.dto.Miembros;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreacionAtributosMiembrosDTO {

    @NotNull(message = "miembroId: no puede estar vacio")
    private Long miembroId;
    @NotNull(message = "atributoEscuelaId: no puede estar vacio")
    private Long atributoEscuelaId;
    @NotBlank(message = "valorAtributo: no puede estar vacio")
    private String valorAtributo;

}