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
import org.junit.jupiter.api.Disabled;
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
import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAtributosMiembros;

@Disabled
@WebMvcTest(ControladorAtributosMiembros.class)
@Import(ControladorAtributosMiembrosTest.ServicioAtributosMiembrosTestConfig.class)
public class ControladorAtributosMiembrosTest {

  @TestConfiguration
  static class ServicioAtributosMiembrosTestConfig {
    @Bean
    ServicioAtributosMiembros servicioAtributosMiembros() {
      return Mockito.mock(ServicioAtributosMiembros.class);
    }
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ServicioAtributosMiembros servicioAtributosMiembros;

  private ObjectMapper objectMapper;
  private CreacionAtributosMiembrosDTO creacionDTO;
  private RespuestaAtributosMiembrosDTO respuestaDTO;

  @BeforeEach
  void setUp() {
    // Configura el ObjectMapper
    objectMapper = new ObjectMapper();

    // Configura el DTO para creación
    creacionDTO = new CreacionAtributosMiembrosDTO();
    creacionDTO.setMiembroId(1L);
    creacionDTO.setAtributoEscuelaId(2L);
    creacionDTO.setValorAtributo("Cuarto año");

    // Configura el DTO para respuesta
    respuestaDTO = new RespuestaAtributosMiembrosDTO();
    respuestaDTO.setId(1L);
    respuestaDTO.setMiembroId(1L);
    respuestaDTO.setNombreMiembro("Juan Pérez");
    respuestaDTO.setAtributoEscuelaId(2L);
    respuestaDTO.setNombreAtributoEscuela("Grado");
    respuestaDTO.setValorAtributo("Cuarto año");
  }

  @Test
  void crearAtributoMiembroRetornaAtributoCreado() throws Exception {
    // Configura comportamiento del mock
    when(servicioAtributosMiembros.crearAtributoMiembro(eq(1L), any(CreacionAtributosMiembrosDTO.class)))
        .thenReturn(respuestaDTO);

    // Realiza la petición POST y valida el resultado
    mockMvc.perform(post("/api/miembros/1/atributos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(creacionDTO)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.miembroId").value(1))
        .andExpect(jsonPath("$.nombreMiembro").value("Juan Pérez"))
        .andExpect(jsonPath("$.atributoEscuelaId").value(2))
        .andExpect(jsonPath("$.nombreAtributoEscuela").value("Grado"))
        .andExpect(jsonPath("$.valorAtributo").value("Cuarto año"));
  }

  @Test
  void obtenerAtributosPorMiembroRetornaListaDeAtributos() throws Exception {
    // Crea un segundo atributo para el test
    RespuestaAtributosMiembrosDTO atributo2 = new RespuestaAtributosMiembrosDTO();
    atributo2.setId(2L);
    atributo2.setMiembroId(1L);
    atributo2.setNombreMiembro("Juan Pérez");
    atributo2.setAtributoEscuelaId(3L);
    atributo2.setNombreAtributoEscuela("Sección");
    atributo2.setValorAtributo("A");

    List<RespuestaAtributosMiembrosDTO> atributos = Arrays.asList(respuestaDTO, atributo2);

    // Configura el comportamiento del mock
    when(servicioAtributosMiembros.obtenerAtributosPorMiembro(1L)).thenReturn(atributos);

    // Realiza la petición GET y valida el resultado
    mockMvc.perform(get("/api/miembros/1/atributos"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].valorAtributo").value("Cuarto año"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].valorAtributo").value("A"));
  }

  @Test
  void obtenerAtributoPorIdRetornaAtributo() throws Exception {
    // Configura el mock
    when(servicioAtributosMiembros.obtenerAtributoPorId(1L)).thenReturn(respuestaDTO);

    // Realiza la petición GET y valida el resultado
    mockMvc.perform(get("/api/miembros/1/atributos/1"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.miembroId").value(1))
        .andExpect(jsonPath("$.nombreMiembro").value("Juan Pérez"));
  }

  @Test
  void actualizarAtributoMiembroRetornaAtributoActualizado() throws Exception {
    // Configura el comportamiento del mock
    when(servicioAtributosMiembros.actualizarAtributoMiembro(eq(1L), any(CreacionAtributosMiembrosDTO.class)))
        .thenReturn(respuestaDTO);

    // Realiza la petición PUT y valida el resultado
    mockMvc.perform(put("/api/miembros/1/atributos/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(creacionDTO)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.valorAtributo").value("Cuarto año"));
  }

  @Test
  void eliminarAtributoMiembroRetornaEstadoNoContent() throws Exception {
    // Configura el mock para eliminar
    doNothing().when(servicioAtributosMiembros).eliminarAtributoMiembro(1L);

    // Realiza la petición DELETE y valida que el estado sea 204 (No Content)
    mockMvc.perform(delete("/api/miembros/1/atributos/1"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }
}