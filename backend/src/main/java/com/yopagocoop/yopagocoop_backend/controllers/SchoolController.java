package com.yopagocoop.yopagocoop_backend.controllers;

import java.util.List;
import java.util.Optional;

import com.yopagocoop.yopagocoop_backend.services.SchoolService;
import com.yopagocoop.yopagocoop_backend.models.School;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

// Indica que esta clase es un controlador REST (maneja peticiones HTTP y devuelve respuestas)
@RestController
// Define la ruta base para todos los endpoints de este controlador
@RequestMapping("/api/schools")
public class SchoolController {

  private final SchoolService schoolService;

  public SchoolController(SchoolService schoolService) {
    this.schoolService = schoolService;
  }

  // get /api/schools
  @GetMapping
  public ResponseEntity<List<School>> getAllSchools() {
    List<School> schools = schoolService.getAllSchools();
    // Devuelve la lista de escuelas con código de estado 200
    return new ResponseEntity<>(schools, HttpStatus.OK);
  }

  // get /api/schools/{id} donde id es la var en la URL
  @GetMapping("/{id}")
  public ResponseEntity<School> getSchoolById(@PathVariable Long id) {
    Optional<School> school = schoolService.getSchoolById(id);
    // Si se encuentra la escuela, la devuelve con código 200
    return school.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
        // Si no se encuentra, devuelve código 404
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  // Mapea las peticiones POST a /api/schools
  @PostMapping
  public ResponseEntity<School> createSchool(@RequestBody School school) {
    // TODO: Chequear si el anterior numero se borro antes de crear una nueva ID
    School createdSchool = schoolService.createSchool(school);
    // Devuelve la escuela creada con código de estado 201
    return new ResponseEntity<>(createdSchool, HttpStatus.CREATED);
  }

  // Mapea las peticiones PUT a /api/schools/{id} (para actualizar)
  @PutMapping("/{id}")
  public ResponseEntity<School> updateSchool(@PathVariable Long id, @RequestBody School updateSchool) {
    School school = schoolService.updateSchool(updateSchool, id);
    if (school != null) {
      // Devuelve la escuela actualizada con código 200
      return new ResponseEntity<>(school, HttpStatus.OK);
    } else {
      // Si no se encuentra la escuela a actualizar, devuelve código 404
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // Mapea las peticiones DELETE a /api/schools/{id} (para eliminar)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
    schoolService.deleteSchool(id);
    // Devuelve código de estado 204 (No Content) indicando que la operación fue
    // exitosa (sin cuerpo en la respuesta)
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
