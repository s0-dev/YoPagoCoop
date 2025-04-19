package com.yopagocoop.yopagocoop_backend.servicios.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosMiembros;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosMiembrosRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioEscuelas;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioMiembros;

@ExtendWith(MockitoExtension.class)
public class ServicioMiembrosImplTest {

  @Mock
  private RepositorioMiembros repositorioMiembros;

  @Mock
  private RepositorioEscuelas repositorioEscuelas;

  @Mock
  private AtributosMiembrosRepositorio atributosMiembrosRepositorio;

  @Mock
  private AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ServicioMiembrosImpl servicioMiembros;

  private Escuela escuela;
  private Miembro miembro;
  private AtributosEscuelas atributoEscuela1;
  private AtributosEscuelas atributoEscuela2;
  private AtributosMiembros atributoMiembro1;
  private AtributosMiembros atributoMiembro2;
  private CreacionMiembrosDTO creacionDTO;
  private RespuestaMiembrosDTO respuestaDTO;
  private Map<String, String> atributos;
  private List<AtributosMiembros> listaAtributosMiembros;

  @BeforeEach
  void setUp() {
    // Configurar escuela
    escuela = new Escuela();
    escuela.setId(1L);
    escuela.setNombre("Escuela Test");

    // Configurar miembro
    miembro = new Miembro();
    miembro.setId(1L);
    miembro.setEscuela(escuela);
    miembro.setNombre("Juan");
    miembro.setApellido("Pérez");
    miembro.setDni("12345678");
    miembro.setEmail("juan@example.com");
    miembro.setCelular("123456789");
    miembro.setFechaCreacion(LocalDateTime.now());

    // Configurar atributos específicos de la escuela
    atributoEscuela1 = new AtributosEscuelas();
    atributoEscuela1.setId(1L);
    atributoEscuela1.setEscuela(escuela);
    atributoEscuela1.setNombreAtributo("Grado");
    atributoEscuela1.setTipoDato("String");
    atributoEscuela1.setEsRequerido(true);

    atributoEscuela2 = new AtributosEscuelas();
    atributoEscuela2.setId(2L);
    atributoEscuela2.setEscuela(escuela);
    atributoEscuela2.setNombreAtributo("Sección");
    atributoEscuela2.setTipoDato("String");
    atributoEscuela2.setEsRequerido(true);

    // Configurar atributos de miembro
    atributoMiembro1 = new AtributosMiembros();
    atributoMiembro1.setId(1L);
    atributoMiembro1.setMiembro(miembro);
    atributoMiembro1.setAtributoEspecificoEscuela(atributoEscuela1);
    atributoMiembro1.setValorAtributo("Primero");

    atributoMiembro2 = new AtributosMiembros();
    atributoMiembro2.setId(2L);
    atributoMiembro2.setMiembro(miembro);
    atributoMiembro2.setAtributoEspecificoEscuela(atributoEscuela2);
    atributoMiembro2.setValorAtributo("A");

    listaAtributosMiembros = Arrays.asList(atributoMiembro1, atributoMiembro2);

    // Configurar mapa de atributos para DTO
    atributos = new HashMap<>();
    atributos.put("Grado", "Primero");
    atributos.put("Sección", "A");

    // Configurar DTO de creación
    creacionDTO = new CreacionMiembrosDTO();
    creacionDTO.setEscuelaId(1L);
    creacionDTO.setNombre("Juan");
    creacionDTO.setApellido("Pérez");
    creacionDTO.setDni("12345678");
    creacionDTO.setEmail("juan@example.com");
    creacionDTO.setCelular("123456789");
    creacionDTO.setAtributos(atributos);

    // Configurar DTO de respuesta
    respuestaDTO = new RespuestaMiembrosDTO();
    respuestaDTO.setId(1L);
    respuestaDTO.setEscuelaId(1L);
    respuestaDTO.setEscuelaNombre("Escuela Test");
    respuestaDTO.setNombre("Juan");
    respuestaDTO.setApellido("Pérez");
    respuestaDTO.setDni("12345678");
    respuestaDTO.setEmail("juan@example.com");
    respuestaDTO.setCelular("123456789");
    respuestaDTO.setFechaCreacion(miembro.getFechaCreacion());
    // No setting attributes here, will be done by service
  }

