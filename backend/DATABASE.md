# Base de datos

La base de datos es en PostgreSQL, las tablas son asi:

Instituciones:

- ID (PK)
- Nombre
- Dirección
- CUIT
- Email
- Creado el

Miembros:

- ID (PK)
- DNI
- Nombre
- Apellido
- Email
- Telefono
- Creado el
- Institución (FK ID_Instituciones)

Van a haber 2 tablas más, para agregar atributos adicionales dependiendo de la Institución, utilizando el módelo EAV (Entidad, Atributo, Valor) de SQL.

Instituciones_Atributos:

- ID (PK)
- Institucion (FK ID_Instituciones)
- Nombre_Atributo
- Tipo_Dato
- Is_required

Miembros_Atributos:

- ID
- Miembro id (FK ID_Miembros)
- Atributo_Institución id (FK ID_Instituciones_Atributos)
- Valor_Atributo
