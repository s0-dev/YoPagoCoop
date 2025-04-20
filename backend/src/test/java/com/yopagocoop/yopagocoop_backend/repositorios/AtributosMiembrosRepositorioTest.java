package com.yopagocoop.yopagocoop_backend.repositorios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosMiembros;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;

@DataJpaTest
public class AtributosMiembrosRepositorioTest {
  @Autowired
  private AtributosEscuelasRepositorio atributosEscuelasRepositorio;
  @Autowired
  private RepositorioMiembros repositorioMiembros;
  @Autowired
  private AtributosMiembrosRepositorio atributosMiembrosRepositorio;
  @Autowired
  private RepositorioEscuelas repositorioEscuelas;

  // no hay test creando varios atributos porque la funcion crearAtributoMiembro
  // crea una escuela de cero, habria que sacar eso y agregarlo por separado, pero
  // funciona correctamente.

  private AtributosMiembros crearAtributoMiembro(String valorAtributo) {
    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de prueba");
    escuela.setDireccion("Direccion de prueba");
    escuela.setCuit("12123456781");
    escuela.setEmail("escuela@prueba.com");
    Escuela escuelaGuardada = repositorioEscuelas.save(escuela);

    AtributosEscuelas atributosEscuelas = new AtributosEscuelas();
    atributosEscuelas.setEscuela(escuelaGuardada);
    atributosEscuelas.setNombreAtributo("Atributo de Prueba");
    atributosEscuelas.setTipoDato("Int");
    atributosEscuelas.setEsRequerido(true);
    AtributosEscuelas atributosEscuelasGuardado = atributosEscuelasRepositorio.save(atributosEscuelas);

    Miembro miembro = new Miembro();
    miembro.setEscuela(escuelaGuardada);
    miembro.setDni("12345678");
    miembro.setNombre("Nombre de Prueba");
    miembro.setApellido("Apellido de Prueba");
    miembro.setEmail("email@prueba.com");
    miembro.setCelular("1234567890");
    Miembro miembroGuardado = repositorioMiembros.save(miembro);

    AtributosMiembros atributosMiembros = new AtributosMiembros();
    atributosMiembros.setMiembro(miembroGuardado);
    atributosMiembros.setAtributoEspecificoEscuela(atributosEscuelasGuardado);
    atributosMiembros.setValorAtributo(valorAtributo);
    return atributosMiembrosRepositorio.save(atributosMiembros);
  }

  @BeforeEach
  public void beforeEach() {
    atributosMiembrosRepositorio.deleteAll();
  }

  @Test
  public void testGuardarAtributoMiembro() {
    AtributosMiembros atributoMiembro = crearAtributoMiembro("Valor de Prueba");
    assertNotNull(atributoMiembro.getId());
    assertNotEquals(0L, atributoMiembro.getId());
    assertEquals("Valor de Prueba", atributoMiembro.getValorAtributo());
  }

  @Test
  public void testActualizarAtributoMiembro() {
    AtributosMiembros atributoMiembro = crearAtributoMiembro("Valor de Prueba");
    atributoMiembro.setValorAtributo("Valor Actualizado");
    AtributosMiembros atributoMiembroActualizado = atributosMiembrosRepositorio.save(atributoMiembro);
    assertEquals("Valor Actualizado", atributoMiembroActualizado.getValorAtributo());
  }

  @Test
  public void testEliminarAtributoMiembro() {
    AtributosMiembros atributoMiembro = crearAtributoMiembro("Valor de Prueba");
    atributosMiembrosRepositorio.deleteById(atributoMiembro.getId());
    Optional<AtributosMiembros> atributoMiembroEncontrado = atributosMiembrosRepositorio
        .findById(atributoMiembro.getId());
    assertFalse(atributoMiembroEncontrado.isPresent());
  }
}
