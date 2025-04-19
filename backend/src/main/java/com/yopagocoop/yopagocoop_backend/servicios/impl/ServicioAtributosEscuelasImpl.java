package com.yopagocoop.yopagocoop_backend.servicios.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioEscuelas;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioAtributosEscuelas;

@Service
public class ServicioAtributosEscuelasImpl implements ServicioAtributosEscuelas {

  private final AtributosEscuelasRepositorio atributosEscuelasRepositorio;
  private final RepositorioEscuelas repositorioEscuelas;
  private final ModelMapper modelMapper;

  public ServicioAtributosEscuelasImpl(AtributosEscuelasRepositorio atributosEscuelasRepositorio,
      RepositorioEscuelas repositorioEscuelas,
      ModelMapper modelMapper) {
    this.atributosEscuelasRepositorio = atributosEscuelasRepositorio;
    this.repositorioEscuelas = repositorioEscuelas;
    this.modelMapper = modelMapper;
  }

  @Override
  @Transactional
  public RespuestaAtributosEscuelasDTO crearAtributoEscuela(Long escuelaId, CreacionAtributosEscuelasDTO atributoDTO) {
    // Verificar que la escuela existe
    Escuela escuela = repositorioEscuelas.findById(escuelaId)
        .orElseThrow(() -> new RuntimeException("Escuela no encontrada con ID: " + escuelaId));

    // Crear y configurar el atributo
    AtributosEscuelas atributo = modelMapper.map(atributoDTO, AtributosEscuelas.class);
    atributo.setEscuela(escuela);

    // Guardar el atributo
    AtributosEscuelas atributoGuardado = atributosEscuelasRepositorio.save(atributo);

    // Retornar el DTO de respuesta
    return modelMapper.map(atributoGuardado, RespuestaAtributosEscuelasDTO.class);
  }

  @Override
  public List<RespuestaAtributosEscuelasDTO> obtenerAtributosPorEscuela(Long escuelaId) {
    // Verificar que la escuela existe
    if (!repositorioEscuelas.existsById(escuelaId)) {
      throw new RuntimeException("Escuela no encontrada con ID: " + escuelaId);
    }

    // Obtener todos los atributos de la escuela
    List<AtributosEscuelas> atributos = atributosEscuelasRepositorio.findByEscuelaId(escuelaId);

    // Convertir a DTOs y retornar
    return atributos.stream()
        .map(atributo -> modelMapper.map(atributo, RespuestaAtributosEscuelasDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public RespuestaAtributosEscuelasDTO obtenerAtributoEscuelaPorId(Long id) {
    // Buscar el atributo por ID
    AtributosEscuelas atributo = atributosEscuelasRepositorio.findById(id)
        .orElseThrow(() -> new RuntimeException("Atributo de escuela no encontrado con ID: " + id));

    // Convertir a DTO y retornar
    return modelMapper.map(atributo, RespuestaAtributosEscuelasDTO.class);
  }

  @Override
  @Transactional
  public RespuestaAtributosEscuelasDTO actualizarAtributoEscuela(Long id, CreacionAtributosEscuelasDTO atributoDTO) {
    // Verificar que el atributo existe
    AtributosEscuelas atributoExistente = atributosEscuelasRepositorio.findById(id)
        .orElseThrow(() -> new RuntimeException("Atributo no encontrado con ID: " + id));

    // Actualizar propiedades
    atributoExistente.setNombreAtributo(atributoDTO.getNombreAtributo());
    atributoExistente.setTipoDato(atributoDTO.getTipoDato());
    atributoExistente.setEsRequerido(atributoDTO.getEsRequerido());

    // Guardar cambios
    AtributosEscuelas atributoActualizado = atributosEscuelasRepositorio.save(atributoExistente);

    // Retornar DTO de respuesta
    return modelMapper.map(atributoActualizado, RespuestaAtributosEscuelasDTO.class);
  }

  @Override
  @Transactional
  public void eliminarAtributoEscuela(Long id) {
    // Verificar que el atributo existe
    if (!atributosEscuelasRepositorio.existsById(id)) {
      throw new RuntimeException("Atributo no encontrado con ID: " + id);
    }

    // Eliminar el atributo
    atributosEscuelasRepositorio.deleteById(id);
  }
}