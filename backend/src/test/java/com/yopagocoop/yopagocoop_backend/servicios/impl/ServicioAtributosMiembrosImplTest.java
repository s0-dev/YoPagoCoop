package com.yopagocoop.yopagocoop_backend.servicios.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosMiembros;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosMiembrosRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioMiembros;

@ExtendWith(MockitoExtension.class)
public class ServicioAtributosMiembrosImplTest {

  @Mock
  private AtributosMiembrosRepositorio atributosMiembrosRepositorio;

  @Mock
  private RepositorioMiembros repositorioMiembros;

  @Mock
  private AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ServicioAtributosMiembrosImpl servicioAtributosMiembros;

  // Helper methods to create test objects
  private Escuela createEscuela(Long id, String nombre) {
    Escuela escuela = new Escuela();
    escuela.setId(id);
    escuela.setNombre(nombre);
    return escuela;
  }

  private Miembro createMiembro(Long id, Escuela escuela, String nombre, String apellido) {
    Miembro miembro = new Miembro();
    miembro.setId(id);
    miembro.setEscuela(escuela);
    miembro.setNombre(nombre);
    miembro.setApellido(apellido);
    miembro.setEmail(nombre.toLowerCase() + "@example.com");
    miembro.setCelular("123456789");
    return miembro;
  }

  private AtributosEscuelas createAtributoEscuela(Long id, Escuela escuela, String nombre) {
    AtributosEscuelas atributo = new AtributosEscuelas();
    atributo.setId(id);
    atributo.setEscuela(escuela);
    atributo.setNombreAtributo(nombre);
    atributo.setTipoDato("String");
    atributo.setEsRequerido(true);
    return atributo;
  }

  private AtributosMiembros createAtributoMiembro(Long id, Miembro miembro, AtributosEscuelas atributoEscuela,
      String valor) {
    AtributosMiembros atributo = new AtributosMiembros();
    atributo.setId(id);
    atributo.setMiembro(miembro);
    atributo.setAtributoEspecificoEscuela(atributoEscuela);
    atributo.setValorAtributo(valor);
    return atributo;
  }

  private CreacionAtributosMiembrosDTO createCreacionDTO(Long atributoEscuelaId, String valor) {
    CreacionAtributosMiembrosDTO dto = new CreacionAtributosMiembrosDTO();
    dto.setAtributoEscuelaId(atributoEscuelaId);
    dto.setValorAtributo(valor);
    return dto;
  }

  private RespuestaAtributosMiembrosDTO createRespuestaDTO(Long id, Long miembroId, String nombreMiembro,
      Long atributoEscuelaId, String nombreAtributo, String valor) {
    RespuestaAtributosMiembrosDTO dto = new RespuestaAtributosMiembrosDTO();
    dto.setId(id);
    dto.setMiembroId(miembroId);
    dto.setNombreMiembro(nombreMiembro);
    dto.setAtributoEscuelaId(atributoEscuelaId);
    dto.setNombreAtributoEscuela(nombreAtributo);
    dto.setValorAtributo(valor);
    return dto;
  }

  private Escuela escuela;
  private Miembro miembro;
  private AtributosEscuelas atributoEscuela;
  private AtributosMiembros atributoMiembro;
  private CreacionAtributosMiembrosDTO creacionDTO;
  private RespuestaAtributosMiembrosDTO respuestaDTO;

  @BeforeEach
  void setUp() {
    // Usa las funciones para crear las entidades
    escuela = createEscuela(1L, "Escuela Test");
    miembro = createMiembro(1L, escuela, "Juan", "Pérez");
    atributoEscuela = createAtributoEscuela(1L, escuela, "Grado");
    atributoMiembro = createAtributoMiembro(1L, miembro, atributoEscuela, "Primero");
    creacionDTO = createCreacionDTO(1L, "Primero");
    respuestaDTO = createRespuestaDTO(1L, 1L, "Juan", 1L, "Grado", "Primero");
  }

