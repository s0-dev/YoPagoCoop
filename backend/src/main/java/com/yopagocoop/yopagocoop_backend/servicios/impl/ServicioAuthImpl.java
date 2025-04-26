package com.yopagocoop.yopagocoop_backend.servicios.impl;

import com.yopagocoop.yopagocoop_backend.dto.Auth.login.PeticionLoginDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.login.RespuestaLoginDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.registro.RespuestaRegistroDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosMiembros;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosMiembrosRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioEscuelas;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioMiembros;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAuth;
import com.yopagocoop.yopagocoop_backend.utils.JwtUtils;

import jakarta.transaction.Transactional;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioAuthImpl implements ServicioAuth {

    private final RepositorioMiembros repositorioMiembros;
    private final RepositorioEscuelas repositorioEscuelas;
    private final AtributosEscuelasRepositorio atributosEscuelasRepositorio;
    private final AtributosMiembrosRepositorio atributosMiembrosRepositorio;
    private final JwtUtils jwtUtils;

    public ServicioAuthImpl(RepositorioMiembros repositorioMiembros, RepositorioEscuelas repositorioEscuelas,
            AtributosEscuelasRepositorio atributosEscuelasRepositorio,
            AtributosMiembrosRepositorio atributosMiembrosRepositorio, JwtUtils jwtUtils) {
        this.repositorioMiembros = repositorioMiembros;
        this.repositorioEscuelas = repositorioEscuelas;
        this.atributosEscuelasRepositorio = atributosEscuelasRepositorio;
        this.atributosMiembrosRepositorio = atributosMiembrosRepositorio;
        this.jwtUtils = jwtUtils;
    }

    // TODO: SERVICIO_AUTH
    // Agregar validaciones

    @Override
    @Transactional
    public RespuestaRegistroDTO registro(CreacionMiembrosDTO creacionMiembros) {
        // fail fast
        if (repositorioMiembros.findByEmail(creacionMiembros.getEmail()).isPresent()) {
            RespuestaRegistroDTO respuesta = new RespuestaRegistroDTO(false, "El email ya esta registrado");
            return respuesta;
        }

        // encripta contraseña
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // encontrar escuelaId
        Escuela escuela = repositorioEscuelas.findById(creacionMiembros.getEscuelaId())
                .orElseThrow(() -> new RuntimeException("Escuela no encontrada"));

        // crea un nuevo miembro en la db
        Miembro miembro = new Miembro();
        miembro.setEscuela(escuela);
        miembro.setNombre(creacionMiembros.getNombre());
        miembro.setApellido(creacionMiembros.getApellido());
        miembro.setEmail(creacionMiembros.getEmail());
        miembro.setContraseña(passwordEncoder.encode((creacionMiembros.getContraseña())));
        miembro.setCelular(creacionMiembros.getCelular());

        repositorioMiembros.save(miembro);

        // procesamos los atributos del miembro
        if (creacionMiembros.getAtributos() != null && !creacionMiembros.getApellido().isEmpty()) {
            // tomamos los valores del map, key y valor
            for (Map.Entry<String, String> entry : creacionMiembros.getAtributos().entrySet()) {
                String nombreAtributo = entry.getKey();
                String valorAtributo = entry.getValue();

                // procesamos los atributos de la escuela
                AtributosEscuelas atributosEscuela = atributosEscuelasRepositorio
                        .findByEscuelaIdAndNombreAtributo(escuela.getId(), nombreAtributo)
                        .orElseThrow(() -> new RuntimeException("Atributo no encontrado: " + nombreAtributo));

                // creamos el nuevo atributo del miembro
                AtributosMiembros atributoMiembro = new AtributosMiembros();

                atributoMiembro.setMiembro(miembro);
                atributoMiembro.setAtributoEspecificoEscuela(atributosEscuela);
                atributoMiembro.setValorAtributo(valorAtributo);

                atributosMiembrosRepositorio.save(atributoMiembro);
            }
        }

        // crea la nueva respuesta
        RespuestaRegistroDTO respuesta = new RespuestaRegistroDTO(true, "Usuario registrado con éxito");
        return respuesta;
    }

    @Override
    public RespuestaLoginDTO login(PeticionLoginDTO peticionLogin) {
        // busca el usuario por email
        Miembro miembro = repositorioMiembros.findByEmail(peticionLogin.getEmail()).orElse(null);

        // fail fast
        if (miembro == null) {
            RespuestaLoginDTO respuesta = new RespuestaLoginDTO(false, "Usuario no encontrado", null);
            return respuesta;
        }

        // desencripta la contraseña
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // en caso de ser incorrecta, fail fast
        if (!passwordEncoder.matches(peticionLogin.getContraseña(), miembro.getContraseña())) {
            RespuestaLoginDTO respuesta = new RespuestaLoginDTO(false, "Contraseña incorrecta", null);
            return respuesta;
        }

        // genera un token con el email y lo envia por la respuesta
        String token = jwtUtils.generateToken(peticionLogin.getEmail());

        RespuestaLoginDTO respuesta = new RespuestaLoginDTO(true, "Login exitoso", token);
        return respuesta;
    }
}