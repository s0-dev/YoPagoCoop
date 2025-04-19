package com.yopagocoop.yopagocoop_backend.servicios;

import java.util.List;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaAtributosMiembrosDTO;

public interface ServicioAtributosMiembros {

  RespuestaAtributosMiembrosDTO crearAtributoMiembro(Long miembroId, CreacionAtributosMiembrosDTO atributoDTO);

  List<RespuestaAtributosMiembrosDTO> obtenerAtributosPorMiembro(Long miembroId);

  RespuestaAtributosMiembrosDTO obtenerAtributoPorId(Long id);

  RespuestaAtributosMiembrosDTO actualizarAtributoMiembro(Long id, CreacionAtributosMiembrosDTO atributoDTO);

  void eliminarAtributoMiembro(Long id);
}