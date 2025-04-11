package com.yopagocoop.yopagocoop_backend.utils.Miembros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValidadorCelularUnitTest {

  public final ValidadorCelular validadorCelular = new ValidadorCelular();

  @Test
  void celularValido_codigoArea2Digitos() {
    assertTrue(validadorCelular.esValido("11 12345678"));
  }

  @Test
  void celularValido_codigoArea3Digitos() {
    assertTrue(validadorCelular.esValido("111 12345678"));
  }

  @Test
  void celularValido_codigoArea4Digitos() {
    assertTrue(validadorCelular.esValido("1111 12345678"));
  }

  @Test
  void celularValido_numeroValidoConGuion() {
    assertTrue(validadorCelular.esValido("11 1234-5678"));
    assertTrue(validadorCelular.esValido("111 1234-5678"));
    assertTrue(validadorCelular.esValido("1111 1234-5678"));
  }

  @Test
  void celularInvalido_numeroMenorA10Digitos() {
    assertFalse(validadorCelular.esValido("11 1234567"));
  }

  @Test
  void celularInvalido_numeroMayorA12Digitos() {
    assertFalse(validadorCelular.esValido("1111 123456789"));
  }

  @Test
  void celularInvalido_caracteresNoNumericos() {
    assertFalse(validadorCelular.esValido("null"));
    assertFalse(validadorCelular.esValido(""));
    assertFalse(validadorCelular.esValido("11 12345678!"));
    assertFalse(validadorCelular.esValido("1-1 2345678"));
    assertFalse(validadorCelular.esValido("11 12345a678"));
    assertFalse(validadorCelular.esValido("a11 12345678"));
  }

  @Test
  void celularFormateado_numeroValidoSinGuion() {
    assertEquals("+54 9 11 1234-5678", validadorCelular.formatearCelularArgentina("11 12345678"));

    assertEquals("+54 9 111 1234-5678", validadorCelular.formatearCelularArgentina("111 12345678"));

    assertEquals("+54 9 1111 1234-5678", validadorCelular.formatearCelularArgentina("1111 12345678"));
  }

  @Test
  void celularFormateado_numeroValidoConGuion() {
    assertEquals("+54 9 11 1234-5678", validadorCelular.formatearCelularArgentina("11 1234-5678"));

    assertEquals("+54 9 111 1234-5678", validadorCelular.formatearCelularArgentina("111 1234-5678"));

    assertEquals("+54 9 1111 1234-5678", validadorCelular.formatearCelularArgentina("1111 1234-5678"));
  }

}
