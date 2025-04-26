package com.yopagocoop.yopagocoop_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yopagocoop.yopagocoop_backend.dto.Auth.RespuestaLoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class RutasProtegidasTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void accesoConTokenInvalido() throws Exception {
        // Simular petición GET a /api/miembros con token inválido
        mockMvc.perform(get("/api/miembros").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer tokenInvalido"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void accesoSinToken() throws Exception {
        // Simular peticion GET a /api/miembros sin token
        mockMvc.perform(get("/api/miembros"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void accesoConTokenValido() throws Exception {
        // Registrar un usuario
        Map<String, String> datosRegistro = Map.of(
                "nombre", "TestNombre",
                "apellido", "TestApellido",
                "email", "test@example.com",
                "password", "TestPassword");

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(datosRegistro)))
                .andExpect(status().isOk());

        // Crear credenciales de login
        Map<String, String> credencialesLogin = Map.of("email", "test@example.com", "password", "TestPassword");
        // Loguear al usuario
        MvcResult resultLogin = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(credencialesLogin)))
                .andExpect(status().isOk()).andReturn();

        String token = new ObjectMapper()
                .readValue(resultLogin.getResponse().getContentAsString(), RespuestaLoginDTO.class).getToken();
        // Simular peticion GET a /api/miembros con el token
        mockMvc.perform(get("/api/miembros")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

}