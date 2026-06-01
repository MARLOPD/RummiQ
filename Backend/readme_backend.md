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
- **Persistencia de Datos**: Base de datos PostgreSQL alojada en Supabase

---

## 💻 Requisitos

- **Java**: 21 o superior
- **Maven**: 3.8.0 o superior
- **Git**: Para clonar el repositorio

### Requisitos Opcionales
- **Docker**: Para containerizar la aplicación
- **Postman**: Para testear la API REST

---

## 🔧 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/MARLOPD/RummiQ.git
cd RummiQ/Backend/rummyq-backend
```

### 2. Conectar Base de Datos


La conexión a la base de datos se realiza mediante una connectionString, por ello es necesario tener los valores de las variables de entorno:

```
RUMMYQ_SUPABASE_USER = {usuario}
RUMMYQ_SUPABASE_URL = {url}
RUMMYQ_SUPABASE_PASSWORD = {password}
```

### 3. Instalar Dependencias

```bash
mvn clean install
```

---

## ⚙️ Configuración

### Variables de Entorno

Alternativamente, puedes usar variables de entorno:

```bash
export RUMMYQ_SUPABASE_USER={usuario}
export RUMMYQ_SUPABASE_URL={url}
export RUMMYQ_SUPABASE_PASSWORD={password}
```

---

## 📁 Estructura del Proyecto

```
. 📂 Backend
├── 📄 readme_backend.md
└── 📂 rummyq-backend/
│  ├── 📄 Dockerfile
│  ├── 📄 pom.xml
│  └── 📂 src/
│    └── 📂 main/
│      └── 📂 java/
│        └── 📂 com/
│          └── 📂 rummyq/
│            └── 📂 backend/
│              ├── 📄 BackendApplication.java
│              └── 📂 config/
│                ├── 📄 BCryptEncryption.java
│                ├── 📄 DatabaseConnection.java
│              └── 📂 controllers/
│                ├── 📄 AuthController.java
│                ├── 📄 ChatController.java
│                ├── 📄 GameController.java
│                ├── 📄 HealthController.java
│              └── 📂 models/
│                ├── 📄 Ficha.java
│                ├── 📄 Jugador.java
│                ├── 📄 LoginRequirements.java
│                ├── 📄 Mensaje.java
│                ├── 📄 MensajeDTO.java
│                ├── 📄 Partida.java
│                ├── 📄 PartidaDTO.java
│                ├── 📄 RegistrationRequirements.java
│                ├── 📄 StartRequest.java
│              └── 📂 repositories/
│                ├── 📄 ChatRepository.java
│                ├── 📄 RecoveryPasswordRepository.java
│                ├── 📄 UserRepository.java
│              └── 📂 services/
│                ├── 📄 ChatService.java
│                ├── 📄 GameSessionManager.java
│                ├── 📄 JugadorService.java
│                ├── 📄 PartidaMapper.java
│                ├── 📄 PartidaService.java
│                ├── 📄 PasswordRecovery.java
│                ├── 📄 TileBag.java
│                ├── 📄 UserManagment.java
│                ├── 📄 ValidateGame.java
│              └── 📂 websocket/
│                ├── 📄 GameMessage.java
│                ├── 📄 GameStatus.java
│                └── 📂 config/
│                  ├── 📄 WebSocketConfig.java
│                └── 📂 handlers/
│                  ├── 📄 GameWebSocketHandler.java
│  └── 📂 target/
```

---

## 🏗️ Componentes Principales

### Controllers

#### AuthController
```java
POST   /api/signup             # Registrar usuario
POST   /api/login              # Iniciar sesión
POST   /api/findUser           # Recuperar contraseña
POST   /api/updatePassword     # Actualizar contraseña
POST   /api/verifyAnswer       # Verificar respuesta de seguridad
```

#### GameController
```java
POST   /api/game/start # Iniciar partida
```

### HealthController
```java
GET    /api/health # Verificar estado del servidor
```

### Services

#### GameSessionManager
Gestiona la lógica principal del juego:
- Inicialización de partidas
- Distribución de fichas
- Manejo de turnos
- Detección de ganador

#### ValidateGame
Valida movimientos de jugadores:
- Comprobación de grupos y escaleras
- Validación de comodines
- Verificación de tiles disponibles
- Detección de movimientos inválidos

### WebSocket Handlers

#### GameWebSocketHandler
Maneja la comunicación WebSocket:
- Conexión/desconexión de clientes
- Enrutamiento de mensajes
- Sincronización de estado
- Notificaciones a jugadoresz

---

## 📡 WebSocket API

# 🎲 RummyQ WebSocket — Guía de Mensajes

**Endpoint:** `ws://localhost:8080/ws/game`

---

## 📤 Mensajes: Cliente → Servidor

### 1. `UNIRSE` — Unirse a una sala

```json
{
  "type": "UNIRSE",
  "roomId": "sala-001",
  "player": "Alice"
}
```

