package com.yopagocoop.yopagocoop_backend.repositorios;

import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class AtributosEscuelasRepositorioTest {

  @Autowired
  private AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  @Autowired
  private RepositorioEscuelas repositorioEscuelas;

  // TODO: TEST_ATRIBUTOS_ESCUELAS
  // cuando se elimine un miembro, eliminar la relacion entre el miembro y el
  // atributo especifico.

  @BeforeEach
  public void beforeEach() {
    atributosEscuelasRepositorio.deleteAll();
    repositorioEscuelas.deleteAll();
  }

  @Test
  public void testBuscarAtributosEspecificosPorIdEscuela() {
    // Preparar
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de prueba");
    escuela.setDireccion("Direccion de prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    Escuela escuelaGuardada = repositorioEscuelas.save(escuela);

    AtributosEscuelas atributo1 = new AtributosEscuelas();
    atributo1.setEscuela(escuelaGuardada);
    atributo1.setNombreAtributo("Atributo 1");
    atributo1.setTipoDato("String");
    atributo1.setEsRequerido(true);
    atributosEscuelasRepositorio.save(atributo1);

    AtributosEscuelas atributo2 = new AtributosEscuelas();
    atributo2.setEscuela(escuelaGuardada);
    atributo2.setNombreAtributo("Atributo 2");
    atributo2.setTipoDato("Integer");
    atributo2.setEsRequerido(false);
    atributosEscuelasRepositorio.save(atributo2);

    // Actuar
    List<AtributosEscuelas> atributos = atributosEscuelasRepositorio.findByEscuela_Id(escuelaGuardada.getId());

    // Verificar
    assertEquals(2, atributos.size());
    assertNotEquals(0, atributos.get(0).getId());
    assertNotNull(atributos.get(0).getEscuela());
    assertEquals("Atributo 1", atributos.get(0).getNombreAtributo());
    assertNotEquals(0, atributos.get(1).getId());
    assertNotNull(atributos.get(1).getEscuela());
    assertEquals("Atributo 2", atributos.get(1).getNombreAtributo());
  }
}
