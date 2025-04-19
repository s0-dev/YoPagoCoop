package com.yopagocoop.yopagocoop_backend.servicios;

import java.util.List;

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaAtributosEscuelasDTO;

public interface ServicioAtributosEscuelas {
  RespuestaAtributosEscuelasDTO crearAtributoEscuela(Long id,
      CreacionAtributosEscuelasDTO creacionAtributosEscuelasDTO);

  List<RespuestaAtributosEscuelasDTO> obtenerAtributosPorEscuela(Long escuelaId);

  RespuestaAtributosEscuelasDTO obtenerAtributoEscuelaPorId(Long id);

  RespuestaAtributosEscuelasDTO actualizarAtributoEscuela(Long id,
      CreacionAtributosEscuelasDTO creacionAtributosEscuelasDTO);

  void eliminarAtributoEscuela(Long id);
}
