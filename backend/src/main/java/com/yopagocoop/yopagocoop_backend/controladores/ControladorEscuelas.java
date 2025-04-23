package com.yopagocoop.yopagocoop_backend.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioEscuelas;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/escuelas")
public class ControladorEscuelas {
  // Inyeccion de dependencias, ya que el controlador depende del servicio
  private final ServicioEscuelas servicioEscuelas;

  public ControladorEscuelas(ServicioEscuelas servicioEscuelas) {
    this.servicioEscuelas = servicioEscuelas;
  }

  // path base cuando esta vacio
  @PostMapping
  public ResponseEntity<RespuestaEscuelasDTO> crearEscuela(@RequestBody CreacionEscuelasDTO creacionEscuelasDTO) {
    RespuestaEscuelasDTO escuelaCreada = servicioEscuelas.crearEscuela(creacionEscuelasDTO);
    return new ResponseEntity<>(escuelaCreada, HttpStatus.CREATED);
  }

  // path base + /id al final quedaria:
  // /api/escuelas/{id}
  @GetMapping("/{id}")
  public ResponseEntity<RespuestaEscuelasDTO> obtenerEscuelaPorId(@PathVariable Long id) {
    RespuestaEscuelasDTO escuela = servicioEscuelas.obtenerEscuelaPorId(id);
    return ResponseEntity.ok(escuela);
  }

  @GetMapping
  public ResponseEntity<List<RespuestaEscuelasDTO>> obtenerTodasLasEscuelas() {
    List<RespuestaEscuelasDTO> escuelas = servicioEscuelas.obtenerTodasLasEscuelas();
    return ResponseEntity.ok(escuelas);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RespuestaEscuelasDTO> actualizarEscuela(@PathVariable Long id,
      @RequestBody CreacionEscuelasDTO creacionEscuelasDTO) {
    RespuestaEscuelasDTO escuelaActualizada = servicioEscuelas.actualizarEscuela(id, creacionEscuelasDTO);
    return ResponseEntity.ok(escuelaActualizada);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarEscuela(@PathVariable Long id) {
    servicioEscuelas.eliminarEscuela(id);
    return ResponseEntity.noContent().build();
  }
}
