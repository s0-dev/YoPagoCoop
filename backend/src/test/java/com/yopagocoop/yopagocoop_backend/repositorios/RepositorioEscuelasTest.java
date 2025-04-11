package com.yopagocoop.yopagocoop_backend.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.yopagocoop.yopagocoop_backend.modelos.Escuela;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class RepositorioEscuelasTest {

  @Autowired
  private RepositorioEscuelas repositorioEscuelas;

  @Test
  public void testBuscarEscuelaPorNombre() {
    // Preparacion
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
    escuela.setCuit("12345678901");
    escuela.setEmail("prueba@escuela.com");
    repositorioEscuelas.save(escuela);
    // Actuar
    Optional<Escuela> escuelaEncontrada = repositorioEscuelas.findByNombre("Escuela de Prueba");
    // Verificar
    assertTrue(escuelaEncontrada.isPresent());
    assertEquals(escuela.getNombre(), escuelaEncontrada.get().getNombre());
  }

}
