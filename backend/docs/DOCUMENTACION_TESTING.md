# Documentacion de Testing

Testing es un aspecto crítico en el desarrollo de YoPagoCoop, por eso nos dedicamos a seguir un proceso de primero implementar los tests, y posteriormente incluir la lógica que va a actuar en base a esos tests, o mejor conocido como TDD (Test Driven Development), al comprender profundamente la lógica de negocio de nuestro sistema, podemos llevar a cabo este patrón de desarrollo el cual nos beneficia al equipo interno.

## Tipos de Tests actuales en YoPagoCoop:

- Test unitarios: prueban componentes individuales y en insolación para su correcta implementación

## Tipos de Tests faltantes:

- Test de integración: prueban componentes en conjunto, principalmente dos o más.
- Test de Fin a Fin: prueban todo el sistema, desde la petición HTTP hasta la base de datos y vice-versa.

## Convenciones de nombramiento de Tests:

- Unit Tests: `test{NombreMetodo}_{ComportamientoEsperado}`

## Buenas practicas

1. Patron PEV: Preparar, Ejecutar y Verificar para la estructura del test
2. Uso de nombres significantes que describan el metodo y el comportamiento
3. Los tests prueban tanto fallo como éxito
4. Mockear las dependencias externas para aislar los componentes probados
5. Usar las verificaciones para validar resultados, no para verificar las llamadas a los metodos
6. Mantener los tests independientes - ningún test debería depender de otros.

## Futuras mejoras

1. Añadir tests de integración y fin a fin para comprobar las interacciones de los componentes
2. Armar tests automáticos con una pipeline CI/CD
3. Agregar tests de rendimiento para operaciones críticas
