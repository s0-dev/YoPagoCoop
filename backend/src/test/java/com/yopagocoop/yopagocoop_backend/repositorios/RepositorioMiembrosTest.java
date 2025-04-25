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
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;

@DataJpaTest
public class RepositorioMiembrosTest {

  @Autowired
  private RepositorioMiembros repositorioMiembros;

  @Autowired
  private RepositorioEscuelas repositorioEscuelas;

  @BeforeEach
  public void beforeEach() {
    repositorioMiembros.deleteAll();
    repositorioEscuelas.deleteAll();
  }

  private Escuela crearEscuela(String cuit) {
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
    escuela.setCuit(cuit);
    escuela.setEmail("escuela@prueba.com");
    return repositorioEscuelas.save(escuela);
  }

  private void crearMiembroVoid(Escuela escuela) {
    Miembro miembro = new Miembro();
    miembro.setEscuela(escuela);
    miembro.setNombre("Nombre de Prueba");
    miembro.setApellido("Apellido de Prueba");
    miembro.setEmail("email@prueba.com");
    miembro.setCelular("1234567890");
    repositorioMiembros.save(miembro);
  }

  private Miembro crearMiembro(Escuela escuela) {
    Miembro miembro = new Miembro();
    miembro.setEscuela(escuela);
    miembro.setNombre("Nombre de Prueba");
    miembro.setApellido("Apellido de Prueba");
    miembro.setEmail("miembro@prueba.com");
    miembro.setCelular("1234567890");
    return repositorioMiembros.save(miembro);
  }

  @Test
  public void testGuardarMiembro() {

    Escuela escuelaGuardada = crearEscuela("12123456781");
    Miembro miembroGuardado = crearMiembro(escuelaGuardada);
    assertNotNull(miembroGuardado.getId());
    assertNotEquals(0L, miembroGuardado.getId());
  }

  @Test
  public void testActualizarMiembro() {
    Escuela escuelaGuardada = crearEscuela("12123456781");
    Miembro miembro = crearMiembro(escuelaGuardada);
    miembro.setNombre("Nombre actualizado");
    Miembro miembroActualizado = repositorioMiembros.save(miembro);
    assertEquals(miembro.getNombre(), miembroActualizado.getNombre());
  }

  @Test
  public void testBorrarMiembro() {
    Escuela escuelaGuardada = crearEscuela("12123456781");
    Miembro miembroGuardado = crearMiembro(escuelaGuardada);
    repositorioMiembros.deleteById(miembroGuardado.getId());
    Optional<Miembro> miembroEncontrado = repositorioMiembros.findById(miembroGuardado.getId());
    assertFalse(miembroEncontrado.isPresent());
  }

  @Test
  public void testEncontrarTodosLosMiembros() {
    Escuela escuelaGuardada = crearEscuela("12123456781");
    crearMiembroVoid(escuelaGuardada);
    crearMiembroVoid(escuelaGuardada);
    List<Miembro> miembros = repositorioMiembros.findAll();
    assertEquals(2, miembros.size());
  }

  @Test
  public void testEncontrarTodosLosMiembrosDeUnaEscuela() {
    Escuela escuelaGuardada = crearEscuela("12123456781");
    crearMiembroVoid(escuelaGuardada);
    List<Miembro> miembros = repositorioMiembros.findByEscuelaId(escuelaGuardada.getId());
    assertEquals(1, miembros.size());
  }

  @Test
  public void testEncontrarMiembroPorEmail() {
    Escuela escuelaGuardada = crearEscuela("12123456781");
    Miembro miembro = crearMiembro(escuelaGuardada);
    Optional<Miembro> miembroEncontrado = repositorioMiembros.findByEmail(miembro.getEmail());
    assertTrue(miembroEncontrado.isPresent());
    assertEquals(miembro.getEmail(), miembroEncontrado.get().getEmail());
  }
}
