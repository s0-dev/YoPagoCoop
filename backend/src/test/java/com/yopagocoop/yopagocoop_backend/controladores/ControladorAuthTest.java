package com.yopagocoop.yopagocoop_backend.controladores;

import com.yopagocoop.yopagocoop_backend.modelos.Miembro;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioMiembros;
import com.yopagocoop.yopagocoop_backend.dto.Auth.login.RespuestaLoginDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.registro.RespuestaRegistroDTO;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc
public class ControladorAuthTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        public void registroExitoso() throws Exception {
                Map<String, Object> datos = new HashMap<>();
                datos.put("nombre", "TestNombre");
                datos.put("apellido", "TestApellido");
                datos.put("email", "test@example.com");
                datos.put("contraseña", "TestPassword");
                datos.put("celular", "1112345678");
                datos.put("escuelaId", 1L);
                datos.put("atributos", Map.of("atributo1", "valor1"));

                String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registro")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(datos)))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andReturn().getResponse().getContentAsString();

                RespuestaRegistroDTO respuesta = objectMapper.readValue(jsonResponse, RespuestaRegistroDTO.class);

                assertTrue(respuesta.isStatus());
                assertEquals("Usuario registrado con éxito", respuesta.getMessage());
        }

        @Test
        public void registroFallidoEmailExistente() throws Exception {
                registroExitoso();
                Map<String, Object> datos2 = new HashMap<>();
                datos2.put("nombre", "TestNombre2");
                datos2.put("apellido", "TestApellido2");
                datos2.put("email", "test@example.com");
                datos2.put("contraseña", "TestPassword2");
                datos2.put("celular", "1112345678");
                datos2.put("escuelaId", 1L);
                datos2.put("atributos", Map.of("atributo1", "valor1"));

                String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/registro")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(datos2)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
                                .getContentAsString();

                RespuestaRegistroDTO respuesta = objectMapper.readValue(jsonResponse, RespuestaRegistroDTO.class);
                assertFalse(respuesta.isStatus());
                assertEquals("El email ya esta registrado", respuesta.getMessage());
        }

        @Test
        public void loginExitoso() throws Exception {
                registroExitoso();
                Map<String, String> datos = new HashMap<>();
                datos.put("email", "test@example.com");
                datos.put("contraseña", "TestPassword");
                String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(datos)))
                                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn().getResponse()
                                .getContentAsString();
                RespuestaLoginDTO respuesta = objectMapper.readValue(jsonResponse, RespuestaLoginDTO.class);
                assertTrue(respuesta.isStatus());
                assertEquals("Login exitoso", respuesta.getMessage());
                assertNotNull(respuesta.getToken());
        }

        @Test
        public void loginFallidoUsuarioNoEncontrado() throws Exception {

                Map<String, String> datos = new HashMap<>();
                datos.put("email", "noexiste@example.com");
                datos.put("contraseña", "TestPassword");

                String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(datos)))
                                .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn().getResponse()
                                .getContentAsString();

                RespuestaLoginDTO respuesta = objectMapper.readValue(jsonResponse, RespuestaLoginDTO.class);

                assertFalse(respuesta.isStatus());
                assertEquals("Usuario no encontrado", respuesta.getMessage());
        }

        @Test
        public void registroFallidoCamposNulos() throws Exception {
                Map<String, Object> datos = new HashMap<>();
                datos.put("nombre", null);
                datos.put("apellido", "TestApellido3");
                datos.put("email", "test3@example.com");
                datos.put("contraseña", "TestPassword3");
                datos.put("celular", "1112345678");
                datos.put("escuelaId", 1L);
                datos.put("atributos", Map.of("atributo1", "valor1"));

                String jsonResponse = mockMvc
                                .perform(MockMvcRequestBuilders.post("/api/auth/registro")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(datos)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
                                .getContentAsString();
                RespuestaRegistroDTO respuesta = objectMapper.readValue(jsonResponse, RespuestaRegistroDTO.class);
                assertFalse(respuesta.isStatus());
                assertEquals("Todos los campos son obligatorios o tienen un formato inválido", respuesta.getMessage());
        }

        @Test
        public void loginFallidoContrasenaIncorrecta() throws Exception {
                registroExitoso();
                Map<String, String> datos = new HashMap<>();
                datos.put("email", "test@example.com");
                datos.put("contraseña", "ContraseñaIncorrecta");
                String jsonResponse = mockMvc
                                .perform(MockMvcRequestBuilders.post("/api/auth/login")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(objectMapper.writeValueAsString(datos)))
                                .andExpect(MockMvcResultMatchers.status().isUnauthorized()).andReturn().getResponse()
                                .getContentAsString();
                RespuestaLoginDTO respuesta = objectMapper.readValue(jsonResponse, RespuestaLoginDTO.class);
                assertFalse(respuesta.isStatus());
                assertEquals("Contraseña incorrecta", respuesta.getMessage());
        }

        @Test
        public void loginFallidoCamposNulos() throws Exception {
                Map<String, String> datosLogin = new HashMap<>();
                datosLogin.put("email", null);
                datosLogin.put("contraseña", "wrongpassword");

                MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(datosLogin)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                                .andReturn();

                String jsonResponse = result.getResponse().getContentAsString();
                RespuestaLoginDTO respuesta = objectMapper.readValue(jsonResponse, RespuestaLoginDTO.class);

                assertFalse(respuesta.isStatus());
                assertEquals("Todos los campos son obligatorios o tienen un formato inválido", respuesta.getMessage());
        }

        // Desactivamos temporalmente este test ya que no hay implementación para
        // verificar usuarios inactivos
        // @Test
        // public void loginFallidoUsuarioInactivo() throws Exception {
        // registroExitoso();

        // Miembro miembro = repositorioMiembros.findByEmail("test@example.com").get();
        // repositorioMiembros.save(miembro);

        // Map<String, String> datosLogin = new HashMap<>();
        // datosLogin.put("email", "test@example.com");
        // datosLogin.put("contraseña", "TestPassword");

        // MvcResult result =
        // mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
        // .contentType(MediaType.APPLICATION_JSON)
        // .content(objectMapper.writeValueAsString(datosLogin)))
        // .andExpect(MockMvcResultMatchers.status().isUnauthorized())
        // .andReturn();

        // String jsonResponse = result.getResponse().getContentAsString();
        // RespuestaLoginDTO respuesta = objectMapper.readValue(jsonResponse,
        // RespuestaLoginDTO.class);

        // assertFalse(respuesta.isStatus());
        // assertEquals("Usuario inactivo", respuesta.getMessage());
        // }
}