  @Test
  void crearAtributoMiembro_DebeCrearYRetornarAtributo() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.of(miembro));
    when(atributosEscuelasRepositorio.findById(1L)).thenReturn(Optional.of(atributoEscuela));
    when(atributosMiembrosRepositorio.save(any(AtributosMiembros.class))).thenReturn(atributoMiembro);
    when(modelMapper.map(atributoMiembro, RespuestaAtributosMiembrosDTO.class)).thenReturn(respuestaDTO);

    // Ejecutar
    RespuestaAtributosMiembrosDTO resultado = servicioAtributosMiembros.crearAtributoMiembro(1L, creacionDTO);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Primero", resultado.getValorAtributo());
    verify(repositorioMiembros).findById(1L);
    verify(atributosEscuelasRepositorio).findById(1L);
    verify(atributosMiembrosRepositorio).save(any(AtributosMiembros.class));
  }

  @Test
  void crearAtributoMiembro_MiembroNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.empty());

    // Ejectuar y Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosMiembros.crearAtributoMiembro(1L, creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Miembro no encontrado"));
  }

  @Test
  void crearAtributoMiembro_AtributoEscuelaNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.of(miembro));
    when(atributosEscuelasRepositorio.findById(1L)).thenReturn(Optional.empty());

    // Ejectuar y Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosMiembros.crearAtributoMiembro(1L, creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Atributo de escuela no encontrado"));
  }

  @Test
  void obtenerAtributosPorMiembro_DebeRetornarListaDeAtributos() {
    // Preparar
    List<AtributosMiembros> atributos = Arrays.asList(atributoMiembro);
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.of(miembro));
    when(atributosMiembrosRepositorio.findByMiembro(miembro)).thenReturn(atributos);
    when(modelMapper.map(atributoMiembro, RespuestaAtributosMiembrosDTO.class)).thenReturn(respuestaDTO);

    // Ejecutar
    List<RespuestaAtributosMiembrosDTO> resultado = servicioAtributosMiembros.obtenerAtributosPorMiembro(1L);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Primero", resultado.get(0).getValorAtributo());
    verify(repositorioMiembros).findById(1L);
    verify(atributosMiembrosRepositorio).findByMiembro(miembro);
  }

  @Test
  void obtenerAtributosPorMiembro_MiembroNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.empty());

    // Ejectuar y Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosMiembros.obtenerAtributosPorMiembro(1L);
    });

    assertTrue(exception.getMessage().contains("Miembro no encontrado"));
  }

  @Test
  void obtenerAtributoPorId_DebeRetornarAtributo() {
    // Preparar
    when(atributosMiembrosRepositorio.findById(1L)).thenReturn(Optional.of(atributoMiembro));
    when(modelMapper.map(atributoMiembro, RespuestaAtributosMiembrosDTO.class)).thenReturn(respuestaDTO);

    // Ejecutar
    RespuestaAtributosMiembrosDTO resultado = servicioAtributosMiembros.obtenerAtributoPorId(1L);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Primero", resultado.getValorAtributo());
    verify(atributosMiembrosRepositorio).findById(1L);
  }

  @Test
  void obtenerAtributoPorId_AtributoNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(atributosMiembrosRepositorio.findById(1L)).thenReturn(Optional.empty());

    // Ejectuar y Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosMiembros.obtenerAtributoPorId(1L);
    });

    assertTrue(exception.getMessage().contains("Atributo de miembro no encontrado"));
  }

  @Test
  void actualizarAtributoMiembro_DebeEjecutarualizarYRetornarAtributo() {
    // Preparar
    AtributosEscuelas nuevoAtributoEscuela = createAtributoEscuela(2L, escuela, "Sección");
    AtributosMiembros atributoEjecutarualizado = createAtributoMiembro(1L, miembro, nuevoAtributoEscuela, "B");
    CreacionAtributosMiembrosDTO dtoEjecutarualizado = createCreacionDTO(2L, "B");
    RespuestaAtributosMiembrosDTO respuestaEjecutarualizada = createRespuestaDTO(1L, 1L, "Juan", 2L, "Sección", "B");

    when(atributosMiembrosRepositorio.findById(1L)).thenReturn(Optional.of(atributoMiembro));
    when(atributosEscuelasRepositorio.findById(2L)).thenReturn(Optional.of(nuevoAtributoEscuela));
    when(atributosMiembrosRepositorio.save(any(AtributosMiembros.class))).thenReturn(atributoEjecutarualizado);
    when(modelMapper.map(atributoEjecutarualizado, RespuestaAtributosMiembrosDTO.class))
        .thenReturn(respuestaEjecutarualizada);

    // Ejecutar
    RespuestaAtributosMiembrosDTO resultado = servicioAtributosMiembros.actualizarAtributoMiembro(1L,
        dtoEjecutarualizado);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("B", resultado.getValorAtributo());
    assertEquals(2L, resultado.getAtributoEscuelaId());
    verify(atributosMiembrosRepositorio).findById(1L);
    verify(atributosEscuelasRepositorio).findById(2L);
    verify(atributosMiembrosRepositorio).save(any(AtributosMiembros.class));
  }

  @Test
  void actualizarAtributoMiembro_AtributoNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(atributosMiembrosRepositorio.findById(1L)).thenReturn(Optional.empty());

    // Ejectuar y Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosMiembros.actualizarAtributoMiembro(1L, creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Atributo de miembro no encontrado"));
  }

  @Test
  void actualizarAtributoMiembro_NuevoAtributoEscuelaNoExistente_DebeLanzarExcepcion() {
    // Preparar
    CreacionAtributosMiembrosDTO dtoEjecutarualizado = createCreacionDTO(2L, "B");

    when(atributosMiembrosRepositorio.findById(1L)).thenReturn(Optional.of(atributoMiembro));
    when(atributosEscuelasRepositorio.findById(2L)).thenReturn(Optional.empty());

    // Ejectuar y Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosMiembros.actualizarAtributoMiembro(1L, dtoEjecutarualizado);
    });

    assertTrue(exception.getMessage().contains("Atributo de escuela no encontrado"));
  }

  @Test
  void eliminarAtributoMiembro_DebeEliminarAtributo() {
    // Preparar
    when(atributosMiembrosRepositorio.existsById(1L)).thenReturn(true);
    doNothing().when(atributosMiembrosRepositorio).deleteById(1L);

    // Ejecutar
    servicioAtributosMiembros.eliminarAtributoMiembro(1L);

    // Verificar
    verify(atributosMiembrosRepositorio).deleteById(1L);
  }

  @Test
  void eliminarAtributoMiembro_AtributoNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(atributosMiembrosRepositorio.existsById(1L)).thenReturn(false);

    // Ejectuar y Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosMiembros.eliminarAtributoMiembro(1L);
    });

    assertTrue(exception.getMessage().contains("Atributo de miembro no encontrado"));
  }
}