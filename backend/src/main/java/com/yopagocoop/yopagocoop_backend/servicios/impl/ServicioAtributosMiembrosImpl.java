package com.yopagocoop.yopagocoop_backend.servicios.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.CreacionAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosMiembros;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosMiembrosRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioMiembros;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAtributosMiembros;

@Service
public class ServicioAtributosMiembrosImpl implements ServicioAtributosMiembros {

  private final AtributosMiembrosRepositorio atributosMiembrosRepositorio;
  private final RepositorioMiembros repositorioMiembros;
  private final AtributosEscuelasRepositorio atributosEscuelasRepositorio;
  private final ModelMapper modelMapper;

  public ServicioAtributosMiembrosImpl(
      AtributosMiembrosRepositorio atributosMiembrosRepositorio,
      RepositorioMiembros repositorioMiembros,
      AtributosEscuelasRepositorio atributosEscuelasRepositorio,
      ModelMapper modelMapper) {
    this.atributosMiembrosRepositorio = atributosMiembrosRepositorio;
    this.repositorioMiembros = repositorioMiembros;
    this.atributosEscuelasRepositorio = atributosEscuelasRepositorio;
    this.modelMapper = modelMapper;
  }

  @Override
  @Transactional
  public RespuestaAtributosMiembrosDTO crearAtributoMiembro(Long miembroId, CreacionAtributosMiembrosDTO atributoDTO) {
    // Verificar que el miembro existe
    Miembro miembro = repositorioMiembros.findById(miembroId)
        .orElseThrow(() -> new RuntimeException("Miembro no encontrado con ID: " + miembroId));

    // Verificar que el atributo específico de la escuela existe
    AtributosEscuelas atributoEscuela = atributosEscuelasRepositorio.findById(atributoDTO.getAtributoEscuelaId())
        .orElseThrow(() -> new RuntimeException(
            "Atributo de escuela no encontrado con ID: " + atributoDTO.getAtributoEscuelaId()));

    // Crear y configurar el atributo del miembro
    AtributosMiembros atributoMiembro = new AtributosMiembros();
    atributoMiembro.setMiembro(miembro);
    atributoMiembro.setAtributoEspecificoEscuela(atributoEscuela);
    atributoMiembro.setValorAtributo(atributoDTO.getValorAtributo());

    // Guardar el atributo
    AtributosMiembros atributoGuardado = atributosMiembrosRepositorio.save(atributoMiembro);

    // Convertir a DTO y retornar
    return modelMapper.map(atributoGuardado, RespuestaAtributosMiembrosDTO.class);
  }

  @Override
  public List<RespuestaAtributosMiembrosDTO> obtenerAtributosPorMiembro(Long miembroId) {
    // Verificar que el miembro existe
    Miembro miembro = repositorioMiembros.findById(miembroId)
        .orElseThrow(() -> new RuntimeException("Miembro no encontrado con ID: " + miembroId));

    // Obtener los atributos del miembro
    List<AtributosMiembros> atributos = atributosMiembrosRepositorio.findByMiembro(miembro);

    // Convertir a lista de DTOs y retornar
    return atributos.stream()
        .map(atributo -> modelMapper.map(atributo, RespuestaAtributosMiembrosDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public RespuestaAtributosMiembrosDTO obtenerAtributoPorId(Long id) {
    // Buscar el atributo por ID
    AtributosMiembros atributo = atributosMiembrosRepositorio.findById(id)
        .orElseThrow(() -> new RuntimeException("Atributo de miembro no encontrado con ID: " + id));

    // Convertir a DTO y retornar
    return modelMapper.map(atributo, RespuestaAtributosMiembrosDTO.class);
  }

  @Override
  @Transactional
  public RespuestaAtributosMiembrosDTO actualizarAtributoMiembro(Long id, CreacionAtributosMiembrosDTO atributoDTO) {
    // Verificar que el atributo existe
    AtributosMiembros atributoExistente = atributosMiembrosRepositorio.findById(id)
        .orElseThrow(() -> new RuntimeException("Atributo de miembro no encontrado con ID: " + id));

    // Verificar que el atributo de escuela existe, si ha cambiado
    if (!atributoExistente.getAtributoEspecificoEscuela().getId().equals(atributoDTO.getAtributoEscuelaId())) {
      AtributosEscuelas nuevoAtributoEscuela = atributosEscuelasRepositorio.findById(atributoDTO.getAtributoEscuelaId())
          .orElseThrow(() -> new RuntimeException(
              "Atributo de escuela no encontrado con ID: " + atributoDTO.getAtributoEscuelaId()));
      atributoExistente.setAtributoEspecificoEscuela(nuevoAtributoEscuela);
    }

    // Actualizar el valor
    atributoExistente.setValorAtributo(atributoDTO.getValorAtributo());

    // Guardar los cambios
    AtributosMiembros atributoActualizado = atributosMiembrosRepositorio.save(atributoExistente);

    // Convertir a DTO y retornar
    return modelMapper.map(atributoActualizado, RespuestaAtributosMiembrosDTO.class);
  }

  @Override
  @Transactional
  public void eliminarAtributoMiembro(Long id) {
    // Verificar que el atributo existe
    if (!atributosMiembrosRepositorio.existsById(id)) {
      throw new RuntimeException("Atributo de miembro no encontrado con ID: " + id);
    }

    // Eliminar el atributo
    atributosMiembrosRepositorio.deleteById(id);
  }
}