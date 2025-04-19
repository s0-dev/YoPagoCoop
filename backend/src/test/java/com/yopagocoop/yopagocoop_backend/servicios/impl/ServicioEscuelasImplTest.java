package com.yopagocoop.yopagocoop_backend.servicios.impl;

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioEscuelas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServicioEscuelasImplTest {

  @Mock
  private RepositorioEscuelas repositorioEscuelas;

  @Mock
  private AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ServicioEscuelasImpl servicioEscuelas;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    repositorioEscuelas.deleteAll();
  }

  @Test
  public void testCrearEscuela() {
    CreacionEscuelasDTO escuelaCreacionDTO = new CreacionEscuelasDTO();
    escuelaCreacionDTO.setNombre("Escuela de Prueba");
    escuelaCreacionDTO.setDireccion("Direccion de Prueba");
    escuelaCreacionDTO.setCuit("12345678901");
    escuelaCreacionDTO.setEmail("prueba@escuela.com");

    Escuela escuela = new Escuela();
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
    escuela.setCuit("12345678901");
    escuela.setEmail("prueba@escuela.com");

    RespuestaEscuelasDTO escuelaRespuestaDTO = new RespuestaEscuelasDTO();
    escuelaRespuestaDTO.setNombre("Escuela de Prueba");
    escuelaRespuestaDTO.setDireccion("Direccion de Prueba");
    escuelaRespuestaDTO.setCuit("12345678901");
    escuelaRespuestaDTO.setEmail("prueba@escuela.com");

    when(modelMapper.map(escuelaCreacionDTO, Escuela.class)).thenReturn(escuela);
    when(repositorioEscuelas.save(any(Escuela.class))).thenReturn(escuela);
    when(modelMapper.map(escuela, RespuestaEscuelasDTO.class)).thenReturn(escuelaRespuestaDTO);

    RespuestaEscuelasDTO escuelaCreada = servicioEscuelas.crearEscuela(escuelaCreacionDTO);

    assertNotNull(escuelaCreada);
    assertEquals("Escuela de Prueba", escuelaCreada.getNombre());
    verify(repositorioEscuelas, times(1)).save(escuela);
  }

  @Test
  public void testActualizarEscuela() {
    Long id = 1L;
    CreacionEscuelasDTO escuelaCreacionDTO = new CreacionEscuelasDTO();
    escuelaCreacionDTO.setNombre("Escuela Nueva");
    escuelaCreacionDTO.setDireccion("Direccion Nueva");
    escuelaCreacionDTO.setCuit("12345678901");
    escuelaCreacionDTO.setEmail("nueva@escuela.com");

    Escuela escuelaExistente = new Escuela();
    escuelaExistente.setId(id);
    escuelaExistente.setNombre("Escuela Antigua");
    escuelaExistente.setDireccion("Direccion Antigua");
    escuelaExistente.setCuit("12345678901");
    escuelaExistente.setEmail("antigua@escuela.com");

    Escuela escuelaActualizada = new Escuela();
    escuelaActualizada.setId(id);
    escuelaActualizada.setNombre("Escuela Nueva");
    escuelaActualizada.setDireccion("Direccion Nueva");
    escuelaActualizada.setCuit("12345678901");
    escuelaActualizada.setEmail("nueva@escuela.com");

    RespuestaEscuelasDTO escuelaRespuestaDTO = new RespuestaEscuelasDTO();
    escuelaRespuestaDTO.setId(id);
    escuelaRespuestaDTO.setNombre("Escuela Nueva");
    escuelaRespuestaDTO.setDireccion("Direccion Nueva");
    escuelaRespuestaDTO.setCuit("12345678901");
    escuelaRespuestaDTO.setEmail("nueva@escuela.com");

    when(repositorioEscuelas.existsById(id)).thenReturn(true);
    when(modelMapper.map(escuelaCreacionDTO, Escuela.class)).thenReturn(escuelaActualizada);
    when(repositorioEscuelas.save(any(Escuela.class))).thenReturn(escuelaActualizada);
    when(modelMapper.map(escuelaActualizada, RespuestaEscuelasDTO.class)).thenReturn(escuelaRespuestaDTO);

    RespuestaEscuelasDTO result = servicioEscuelas.actualizarEscuela(id, escuelaCreacionDTO);

    assertNotNull(result);
    assertEquals("Escuela Nueva", result.getNombre());
    assertEquals(id, result.getId());
    verify(repositorioEscuelas, times(1)).save(escuelaActualizada);
  }

  @Test
  public void testActualizarEscuelaNoExistente() {
    Long id = 1L;
    CreacionEscuelasDTO escuelaCreacionDTO = new CreacionEscuelasDTO();
    escuelaCreacionDTO.setNombre("Escuela Nueva");
    escuelaCreacionDTO.setDireccion("Direccion Nueva");
    escuelaCreacionDTO.setCuit("12345678901");
    escuelaCreacionDTO.setEmail("nueva@escuela.com");

    when(repositorioEscuelas.existsById(id)).thenReturn(false);

    assertThrows(RuntimeException.class, () -> servicioEscuelas.actualizarEscuela(id, escuelaCreacionDTO));
  }

  @Test
  public void testEliminarEscuela() {
    Long id = 1L;

    doNothing().when(repositorioEscuelas).deleteById(id);

    servicioEscuelas.eliminarEscuela(id);

    verify(repositorioEscuelas, times(1)).deleteById(id);
  }

  @Test
  public void testObtenerEscuela() {
    Long id = 1L;
    Escuela escuela = new Escuela();
    escuela.setId(id);
    escuela.setNombre("Escuela de Prueba");
    escuela.setDireccion("Direccion de Prueba");
    escuela.setCuit("12345678901");
    escuela.setEmail("prueba@escuela.com");

    RespuestaEscuelasDTO escuelaRespuestaDTO = new RespuestaEscuelasDTO();
    escuelaRespuestaDTO.setId(id);
    escuelaRespuestaDTO.setNombre("Escuela de Prueba");
    escuelaRespuestaDTO.setDireccion("Direccion de Prueba");
    escuelaRespuestaDTO.setCuit("12345678901");
    escuelaRespuestaDTO.setEmail("prueba@escuela.com");

    when(repositorioEscuelas.findById(id)).thenReturn(Optional.of(escuela));
    when(modelMapper.map(escuela, RespuestaEscuelasDTO.class)).thenReturn(escuelaRespuestaDTO);

    RespuestaEscuelasDTO escuelaEncontrada = servicioEscuelas.obtenerEscuelaPorId(id);

    assertNotNull(escuelaEncontrada);
    assertEquals(id, escuelaEncontrada.getId());
    assertEquals("Escuela de Prueba", escuelaEncontrada.getNombre());
    verify(repositorioEscuelas, times(1)).findById(id);
  }

  @Test
  public void testObtenerEscuelaNoExistente() {
    Long id = 1L;

    when(repositorioEscuelas.findById(id)).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> servicioEscuelas.obtenerEscuelaPorId(id));
  }

  @Test
  public void testObtenerTodasLasEscuelasRespuesta() {
    Escuela escuela1 = new Escuela();
    escuela1.setId(1L);
    escuela1.setNombre("Escuela 1");
    escuela1.setDireccion("Direccion 1");
    escuela1.setCuit("12345678901");
    escuela1.setEmail("prueba1@escuela.com");
    Escuela escuela2 = new Escuela();
    escuela2.setId(2L);
    escuela2.setNombre("Escuela 2");
    escuela2.setDireccion("Direccion 2");
    escuela2.setCuit("12345678902");
    escuela2.setEmail("prueba2@escuela.com");
    List<Escuela> escuelas = Arrays.asList(escuela1, escuela2);

    RespuestaEscuelasDTO escuelaRespuestaDTO1 = new RespuestaEscuelasDTO();
    escuelaRespuestaDTO1.setId(1L);
    escuelaRespuestaDTO1.setNombre("Escuela 1");
    escuelaRespuestaDTO1.setDireccion("Direccion 1");
    escuelaRespuestaDTO1.setCuit("12345678901");
    escuelaRespuestaDTO1.setEmail("prueba1@escuela.com");
    RespuestaEscuelasDTO escuelaRespuestaDTO2 = new RespuestaEscuelasDTO();
    escuelaRespuestaDTO2.setId(2L);
    escuelaRespuestaDTO2.setNombre("Escuela 2");
    escuelaRespuestaDTO2.setDireccion("Direccion 2");
    escuelaRespuestaDTO2.setCuit("12345678902");
    escuelaRespuestaDTO2.setEmail("prueba2@escuela.com");

    when(repositorioEscuelas.findAll()).thenReturn(escuelas);
    when(modelMapper.map(escuela1, RespuestaEscuelasDTO.class)).thenReturn(escuelaRespuestaDTO1);
    when(modelMapper.map(escuela2, RespuestaEscuelasDTO.class)).thenReturn(escuelaRespuestaDTO2);

    List<RespuestaEscuelasDTO> escuelasEncontradas = servicioEscuelas.obtenerTodasLasEscuelas();

    assertEquals(2, escuelasEncontradas.size());
    assertEquals("Escuela 1", escuelasEncontradas.get(0).getNombre());
    assertEquals("Escuela 2", escuelasEncontradas.get(1).getNombre());
    verify(repositorioEscuelas, times(1)).findAll();
  }
}