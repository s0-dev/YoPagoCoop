package com.yopagocoop.yopagocoop_backend.utils.Miembros;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.EmailValidator;

public class ValidadorEmail {
  // esValido
  // ingresa una string, devuelve true o false

  // la validación de email es bastante compleja como para simplemente implementar
  // un regex e ir verificando los distintos dominios y TLD, asi que vamos a
  // utilizar la libreria de Apache Commons Validator

  // no permite direcciones locales, solo dominios de internet
  // necesita un TLD reconocido
  private final EmailValidator emailValidator = new EmailValidator(false, false, DomainValidator.getInstance(false));

  public boolean esValido(String email) {
    return emailValidator.isValid(email);
  }
}
