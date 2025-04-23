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
import java.util.List;

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
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioEscuelas;

@WebMvcTest(ControladorEscuelas.class)
@Import(ControladorEscuelasTest.ServicioEscuelasTestConfig.class)
public class ControladorEscuelasTest {

  @TestConfiguration
  static class ServicioEscuelasTestConfig {
    @Bean
    ServicioEscuelas servicioEscuelas() {
      return Mockito.mock(ServicioEscuelas.class);
    }
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ServicioEscuelas servicioEscuelas;

  private ObjectMapper objectMapper;
  private CreacionEscuelasDTO creacionDTO;
  private RespuestaEscuelasDTO respuestaDTO;

  @BeforeEach
  void setUp() {
    // Configurar ObjectMapper para el manejo correcto de LocalDateTime
    objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Configurar DTO de creación
    creacionDTO = new CreacionEscuelasDTO();
    creacionDTO.setNombre("Escuela Test");
    creacionDTO.setDireccion("Calle Test 123");
    creacionDTO.setCuit("30123456789");
    creacionDTO.setEmail("escuela@test.com");

    // Configurar DTO de respuesta con un LocalDateTime fijo para evitar problemas
    // de serialización
    LocalDateTime fixedDateTime = LocalDateTime.of(2023, 5, 15, 10, 30);
    respuestaDTO = new RespuestaEscuelasDTO();
    respuestaDTO.setId(1L);
    respuestaDTO.setNombre("Escuela Test");
    respuestaDTO.setDireccion("Calle Test 123");
    respuestaDTO.setCuit("30123456789");
    respuestaDTO.setEmail("escuela@test.com");
    respuestaDTO.setFechaCreacion(fixedDateTime);
  }

  @Test
  void crearEscuelaRetornaEscuelaCreada() throws Exception {
    // Configurar comportamiento del mock
    when(servicioEscuelas.crearEscuela(any(CreacionEscuelasDTO.class))).thenReturn(respuestaDTO);

    // Realizar y validar la petición
    mockMvc.perform(post("/api/escuelas")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(creacionDTO)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombre").value("Escuela Test"))
        .andExpect(jsonPath("$.cuit").value("30123456789"));
  }

  @Test
  void obtenerEscuelaPorIdRetornaEscuela() throws Exception {
    // Configurar comportamiento del mock
    when(servicioEscuelas.obtenerEscuelaPorId(1L)).thenReturn(respuestaDTO);

    // Realizar y validar la petición
    mockMvc.perform(get("/api/escuelas/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombre").value("Escuela Test"));
  }

  @Test
  void obtenerTodasLasEscuelasRetornaListaDeEscuelas() throws Exception {
    // Crear una segunda escuela
    RespuestaEscuelasDTO escuela2 = new RespuestaEscuelasDTO();
    escuela2.setId(2L);
    escuela2.setNombre("Otra Escuela");
    escuela2.setDireccion("Otra Calle 456");
    escuela2.setCuit("30987654321");
    escuela2.setEmail("otra@escuela.com");
    escuela2.setFechaCreacion(LocalDateTime.of(2023, 6, 20, 14, 0));

    List<RespuestaEscuelasDTO> escuelas = Arrays.asList(respuestaDTO, escuela2);

    // Configurar comportamiento del mock
    when(servicioEscuelas.obtenerTodasLasEscuelas()).thenReturn(escuelas);

    // Realizar y validar la petición
    mockMvc.perform(get("/api/escuelas"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2));
  }

  @Test
  void actualizarEscuelaRetornaEscuelaActualizada() throws Exception {
    // Configurar comportamiento del mock
    when(servicioEscuelas.actualizarEscuela(eq(1L), any(CreacionEscuelasDTO.class))).thenReturn(respuestaDTO);

    // Realizar y validar la petición
    mockMvc.perform(put("/api/escuelas/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(creacionDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombre").value("Escuela Test"));
  }

  @Test
  void eliminarEscuelaRetornaEstadoNoContent() throws Exception {
    // Configurar comportamiento del mock
    doNothing().when(servicioEscuelas).eliminarEscuela(1L);

    // Realizar y validar la petición
    mockMvc.perform(delete("/api/escuelas/1"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }
}