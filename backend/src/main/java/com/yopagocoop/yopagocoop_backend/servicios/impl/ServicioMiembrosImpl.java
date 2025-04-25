package com.yopagocoop.yopagocoop_backend.servicios.impl;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosMiembros;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosMiembrosRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioEscuelas;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioMiembros;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioMiembros;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServicioMiembrosImpl implements ServicioMiembros {

  // TODO: SERVICIO_MIEMBROS
  // Validación de DNI, CEL, EMAIL

  private final RepositorioMiembros repositorioMiembros;
  private final RepositorioEscuelas repositorioEscuelas;
  private final AtributosMiembrosRepositorio atributosMiembrosRepositorio;
  private final ModelMapper modelMapper;
  private final AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  public ServicioMiembrosImpl(RepositorioMiembros repositorioMiembros,
      RepositorioEscuelas repositorioEscuelas,
      AtributosMiembrosRepositorio atributosMiembrosRepositorio,
      ModelMapper modelMapper, AtributosEscuelasRepositorio atributosEscuelasRepositorio) {
    this.repositorioMiembros = repositorioMiembros;
    this.repositorioEscuelas = repositorioEscuelas;
    this.atributosMiembrosRepositorio = atributosMiembrosRepositorio;
    this.modelMapper = modelMapper;
    this.atributosEscuelasRepositorio = atributosEscuelasRepositorio;
  }

  @Override
  @Transactional
  public RespuestaMiembrosDTO crearMiembro(CreacionMiembrosDTO miembroDTO) {
    // Verificar que la escuela existe
    Escuela escuela = repositorioEscuelas.findById(miembroDTO.getEscuelaId())
        .orElseThrow(() -> new RuntimeException("Escuela no encontrada con ID: " + miembroDTO.getEscuelaId()));

    // Mapear DTO a entidad
    Miembro miembro = modelMapper.map(miembroDTO, Miembro.class);
    miembro.setEscuela(escuela);

    // Guardar el miembro
    Miembro miembroGuardado = repositorioMiembros.save(miembro);

    // Procesar atributos específicos si existen
    if (miembroDTO.getAtributos() != null && !miembroDTO.getAtributos().isEmpty()) {
      procesarAtributosMiembro(miembroGuardado, miembroDTO.getAtributos());
    }

    // Mapear entidad a DTO de respuesta
    RespuestaMiembrosDTO respuestaDTO = modelMapper.map(miembroGuardado, RespuestaMiembrosDTO.class);

    // Agregar atributos al DTO de respuesta
    respuestaDTO.setAtributos(obtenerAtributosMiembro(miembroGuardado));

    return respuestaDTO;
  }

  @Override
  @Transactional
  public RespuestaMiembrosDTO actualizarMiembro(Long id, CreacionMiembrosDTO miembroDTO) {
    // Verificar que el miembro existe
    Miembro miembroExistente = repositorioMiembros.findById(id)
        .orElseThrow(() -> new RuntimeException("Miembro no encontrado con ID: " + id));

    // Si cambia la escuela, verificar que la nueva escuela existe
    if (!miembroExistente.getEscuela().getId().equals(miembroDTO.getEscuelaId())) {
      Escuela nuevaEscuela = repositorioEscuelas.findById(miembroDTO.getEscuelaId())
          .orElseThrow(() -> new RuntimeException("Escuela no encontrada con ID: " + miembroDTO.getEscuelaId()));
      miembroExistente.setEscuela(nuevaEscuela);
    }

    // Actualizar datos básicos
    actualizarDatosMiembro(miembroExistente, miembroDTO);

    // Guardar cambios
    Miembro miembroActualizado = repositorioMiembros.save(miembroExistente);

    // Actualizar atributos específicos
    if (miembroDTO.getAtributos() != null) {
      // Eliminar atributos existentes
      atributosMiembrosRepositorio.deleteByMiembro(miembroActualizado);
      // Agregar nuevos atributos
      procesarAtributosMiembro(miembroActualizado, miembroDTO.getAtributos());
    }

    // Mapear entidad a DTO de respuesta
    RespuestaMiembrosDTO respuestaDTO = modelMapper.map(miembroActualizado, RespuestaMiembrosDTO.class);

    // Agregar atributos al DTO de respuesta
    respuestaDTO.setAtributos(obtenerAtributosMiembro(miembroActualizado));

    return respuestaDTO;
  }

  @Override
  public RespuestaMiembrosDTO obtenerMiembroPorId(Long id) {
    // Buscar miembro por ID
    Miembro miembro = repositorioMiembros.findById(id)
        .orElseThrow(() -> new RuntimeException("Miembro no encontrado con ID: " + id));

    // Mapear entidad a DTO
    RespuestaMiembrosDTO respuestaDTO = modelMapper.map(miembro, RespuestaMiembrosDTO.class);

    // Agregar atributos
    respuestaDTO.setAtributos(obtenerAtributosMiembro(miembro));

    return respuestaDTO;
  }

  @Override
  public List<RespuestaMiembrosDTO> obtenerTodosLosMiembros() {
    // Obtener todos los miembros
    List<Miembro> miembros = repositorioMiembros.findAll();

    // Mapear lista de entidades a lista de DTOs
    return miembros.stream()
        .map(miembro -> {
          RespuestaMiembrosDTO dto = modelMapper.map(miembro, RespuestaMiembrosDTO.class);
          dto.setAtributos(obtenerAtributosMiembro(miembro));
          return dto;
        })
        .collect(Collectors.toList());
  }

  @Override
  public List<RespuestaMiembrosDTO> obtenerMiembrosPorEscuela(Long escuelaId) {
    // Verificar que la escuela existe
    if (!repositorioEscuelas.existsById(escuelaId)) {
      throw new RuntimeException("Escuela no encontrada con ID: " + escuelaId);
    }

    // Obtener miembros de la escuela
    List<Miembro> miembros = repositorioMiembros.findByEscuelaId(escuelaId);

    // Mapear lista de entidades a lista de DTOs
    return miembros.stream()
        .map(miembro -> {
          RespuestaMiembrosDTO dto = modelMapper.map(miembro, RespuestaMiembrosDTO.class);
          dto.setAtributos(obtenerAtributosMiembro(miembro));
          return dto;
        })
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void eliminarMiembro(Long id) {
    // Verificar que el miembro existe
    if (!repositorioMiembros.existsById(id)) {
      throw new RuntimeException("Miembro no encontrado con ID: " + id);
    }

    // Eliminar miembro
    repositorioMiembros.deleteById(id);
  }

  private void actualizarDatosMiembro(Miembro miembro, CreacionMiembrosDTO miembroDTO) {
    miembro.setNombre(miembroDTO.getNombre());
    miembro.setApellido(miembroDTO.getApellido());
    miembro.setEmail(miembroDTO.getEmail());
    miembro.setCelular(miembroDTO.getCelular());
  }

  private void procesarAtributosMiembro(Miembro miembro, Map<String, String> atributos) {
    Long escuelaId = miembro.getEscuela().getId();

    atributos.forEach((nombreAtributo, valor) -> {
      // Buscar el atributo específico de la escuela por nombre
      AtributosEscuelas atributoEscuela = atributosEscuelasRepositorio
          .findByEscuelaIdAndNombreAtributo(escuelaId, nombreAtributo)
          .orElseThrow(() -> new RuntimeException(
              "Atributo específico '" + nombreAtributo + "' no encontrado para la escuela ID: " + escuelaId));

      AtributosMiembros atributoMiembro = new AtributosMiembros();
      atributoMiembro.setMiembro(miembro);
      atributoMiembro.setAtributoEspecificoEscuela(atributoEscuela);
      atributoMiembro.setValorAtributo(valor);
      atributosMiembrosRepositorio.save(atributoMiembro);
    });
  }

  // Metodo para obtener aitributos
  private Map<String, String> obtenerAtributosMiembro(Miembro miembro) {
    List<AtributosMiembros> atributos = atributosMiembrosRepositorio.findByMiembro(miembro);

    Map<String, String> atributosMap = new HashMap<>();
    atributos.forEach(atributo -> {
      atributosMap.put(
          atributo.getAtributoEspecificoEscuela().getNombreAtributo(),
          atributo.getValorAtributo());
    });

    return atributosMap;
  }
}