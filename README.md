# Padel Booking

API REST para la gestión de reservas de pistas de pádel, hecha con Spring Boot siguiendo arquitectura hexagonal (separar la lógica de negocio del framework y la base de datos). El frontend en Angular va en otro repositorio aparte y consume esta API.

## Arquitectura

```
domain/            → entidades y reglas de negocio, sin dependencias externas
application/
  port/in/         → casos de uso que expone la aplicación
  port/out/        → lo que la aplicación necesita del exterior
  service/         → implementación de los casos de uso
infrastructure/
  adapter/in/web/          → controllers REST, DTOs, manejo de errores
  adapter/out/persistence/ → entidades JPA, repositorios, adaptadores
  config/                  → seguridad, CORS
```

## Stack

- Java 21 + Spring Boot
- Spring Data JPA + MySQL
- Spring Security + JWT
- JUnit 5, Mockito, Testcontainers
- Docker / Docker Compose

## Cómo levantarlo en local

Necesitas Docker (para MySQL) y JDK 21.

```bash
docker compose up -d
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Endpoints

| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/api/auth/registro` | pública | Crea una cuenta (email + password) |
| `POST` | `/api/auth/login` | pública | Devuelve un JWT si las credenciales son correctas |
| `GET` | `/api/pistas` | pública | Lista las pistas activas |
| `GET` | `/api/pistas/{id}/disponibilidad?fecha=YYYY-MM-DD` | pública | Franjas libres de una pista ese día |
| `POST` | `/api/reservas` | requiere token | Crea una reserva |
| `GET` | `/api/reservas/mias` | requiere token | Lista tus propias reservas |
| `PATCH` | `/api/reservas/{id}/cancelar` | requiere token | Cancela una reserva propia (403 si no es tuya) |

## Tests

```bash
./mvnw test
```

## Autor

Pablo García Olmeda — [LinkedIn](https://www.linkedin.com/in/pablo-garcia-olmeda-741116301)
