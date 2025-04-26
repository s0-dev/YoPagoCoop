package com.yopagocoop.yopagocoop_backend.dto.Escuelas;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreacionEscuelasDTO {

    @NotBlank(message = "nombre: no puede estar vacio")
    private String nombre;
    @NotBlank(message = "direccion: no puede estar vacio")
    private String direccion;
    @NotBlank(message = "cuit: no puede estar vacio")
    private String cuit;
    @NotBlank(message = "email: no puede estar vacio")
    @Email(message = "email: debe ser valido")
    private String email;

}
