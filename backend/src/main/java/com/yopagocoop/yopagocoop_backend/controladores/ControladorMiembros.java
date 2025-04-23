package com.yopagocoop.yopagocoop_backend.controladores;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioMiembros;

import java.util.List;

@RestController
@RequestMapping("/api/miembros")
public class ControladorMiembros {

  private final ServicioMiembros servicioMiembros;

  public ControladorMiembros(ServicioMiembros servicioMiembros) {
    this.servicioMiembros = servicioMiembros;
  }

  @PostMapping
  public ResponseEntity<RespuestaMiembrosDTO> crearMiembro(@RequestBody CreacionMiembrosDTO creacionMiembrosDTO) {
    RespuestaMiembrosDTO miembroCreado = servicioMiembros.crearMiembro(creacionMiembrosDTO);
    return new ResponseEntity<>(miembroCreado, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RespuestaMiembrosDTO> obtenerMiembroPorId(@PathVariable Long id) {
    RespuestaMiembrosDTO miembro = servicioMiembros.obtenerMiembroPorId(id);
    return ResponseEntity.ok(miembro);
  }

  @GetMapping
  public ResponseEntity<List<RespuestaMiembrosDTO>> obtenerTodosLosMiembros() {
    List<RespuestaMiembrosDTO> miembros = servicioMiembros.obtenerTodosLosMiembros();
    return ResponseEntity.ok(miembros);
  }

  @GetMapping("/escuela/{escuelaId}")
  public ResponseEntity<List<RespuestaMiembrosDTO>> obtenerMiembrosPorEscuela(@PathVariable Long escuelaId) {
    List<RespuestaMiembrosDTO> miembros = servicioMiembros.obtenerMiembrosPorEscuela(escuelaId);
    return ResponseEntity.ok(miembros);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RespuestaMiembrosDTO> actualizarMiembro(
      @PathVariable Long id,
      @RequestBody CreacionMiembrosDTO creacionMiembrosDTO) {
    RespuestaMiembrosDTO miembroActualizado = servicioMiembros.actualizarMiembro(id, creacionMiembrosDTO);
    return ResponseEntity.ok(miembroActualizado);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarMiembro(@PathVariable Long id) {
    servicioMiembros.eliminarMiembro(id);
    return ResponseEntity.noContent().build();
  }
}