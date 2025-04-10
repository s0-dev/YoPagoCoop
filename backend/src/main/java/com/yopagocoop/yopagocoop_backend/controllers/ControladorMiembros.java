package com.yopagocoop.yopagocoop_backend.controllers;

public class ControladorMiembros {

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