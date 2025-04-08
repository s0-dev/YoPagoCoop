package com.yopagocoop.yopagocoop_backend.utils.Schools;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValidadorCuitUnitTest {

  private final ValidadorCuit validadorCuit = new ValidadorCuit();

  @Test
  void cuitValido_con10Digitos() {
    assertTrue(validadorCuit.esValido("1234567890"));
  }

  @Test
  void cuitValido_con11Digitos() {
    assertTrue(validadorCuit.esValido("12345678901"));
  }

  @Test
  void cuitValido_con12Digitos() {
    assertTrue(validadorCuit.esValido("123456789012"));
  }

  @Test
  void cuitInvalido_conMenos10Digitos() {
    assertFalse(validadorCuit.esValido("123456789"));
  }

  @Test
  void cuitInvalido_conMas12Digitos() {
    assertFalse(validadorCuit.esValido("1234567890123"));
  }

  @Test
  void cuitInvalido_conCarecteresNoNumericos() {
    assertFalse(validadorCuit.esValido("12345-67890"));
    assertFalse(validadorCuit.esValido("a1234567890"));
    assertFalse(validadorCuit.esValido("1234567890a"));
  }

  @Test
  void cuitInvalido_conStringVacia() {
    assertFalse(validadorCuit.esValido(""));
  }

  @Test
  void cuitInvalido_conValorNull() {
    assertFalse(validadorCuit.esValido(null));
  }
}
