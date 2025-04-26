package com.yopagocoop.yopagocoop_backend.servicios.impl;

import com.yopagocoop.yopagocoop_backend.dto.Auth.RespuestaRegistroDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.RespuestaLoginDTO;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioMiembros;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAuth;
import com.yopagocoop.yopagocoop_backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioAuthImpl implements ServicioAuth {
    @Autowired
    private RepositorioMiembros repositorioMiembros;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public RespuestaRegistroDTO registro(String nombre, String apellido, String email, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RespuestaRegistroDTO respuesta = new RespuestaRegistroDTO(false, password);

        if (repositorioMiembros.findByEmail(email).isPresent()) {
            respuesta.setStatus(false);
            respuesta.setMessage("El email ya esta registrado");
            return respuesta;
        }

        Miembro miembro = new Miembro();
        miembro.setNombre(nombre);
        miembro.setApellido(apellido);
        miembro.setEmail(email);
        miembro.setPassword(passwordEncoder.encode(password));
        repositorioMiembros.save(miembro);
        respuesta.setStatus(true);
        respuesta.setMessage("Registro exitoso");
        return respuesta;
    }

    @Override
    public RespuestaLoginDTO login(String email, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        RespuestaLoginDTO respuesta = new RespuestaLoginDTO(false, password, password);

        Miembro miembro = repositorioMiembros.findByEmail(email).orElse(null);
        if (miembro == null) {
            respuesta.setStatus(false);
            respuesta.setMessage("Usuario no encontrado");
            return respuesta;
        }
        if (!passwordEncoder.matches(password, miembro.getPassword())) {
            respuesta.setStatus(false);
            respuesta.setMessage("Contraseña incorrecta");
            return respuesta;
        }
        String token = jwtUtils.generateToken(email);
        respuesta.setStatus(true);
        respuesta.setMessage("Login exitoso");
        respuesta.setToken(token);
        return respuesta;
    }
}