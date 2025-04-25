package com.yopagocoop.yopagocoop_backend.controladores;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioMiembros;

@WebMvcTest(ControladorMiembros.class)
@Import(ControladorMiembrosTest.ServicioMiembrosTestConfig.class)
public class ControladorMiembrosTest {

  @TestConfiguration
  static class ServicioMiembrosTestConfig {
    @Bean
    ServicioMiembros servicioMiembros() {
      return Mockito.mock(ServicioMiembros.class);
    }
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ServicioMiembros servicioMiembros;

  private ObjectMapper objectMapper;
  private CreacionMiembrosDTO creacionDTO;
  private RespuestaMiembrosDTO respuestaDTO;

  @BeforeEach
  void setUp() {
    // Configure ObjectMapper for proper handling of LocalDateTime
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Setup creation DTO
    creacionDTO = new CreacionMiembrosDTO();
    creacionDTO.setEscuelaId(1L);
    creacionDTO.setNombre("Juan");
    creacionDTO.setApellido("Pérez");
    creacionDTO.setEmail("juan.perez@example.com");
    creacionDTO.setCelular("1123456789");

    // Add attributes
    Map<String, String> atributos = new HashMap<>();
    atributos.put("Grado", "3");
    atributos.put("Sección", "A");
    creacionDTO.setAtributos(atributos);

    // Setup response DTO with a fixed LocalDateTime to avoid serialization issues
    LocalDateTime fixedDateTime = LocalDateTime.of(2023, 5, 15, 10, 30);
    respuestaDTO = new RespuestaMiembrosDTO();
    respuestaDTO.setId(1L);
    respuestaDTO.setEscuelaId(1L);
    respuestaDTO.setEscuelaNombre("Escuela Test");
    respuestaDTO.setNombre("Juan");
    respuestaDTO.setApellido("Pérez");
    respuestaDTO.setEmail("juan.perez@example.com");
    respuestaDTO.setCelular("1123456789");
    respuestaDTO.setFechaCreacion(fixedDateTime);
    respuestaDTO.setAtributos(atributos);
  }

  @Test
  void crearMiembroRetornaMiembroCreado() throws Exception {
    // Setup mock behavior
    when(servicioMiembros.crearMiembro(any(CreacionMiembrosDTO.class))).thenReturn(respuestaDTO);

    // Perform and validate request
    mockMvc.perform(post("/api/miembros")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(creacionDTO)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombre").value("Juan"))
        .andExpect(jsonPath("$.apellido").value("Pérez"))
        .andExpect(jsonPath("$.atributos.Grado").value("3"))
        .andExpect(jsonPath("$.atributos.Sección").value("A"));
  }

  @Test
  void obtenerMiembroPorIdRetornaMiembro() throws Exception {
    // Setup mock behavior
    when(servicioMiembros.obtenerMiembroPorId(1L)).thenReturn(respuestaDTO);

    // Perform and validate request
    mockMvc.perform(get("/api/miembros/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombre").value("Juan"));
  }

  @Test
  void obtenerTodosLosMiembrosRetornaListaDeMiembros() throws Exception {
    // Create a second member
    RespuestaMiembrosDTO miembro2 = new RespuestaMiembrosDTO();
    miembro2.setId(2L);
    miembro2.setEscuelaId(1L);
    miembro2.setEscuelaNombre("Escuela Test");
    miembro2.setNombre("Ana");
    miembro2.setApellido("Gómez");
    miembro2.setEmail("ana.gomez@example.com");
    miembro2.setCelular("1187654321");
    miembro2.setFechaCreacion(LocalDateTime.of(2023, 6, 20, 14, 0));

    Map<String, String> atributos2 = new HashMap<>();
    atributos2.put("Grado", "4");
    atributos2.put("Sección", "B");
    miembro2.setAtributos(atributos2);

    List<RespuestaMiembrosDTO> miembros = Arrays.asList(respuestaDTO, miembro2);

    // Setup mock behavior
    when(servicioMiembros.obtenerTodosLosMiembros()).thenReturn(miembros);

    // Perform and validate request
    mockMvc.perform(get("/api/miembros"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[0].nombre").value("Juan"))
        .andExpect(jsonPath("$[1].nombre").value("Ana"));
  }

  @Test
  void obtenerMiembrosPorEscuelaRetornaMiembrosDeLaEscuela() throws Exception {
    // Setup mock behavior
    List<RespuestaMiembrosDTO> miembros = Arrays.asList(respuestaDTO);
    when(servicioMiembros.obtenerMiembrosPorEscuela(1L)).thenReturn(miembros);

    // Perform and validate request
    mockMvc.perform(get("/api/miembros/escuela/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].escuelaId").value(1));
  }

  @Test
  void actualizarMiembroRetornaMiembroActualizado() throws Exception {
    // Setup mock behavior
    when(servicioMiembros.actualizarMiembro(eq(1L), any(CreacionMiembrosDTO.class))).thenReturn(respuestaDTO);

    // Perform and validate request
    mockMvc.perform(put("/api/miembros/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(creacionDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombre").value("Juan"))
        .andExpect(jsonPath("$.apellido").value("Pérez"));
  }

  @Test
  void eliminarMiembroRetornaEstadoNoContent() throws Exception {
    // Setup mock behavior
    doNothing().when(servicioMiembros).eliminarMiembro(1L);

    // Perform and validate request
    mockMvc.perform(delete("/api/miembros/1"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }
}