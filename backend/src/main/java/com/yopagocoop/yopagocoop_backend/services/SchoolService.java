package com.yopagocoop.yopagocoop_backend.services;

import java.util.*;

import org.springframework.stereotype.Service;

import com.yopagocoop.yopagocoop_backend.models.School;
import com.yopagocoop.yopagocoop_backend.repositories.SchoolRepository;

// Indica que esta clase es un servicio de Spring
@Service
public class SchoolService {

  private final SchoolRepository schoolRepository;

  public SchoolService(SchoolRepository schoolRepository) {
    this.schoolRepository = schoolRepository;
  }

  // TODO: SCHOOL_SERVICE
  // Revisar bien cuales serian los servicios a utilizar en las escuelas

  public List<School> getAllSchools() {
    return schoolRepository.findAll();
  }

  public Optional<School> getSchoolById(Long id) {
    return schoolRepository.findById(id);
  }

  public Optional<School> getSchoolByCuit(String cuit) {
    return schoolRepository.findByCuit(cuit);
  }

  public School createSchool(School school) {

    return schoolRepository.save(school);
  }

  public School updateSchool(School school, Long id) {
    Optional<School> existingSchool = schoolRepository.findById(id);
    if (existingSchool.isPresent()) {
      School schoolToUpdate = existingSchool.get();
      // TODO: Los campos a actualizar de la escuela
      return schoolRepository.save(schoolToUpdate);
    } else {
      return null; // Indica que no se encontró la escuela a actualizar
    }
  }

  public void deleteSchool(Long id) {
    schoolRepository.deleteById(id);
  }

}