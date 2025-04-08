package com.yopagocoop.yopagocoop_backend.utils.Schools;

public class ValidadorCuit {
  // esValido
  // ingresa una string, y revisa primero si esta vacia o es null, posteriormente
  // revisa via regex si es unicamente numerica y tiene entre 10 a 12 digitos,
  // devuelve true o false

  public boolean esValido(String dni) {
    if (dni == null || dni.isEmpty()) {
      return false;
    }
    return dni.matches("\\d+") && dni.length() >= 10 && dni.length() <= 12;
  }
}