> [!NOTE]
> El `roomId` puede ser cualquier string. Si la sala no existe, se crea automáticamente.
> Mínimo 2 jugadores, máximo 4.

---

### 2. `INICIAR_PARTIDA` — Iniciar el juego

```json
{
  "type": "INICIAR_PARTIDA",
  "roomId": "sala-001"
}
```

> [!IMPORTANT]
> Solo se puede iniciar con ≥2 jugadores ya unidos a la sala.
> Tras iniciar, cada jugador recibe su mano privada (14 fichas).

---

### 3. `JUGAR_GRUPO` — Colocar fichas en la mesa

Coloca uno o más grupos/escaleras. Cada grupo es un array de fichas.

#### Ejemplo: un grupo (mismo número, colores distintos)
```json
{
  "type": "JUGAR_GRUPO",
  "groups": [
    [
      { "numero": 7, "color": "ROJO",    "esComodin": false },
      { "numero": 7, "color": "AZUL",    "esComodin": false },
      { "numero": 7, "color": "NEGRO",   "esComodin": false }
    ]
  ]
}
```

#### Ejemplo: una escalera (mismo color, consecutivos)
```json
{
  "tipo": "JUGAR_GRUPO",
  "grupos": [
    [
      { "numero": 5, "color": "ROJO", "esComodin": false },
      { "numero": 6, "color": "ROJO", "esComodin": false },
      { "numero": 7, "color": "ROJO", "esComodin": false }
    ]
  ]
}
```

#### Ejemplo: escalera con comodín
```json
{
  "tipo": "JUGAR_GRUPO",
  "grupos": [
    [
      { "numero": 5,    "color": "AZUL",  "esComodin": false },
      { "numero": null, "color": "NEGRO", "esComodin": true  },
      { "numero": 7,    "color": "AZUL",  "esComodin": false }
    ]
  ]
}
```

#### Ejemplo: apertura (primera jugada — debe sumar ≥30 pts)
```json
{
  "type": "JUGAR_GRUPO",
  "groups": [
    [
      { "numero": 10, "color": "ROJO",     "esComodin": false },
      { "numero": 10, "color": "AZUL",     "esComodin": false },
      { "numero": 10, "color": "NEGRO",    "esComodin": false }
    ],
    [
      { "numero": 11, "color": "AMARILLO", "esComodin": false },
      { "numero": 12, "color": "AMARILLO", "esComodin": false },
      { "numero": 13, "color": "AMARILLO", "esComodin": false }
    ]
  ]
}
```
> 10+10+10 + 11+12+13 = **66 puntos** ✅

---

### 4. `ROBAR_FICHA` — Robar del mazo (y pasar turno)

```json
{
  "tipo": "ROBAR_FICHA"
}
```

---

### 5. `PASAR_TURNO` — Pasar el turno

```json
{
  "tipo": "PASAR_TURNO"
}
```

---

## 📥 Mensajes: Servidor → Cliente

### `ESTADO_PARTIDA` — Estado público (broadcast a todos)

```json
{
  "tipo": "ESTADO_PARTIDA",
  "estado": {
    "roomId": "sala-001",
    "fase": "EN_CURSO",
    "turno": "Alice",
    "fichasRestantes": 78,
    "jugadores": [
      { "nombre": "Alice", "cantFichas": 14, "puntos": 87 },
      { "nombre": "Bob",   "cantFichas": 13, "puntos": 45 }
    ],
    "mesa": [
      [
        { "numero": 7, "color": "ROJO",  "esComodin": false },
        { "numero": 7, "color": "AZUL",  "esComodin": false },
        { "numero": 7, "color": "NEGRO", "esComodin": false }
      ]
    ]
  }
}
```

| `fase` | Significado |
|--------|-------------|
| `ESPERANDO` | Sala de espera, esperando jugadores |
| `EN_CURSO` | Partida en progreso |
| `TERMINADA` | Partida finalizada |

---

### `MANO_JUGADOR` — Mano privada (unicast al jugador)

```json
{
  "tipo": "MANO_JUGADOR",
  "fichas": [
    { "numero": 3,    "color": "ROJO",    "esComodin": false },
    { "numero": 7,    "color": "AZUL",    "esComodin": false },
    { "numero": 7,    "color": "NEGRO",   "esComodin": false },
    { "numero": 7,    "color": "AMARILLO","esComodin": false },
    { "numero": 11,   "color": "ROJO",    "esComodin": false },
    { "numero": null, "color": "NEGRO",   "esComodin": true  }
  ]
}
```

---

### `RESULTADO_JUGADA` — Resultado de una jugada (unicast)

