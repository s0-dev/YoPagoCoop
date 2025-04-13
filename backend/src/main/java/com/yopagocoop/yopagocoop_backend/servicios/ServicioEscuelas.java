package com.yopagocoop.yopagocoop_backend.servicios;

import java.util.List;

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaEscuelasDTO;

// Indica que esta interfaz es un servicio de Spring
public interface ServicioEscuelas {
  // en esta interfaz, se declaran qué es lo que va a hacer el servicio
  // empezando por un CRUD
  // Devuelve un DTO de respuesta basado en un DTO de creación
  RespuestaEscuelasDTO crearEscuela(CreacionEscuelasDTO escuelaDTO);

  // Devuelve un DTO de respuesta basado en un identificador
  RespuestaEscuelasDTO obtenerEscuelaPorId(Long id);

  // Devuelve todas las entidades en formato Lista de DTO de respuesta
  List<RespuestaEscuelasDTO> obtenerTodasLasEscuelas();

  // Devuelve un DTO respuesta recibiendo el identificador y un DTO de creacion
  RespuestaEscuelasDTO actualizarEscuela(Long id, CreacionEscuelasDTO escuelaDTO);

  // Borra la escuela sin devolver nada
  void borrarEscuela(Long id);
}