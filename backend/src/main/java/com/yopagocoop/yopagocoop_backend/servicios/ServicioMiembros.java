package com.yopagocoop.yopagocoop_backend.servicios;

public interface ServicioMiembros {

}

/*
 * 
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