  @Test
  void crearMiembro_DebeCrearYRetornarMiembro() {
    // Preparar
    when(repositorioEscuelas.findById(1L)).thenReturn(Optional.of(escuela));
    when(modelMapper.map(creacionDTO, Miembro.class)).thenReturn(miembro);
    when(repositorioMiembros.save(any(Miembro.class))).thenReturn(miembro);
    when(modelMapper.map(miembro, RespuestaMiembrosDTO.class)).thenReturn(respuestaDTO);

    // Mock para el proceso de atributos - Important for the fix
    when(atributosEscuelasRepositorio.findByEscuelaIdAndNombreAtributo(eq(1L), eq("Grado")))
        .thenReturn(Optional.of(atributoEscuela1));
    when(atributosEscuelasRepositorio.findByEscuelaIdAndNombreAtributo(eq(1L), eq("Sección")))
        .thenReturn(Optional.of(atributoEscuela2));
    when(atributosMiembrosRepositorio.findByMiembro(miembro)).thenReturn(listaAtributosMiembros);

    // Mock save for attribute
    when(atributosMiembrosRepositorio.save(any(AtributosMiembros.class))).thenReturn(atributoMiembro1);

    // Ejecutar
    RespuestaMiembrosDTO resultado = servicioMiembros.crearMiembro(creacionDTO);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Juan", resultado.getNombre());
    assertEquals("Pérez", resultado.getApellido());
    verify(repositorioEscuelas).findById(1L);
    verify(repositorioMiembros).save(miembro);
    verify(atributosEscuelasRepositorio).findByEscuelaIdAndNombreAtributo(eq(1L), eq("Grado"));
    verify(atributosEscuelasRepositorio).findByEscuelaIdAndNombreAtributo(eq(1L), eq("Sección"));
    verify(atributosMiembrosRepositorio, times(2)).save(any(AtributosMiembros.class));
    verify(atributosMiembrosRepositorio).findByMiembro(miembro);
  }