```json
// Jugada aceptada
{ "tipo": "RESULTADO_JUGADA", "ok": true, "motivo": null }

// Jugada rechazada
{ "tipo": "RESULTADO_JUGADA", "ok": false, "motivo": "La apertura debe sumar al menos 30 puntos. Tu jugada suma 18." }

// Otros motivos posibles:
// "Cada combinación debe tener al menos 3 fichas."
// "En un grupo todas las fichas deben tener el mismo número."
// "En un grupo no puede haber dos fichas del mismo color."
// "En una escalera todas las fichas deben ser del mismo color."
// "Faltan N ficha(s) para completar la escalera consecutiva."
// "No es tu turno."
// "No tienes la ficha X en tu mano."
```

---

### `TURNO` — Indica quién juega ahora (broadcast)

```json
{ "tipo": "TURNO", "jugador": "Bob" }
```

---

### `FIN_PARTIDA` — El juego terminó (broadcast)

```json
{ "tipo": "FIN_PARTIDA", "ganador": "Alice" }
```

---

### `ERROR` — Error genérico (unicast)

```json
{ "tipo": "ERROR", "mensaje": "No estás en ninguna sala. Envía UNIRSE primero." }
```

---

## 🎯 Colores válidos

| Valor JSON | Descripción |
|------------|-------------|
| `"ROJO"` | Rojo |
| `"AZUL"` | Azul |
| `"NEGRO"` | Negro |
| `"AMARILLO"` | Amarillo |

> [!CAUTION]
> Los colores deben ir en **MAYÚSCULAS** exactamente como aparecen arriba.

---

## 🧪 Flujo completo de prueba (2 jugadores)

```
1. Jugador 1 conecta a ws://localhost:8080/ws/game
2. Jugador 2 conecta a ws://localhost:8080/ws/game

3. Jugador 1 envía: UNIRSE { roomId: "sala-001", jugador: "Alice" }
   ← Ambos reciben: ESTADO_PARTIDA (fase: ESPERANDO, 1 jugador)

4. Jugador 2 envía: UNIRSE { roomId: "sala-001", jugador: "Bob" }
   ← Ambos reciben: ESTADO_PARTIDA (fase: ESPERANDO, 2 jugadores)

5. Jugador 1 envía: INICIAR_PARTIDA { roomId: "sala-001" }
   ← Alice recibe:  MANO_JUGADOR (sus 14 fichas)
   ← Bob recibe:    MANO_JUGADOR (sus 14 fichas)
   ← Ambos reciben: ESTADO_PARTIDA (fase: EN_CURSO)
   ← Ambos reciben: TURNO { jugador: "Alice" }

6. Alice envía: JUGAR_GRUPO (con fichas de su mano que sumen ≥30)
   ← Alice recibe:  RESULTADO_JUGADA { ok: true }
   ← Alice recibe:  MANO_JUGADOR (mano actualizada)
   ← Ambos reciben: ESTADO_PARTIDA (mesa actualizada)
   ← Ambos reciben: TURNO { jugador: "Bob" }

7. Bob envía: ROBAR_FICHA
   ← Bob recibe:    MANO_JUGADOR (con ficha nueva)
   ← Ambos reciben: ESTADO_PARTIDA
   ← Ambos reciben: TURNO { jugador: "Alice" }
```

---

## 🛠 Herramientas para probar

### Opción 1: Postman
1. Nueva pestaña → **WebSocket Request**
2. URL: `ws://localhost:8080/ws/game`
3. Conectar → pegar JSON en el campo de mensaje → Send

### Opción 2: JavaScript (en consola del navegador)
```javascript
const ws = new WebSocket('ws://localhost:8080/ws/game');

ws.onmessage = (e) => console.log('← ', JSON.parse(e.data));

// Enviar mensaje
ws.send(JSON.stringify({
  tipo: "UNIRSE",
  roomId: "sala-001",
  jugador: "Alice"
}));
```

### Opción 3: websocat (CLI)
```bash
websocat ws://localhost:8080/ws/game
# Luego escribir el JSON directamente
```

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
curl -X POST https://rummiqback.onrender.com/api/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"pass123","nombre":"Usuario"}'

# Iniciar sesión
curl -X POST https://rummiqback.onrender.com/api/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"pass123"}'
```

### Testear WebSocket

Usar herramientas como:
- **wscat**: `npm install -g wscat`
- **Postman**: Cliente WebSocket integrado
- **Browser DevTools**: Inspeccionar conexiones

```bash
wscat -c wss://rummiqback.onrender.com/ws/game
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
- ✅ **CORS**: Pendiente por configurar
- ✅ **WebSocket**: Validación de conexiones 

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
```

---

## 📝 Historial de Versiones

| Versión | Fecha | Cambios |
|---------|-------|---------|
| 1.0.0 | Mayo 2026 | Lanzamiento inicial |
| 1.0.1 | Junio 2026 | Implementar para multiplayer |

---

## 📞 Contacto y Soporte

Para problemas técnicos o preguntas sobre el backend, contacta al equipo de desarrollo o deja una **Issue** en el repositorio.

---

**Última actualización**: Junio 2026  
**Versión**: 1.0.1

