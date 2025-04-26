package com.yopagocoop.yopagocoop_backend.controladores;

import com.yopagocoop.yopagocoop_backend.dto.Auth.login.PeticionLoginDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.login.RespuestaLoginDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.registro.RespuestaRegistroDTO;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionMiembrosDTO;

import com.yopagocoop.yopagocoop_backend.servicios.ServicioAuth;
import com.yopagocoop.yopagocoop_backend.utils.Miembros.ValidadorCelular;
import com.yopagocoop.yopagocoop_backend.utils.Miembros.ValidadorEmail;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class ControladorAuth {

    private final ServicioAuth servicioAuth;

    public ControladorAuth(ServicioAuth servicioAuth) {
        this.servicioAuth = servicioAuth;
    }

    // TODO: CONTROLADOR_AUTH
    // Cambiar la LOGICA DE NEGOCIO AL SERVICIO.
    // Centrar el manejo de Errores con @ControllerAdvice
    // Implementar Excepciones Especificas

    @PostMapping("/registro")
    public ResponseEntity<RespuestaRegistroDTO> registro(
            @Valid @RequestBody CreacionMiembrosDTO creacionMiembros,
            BindingResult result) {

        // fail fast Bean Validation
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    new RespuestaRegistroDTO(false, "Todos los campos son obligatorios o tienen un formato inválido"));
        }

        // Validaciones especificas
        ValidadorEmail validadorEmail = new ValidadorEmail();
        if (!validadorEmail.esValido(creacionMiembros.getEmail())) {
            return ResponseEntity.badRequest().body(
                    new RespuestaRegistroDTO(false, "El formato del email es inválido"));
        }

        ValidadorCelular validadorCelular = new ValidadorCelular();
        if (!validadorCelular.esValido(creacionMiembros.getCelular())) {
            return ResponseEntity.badRequest().body(
                    new RespuestaRegistroDTO(false, "El formato del número de celular es inválido"));
        }

        try {
            // Formateamos el celular para asegurar consistencia en el formato
            String celularFormateado = validadorCelular.formatearCelularArgentina(creacionMiembros.getCelular());
            if (celularFormateado != null) {
                creacionMiembros.setCelular(celularFormateado);
            }

            // Llamamos al servicio para procesar el registro
            RespuestaRegistroDTO respuesta = servicioAuth.registro(creacionMiembros);

            // Determinamos el código de estado HTTP basado en la respuesta
            if (respuesta.isStatus()) {
                return ResponseEntity.ok(respuesta); // 200 OK para éxito
            } else {
                return ResponseEntity.badRequest().body(respuesta); // 400 Bad Request para error
            }
        } catch (Exception e) {
            // Manejo de excepciones inesperadas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaRegistroDTO(false, "Error interno del servidor: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaLoginDTO> login(@Valid @RequestBody PeticionLoginDTO peticionLogin,
            BindingResult result) {

        // fail fast Bean Validation
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    new RespuestaLoginDTO(false, "Todos los campos son obligatorios o tienen un formato inválido",
                            null));
        }

        // validaciones especificas

        ValidadorEmail validadorEmail = new ValidadorEmail();
        if (!validadorEmail.esValido(peticionLogin.getEmail())) {
            return ResponseEntity.badRequest().body(
                    new RespuestaLoginDTO(false, "El formato del email es inválido", null));
        }

        try {
            // Llamamos al servicio para procesar el login
            RespuestaLoginDTO respuesta = servicioAuth.login(peticionLogin);

            // Determinamos el código de estado HTTP basado en la respuesta
            if (respuesta.isStatus()) {
                return ResponseEntity.ok(respuesta); // 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respuesta); // 401 Unauthorized
            }
        } catch (Exception e) {
            // Manejo de excepciones inesperadas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new RespuestaLoginDTO(false, "Error interno del servidor: " + e.getMessage(), null));
        }
    }
}