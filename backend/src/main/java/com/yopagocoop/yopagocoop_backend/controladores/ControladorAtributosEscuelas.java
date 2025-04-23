package com.yopagocoop.yopagocoop_backend.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAtributosEscuelas;

import java.util.List;

@RestController
@RequestMapping("/api/escuelas/{escuelaId}/atributos")
public class ControladorAtributosEscuelas {

  private final ServicioAtributosEscuelas servicioAtributosEscuelas;

  public ControladorAtributosEscuelas(ServicioAtributosEscuelas servicioAtributosEscuelas) {
    this.servicioAtributosEscuelas = servicioAtributosEscuelas;
  }

  @PostMapping
  public ResponseEntity<RespuestaAtributosEscuelasDTO> crearAtributoEscuela(
      @PathVariable Long escuelaId,
      @RequestBody CreacionAtributosEscuelasDTO creacionAtributosEscuelasDTO) {
    RespuestaAtributosEscuelasDTO atributoCreado = servicioAtributosEscuelas.crearAtributoEscuela(escuelaId,
        creacionAtributosEscuelasDTO);
    return new ResponseEntity<>(atributoCreado, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<RespuestaAtributosEscuelasDTO>> obtenerAtributosPorEscuela(
      @PathVariable Long escuelaId) {
    List<RespuestaAtributosEscuelasDTO> atributos = servicioAtributosEscuelas.obtenerAtributosPorEscuela(escuelaId);
    return ResponseEntity.ok(atributos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RespuestaAtributosEscuelasDTO> obtenerAtributoEscuelaPorId(
      @PathVariable Long escuelaId,
      @PathVariable Long id) {
    RespuestaAtributosEscuelasDTO atributo = servicioAtributosEscuelas.obtenerAtributoEscuelaPorId(id);
    return ResponseEntity.ok(atributo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RespuestaAtributosEscuelasDTO> actualizarAtributoEscuela(
      @PathVariable Long escuelaId,
      @PathVariable Long id,
      @RequestBody CreacionAtributosEscuelasDTO creacionAtributosEscuelasDTO) {
    RespuestaAtributosEscuelasDTO atributoActualizado = servicioAtributosEscuelas.actualizarAtributoEscuela(id,
        creacionAtributosEscuelasDTO);
    return ResponseEntity.ok(atributoActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarAtributoEscuela(
      @PathVariable Long escuelaId,
      @PathVariable Long id) {
    servicioAtributosEscuelas.eliminarAtributoEscuela(id);
    return ResponseEntity.noContent().build();
  }
}