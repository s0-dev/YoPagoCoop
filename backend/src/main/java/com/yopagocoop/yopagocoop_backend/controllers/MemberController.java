package com.yopagocoop.yopagocoop_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yopagocoop.yopagocoop_backend.models.Member;
import com.yopagocoop.yopagocoop_backend.services.MemberService;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/members")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping
  public ResponseEntity<List<Member>> getAllMembers() {
    List<Member> members = memberService.getAllMembers();
    return new ResponseEntity<>(members, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
    Optional<Member> member = memberService.getMemberById(id);
    return member.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public ResponseEntity<Member> createMember(@RequestBody Member member) {
    // TODO MEMBER_CONTROLLLER
    // Validar los datos del miembro antes de crear
    Member createdMember = memberService.createMember(member);
    return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member updateMember) {
    Member member = memberService.updateMember(updateMember, id);
    // TODO: MEMBER_CONTROLLER
    // Implementar fail fast y validaciones
    if (member != null) {
      return new ResponseEntity<>(member, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Member> deleteMember(@PathVariable Long id) {
    memberService.deleteMember(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}

/*
 * TODO: in member controller, create
 * 1. Endpoint para Registrar/Crear Miembro
 * - Recibir los datos básicos del miembro (de la tabla members).
 * - Recibir un conjunto de atributos específicos de la escuela (un mapa o lista
 * de clave-valor donde la clave es el attribute_name o
 * school_specific_attribute_id y el valor es el dato ingresado).
 * - Extraer el school_id del miembro que se está creando.
 * - Llamar al servicio para crear el miembro y sus atributos específicos.
 * 2. Endpoint para Editar Miembro:
 * - Recibir el ID del miembro a editar.
 * - Recibir los datos básicos actualizados del miembro.
 * - Recibir los atributos específicos de la escuela actualizados.
 * - Llamar al servicio para actualizar el miembro y sus atributos específicos.
 * 3. Endpoint para Obtener Miembro por ID
 * - Recibir el ID del miembro.
 * - Llamar al servicio para obtener la información básica del miembro y sus
 * atributos específicos asociados a su school_id.
 */