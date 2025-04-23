# Prerequisitos del backend

- Docker y Docker Compose
- Java 21
- Maven 3.9.9
- Git

## Clonar el repositorio

```sh
git clone https://github.com/s0-dev/YoPagoCoop.git
cd YoPagoCoop
```

## Revisar el archivo .env.example

- Este es el archivo donde vas a colocar la información de tu entorno de desarrollo, revisarlo en la raiz del proyecto

## 1. Construcción inicial:

```sh
docker-compose -f docker-compose-dev.yaml up -d --build
```

- Esta es la primera vez que levantas los contenedores o después de haber modificado los archivos que afectan la construcción de la imagen (Dockerfile, pom.xml, etc.).

## 2. Desarrollo continuo:

```sh
docker-compose -f docker-compose-dev.yaml up -d
```

- Después de la construcción inicial, cuando realices cambios en tus archivos Java dentro de la carpeta src/, guarda los cambios en tu IDE. Spring Boot DevTools detectará estos cambios y reiniciará automáticamente la aplicación dentro del contenedor. No necesitas volver a ejecutar --build en este punto.

## 3. Detener los contenedores al finalizar el trabajo:

```sh
docker-compose -f docker-compose-dev.yaml down
```

## Este comando detiene y elimina los contenedores.

### ⚠️ Detener los contenedores y borrar datos persistentes: ⚠️

```sh
docker-compose -f docker-compose-dev.yaml down -v
```

- En caso de necesitar borrar la base de datos y volver a inciarla, para cambiar ciertas columnas dentro de las tablas, agregarle el -v, esto borra TODOS los datos de la base de datos y el backend, no utilizar este comando en producción ni cuando ya hayan datos almacenados en tu base de datos!
