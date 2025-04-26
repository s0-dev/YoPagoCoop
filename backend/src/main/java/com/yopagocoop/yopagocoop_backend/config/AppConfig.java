package com.yopagocoop.yopagocoop_backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaAtributosMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.dto.Miembros.RespuestaMiembrosDTO;
import com.yopagocoop.yopagocoop_backend.modelos.AtributosMiembros;
import com.yopagocoop.yopagocoop_backend.modelos.Miembro;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/api/auth/").setViewName("forward:/api/auth/login");
    }




  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    // Custom mapping for Miembro to RespuestaMiembrosDTO
    modelMapper.createTypeMap(Miembro.class, RespuestaMiembrosDTO.class)
        .addMappings(mapper -> {
          mapper.map(src -> src.getEscuela().getId(), RespuestaMiembrosDTO::setEscuelaId);
          mapper.map(src -> src.getEscuela().getNombre(), RespuestaMiembrosDTO::setEscuelaNombre);
        });

    // Custom mapping for AtributosMiembros to RespuestaAtributosMiembrosDTO
    modelMapper.createTypeMap(AtributosMiembros.class, RespuestaAtributosMiembrosDTO.class)
        .addMappings(mapper -> {
          mapper.map(src -> src.getMiembro().getId(), RespuestaAtributosMiembrosDTO::setMiembroId);
          mapper.map(src -> src.getMiembro().getNombre(), RespuestaAtributosMiembrosDTO::setNombreMiembro);
          mapper.map(src -> src.getAtributoEspecificoEscuela().getId(),
              RespuestaAtributosMiembrosDTO::setAtributoEscuelaId);
          mapper.map(src -> src.getAtributoEspecificoEscuela().getNombreAtributo(),
              RespuestaAtributosMiembrosDTO::setNombreAtributoEscuela);
        });

    return modelMapper;
  }
}