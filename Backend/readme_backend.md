# 🚀 RummiQ Backend - Servidor de Juego

> Servidor **Spring Boot** con soporte **WebSocket** y **REST API** para la plataforma de juego **Rummikub** multijugador.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)
![WebSocket](https://img.shields.io/badge/WebSocket-Enabled-brightgreen)

---

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Componentes Principales](#componentes-principales)
- [API REST](#api-rest)
- [WebSocket API](#websocket-api)
- [Base de Datos](#base-de-datos)
- [Ejecutar el Servidor](#ejecutar-el-servidor)
- [Debugging](#debugging)

---

## 📝 Descripción

El backend de RummiQ es un servidor **Spring Boot** que proporciona:

- **Autenticación y Autorización**: Gestión segura de usuarios con hashing BCrypt
- **Gestión de Salas**: Creación y administración dinámica de salas de juego
- **Lógica de Juego**: Validación inteligente de jugadas y sincronización de estado
- **Comunicación en Tiempo Real**: WebSocket para actualizaciones instantáneas
- **Persistencia de Datos**: Base de datos MySQL con JPA/Hibernate

---

## 💻 Requisitos

- **Java**: 21 o superior
- **Maven**: 3.8.0 o superior
- **MySQL**: 8.0 o superior
- **Git**: Para clonar el repositorio

### Requisitos Opcionales
- **Docker**: Para containerizar la aplicación
- **Postman**: Para testear la API REST

---

## 🔧 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tuusuario/RummiQ.git
cd RummiQ/Backend/rummyq-backend
```

### 2. Crear Base de Datos

```sql
CREATE DATABASE rummiq;
CREATE USER 'rummiq_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON rummiq.* TO 'rummiq_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Instalar Dependencias

```bash
mvn clean install
```

---

## ⚙️ Configuración

### application.properties

Editar `src/main/resources/application.properties`:

```properties
# ===========================
# DataSource Configuration
# ===========================
spring.datasource.url=jdbc:mysql://localhost:3306/rummiq?useSSL=false&serverTimezone=UTC
spring.datasource.username=rummiq_user
spring.datasource.password=secure_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ===========================
# JPA/Hibernate Configuration
# ===========================
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

# ===========================
# Application Configuration
# ===========================
spring.application.name=rummiq-backend
server.port=8080
server.servlet.context-path=/api

# ===========================
# WebSocket Configuration
# ===========================
spring.websocket.allowed-origins=http://localhost:*

# ===========================
# Logging Configuration
# ===========================
logging.level.root=INFO
logging.level.com.rummyq.backend=DEBUG
logging.level.org.springframework.web=DEBUG
```

### Variables de Entorno

Alternativamente, puedes usar variables de entorno:

```bash
export DB_URL=jdbc:mysql://localhost:3306/rummiq
export DB_USER=rummiq_user
export DB_PASSWORD=secure_password
export SERVER_PORT=8080
```

---

## 📁 Estructura del Proyecto

```
rummyq-backend/
├── src/main/java/com/rummyq/backend/
│   ├── BackendApplication.java           # Punto de entrada
│   ├── config/
│   │   ├── WebSocketConfig.java          # Configuración WebSocket
│   │   ├── JpaConfig.java                # Configuración JPA
│   │   └── SecurityConfig.java           # Seguridad (si aplica)
│   ├── controllers/
│   │   ├── AuthController.java           # Autenticación
│   │   ├── UserController.java           # Gestión de usuarios
│   │   ├── RoomController.java           # Gestión de salas
│   │   └── GameController.java           # Estado del juego
│   ├── models/
│   │   ├── User.java                     # Entidad Usuario
│   │   ├── GameRoom.java                 # Entidad Sala
│   │   ├── Jugador.java                  # Entidad Jugador
│   │   ├── Ficha.java                    # Entidad Ficha
│   │   ├── GameStatus.java               # Estado del juego
│   │   └── ValidateGame.java             # Validación de jugadas
│   ├── repositories/
│   │   ├── UserRepository.java           # Acceso a usuarios
│   │   ├── GameRoomRepository.java       # Acceso a salas
│   │   ├── JugadorRepository.java        # Acceso a jugadores
│   │   └── FichaRepository.java          # Acceso a fichas
│   ├── services/
│   │   ├── AuthService.java              # Lógica autenticación
│   │   ├── UserService.java              # Lógica usuarios
│   │   ├── RoomService.java              # Lógica salas
│   │   ├── GameService.java              # Lógica del juego
│   │   └── ValidateGameService.java      # Validación de jugadas
│   ├── websocket/
│   │   ├── handlers/
│   │   │   ├── GameWebSocketHandler.java # Manejador principal
│   │   │   └── MessageHandler.java       # Procesamiento de mensajes
│   │   ├── dto/
│   │   │   ├── GameStatusDTO.java        # DTO de estado del juego
│   │   │   ├── PlayDTO.java              # DTO de jugadas
│   │   │   └── MessageDTO.java           # DTO de mensajes
│   │   └── GameSessionManager.java       # Gestión de sesiones
│   └── utils/
│       ├── HashUtil.java                 # Utilidades de hash
│       ├── JwtUtil.java                  # Utilidades JWT (si aplica)
│       └── ValidationUtil.java           # Validaciones generales
├── src/main/resources/
│   ├── application.properties             # Configuración principal
│   ├── application-dev.properties         # Configuración desarrollo
│   ├── application-prod.properties        # Configuración producción
│   ├── schema/
│   │   ├── schema.sql                     # DDL de tablas
│   │   └── data.sql                       # Datos iniciales
│   └── logback-spring.xml                 # Configuración logging
├── src/test/java/                         # Tests unitarios
├── pom.xml                                # Dependencias Maven
└── Dockerfile                             # Configuración Docker
```

---

## 🏗️ Componentes Principales

### Controllers

#### AuthController
```java
POST   /api/auth/register      # Registrar usuario
POST   /api/auth/login         # Iniciar sesión
POST   /api/auth/recover       # Recuperar contraseña
POST   /api/auth/logout        # Cerrar sesión
```

#### RoomController
```java
POST   /api/rooms              # Crear sala
GET    /api/rooms              # Listar salas
GET    /api/rooms/{id}         # Obtener sala
POST   /api/rooms/{id}/join    # Unirse a sala
POST   /api/rooms/{id}/leave   # Salir de sala
DELETE /api/rooms/{id}         # Eliminar sala
```

#### GameController
```java
GET    /api/games/{roomId}     # Obtener estado del juego
POST   /api/games/{roomId}/play # Realizar jugada
GET    /api/games/{roomId}/status # Estado actualizado
```

### Services

#### GameService
Gestiona la lógica principal del juego:
- Inicialización de partidas
- Distribución de fichas
- Manejo de turnos
- Detección de ganador

#### ValidateGameService
Valida movimientos de jugadores:
- Comprobación de grupos y escaleras
- Validación de comodines
- Verificación de tiles disponibles
- Detección de movimientos inválidos

#### RoomService
Gestiona las salas de juego:
- Creación de salas
- Adición/remoción de jugadores
- Inicio automático cuando hay 2+ jugadores
- Limpieza de salas vacías

### WebSocket Handlers

#### GameWebSocketHandler
Maneja la comunicación WebSocket:
- Conexión/desconexión de clientes
- Enrutamiento de mensajes
- Sincronización de estado
- Notificaciones a jugadores

---

## 📡 WebSocket API

### Estructura de Mensajes

Los mensajes WebSocket usan formato JSON:

```json
{
  "type": "PLAY",
  "gameRoomId": "12345",
  "userId": "user123",
  "payload": {
    "tiles": [...],
    "action": "place"
  }
}
```

### Tipos de Mensajes

#### PLAY
Envío de jugada desde cliente

**Request**:
```json
{
  "type": "PLAY",
  "tiles": [
    {"color": "RED", "numero": 5},
    {"color": "RED", "numero": 6},
    {"color": "RED", "numero": 7}
  ]
}
```

**Response**:
```json
{
  "type": "PLAY_RESULT",
  "success": true,
  "message": "Jugada válida",
  "gameStatus": {
    "tablero": [...],
    "turnoActual": "user456",
    "fichasRestantes": {"user123": 12}
  }
}
```

#### STATUS_UPDATE
Actualización de estado del juego

**Broadcast**:
```json
{
  "type": "STATUS_UPDATE",
  "gameStatus": {
    "jugadores": [...],
    "tablero": [...],
    "turnoActual": "user123"
  }
}
```

#### FIN_PARTIDA
Fin de la partida

**Broadcast**:
```json
{
  "type": "FIN_PARTIDA",
  "ganador": "user123",
  "puntos": {
    "user123": 100,
    "user456": -50
  }
}
```

#### ROOM_UPDATE
Actualización de jugadores en sala

**Broadcast**:
```json
{
  "type": "ROOM_UPDATE",
  "jugadores": ["user123", "user456"],
  "estado": "ESPERANDO"
}
```

---

## 🗄️ Base de Datos

### Diagrama de Tablas

```
┌─────────────┐
│    users    │
├─────────────┤
│ id (PK)     │
│ email (UQ)  │
│ password    │
│ nombre      │
│ created_at  │
└─────────────┘

┌─────────────────────┐
│    game_rooms       │
├─────────────────────┤
│ id (PK)             │
│ nombre              │
│ host_id (FK)        │
│ estado              │
│ max_jugadores = 4   │
│ created_at          │
└─────────────────────┘

┌─────────────────────────┐
│   room_jugadores        │
├─────────────────────────┤
│ room_id (FK)            │
│ user_id (FK)            │
│ posicion                │
│ ficha_hand (JSON)       │
│ ficha_mesa (JSON)       │
└─────────────────────────┘

┌─────────────────────┐
│    fichas           │
├─────────────────────┤
│ id (PK)             │
│ color               │
│ numero              │
│ game_room_id (FK)   │
│ propietario_id (FK) │
└─────────────────────┘
```

### Scripts SQL

Ver `src/main/resources/schema/` para:
- `schema.sql` - Creación de tablas
- `data.sql` - Datos iniciales (usuarios de prueba)

---

## ▶️ Ejecutar el Servidor

### Opción 1: Maven

```bash
# Desarrollo con logs detallados
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Producción
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"

# Versión compilada
mvn clean install
java -jar target/rummiq-backend-1.0.0.jar
```

### Opción 2: IDE

**IntelliJ IDEA / Eclipse**:
1. Abrir `BackendApplication.java`
2. Hacer click en el botón "Run" o presionar `Shift+F10`

### Opción 3: Docker

```bash
docker build -f Dockerfile -t rummiq-backend .
docker run -p 8080:8080 rummiq-backend
```

---

## 🧪 Debugging

### Logs

Los logs se guardan en:
- **Desarrollo**: Consola + `logs/rummiq-dev.log`
- **Producción**: `logs/rummiq-prod.log`

### Endpoints de Debugging

```bash
# Health Check
curl https://rummiqback.onrender.com/api/health

#  WebSocket
curl https://rummiqback.onrender.com/ws/game
```

### Testear API con cURL

```bash
# Registrar usuario
curl -X POST https://rummiqback.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"pass123","nombre":"Usuario"}'

# Iniciar sesión
curl -X POST https://rummiqback.onrender.com/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"pass123"}'

# Crear sala
curl -X POST https://rummiqback.onrender.com/api/rooms \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Mi Sala","maxJugadores":4}'
```

### Testear WebSocket

Usar herramientas como:
- **wscat**: `npm install -g wscat`
- **Postman**: Cliente WebSocket integrado
- **Browser DevTools**: Inspeccionar conexiones

```bash
wscat -c ws://rummiqback.onrender.com/ws/game
```

---

## 📊 Monitoreo

### Métricas Disponibles (Spring Boot Actuator)

```bash
# Todas las métricas
curl https://rummiqback.onrender.com/actuator

# Métricas específicas
curl https://rummiqback.onrender.com/actuator/metrics/jvm.memory.used
curl https://rummiqback.onrender.com/actuator/health
```

---

## 🔒 Seguridad

### Implementaciones

- ✅ **Hashing de Contraseñas**: BCrypt
- ✅ **Validación de Input**: Prevención de inyecciones SQL
- ✅ **CORS**: Configurado para desarrollo
- ✅ **WebSocket Seguro**: Validación de conexiones

### Mejoras Futuras

- [ ] JWT para autenticación stateless
- [ ] Rate limiting en endpoints
- [ ] Encriptación de datos sensibles
- [ ] Logs de auditoría

---

## 📚 Documentación Adicional

- [README Principal](../README.md)
- [README Frontend](../Frontend/readme_frontend.md)
- [Historias de Usuario](../HistoriasDeUsuario_Entrega.md)

---

## 🆘 Troubleshooting

### Error: "Cannot connect to database"
```bash
# Verificar que MySQL está corriendo
mysql -u rummiq_user -p

# Verificar propiedades de conexión
cat src/main/resources/application.properties | grep datasource
```

### Error: "Port 8080 already in use"
```bash
# Cambiar puerto en application.properties
server.port=8081

# O matar proceso en puerto 8080
lsof -i :8080
kill -9 <PID>
```

### Error: "WebSocket connection failed"
```bash
# Verificar que WebSocket está habilitado
curl https://rummiqback.onrender.com/ws/game

# Revisar logs del servidor
tail -f logs/rummiq-dev.log
```

---

## 📝 Historial de Versiones

| Versión | Fecha | Cambios |
|---------|-------|---------|
| 1.0.0 | Mayo 2026 | Lanzamiento inicial |

---

## 📞 Contacto y Soporte

Para problemas técnicos o preguntas sobre el backend, contacta al equipo de desarrollo.

---

**Última actualización**: Mayo 2026  
**Versión**: 1.0.0

