# Supermercado API

API REST para la gestión administrativa de un supermercado. Desarrollada con Java 17 y Spring Boot 3.

## Requisitos

- Java 17
- Maven 3.8+
- MySQL 8

## Configuración

Por defecto la aplicación busca una base de datos MySQL en `localhost:3306` con el nombre `supermercado`.
Se puede sobreescribir con variables de entorno:

```
DB_HOST, DB_PORT, DB_NAME, DB_USERNAME, DB_PASSWORD
JWT_SECRET
```

## Ejecución

```bash
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Módulos

| Módulo | Endpoints base |
|---|---|
| Categorías | `/api/categorias` |
| Productos | `/api/productos` |
| Proveedores | `/api/proveedores` |
| Abastecimiento | `/api/abastecimiento` |
| Empleados | `/api/empleados` |
| Ventas | `/api/ventas` |
| Autenticación | `/api/auth` |

La colección de Postman con todos los ejemplos está en `docs/postman/`.
