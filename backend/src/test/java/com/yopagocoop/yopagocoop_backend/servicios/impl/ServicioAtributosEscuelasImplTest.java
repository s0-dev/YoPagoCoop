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

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioEscuelas;

@ExtendWith(MockitoExtension.class)
public class ServicioAtributosEscuelasImplTest {

  @Mock
  private AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  @Mock
  private RepositorioEscuelas repositorioEscuelas;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ServicioAtributosEscuelasImpl servicioAtributosEscuelas;

  private Escuela escuela;
  private AtributosEscuelas atributo;
  private CreacionAtributosEscuelasDTO creacionDTO;
  private RespuestaAtributosEscuelasDTO respuestaDTO;

  @BeforeEach
  void setUp() {
    // Configurar escuela
    escuela = new Escuela();
    escuela.setId(1L);
    escuela.setNombre("Escuela Test");

    // Configurar atributo
    atributo = new AtributosEscuelas();
    atributo.setId(1L);
    atributo.setEscuela(escuela);
    atributo.setNombreAtributo("Atributo Test");
    atributo.setTipoDato("String");
    atributo.setEsRequerido(true);

    // Configurar DTO de creación
    creacionDTO = new CreacionAtributosEscuelasDTO();
    creacionDTO.setNombreAtributo("Atributo Test");
    creacionDTO.setTipoDato("String");
    creacionDTO.setEsRequerido(true);

    // Configurar DTO de respuesta
    respuestaDTO = new RespuestaAtributosEscuelasDTO();
    respuestaDTO.setId(1L);
    respuestaDTO.setNombreAtributo("Atributo Test");
    respuestaDTO.setTipoDato("String");
    respuestaDTO.setEsRequerido(true);
  }

  @Test
  void crearAtributoEscuela_DebeCrearYRetornarAtributo() {
    // Preparar
    when(repositorioEscuelas.findById(1L)).thenReturn(Optional.of(escuela));
    when(modelMapper.map(creacionDTO, AtributosEscuelas.class)).thenReturn(atributo);
    when(atributosEscuelasRepositorio.save(any(AtributosEscuelas.class))).thenReturn(atributo);
    when(modelMapper.map(atributo, RespuestaAtributosEscuelasDTO.class)).thenReturn(respuestaDTO);

    // Ejecutar
    RespuestaAtributosEscuelasDTO resultado = servicioAtributosEscuelas.crearAtributoEscuela(1L, creacionDTO);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Atributo Test", resultado.getNombreAtributo());
    verify(repositorioEscuelas).findById(1L);
    verify(atributosEscuelasRepositorio).save(atributo);
  }

  @Test
  void crearAtributoEscuela_EscuelaNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioEscuelas.findById(1L)).thenReturn(Optional.empty());

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosEscuelas.crearAtributoEscuela(1L, creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Escuela no encontrada"));
  }

  @Test
  void obtenerAtributosPorEscuela_DebeRetornarListaDeAtributos() {
    // Preparar
    List<AtributosEscuelas> atributos = Arrays.asList(atributo);
    when(repositorioEscuelas.existsById(1L)).thenReturn(true);
    when(atributosEscuelasRepositorio.findByEscuelaId(1L)).thenReturn(atributos);
    when(modelMapper.map(atributo, RespuestaAtributosEscuelasDTO.class)).thenReturn(respuestaDTO);

    // Ejecutar
    List<RespuestaAtributosEscuelasDTO> resultado = servicioAtributosEscuelas.obtenerAtributosPorEscuela(1L);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Atributo Test", resultado.get(0).getNombreAtributo());
    verify(atributosEscuelasRepositorio).findByEscuelaId(1L);
  }

  @Test
  void obtenerAtributosPorEscuela_EscuelaNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioEscuelas.existsById(1L)).thenReturn(false);

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosEscuelas.obtenerAtributosPorEscuela(1L);
    });

    assertTrue(exception.getMessage().contains("Escuela no encontrada"));
  }

  @Test
  void obtenerAtributoEscuelaPorId_DebeRetornarAtributo() {
    // Preparar
    when(atributosEscuelasRepositorio.findById(1L)).thenReturn(Optional.of(atributo));
    when(modelMapper.map(atributo, RespuestaAtributosEscuelasDTO.class)).thenReturn(respuestaDTO);

    // Ejecutar
    RespuestaAtributosEscuelasDTO resultado = servicioAtributosEscuelas.obtenerAtributoEscuelaPorId(1L);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Atributo Test", resultado.getNombreAtributo());
    verify(atributosEscuelasRepositorio).findById(1L);
  }

  @Test
  void obtenerAtributoEscuelaPorId_AtributoNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(atributosEscuelasRepositorio.findById(1L)).thenReturn(Optional.empty());

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosEscuelas.obtenerAtributoEscuelaPorId(1L);
    });

    assertTrue(exception.getMessage().contains("Atributo de escuela no encontrado"));
  }

  @Test
  void actualizarAtributoEscuela_DebeActualizarYRetornarAtributo() {
    // Preparar
    when(atributosEscuelasRepositorio.findById(1L)).thenReturn(Optional.of(atributo));
    when(atributosEscuelasRepositorio.save(any(AtributosEscuelas.class))).thenReturn(atributo);
    when(modelMapper.map(atributo, RespuestaAtributosEscuelasDTO.class)).thenReturn(respuestaDTO);

    // Modificar datos de prueba para actualización
    creacionDTO.setNombreAtributo("Atributo Actualizado");
    creacionDTO.setEsRequerido(false);
    respuestaDTO.setNombreAtributo("Atributo Actualizado");
    respuestaDTO.setEsRequerido(false);

    // Ejecutar
    RespuestaAtributosEscuelasDTO resultado = servicioAtributosEscuelas.actualizarAtributoEscuela(1L, creacionDTO);

    // Verificar
    assertNotNull(resultado);
    assertEquals("Atributo Actualizado", resultado.getNombreAtributo());
    assertEquals(false, resultado.getEsRequerido());
    verify(atributosEscuelasRepositorio).findById(1L);
    verify(atributosEscuelasRepositorio).save(atributo);
  }

  @Test
  void actualizarAtributoEscuela_AtributoNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(atributosEscuelasRepositorio.findById(1L)).thenReturn(Optional.empty());

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosEscuelas.actualizarAtributoEscuela(1L, creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Atributo no encontrado"));
  }

  @Test
  void eliminarAtributoEscuela_DebeEliminarAtributo() {
    // Preparar
    when(atributosEscuelasRepositorio.existsById(1L)).thenReturn(true);
    doNothing().when(atributosEscuelasRepositorio).deleteById(1L);

    // Ejecutar
    servicioAtributosEscuelas.eliminarAtributoEscuela(1L);

    // Verificar
    verify(atributosEscuelasRepositorio).deleteById(1L);
  }

  @Test
  void eliminarAtributoEscuela_AtributoNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(atributosEscuelasRepositorio.existsById(1L)).thenReturn(false);

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioAtributosEscuelas.eliminarAtributoEscuela(1L);
    });

    assertTrue(exception.getMessage().contains("Atributo no encontrado"));
  }
}