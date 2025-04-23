# Estructura del proyecto

YoPagoCoop sigue la convencion modular de Spring Boot, haciendo un patron de arquitectura Layered Structure

```
src/
├── main/
│   ├── java/com/yopagocoop/yopagocoop_backend/
│   │   ├── controladores/   # Controladores RESTful
│   │   ├── dto/             # Objetos de transferencia de datos
│   │   ├── modelos/         # Modelos de entidades
│   │   ├── repositorios/    # Repositorios
│   │   ├── servicios/       # Servicios de logica de negocio (interfaces e implementaciones)
│   │   └── excepciones/     # Excepciones especificas | EN PROGRESO
│   └── resources/
│       ├── application.properties
│       └── db/changelog/    # Migraciones de Liquibase
├── test/
    └── java/com/yopagocoop/yopagocoop_backend/
        ├── controladores/   # Tests unitarios de los Controladores
        ├── servicios/       # Tests unitarios de los Servicios
        └── repositorios/    # Tests unitarios de los Repositorios
```
