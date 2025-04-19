package com.yopagocoop.yopagocoop_backend.servicios;

import java.util.List;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaMiembrosDTO;

public interface ServicioMiembros {

  RespuestaMiembrosDTO crearMiembro(CreacionMiembrosDTO miembroDTO);

  RespuestaMiembrosDTO actualizarMiembro(Long id, CreacionMiembrosDTO miembroDTO);

  RespuestaMiembrosDTO obtenerMiembroPorId(Long id);

  List<RespuestaMiembrosDTO> obtenerTodosLosMiembros();

  List<RespuestaMiembrosDTO> obtenerMiembrosPorEscuela(Long escuelaId);

  void eliminarMiembro(Long id);
}