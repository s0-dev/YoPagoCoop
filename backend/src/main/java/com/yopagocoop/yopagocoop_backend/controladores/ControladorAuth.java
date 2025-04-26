package com.yopagocoop.yopagocoop_backend.controladores;

import com.yopagocoop.yopagocoop_backend.dto.Auth.RespuestaLoginDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAuth;
import com.yopagocoop.yopagocoop_backend.dto.Auth.RespuestaRegistroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class ControladorAuth {
    @Autowired
    private ServicioAuth servicioAuth;

    @PostMapping("/login")
    public ResponseEntity<RespuestaLoginDTO> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");
        return ResponseEntity.ok(servicioAuth.login(email, password));
    }

    @PostMapping("/registro")
    public ResponseEntity<RespuestaRegistroDTO> registro(@RequestBody Map<String, String> datos) {

        String nombre = datos.get("nombre");
        String apellido = datos.get("apellido");
        String email = datos.get("email");
        String password = datos.get("password");

        return ResponseEntity.ok(servicioAuth.registro(nombre, apellido, email, password));
    }
}