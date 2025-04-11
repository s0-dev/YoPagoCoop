package com.yopagocoop.yopagocoop_backend.utils.Miembros;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValidadorEmailUnitTest {

  public final ValidadorEmail validadorEmail = new ValidadorEmail();

  @Test
  void emailValido_formatoBasico() {
    assertTrue(validadorEmail.esValido("test@example.com"));
    assertTrue(validadorEmail.esValido("user.name@example.org"));
    assertTrue(validadorEmail.esValido("user123@test.co.uk"));
  }

  @Test
  void emailInvalido_sinArroba() {
    assertFalse(validadorEmail.esValido("testexample.com"));
  }

  @Test
  void emailInvalido_sinDominio() {
    assertFalse(validadorEmail.esValido("test@"));
  }

  @Test
  void emailInvalido_sinTLD() {
    assertFalse(validadorEmail.esValido("test@example"));
  }

  @Test
  void emailInvalido_tldInvalido() {
    assertFalse(validadorEmail.esValido("test@example.jaja"));
  }

  @Test
  void emailInvalido_conEspacios() {
    assertFalse(validadorEmail.esValido("test @example.com"));
    assertFalse(validadorEmail.esValido("test@ example.com"));
  }

  @Test
  void emailInvalido_puntoAlFinalDelDominio() {
    assertFalse(validadorEmail.esValido("test@example."));
  }

  @Test
  void emailInvalido_arrobaAlPrincipio() {
    assertFalse(validadorEmail.esValido("@example.com"));
  }

  @Test
  void emailInvalido_multiplesArrobas() {
    assertFalse(validadorEmail.esValido("test@@example.com"));
  }

  @Test
  void emailInvalido_soloTLD() {
    assertFalse(validadorEmail.esValido("@.com"));
  }

}
