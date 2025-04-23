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
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAtributosEscuelas;

@WebMvcTest(ControladorAtributosEscuelas.class)
@Import(ControladorAtributosEscuelasTest.ServicioAtributosEscuelasTestConfig.class)
public class ControladorAtributosEscuelasTest {

  @TestConfiguration
  static class ServicioAtributosEscuelasTestConfig {
    @Bean
    ServicioAtributosEscuelas servicioAtributosEscuelas() {
      return Mockito.mock(ServicioAtributosEscuelas.class);
    }
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ServicioAtributosEscuelas servicioAtributosEscuelas;

  private ObjectMapper objectMapper;
  private CreacionAtributosEscuelasDTO creacionDTO;
  private RespuestaAtributosEscuelasDTO respuestaDTO;

  @BeforeEach
  void setUp() {
    // Configura el ObjectMapper
    objectMapper = new ObjectMapper();

    // Configura el DTO para creación
    creacionDTO = new CreacionAtributosEscuelasDTO();
    creacionDTO.setNombreAtributo("Grado");
    creacionDTO.setTipoDato("String");
    creacionDTO.setEsRequerido(true);

    // Configura el DTO para respuesta
    respuestaDTO = new RespuestaAtributosEscuelasDTO();
    respuestaDTO.setId(1L);
    respuestaDTO.setNombreAtributo("Grado");
    respuestaDTO.setTipoDato("String");
    respuestaDTO.setEsRequerido(true);
  }

  @Test
  void crearAtributoEscuelaRetornaAtributoCreado() throws Exception {
    // Configura comportamiento del mock
    when(servicioAtributosEscuelas.crearAtributoEscuela(eq(1L), any(CreacionAtributosEscuelasDTO.class)))
        .thenReturn(respuestaDTO);

    // Realiza la petición POST y valida el resultado
    mockMvc.perform(post("/api/escuelas/1/atributos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(creacionDTO)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombreAtributo").value("Grado"))
        .andExpect(jsonPath("$.tipoDato").value("String"))
        .andExpect(jsonPath("$.esRequerido").value(true));
  }

  @Test
  void obtenerAtributosPorEscuelaRetornaListaDeAtributos() throws Exception {
    // Crea un segundo atributo para el test
    RespuestaAtributosEscuelasDTO atributo2 = new RespuestaAtributosEscuelasDTO();
    atributo2.setId(2L);
    atributo2.setNombreAtributo("Sección");
    atributo2.setTipoDato("String");
    atributo2.setEsRequerido(true);

    List<RespuestaAtributosEscuelasDTO> atributos = Arrays.asList(respuestaDTO, atributo2);

    // Configura el comportamiento del mock
    when(servicioAtributosEscuelas.obtenerAtributosPorEscuela(1L)).thenReturn(atributos);

    // Realiza la petición GET y valida el resultado
    mockMvc.perform(get("/api/escuelas/1/atributos"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2));
  }

  @Test
  void obtenerAtributoEscuelaPorIdRetornaAtributo() throws Exception {
    // Configura el mock
    when(servicioAtributosEscuelas.obtenerAtributoEscuelaPorId(1L)).thenReturn(respuestaDTO);

    // Realiza la petición GET y valida el resultado
    mockMvc.perform(get("/api/escuelas/1/atributos/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombreAtributo").value("Grado"));
  }

  @Test
  void actualizarAtributoEscuelaRetornaAtributoActualizado() throws Exception {
    // Configura el comportamiento del mock
    when(servicioAtributosEscuelas.actualizarAtributoEscuela(eq(1L), any(CreacionAtributosEscuelasDTO.class)))
        .thenReturn(respuestaDTO);

    // Realiza la petición PUT y valida el resultado
    mockMvc.perform(put("/api/escuelas/1/atributos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(creacionDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nombreAtributo").value("Grado"));
  }

  @Test
  void eliminarAtributoEscuelaRetornaEstadoNoContent() throws Exception {
    // Configura el mock para eliminar
    doNothing().when(servicioAtributosEscuelas).eliminarAtributoEscuela(1L);

    // Realiza la petición DELETE y valida que el estado sea 204 (No Content)
    mockMvc.perform(delete("/api/escuelas/1/atributos/1"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }
}
