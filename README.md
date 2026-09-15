# Padel Booking

Aplicación de reservas de pistas de pádel: backend en Spring Boot con arquitectura hexagonal (separar la lógica de negocio del framework y la base de datos) y frontend en Angular, en el mismo repo (carpeta `frontend/`).

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

**Backend:** Java 21 + Spring Boot, Spring Data JPA + MySQL, Spring Security + JWT, JUnit 5, Mockito, Testcontainers, Docker.
**Frontend:** Angular 19, standalone components, sin NgModules.

## Cómo levantarlo en local

Necesitas Docker (para MySQL), JDK 21 y Node.

```bash
# Backend
docker compose up -d
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

```bash
# Frontend, en otra terminal
cd frontend
npm install
npm start
```

La app queda disponible en `http://localhost:4200`.

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
