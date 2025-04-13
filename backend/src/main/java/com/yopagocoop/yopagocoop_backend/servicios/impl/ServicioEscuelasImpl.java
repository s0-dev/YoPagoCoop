package com.yopagocoop.yopagocoop_backend.servicios.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.yopagocoop.yopagocoop_backend.dto.Escuelas.CreacionEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.dto.Escuelas.RespuestaEscuelasDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosEscuelas;
import com.yopagocoop.yopagocoop_backend.repositorios.AtributosEscuelasRepositorio;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioEscuelas;
import com.yopagocoop.yopagocoop_backend.repositorios.RepositorioMiembros;
import com.yopagocoop.yopagocoop_backend.servicios.ServicioEscuelas;

@Service
public class ServicioEscuelasImpl implements ServicioEscuelas {
  // en esta implementación, es CÓMO se van a hacer los metodos de la interfaz

  // inyección de dependencias de los repositorios necesarios

  private final RepositorioEscuelas repositorioEscuelas;

  private final AtributosEscuelasRepositorio atributosEscuelasRepositorio;

  private final RepositorioMiembros repositorioMiembros;

  private final ModelMapper modelMapper;

  public ServicioEscuelasImpl(RepositorioEscuelas repositorioEscuelas,
      AtributosEscuelasRepositorio atributosEscuelasRepositorio, RepositorioMiembros repositorioMiembros,
      ModelMapper modelMapper) {
    this.repositorioEscuelas = repositorioEscuelas;
    this.atributosEscuelasRepositorio = atributosEscuelasRepositorio;
    this.repositorioMiembros = repositorioMiembros;
    this.modelMapper = modelMapper;
  }

  // Fix: uso del ModelMapper.
  @Override
  public RespuestaEscuelasDTO crearEscuela(CreacionEscuelasDTO escuelaDTO) {
    Escuela escuela = modelMapper.map(escuelaDTO, Escuela.class);

    Escuela escuela = repositorioEscuelas.save(escuela);

    return modelMapper.map(escuela, RespuestaEscuelasDTO);
  }
}
