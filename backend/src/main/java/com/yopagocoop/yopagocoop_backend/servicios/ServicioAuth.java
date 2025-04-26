package com.yopagocoop.yopagocoop_backend.servicios;

import com.yopagocoop.yopagocoop_backend.dto.Auth.login.PeticionLoginDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.login.RespuestaLoginDTO;
import com.yopagocoop.yopagocoop_backend.dto.Auth.registro.RespuestaRegistroDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionMiembrosDTO;

public interface ServicioAuth {
    RespuestaRegistroDTO registro(CreacionMiembrosDTO creacionMiembros);

    RespuestaLoginDTO login(PeticionLoginDTO peticionLogin);
}