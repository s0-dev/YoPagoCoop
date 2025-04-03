# Backend README

Para correr el proyecto con el backend con el entorno de desarrollo:

1. **Construcción inicial:**

```bash
docker-compose -f docker-compose-dev.yaml up -d --build
```

Esta es la primera vez que levantas los contenedores o después de haber modificado los archivos que afectan la construcción de la imagen (Dockerfile, `pom.xml`, etc.).

2. **Desarrollo continuo:**

```bash
docker-compose -f docker-compose-dev.yaml up -d
```

Después de la construcción inicial, cuando realices cambios en tus archivos Java dentro de la carpeta `src/`, guarda los cambios en tu IDE. Spring Boot DevTools detectará estos cambios y reiniciará automáticamente la aplicación dentro del contenedor. **No necesitas volver a ejecutar `--build` en este punto.**

3. **Detener los contenedores al finalizar el trabajo:**

```bash
docker-compose -f docker-compose-dev.yaml down
```

Este comando detiene y elimina los contenedores cuando terminas de trabajar en tu proyecto.

⚠️ **Detener los contenedores y borrar datos persistentes:** ⚠️

```bash
docker-compose -f docker-compose-dev.yaml down -v
```

En caso de necesitar borrar la base de datos y volver a inciarla, para cambiar ciertas columnas dentro de las tablas, agregarle el `-v`, esto borra **TODOS** los datos de la base de datos y el backend, no utilizar este comando en producción ni cuando ya hayan datos almacenados en tu base de datos!

---

Endpoints Actuales:

- /api/schools
  contiene: POST, GET
- /api/schools/{id}
  contiene: PUT, DELETE
