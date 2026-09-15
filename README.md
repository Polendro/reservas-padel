# Padel Booking

[![CI](https://github.com/Polendro/reservas-padel/actions/workflows/ci.yml/badge.svg)](https://github.com/Polendro/reservas-padel/actions/workflows/ci.yml)

API REST para la gestión de reservas de pistas de pádel, construida con **arquitectura hexagonal (Ports & Adapters)**. Proyecto personal para practicar y demostrar cómo separo la lógica de negocio de la infraestructura (framework, base de datos, HTTP) en una aplicación real.

El frontend (Angular) vive/vivirá en un repo aparte y consume esta API.

## Por qué hexagonal

El dominio (`Pista`, `Reserva`, la regla de que no se pueden solapar dos reservas en la misma pista) no depende de Spring ni de JPA. Puedo testearlo con JUnit puro, sin levantar contexto ni base de datos, y cambiar de MySQL a otra base de datos sin tocar una sola clase de negocio.

```
domain/            → entidades y reglas de negocio, sin dependencias externas
application/
  port/in/         → casos de uso que expone la aplicación (interfaces)
  port/out/        → lo que la aplicación necesita del exterior (interfaces)
  service/         → implementación de los casos de uso
infrastructure/
  adapter/in/web/          → controllers REST, DTOs, manejo de errores
  adapter/out/persistence/ → entidades JPA, repositorios Spring Data, adaptadores
  config/                  → seguridad, CORS, etc.
```

## Stack

| Categoría | Tecnología |
|---|---|
| Backend | Java 21, Spring Boot |
| Persistencia | Spring Data JPA + MySQL |
| Seguridad | Spring Security + JWT (jjwt) |
| Tests | JUnit 5, Mockito, Testcontainers |
| Infra | Docker / Docker Compose |
| Frontend | Angular (repo aparte) |

## Cómo levantarlo en local

Necesitas Docker (para MySQL) y JDK 21.

```bash
# 1. Levanta MySQL
docker compose up -d

# 2. Arranca la aplicación
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

Para producción hay que fijar la variable de entorno `JWT_SECRET` (mínimo 32 bytes) — en local no hace falta, `application.yml` trae un valor de desarrollo por defecto.

## Endpoints

| Método | Ruta | Auth | Descripción |
|---|---|---|---|
| `POST` | `/api/auth/registro` | pública | Crea una cuenta (email + password). Falla con `409` si el email ya está registrado. |
| `POST` | `/api/auth/login` | pública | Devuelve un JWT si el email y password son correctos. Falla con `401` si no lo son (mismo error para ambos casos, a propósito). |
| `GET` | `/api/pistas/{id}/disponibilidad?fecha=YYYY-MM-DD` | pública | Devuelve las franjas libres de una pista ese día (horario 08:00-22:00, franjas de 1h). Falla con `404` si la pista no existe. |
| `POST` | `/api/reservas` | 🔒 requiere token | Crea una reserva. Falla con `404` si la pista no existe y con `409` si el horario se solapa con otra reserva confirmada. |
| `PATCH` | `/api/reservas/{id}/cancelar` | 🔒 requiere token | Cancela una reserva. Falla con `404` si no existe y con `409` si ya estaba cancelada o si quedan menos de 2h para el inicio. |

Flujo completo con `curl`:

```bash
# 1. Registro
curl -X POST http://localhost:8080/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{"email": "pablo@test.com", "password": "password123"}'

# 2. Login -> devuelve { "token": "..." }
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "pablo@test.com", "password": "password123"}'

# 3. Usar el token en los endpoints protegidos
curl -X POST http://localhost:8080/api/reservas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token del paso 2>" \
  -d '{
    "pistaId": 1,
    "clienteNombre": "Pablo García",
    "inicio": "2026-10-01T18:00:00",
    "fin": "2026-10-01T19:00:00"
  }'
```

## Tests

```bash
./mvnw test
```

- Los tests de `domain` y `application` son unitarios puros (sin Spring, sin Docker) y corren siempre.
- `PadelBookingApplicationTests` levanta el contexto completo contra un MySQL real de **Testcontainers**, así que necesita Docker Desktop abierto.

## Roadmap

- [x] Autenticación JWT (login + filtro de seguridad)
- [x] Caso de uso: cancelar reserva (con política de antelación)
- [x] Caso de uso: consultar disponibilidad de una pista
- [ ] Test de integración del adaptador de persistencia con Testcontainers
- [x] CI con GitHub Actions
- [ ] Frontend en Angular
- [ ] Despliegue (backend + MySQL en la nube)

## Autor

Pablo García Olmeda — [LinkedIn](https://www.linkedin.com/in/pablo-garcia-olmeda-741116301)
