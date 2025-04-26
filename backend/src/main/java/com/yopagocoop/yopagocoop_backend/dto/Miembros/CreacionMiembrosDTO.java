package com.yopagocoop.yopagocoop_backend.dto.Miembros;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

import lombok.Data;

@Data
public class CreacionMiembrosDTO {

    @NotNull(message = "escuelaId: no puede estar vacio")
    private Long escuelaId;

    @NotBlank(message = "nombre: no puede estar vacio")
    private String nombre;
    @NotBlank(message = "apellido: no puede estar vacio")
    private String apellido;
    @NotBlank(message = "email: no puede estar vacio")
    @Email(message = "email: debe tener formato válido")
    private String email;
    @NotBlank(message = "contraseña: no puede estar vacio")
    private String contraseña;
    @NotBlank(message = "celular: no puede estar vacio")
    private String celular;

    @NotNull(message = "atributos: no puede estar vacio")
    private Map<String, String> atributos;

}