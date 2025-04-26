package com.yopagocoop.yopagocoop_backend.servicios;

import com.yopagocoop.yopagocoop_backend.dto.Auth.RespuestaLoginDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.RespuestaRegistroDTO;

public interface ServicioAuth {

    RespuestaRegistroDTO registro(String nombre, String apellido, String email, String password);

    RespuestaLoginDTO login(String email, String password);
}