  @Test
  void crearMiembro_EscuelaNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioEscuelas.findById(1L)).thenReturn(Optional.empty());

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioMiembros.crearMiembro(creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Escuela no encontrada"));
  }

  @Test
  void crearMiembro_AtributoEscuelaNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioEscuelas.findById(1L)).thenReturn(Optional.of(escuela));
    when(modelMapper.map(creacionDTO, Miembro.class)).thenReturn(miembro);
    when(repositorioMiembros.save(any(Miembro.class))).thenReturn(miembro);
    when(atributosEscuelasRepositorio.findByEscuelaIdAndNombreAtributo(anyLong(), anyString()))
        .thenReturn(Optional.empty());

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioMiembros.crearMiembro(creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Atributo específico"));
  }

  @Test
  void actualizarMiembro_DebeEjecutarualizarYRetornarMiembro() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.of(miembro));
    when(repositorioMiembros.save(any(Miembro.class))).thenReturn(miembro);
    when(modelMapper.map(miembro, RespuestaMiembrosDTO.class)).thenReturn(respuestaDTO);
    doNothing().when(atributosMiembrosRepositorio).deleteByMiembro(miembro);

    // Mock para el proceso de atributos
    when(atributosEscuelasRepositorio.findByEscuelaIdAndNombreAtributo(eq(1L), eq("Grado")))
        .thenReturn(Optional.of(atributoEscuela1));
    when(atributosEscuelasRepositorio.findByEscuelaIdAndNombreAtributo(eq(1L), eq("Sección")))
        .thenReturn(Optional.of(atributoEscuela2));
    when(atributosMiembrosRepositorio.findByMiembro(miembro)).thenReturn(listaAtributosMiembros);
    when(atributosMiembrosRepositorio.save(any(AtributosMiembros.class))).thenReturn(atributoMiembro1);

    // Modificar datos para actualización
    creacionDTO.setNombre("Juan Carlos");
    creacionDTO.setEmail("juancarlos@example.com");
    respuestaDTO.setNombre("Juan Carlos");
    respuestaDTO.setEmail("juancarlos@example.com");

    // Ejecutar
    RespuestaMiembrosDTO resultado = servicioMiembros.actualizarMiembro(1L, creacionDTO);

    // Verificar
    assertNotNull(resultado);
    assertEquals("Juan Carlos", resultado.getNombre());
    assertEquals("juancarlos@example.com", resultado.getEmail());
    verify(repositorioMiembros).findById(1L);
    verify(repositorioMiembros).save(miembro);
    verify(atributosMiembrosRepositorio).deleteByMiembro(miembro);
    verify(atributosEscuelasRepositorio).findByEscuelaIdAndNombreAtributo(eq(1L), eq("Grado"));
    verify(atributosEscuelasRepositorio).findByEscuelaIdAndNombreAtributo(eq(1L), eq("Sección"));
    verify(atributosMiembrosRepositorio).findByMiembro(miembro);
  }

  @Test
  void actualizarMiembro_CambioDeEscuela_DebeEjecutarualizarEscuelaYRetornarMiembro() {
    // Preparar
    Escuela nuevaEscuela = new Escuela();
    nuevaEscuela.setId(2L);
    nuevaEscuela.setNombre("Nueva Escuela");

    when(repositorioMiembros.findById(1L)).thenReturn(Optional.of(miembro));
    when(repositorioEscuelas.findById(2L)).thenReturn(Optional.of(nuevaEscuela));
    when(repositorioMiembros.save(any(Miembro.class))).thenReturn(miembro);
    when(modelMapper.map(miembro, RespuestaMiembrosDTO.class)).thenReturn(respuestaDTO);
    doNothing().when(atributosMiembrosRepositorio).deleteByMiembro(miembro);

    // Mock para el proceso de atributos
    when(atributosEscuelasRepositorio.findByEscuelaIdAndNombreAtributo(anyLong(), anyString()))
        .thenReturn(Optional.of(atributoEscuela1));
    when(atributosMiembrosRepositorio.findByMiembro(miembro)).thenReturn(listaAtributosMiembros);
    when(atributosMiembrosRepositorio.save(any(AtributosMiembros.class))).thenReturn(atributoMiembro1);

    // Modificar datos para cambio de escuela
    creacionDTO.setEscuelaId(2L);

    // Ejecutar
    RespuestaMiembrosDTO resultado = servicioMiembros.actualizarMiembro(1L, creacionDTO);

    // Verificar
    assertNotNull(resultado);
    verify(repositorioEscuelas).findById(2L);
    verify(repositorioMiembros).save(miembro);
    verify(atributosMiembrosRepositorio).findByMiembro(miembro);
  }

  @Test
  void actualizarMiembro_MiembroNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.empty());

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioMiembros.actualizarMiembro(1L, creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Miembro no encontrado"));
  }

  @Test
  void actualizarMiembro_EscuelaNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.of(miembro));
    creacionDTO.setEscuelaId(2L); // Different school ID
    when(repositorioEscuelas.findById(2L)).thenReturn(Optional.empty());

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioMiembros.actualizarMiembro(1L, creacionDTO);
    });

    assertTrue(exception.getMessage().contains("Escuela no encontrada"));
  }

  @Test
  void obtenerMiembroPorId_DebeRetornarMiembro() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.of(miembro));
    when(modelMapper.map(miembro, RespuestaMiembrosDTO.class)).thenReturn(respuestaDTO);
    when(atributosMiembrosRepositorio.findByMiembro(miembro)).thenReturn(listaAtributosMiembros);

    // Ejecutar
    RespuestaMiembrosDTO resultado = servicioMiembros.obtenerMiembroPorId(1L);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1L, resultado.getId());
    assertEquals("Juan", resultado.getNombre());
    verify(repositorioMiembros).findById(1L);
    verify(atributosMiembrosRepositorio).findByMiembro(miembro);
  }

  @Test
  void obtenerMiembroPorId_MiembroNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioMiembros.findById(1L)).thenReturn(Optional.empty());

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioMiembros.obtenerMiembroPorId(1L);
    });

    assertTrue(exception.getMessage().contains("Miembro no encontrado"));
  }

  @Test
  void obtenerTodosLosMiembros_DebeRetornarListaDeMiembros() {
    // Preparar
    List<Miembro> miembros = Arrays.asList(miembro);
    when(repositorioMiembros.findAll()).thenReturn(miembros);
    when(modelMapper.map(miembro, RespuestaMiembrosDTO.class)).thenReturn(respuestaDTO);
    when(atributosMiembrosRepositorio.findByMiembro(miembro)).thenReturn(listaAtributosMiembros);

    // Ejecutar
    List<RespuestaMiembrosDTO> resultado = servicioMiembros.obtenerTodosLosMiembros();

    // Verificar
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Juan", resultado.get(0).getNombre());
    verify(repositorioMiembros).findAll();
    verify(atributosMiembrosRepositorio).findByMiembro(miembro);
  }

  @Test
  void obtenerMiembrosPorEscuela_DebeRetornarMiembrosDeLaEscuela() {
    // Preparar
    List<Miembro> miembros = Arrays.asList(miembro);
    when(repositorioEscuelas.existsById(1L)).thenReturn(true);
    when(repositorioMiembros.findByEscuelaId(1L)).thenReturn(miembros);
    when(modelMapper.map(miembro, RespuestaMiembrosDTO.class)).thenReturn(respuestaDTO);
    when(atributosMiembrosRepositorio.findByMiembro(miembro)).thenReturn(listaAtributosMiembros);

    // Ejecutar
    List<RespuestaMiembrosDTO> resultado = servicioMiembros.obtenerMiembrosPorEscuela(1L);

    // Verificar
    assertNotNull(resultado);
    assertEquals(1, resultado.size());
    assertEquals("Juan", resultado.get(0).getNombre());
    verify(repositorioMiembros).findByEscuelaId(1L);
    verify(atributosMiembrosRepositorio).findByMiembro(miembro);
  }

  @Test
  void obtenerMiembrosPorEscuela_EscuelaNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioEscuelas.existsById(1L)).thenReturn(false);

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioMiembros.obtenerMiembrosPorEscuela(1L);
    });

    assertTrue(exception.getMessage().contains("Escuela no encontrada"));
  }

  @Test
  void eliminarMiembro_DebeEliminarMiembro() {
    // Preparar
    when(repositorioMiembros.existsById(1L)).thenReturn(true);
    doNothing().when(repositorioMiembros).deleteById(1L);

    // Ejecutar
    servicioMiembros.eliminarMiembro(1L);

    // Verificar
    verify(repositorioMiembros).deleteById(1L);
  }

  @Test
  void eliminarMiembro_MiembroNoExistente_DebeLanzarExcepcion() {
    // Preparar
    when(repositorioMiembros.existsById(1L)).thenReturn(false);

    // Ejecutar & Verificar
    Exception exception = assertThrows(RuntimeException.class, () -> {
      servicioMiembros.eliminarMiembro(1L);
    });

    assertTrue(exception.getMessage().contains("Miembro no encontrado"));
  }
}