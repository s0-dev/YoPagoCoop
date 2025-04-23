# API

El backend ofrece una API RESTful para manejar las escuelas y sus miembros

Endpoints Escuelas
| Endpoint | Métodos | Descripción |
|----------|---------|-------------|
| /api/escuelas | GET, POST | Obtener todas las escuelas o crear una |
| /api/escuelas/{id} | GET, PUT, DELETE | Obtener, actualizar o eliminar una escuela |
| /api/escuelas/{escuelaId}/atributos | GET, POST | Obtener todos los atributos de una escuela o agregar uno |
| /api/escuelas/{escuelaId}/atributos/{id} | GET, PUT, DELETE | Obtener, actualizar o eliminar un atributo de escuela |

Endpoints Miembros
| Endpoint | Métodos | Descripción |
|----------|---------|-------------|
| /api/miembros | GET, POST | Obtener todos los miembros o crear uno |
| /api/miembros/{id} | GET, PUT, DELETE | Obtener, actualizar o eliminar un miembro |
| /api/miembros/escuela/{escuelaId} | GET | Obtener todos los miembros de una escuela |
| /api/miembros/{miembroId}/atributos | GET, POST | Obtener todos los atributos de los miembros o crear uno nuevo |
| /api/miembros/{miembroId}/atributos/{id} | GET, PUT, DELETE | Obtener, actualizar o eliminar un atributo de miembro |
