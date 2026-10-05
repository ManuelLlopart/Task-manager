# Task Manager API

REST API para gestión de tareas personales con autenticación JWT.

## 🚀 Demo
Swagger UI: https://task-manager-2-f1dv.onrender.com/swagger-ui.html

## 🛠️ Tecnologías
- Java 21
- Spring Boot 3.3.5
- Spring Security + JWT
- Spring Data JPA + Hibernate
- PostgreSQL
- Docker
- Swagger / OpenAPI

## ⚙️ Funcionalidades
- Registro e inicio de sesión con JWT
- CRUD de tareas con estados (PENDING, IN_PROGRESS, COMPLETED, CANCELLED)
- Gestión de categorías personalizadas y por defecto
- Documentación interactiva con Swagger
- Manejo global de errores

## 📐 Arquitectura
```
src/
├── controller/    → Endpoints REST
├── service/       → Lógica de negocio
├── repository/    → Acceso a datos
├── entity/        → Modelos de base de datos
├── dto/           → Objetos de transferencia de datos
├── security/      → Configuración JWT y Spring Security
└── config/        → Swagger y datos iniciales
```

## 🔧 Correr localmente
1. Clonar el repositorio
2. Configurar las variables de entorno en `application.properties`
3. Ejecutar con Maven:
```bash
./mvnw spring-boot:run
```

## 🔐 Variables de entorno necesarias
| Variable | Descripción |
|----------|-------------|
| `DATABASE_URL` | URL de conexión a PostgreSQL |
| `DATABASE_USERNAME` | Usuario de la base de datos |
| `DATABASE_PASSWORD` | Contraseña de la base de datos |
| `JWT_SECRET` | Clave secreta para firmar tokens JWT |