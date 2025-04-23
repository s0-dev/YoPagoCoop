# Base de datos

- El sistema utiliza PostgreSQL con el siguiente modelo de entidades.

## Tablas principales

1. escuelas - Almacena la información de la escuela:

- id (PK)
- nombre
- direccion
- cuit
- email
- fecha_creacion

2. miembros - Stores member information:

- id (PK)
- id_escuela (FK to escuelas.id)
- dni
- nombre
- apellido
- email
- celular
- fecha_creacion

# Tablas de atributos (Modelo EAV)

3. atributos_especificos_escuelas - Define los atributos especificos para cada escuela.

- id (PK)
- id_escuela (FK to escuelas.id)
- nombre_atributo
- tipo_dato
- es_requerido

4. atributos_especificos_miembros - Define los atributos especificos para cada miembro

- id (PK)
- id_miembro (FK to miembros.id)
- id_atributo_especifico_escuela (FK to atributos_especificos_escuelas.id)
- valor_atributo

Esta estructura soporta el patron EAV, el cual permite que distintas escuelas tengan distintos atributos para cada miembro, permitiendo tablas mas flexibles sin editar las principales.
