package com.yopagocoop.yopagocoop_backend.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAtributosMiembros;

import java.util.List;

@RestController
@RequestMapping("/api/miembros/{miembroId}/atributos")
public class ControladorAtributosMiembros {

  private final ServicioAtributosMiembros servicioAtributosMiembros;

  public ControladorAtributosMiembros(ServicioAtributosMiembros servicioAtributosMiembros) {
    this.servicioAtributosMiembros = servicioAtributosMiembros;
  }

  @PostMapping
  public ResponseEntity<RespuestaAtributosMiembrosDTO> crearAtributoMiembro(
      @PathVariable Long miembroId,
      @RequestBody CreacionAtributosMiembrosDTO creacionDTO) {
    RespuestaAtributosMiembrosDTO atributoCreado = servicioAtributosMiembros.crearAtributoMiembro(miembroId,
        creacionDTO);
    return new ResponseEntity<>(atributoCreado, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<RespuestaAtributosMiembrosDTO>> obtenerAtributosPorMiembro(
      @PathVariable Long miembroId) {
    List<RespuestaAtributosMiembrosDTO> atributos = servicioAtributosMiembros.obtenerAtributosPorMiembro(miembroId);
    return ResponseEntity.ok(atributos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RespuestaAtributosMiembrosDTO> obtenerAtributoPorId(
      @PathVariable Long miembroId,
      @PathVariable Long id) {
    RespuestaAtributosMiembrosDTO atributo = servicioAtributosMiembros.obtenerAtributoPorId(id);
    return ResponseEntity.ok(atributo);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RespuestaAtributosMiembrosDTO> actualizarAtributoMiembro(
      @PathVariable Long miembroId,
      @PathVariable Long id,
      @RequestBody CreacionAtributosMiembrosDTO creacionDTO) {
    RespuestaAtributosMiembrosDTO atributoActualizado = servicioAtributosMiembros.actualizarAtributoMiembro(id,
        creacionDTO);
    return ResponseEntity.ok(atributoActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarAtributoMiembro(
      @PathVariable Long miembroId,
      @PathVariable Long id) {
    servicioAtributosMiembros.eliminarAtributoMiembro(id);
    return ResponseEntity.noContent().build();
  }
}