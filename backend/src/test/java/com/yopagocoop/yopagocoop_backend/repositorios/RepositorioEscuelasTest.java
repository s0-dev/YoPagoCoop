package com.yopagocoop.yopagocoop_backend.repositorios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.yopagocoop.yopagocoop_backend.modelos.Escuela;

@DataJpaTest
public class RepositorioEscuelasTest {

  @Autowired
  private RepositorioEscuelas repositorioEscuelas;

  // Limpiamos todos los datos antes de hacer los tests
  @BeforeEach
  public void beforeEach() {
    repositorioEscuelas.deleteAll();
  }

  @Test
  public void testGuardarEscuela() {
    // Preparar
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de prueba");
    escuela.setDireccion("Direccion de prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    // Actuar
    Escuela escuelaGuardada = repositorioEscuelas.save(escuela);
    // Verificar
    assertNotNull(escuelaGuardada.getId()); // Verifica que se haya asignado un ID
    assertNotEquals(0L, escuelaGuardada.getId());
    assertEquals(escuela.getNombre(), escuelaGuardada.getNombre());
    assertEquals(escuela.getDireccion(), escuelaGuardada.getDireccion());
    assertEquals(escuela.getCuit(), escuelaGuardada.getCuit());
    assertEquals(escuela.getEmail(), escuelaGuardada.getEmail());
  }

  @Test
  public void testActualizarEscuela() {
    // Preparacion
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de prueba");
    escuela.setDireccion("Direccion de prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    repositorioEscuelas.save(escuela);
    // Actuar
    escuela.setNombre("Escuela de prueba actualizada");
    escuela.setDireccion("Direccion de prueba actualizada");
    Escuela escuelaActualizada = repositorioEscuelas.save(escuela);
    // Verificar
    assertEquals(escuela.getNombre(), escuelaActualizada.getNombre());
    assertEquals(escuela.getDireccion(), escuelaActualizada.getDireccion());
  }

  @Test
  public void testBorrarEscuela() {
    // Preparacion
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de prueba");
    escuela.setDireccion("Direccion de prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    Escuela escuelaGuardada = repositorioEscuelas.save(escuela);
    // Actuar
    repositorioEscuelas.deleteById(escuelaGuardada.getId());
    // Verificar
    Optional<Escuela> escuelaEncontrada = repositorioEscuelas.findById(escuelaGuardada.getId());
    assertFalse(escuelaEncontrada.isPresent());
  }

  @Test
  public void testEncontrarTodasLasEscuelas() {
    // Preparacion
    Escuela escuela1 = new Escuela();
    escuela1.setNombre("Escuela de prueba 1");
    escuela1.setDireccion("Direccion de prueba 1");
    escuela1.setCuit("12123456781");
    escuela1.setEmail("escuela1@prueba.com");
    repositorioEscuelas.save(escuela1);
    Escuela escuela2 = new Escuela();
    escuela2.setNombre("Escuela de prueba 2");
    escuela2.setDireccion("Direccion de prueba 2");
    escuela2.setCuit("12123456782");
    escuela2.setEmail("escuela2@prueba.com");
    repositorioEscuelas.save(escuela2);
    // Actuar
    List<Escuela> escuelas = repositorioEscuelas.findAll();
    // Verificar
    assertEquals(2, escuelas.size());
  }

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

  @Test
  public void testEncontrarPorEmail() {
    // Preparacion
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de prueba");
    escuela.setDireccion("Direccion de prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    repositorioEscuelas.save(escuela);
    // Actuar
    Optional<Escuela> escuelaEncontrada = repositorioEscuelas.findByEmail("escuela@prueba.com");
    // Verificar
    assertTrue(escuelaEncontrada.isPresent());
    assertEquals(escuela.getEmail(), escuelaEncontrada.get().getEmail());
  }

  @Test
  public void testEncontrarPorCuit() {
    // Preparacion
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de prueba");
    escuela.setDireccion("Direccion de prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    repositorioEscuelas.save(escuela);
    // Actuar
    Optional<Escuela> escuelaEncontrada = repositorioEscuelas.findByCuit("12123456781");
    // Verificar
    assertTrue(escuelaEncontrada.isPresent());
    assertEquals(escuela.getCuit(), escuelaEncontrada.get().getCuit());
  }
}