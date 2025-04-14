package com.yopagocoop.yopagocoop_backend.servicios.impl;

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaAtributosEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.modelos.Escuela;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioEscuelas;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioEscuelas;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioEscuelasImpl implements ServicioEscuelas {

  private final RepositorioEscuelas repositorioEscuelas;

  private final AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  private final ModelMapper modelMapper;

  public ServicioEscuelasImpl(RepositorioEscuelas repositorioEscuelas,
      AtributosEscuelasRepositorio atributosEscuelasRepositorio, ModelMapper modelMapper) {
    this.repositorioEscuelas = repositorioEscuelas;
    this.atributosEscuelasRepositorio = atributosEscuelasRepositorio;
    this.modelMapper = modelMapper;
  }

  @Override
  public RespuestaEscuelasDTO crearEscuela(CreacionEscuelasDTO escuelaCreacionDTO) {
    Escuela escuela = modelMapper.map(escuelaCreacionDTO, Escuela.class);
    Escuela escuelaGuardada = repositorioEscuelas.save(escuela);
    return modelMapper.map(escuelaGuardada, RespuestaEscuelasDTO.class);
  }

  @Override
  public RespuestaEscuelasDTO actualizarEscuela(Long id, CreacionEscuelasDTO escuelaCreacionDTO) {
    if (!repositorioEscuelas.existsById(id)) {
      throw new RuntimeException("La escuela con ID " + id + " no existe.");
    }
    Escuela escuela = modelMapper.map(escuelaCreacionDTO, Escuela.class);
    escuela.setId(id);
    Escuela escuelaGuardada = repositorioEscuelas.save(escuela);
    return modelMapper.map(escuelaGuardada, RespuestaEscuelasDTO.class);
  }

  @Override
  public void eliminarEscuela(Long id) {
    repositorioEscuelas.deleteById(id);
  }

  @Override
  public RespuestaEscuelasDTO obtenerEscuelaPorId(Long id) {
    Escuela escuela = repositorioEscuelas.findById(id)
        .orElseThrow(() -> new RuntimeException("La escuela con ID " + id + " no se encontró."));
    return modelMapper.map(escuela, RespuestaEscuelasDTO.class);
  }

  @Override
  public List<RespuestaEscuelasDTO> obtenerTodasLasEscuelas() {
    List<Escuela> escuelas = repositorioEscuelas.findAll();
    return escuelas.stream()
        .map(escuela -> modelMapper.map(escuela, RespuestaEscuelasDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<RespuestaAtributosEscuelasDTO> obtenerAtributosEscuela() {
    List<AtributosEscuelas> atributosEscuelas = atributosEscuelasRepositorio.findAll();
    return atributosEscuelas.stream()
        .map(atributosEscuela -> modelMapper.map(atributosEscuela, RespuestaAtributosEscuelasDTO.class))
        .collect(Collectors.toList());
  }
}