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

import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;

@DataJpaTest
public class RepositorioEscuelasTest {

  @Autowired
  private RepositorioEscuelas repositorioEscuelas;

  @Autowired
  private RepositorioMiembros repositorioMiembros;

  @Autowired
  private AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  // HECHO: TEST_ESCUELAS
  // crear test para que cuando se borre una escuela se borren sus atributos y
  // miembros

  // Limpiamos todos los datos antes de hacer los tests
  @BeforeEach
  public void beforeEach() {
    repositorioEscuelas.deleteAll();
  }

  @Test
  public void testGuardarEscuela() {
    // Preparar
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
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
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    repositorioEscuelas.save(escuela);
    // Actuar
    escuela.setNombre("Escuela de Prueba actualizada");
    escuela.setDireccion("Direccion de Prueba actualizada");
    Escuela escuelaActualizada = repositorioEscuelas.save(escuela);
    // Verificar
    assertEquals(escuela.getNombre(), escuelaActualizada.getNombre());
    assertEquals(escuela.getDireccion(), escuelaActualizada.getDireccion());
  }

  @Test
  public void testBorrarEscuela() {
    // Preparacion
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
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
    escuela1.setNombre("Escuela de Prueba 1");
    escuela1.setDireccion("Direccion de Prueba 1");
    escuela1.setCuit("12123456781");
    escuela1.setEmail("escuela1@prueba.com");
    repositorioEscuelas.save(escuela1);
    Escuela escuela2 = new Escuela();
    escuela2.setNombre("Escuela de Prueba 2");
    escuela2.setDireccion("Direccion de Prueba 2");
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
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
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
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    repositorioEscuelas.save(escuela);
    // Actuar
    Optional<Escuela> escuelaEncontrada = repositorioEscuelas.findByCuit("12123456781");
    // Verificar
    assertTrue(escuelaEncontrada.isPresent());
    assertEquals(escuela.getCuit(), escuelaEncontrada.get().getCuit());
  }

  // Este test costo 6 horas en completar.
  @Test
  public void testEliminarEscuelaConDependencias() {
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    Escuela escuelaGuardada = repositorioEscuelas.save(escuela);

    AtributosEscuelas atributosEscuelas1 = new AtributosEscuelas();
    atributosEscuelas1.setNombreAtributo("Atributo de Prueba");
    atributosEscuelas1.setTipoDato("Int");
    atributosEscuelas1.setEsRequerido(true);

    AtributosEscuelas atributosEscuelas2 = new AtributosEscuelas();
    atributosEscuelas2.setNombreAtributo("Atributo de Prueba 2");
    atributosEscuelas2.setTipoDato("String");
    atributosEscuelas2.setEsRequerido(true);

    Miembro miembro = new Miembro();
    miembro.setDni("12345678");
    miembro.setNombre("Nombre de Prueba");
    miembro.setApellido("Apellido de Prueba");
    miembro.setEmail("email@prueba.com");
    miembro.setCelular("1234567890");

    Miembro miembro2 = new Miembro();
    miembro2.setDni("123456789");
    miembro2.setNombre("Nombre de Prueba 2");
    miembro2.setApellido("Apellido de Prueba 2");
    miembro2.setEmail("email2@prueba.com");
    miembro2.setCelular("1234567890");

    escuela.setAtributosEscuelas(List.of(atributosEscuelas1, atributosEscuelas2));
    escuela.setMiembros(List.of(miembro, miembro2));

    atributosEscuelas1.setEscuela(escuelaGuardada);
    atributosEscuelas2.setEscuela(escuelaGuardada);
    miembro.setEscuela(escuelaGuardada);
    miembro2.setEscuela(escuelaGuardada);
    atributosEscuelasRepositorio.save(atributosEscuelas1);
    atributosEscuelasRepositorio.save(atributosEscuelas2);
    repositorioMiembros.save(miembro);
    repositorioMiembros.save(miembro2);

    repositorioEscuelas.delete(escuelaGuardada);

    assertFalse(repositorioEscuelas.findById(escuelaGuardada.getId()).isPresent());
    assertFalse(atributosEscuelasRepositorio.findById(atributosEscuelas1.getId()).isPresent());
    assertFalse(atributosEscuelasRepositorio.findById(atributosEscuelas2.getId()).isPresent());
    assertFalse(repositorioMiembros.findById(miembro.getId()).isPresent());
    assertFalse(repositorioMiembros.findById(miembro2.getId()).isPresent());
  }

}