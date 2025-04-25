// ARCHIVADO

package com.yopagocoop.yopagocoop_backend.utils.Miembros;

public class ValidadorDni {
  // implementar esValido
  // funcion la cual tome una string, y analice si tiene SOLO valores numericos, y
  // tenga una longitud entre 7 a 9 digitos, y que devuelva true o false

  public boolean esValido(String dni) {
    if (dni == null || dni.isEmpty()) {
      return false;
    }
    return dni.matches("\\d+") && dni.length() >= 7 && dni.length() <= 9;
  }

}
