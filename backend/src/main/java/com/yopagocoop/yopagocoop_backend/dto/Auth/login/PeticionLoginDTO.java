package com.yopagocoop.yopagocoop_backend.dto.Auth.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PeticionLoginDTO {

  @NotBlank(message = "El email es obligatorio")
  @Email(message = "El email debe tener un formato válido")
  private String email;
  @NotBlank(message = "La contraseña es obligatoria")
  private String contraseña;

}
