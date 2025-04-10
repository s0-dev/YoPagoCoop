package com.yopagocoop.yopagocoop_backend.utils.Miembros;

public class ValidadorCelular {
  // esValido
  // ingresa una string, devuelve true o false

  // los numeros celulares estan compuestos de:
  // COD_PAIS +54 / 54 para ARG y puede o no tener el "+"
  // PREFIJO_CEL 9
  // COD_AREA 11 para CABA
  // PREFIJO_LOCAL algunos se marcan con 15, y es opcional
  // Numero local, 8 digitos y puede o no tener un "-"

  // asumimos que siempre va a tener +54, ya que opera dentro de argentina, y
  // siempre va a tener el prefijo 9, ya que es celulares lo que pedimos.
  // vamos a revisar que el cod de area sea correcto, con el minimo siendo 2
  // digitos (11 CABA) y el maximo siendo 4 digitos (3894 TUCUMÁN)
  // y despues la ultima validación es que tenga 8 digitos, del numero local

  private static final String CELULAR_ARGENTINA_REGEX = "^\\d{2,4}\\d{8}$";

  private String limpiarNumero(String celular) {

    if (celular == null || celular.isEmpty()) {
      return null;
    }

    return celular.trim().replaceAll("-", "").replaceAll(" ", "");

  }

  public boolean esValido(String celular) {

    String numeroLimpio = limpiarNumero(celular);

    if (numeroLimpio == null || numeroLimpio.isEmpty()) {
      return false;
    }

    if (numeroLimpio.length() < 10 || numeroLimpio.length() > 12) {
      return false;
    }

    return numeroLimpio.matches(CELULAR_ARGENTINA_REGEX);
  }

  public String formatearCelularArgentina(String celular) {

    String numeroLimpio = limpiarNumero(celular);

    if (esValido(celular)) {

      String codigoArea = numeroLimpio.substring(0, numeroLimpio.length() - 8);

      String numeroLocal = numeroLimpio.substring(numeroLimpio.length() - 8);

      String numeroLocalFormateado = numeroLocal.substring(0, 4) + "-" + numeroLocal.substring(4);

      return "+54 9 " + codigoArea + " " + numeroLocalFormateado;

    } else {
      return null;
    }
  }
}
