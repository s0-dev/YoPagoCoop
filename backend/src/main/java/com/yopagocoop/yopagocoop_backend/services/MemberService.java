package com.yopagocoop.yopagocoop_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yopagocoop.yopagocoop_backend.models.Member;
import com.yopagocoop.yopagocoop_backend.repositories.MemberRepository;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
  // TODO: MEMBER_SERVICE
  // Revisar bien cuales serian los servicios a utilizar en los miembros

  public List<Member> getAllMembers() {
    return memberRepository.findAll();
  }

  public Optional<Member> getMemberById(Long id) {
    return memberRepository.findById(id);
  }

  public Optional<Member> getMemberByDni(String dni) {
    return memberRepository.buscarPorDni(dni);
  }

  public Member createMember(Member member) {

    return memberRepository.save(member);
  }

  public Member updateMember(Member member, Long id) {
    Optional<Member> existingMember = memberRepository.findById(id);
    if (existingMember.isPresent()) {
      Member memberToUpdate = existingMember.get();
      // TODO: MEMBER_SERVICE
      // Los campos a actualizar del miembro
      return memberRepository.save(memberToUpdate);
    } else {
      return null;
    }
  }

  public void deleteMember(Long id) {
    memberRepository.deleteById(id);
  }

}

/*
 * TODO: MEMBER_SERVICE
 * 1. Método para Crear Miembro
 * - Recibir los datos básicos del miembro y el school_id
 * - Guardar la información básica del miembro en la tabla members
 * - Recibir el mapa/lista de atributos específicos
 * Para cada atributo específico:
 * - Buscar el school_specific_attribute correspondiente por school_id y
 * attribute_name.
 * - Guardar el valor del atributo en la tabla member_attributes relacionándolo
 * con el member_id y el school_specific_attribute_id.
 * - Validación: Antes de guardar, podrías validar si los atributos obligatorios
 * (is_required = true en school_specific_attributes para la school_id dada)
 * están presentes en los datos recibidos.
 * 2. Método para Editar Miembro
 * - Recibir el ID del miembro a editar, los datos básicos actualizados y los
 * atributos específicos actualizados.
 * - Actualizar la información básica del miembro en la tabla members.
 * Para los atributos específicos:
 * - Actualizar los valores existentes en member_attributes para ese member_id y
 * school_id.
 * - Insertar nuevos atributos si no existían previamente para ese miembro y
 * escuela.
 * - Validación: Similar a la creación, validar que los atributos obligatorios
 * para la escuela estén presentes.
 * 3. Método para Obtener Miembro por ID con atributos especificos
 * - Obtener la información básica del miembro desde la tabla members.
 * - Consultar la tabla member_attributes para obtener todos los valores de
 * atributos específicos asociados a ese member_id.
 * - Consultar la tabla school_specific_attributes para obtener los nombres de
 * los atributos correspondientes al school_id del miembro.
 * - Combinar esta información en un objeto de respuesta que incluya tanto los
 * datos básicos del miembro como un mapa o lista de sus atributos específicos
 * (clave-valor).
 */