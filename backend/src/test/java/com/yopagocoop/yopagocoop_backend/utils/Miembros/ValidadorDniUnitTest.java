package com.yopagocoop.yopagocoop_backend.utils.Miembros;

import org.junit.jupiter.api.Test;

import com.yopagocoop.yopagocoop_backend.utils.Miembros.ValidadorDni;

import static org.junit.jupiter.api.Assertions.*;

public class ValidadorDniUnitTest {
  private final ValidadorDni validadorDni = new ValidadorDni();

  @Test
  void dniValido_con7Digitos() {
    assertTrue(validadorDni.esValido("1234567"));
  }

  @Test
  void dniValido_con8Digitos() {
    assertTrue(validadorDni.esValido("12345678"));
  }

  @Test
  void dniValido_con9Digitos() {
    assertTrue(validadorDni.esValido("123456789"));
  }

  @Test
  void dniInvalido_conMenos7Digitos() {
    assertFalse(validadorDni.esValido("123456"));
  }

  @Test
  void dniInvalido_conMas9Digitos() {
    assertFalse(validadorDni.esValido("1234567890"));
  }

  @Test
  void dniInvalido_conCaracteresNoNumericos() {
    assertFalse(validadorDni.esValido("1234-5678"));
    assertFalse(validadorDni.esValido("a12345678"));
    assertFalse(validadorDni.esValido("12345678a"));
  }

  @Test
  void dniInvalido_conStringVacia() {
    assertFalse(validadorDni.esValido(""));
  }

  @Test
  void dniInvalido_conValorNull() {
    assertFalse(validadorDni.esValido(null));
  }
}
