# 🎮 RummiQ - Juego de Rummikub en Línea

> Una aplicación de juego de **Rummikub** multijugador en tiempo real, con arquitectura cliente-servidor basada en **JavaFX** y **Spring Boot**.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![JavaFX](https://img.shields.io/badge/JavaFX-21-brightgreen)

---

## 📋 Tabla de Contenidos

- [Descripción del Proyecto](#descripción-del-proyecto)
- [Características Principales](#características-principales)
- [Requisitos del Sistema](#requisitos-del-sistema)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Ejecutar el Proyecto](#ejecutar-el-proyecto)
- [Generar Ejecutable (.exe)](#-generar-ejecutable-exe-para-windows)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Arquitectura](#arquitectura)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Características Implementadas](#características-implementadas)
- [Scope del Proyecto](#-scope-del-proyecto)
- [Licencia](#licencia)

---

## 📝 Descripción del Proyecto

**RummiQ** es una plataforma multijugador de **Rummikub** que permite a los jugadores competir en línea en tiempo real. El proyecto implementa la lógica completa del juego, gestión de usuarios, salas de juego dinámicas y comunicación en vivo mediante **WebSockets**.

El proyecto está dividido en dos componentes principales:
- **Backend**: Servidor REST y WebSocket con Spring Boot
- **Frontend**: Aplicación de escritorio con JavaFX 21

---

## ✨ Características Principales

### 🔐 Gestión de Usuarios
- Registro seguro de usuarios con hashing BCrypt
- Autenticación con correo electrónico y contraseña
- Recuperación de contraseña
- Cierre de sesión seguro

### 🎮 Gestión de Salas de Juego
- Crear salas privadas de juego
- Unirse a salas existentes mediante ID
- Soporte para hasta 4 jugadores por sala
- Visualización en tiempo real de miembros activos
- Actualización dinámica del estado de la sala

### 🎯 Mecánica de Juego
- Distribución aleatoria de 14 fichas iniciales por jugador
- Validación inteligente de jugadas (grupos y escaleras)
- Soporte para comodines (Jokers)
- Tablero interactivo con drag & drop
- Restauración automática de movimientos inválidos
- Sincronización de estado del tablero entre jugadores
- Detección de fin de partida y ganador

### 🔄 Comunicación en Tiempo Real
- WebSockets para actualización instantánea del juego
- Sincronización de tablero entre todos los jugadores
- Notificaciones de cambios de turno
- Mensajes de validación de jugadas

---

## 💻 Requisitos del Sistema

### Requisitos Mínimos
- **Java**: 21 o superior
- **Maven**: 3.8.0 o superior
- **Base de Datos**: MySQL 8.0 o superior (Backend)
- **Sistema Operativo**: Windows, macOS o Linux

### Requisitos Adicionales (Opcional)
- **Docker**: Para containerizar la aplicación
- **Git**: Para clonar el repositorio

---

## 🚀 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/MARLOPD/RummiQ.git
cd RummiQ
```

### 2. Configurar Base de Datos

#### Variables de Entorno

La conexión a la base de datos se realiza mediante una connectionString, por ello es necesario tener los valores de las variables de entorno:

RUMMYQ_SUPABASE_USER = {usuario}
RUMMYQ_SUPABASE_URL = {url}
RUMMYQ_SUPABASE_PASSWORD = {password}

### 3. Instalar Dependencias Maven

```bash
# En la carpeta Backend
cd Backend/rummyq-backend
mvn clean install

# En la carpeta Frontend
cd ../../Frontend
mvn clean install
```

---

## ⚙️ Configuración

### Backend

Editar las variables de entorno del sistema:

```properties
# Base de Datos
RUMMYQ_SUPABASE_USER 
RUMMYQ_SUPABASE_URL 
RUMMYQ_SUPABASE_PASSWORD

# WebSocket
server.port=8080
spring.application.name=localhost

Existe una versión desplegada en render:

https://rummiqback.onrender.com

### Frontend

La aplicación frontend se conecta automáticamente al backend en `ws://rummiqback.onrender.com/ws/game`.

```java
com.rummyq.websocket.GameWebSocketClient.getInstance().connect("ws://rummiqback.onrender.com/ws/game");
```

---

## ▶️ Ejecutar el Proyecto

### Opción 1: Con Maven

#### Backend (Terminal 1)

```bash
cd Backend/rummyq-backend
mvn spring-boot:run
```

El servidor estará disponible en `https://localhost:8080`

Si usas la versión desplegada en render, el servidor estará disponible en `https://rummiqback.onrender.com`

#### Frontend (Terminal 2)

```bash
cd Frontend
mvn javafx:run
```

La aplicación se abrirá en una ventana de escritorio.

### Opción 2: Con Docker (Próximamente, no implementado)

```bash
docker-compose up
```

---

## 📦 Generar Ejecutable (.exe) para Windows

Si deseas distribuir la aplicación como un ejecutable nativo de Windows (sin necesidad de tener Java instalado), sigue estos pasos:

### Requisitos Previos

| Herramienta | Versión Mínima | Verificar Instalación |
|-------------|---------------|----------------------|
| **JDK** | 21 o superior | `java --version` |
| **Maven** | 3.8.0 o superior | `mvn --version` |
| **jpackage** | Incluido en JDK 14+ | `jpackage --version` |

> **Nota:** `jpackage` viene incluido con el JDK desde la versión 14. No requiere instalación adicional.

### Paso 1: Compilar el proyecto con Maven

Desde la carpeta `Frontend/`, ejecuta:

```bash
cd Frontend
mvn clean package -DskipTests
```

Esto generará:
- `target/rummyq-1.0-SNAPSHOT.jar` — JAR del proyecto
- `target/rummyq-1.0-SNAPSHOT-all.jar` — Fat JAR con todas las dependencias

### Paso 2: Copiar dependencias

Extrae las dependencias a un directorio separado (necesario para `jpackage` con módulos):

```bash
mvn dependency:copy-dependencies -DoutputDirectory=target/deps
```

### Paso 3: Generar el ejecutable con jpackage

```bash
jpackage --type app-image ^
  --name RummiQ ^
  --module-path "target/classes;target/deps" ^
  --module com.rummyq/com.rummyq.MainApp ^
  --dest target/dist ^
  --vendor "RummiQ" ^
  --app-version "1.0.0" ^
  --description "RummiQ - Juego de Rummikub en linea"
```

> **💡 Tip (PowerShell):** Si usas PowerShell en lugar de CMD, reemplaza los `^` por `` ` `` (backtick) para los saltos de línea.

### Resultado

El ejecutable se generará en:

```
Frontend/target/dist/RummiQ/
├── RummiQ.exe          ← Lanzador nativo de Windows
├── app/                ← JARs de la aplicación y dependencias
└── runtime/            ← JRE embebido (no requiere Java instalado)
```

### Ejecutar

Simplemente haz doble clic en `RummiQ.exe` o ejecútalo desde la terminal:

```bash
target\dist\RummiQ\RummiQ.exe
```

### Distribuir

Para compartir la aplicación, comprime toda la carpeta `RummiQ/` en un ZIP:

```bash
# PowerShell
Compress-Archive -Path "target\dist\RummiQ" -DestinationPath "RummiQ-v1.0.0-windows.zip"
```

El archivo ZIP resultante (~133 MB) es completamente portátil y autocontenido — el usuario final **no necesita tener Java instalado**.

### Generar Instalador (Opcional)

Si prefieres generar un instalador `.exe` (con asistente de instalación/desinstalación), necesitas instalar [WiX Toolset](https://wixtoolset.org/) y cambiar el tipo:

```bash
jpackage --type exe ^
  --name RummiQ ^
  --module-path "target/classes;target/deps" ^
  --module com.rummyq/com.rummyq.MainApp ^
  --dest target/installer ^
  --vendor "RummiQ" ^
  --app-version "1.0.0" ^
  --win-menu --win-shortcut
```

---

## 📁 Estructura del Proyecto

```
. 📂 RummiQ
└── 📂 Backend/
│  ├── 📄 readme_backend.md
│  └── 📂 rummyq-backend/
│    ├── 📄 Dockerfile
│    ├── 📄 pom.xml
│    └── 📂 src/
│      └── 📂 main/
│        └── 📂 java/
│          └── 📂 com/
│            └── 📂 rummyq/
│              └── 📂 backend/
│                ├── 📄 BackendApplication.java
│                └── 📂 config/
│                  ├── 📄 BCryptEncryption.java
│                  ├── 📄 DatabaseConnection.java
│                └── 📂 controllers/
│                  ├── 📄 AuthController.java
│                  ├── 📄 ChatController.java
│                  ├── 📄 GameController.java
│                  ├── 📄 HealthController.java
│                └── 📂 models/
│                  ├── 📄 Ficha.java
│                  ├── 📄 Jugador.java
│                  ├── 📄 LoginRequirements.java
│                  ├── 📄 Mensaje.java
│                  ├── 📄 MensajeDTO.java
│                  ├── 📄 Partida.java
│                  ├── 📄 PartidaDTO.java
│                  ├── 📄 RegistrationRequirements.java
│                  ├── 📄 StartRequest.java
│                └── 📂 repositories/
│                  ├── 📄 ChatRepository.java
│                  ├── 📄 RecoveryPasswordRepository.java
│                  ├── 📄 UserRepository.java
│                └── 📂 services/
│                  ├── 📄 ChatService.java
│                  ├── 📄 GameSessionManager.java
│                  ├── 📄 JugadorService.java
│                  ├── 📄 PartidaMapper.java
│                  ├── 📄 PartidaService.java
│                  ├── 📄 PasswordRecovery.java
│                  ├── 📄 TileBag.java
│                  ├── 📄 UserManagment.java
│                  ├── 📄 ValidateGame.java
│                └── 📂 websocket/
│                  ├── 📄 GameMessage.java
│                  ├── 📄 GameStatus.java
│                  └── 📂 config/
│                    ├── 📄 WebSocketConfig.java
│                  └── 📂 handlers/
│                    ├── 📄 GameWebSocketHandler.java
│    └── 📂 target/
└── 📂 Frontend/
│  ├── 📄 pom.xml
│  ├── 📄 readme_frontend.md
│  └── 📂 src/
│    └── 📂 main/
│      └── 📂 java/
│        └── 📂 com/
│          └── 📂 rummyq/
│            ├── 📄 MainApp.java
│            └── 📂 api/
│              ├── 📄 AuthService.java
│              ├── 📄 RecoveryPasswordService.java
│              ├── 📄 RegistrationService.java
│            └── 📂 core/
│              ├── 📄 ComponentFactory.java
│              ├── 📄 DialogReglas.java
│              ├── 📄 EnterRoom.java
│              ├── 📄 UIColors.java
│              ├── 📄 WaitingRoom.java
│            └── 📂 features/
│              └── 📂 gameScene/
│                ├── 📄 GameBoard.java
│                ├── 📄 GameSceneActions.java
│                ├── 📄 GameSceneIndividuals.java
│                ├── 📄 GameTiles.java
│                ├── 📄 PlayerPositions.java
│              └── 📂 loginScene/
│                ├── 📄 LoginSceneActions.java
│                ├── 📄 LoginSceneAnimationEffects.java
│                ├── 📄 LoginSceneForms.java
│                ├── 📄 LoginSceneIndividuals.java
│              └── 📂 mainScene/
│                ├── 📄 MainSceneActions.java
│                ├── 📄 MainSceneAnimationEffects.java
│                ├── 📄 MainSceneIndividuals.java
│              └── 📂 passwordRecoveryScene/
│                ├── 📄 RecoverySceneActions.java
│                ├── 📄 RecoverySceneFormInformation.java
│                ├── 📄 RecoverySceneForms.java
│                ├── 📄 RecoverySceneIndividuals.java
│              └── 📂 signUp/
│                ├── 📄 SecurityQuestions.java
│                ├── 📄 SignUpSceneActions.java
│                ├── 📄 SignUpSceneForms.java
│                ├── 📄 SignUpSceneIndividuals.java
│            └── 📂 model/
│              ├── 📄 ScreenConfig.java
│              ├── 📄 SignUpForm.java
│              ├── 📄 User.java
│            └── 📂 util/
│              ├── 📄 HashUtil.java
│            └── 📂 view/
│              ├── 📄 GameScene.java
│              ├── 📄 LoginScene.java
│              ├── 📄 MainScene.java
│              ├── 📄 PasswordRecoveryScene.java
│              ├── 📄 SignUpScene.java
│            └── 📂 websocket/
│              ├── 📄 GameWebScoketHandler.java
│              ├── 📄 GameWebSocketClient.java
│              └── 📂 dto/
│                ├── 📄 GameMessageDTO.java
│                ├── 📄 GameStatusDTO.java
│                ├── 📄 RoomDTO.java
│                ├── 📄 TileDTO.java
│        ├── 📄 module-info.java
│      └── 📂 resources/
│        └── 📂 assets/
│          ├── 📄 ajustesIcon.png
│          ├── 📄 chatIcon.png
│          ├── 📄 exitIcon.png
│          ├── 📄 jugadoresIcon.png
│          ├── 📄 secreteTile.png
│          ├── 📄 wood.png
│  └── 📂 target/
├── 📄 HistoriasDeUsuario_Entrega.md
├── 📄 LICENSE
└── 📄 README.md
```

---

## 🏗️ Arquitectura

### Arquitectura General

```
┌─────────────────────────────────────┐
│       Cliente JavaFX (Frontend)     │
│  - Interfaz gráfica interactiva     │
│  - Gestión de estados locales       │
│  - Validación de UI                 │
└──────────────┬──────────────────────┘
               │ WebSocket / REST
               │
┌──────────────▼──────────────────────┐
│  Servidor Spring Boot (Backend)     │
│  - WebSocket Handler                │
│  - Lógica de validación de juego    │
│  - Gestión de salas                 │
│  - Sincronización de estado         │
└──────────────┬──────────────────────┘
               │ JDBC
               │
┌──────────────▼──────────────────────┐
│       Base de Datos MySQL           │
│  - Usuarios                         │
│  - Salas de juego                   │
│  - Historial de partidas            │
└─────────────────────────────────────┘
```

### Flujo de Comunicación

1. **Conexión Inicial**:
   - Cliente se conecta al servidor WebSocket
   - Se envía token de autenticación

2. **Durante la Partida**:
   - Cliente envía movimientos (JSON)
   - Backend valida la jugada
   - Servidor sincroniza tablero a todos los jugadores
   - Cliente actualiza UI en tiempo real

3. **Fin de Partida**:
   - Servidor detecta ganador
   - Envía mensaje `FIN_PARTIDA` a todos
   - Clientes muestran resultado y vuelven al menú

---

## 🛠️ Tecnologías Utilizadas

### Backend
| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| **Java** | 21 | Lenguaje principal |
| **Spring Boot** | 3.x | Framework web y REST |
| **Spring WebSocket** | 3.x | Comunicación en tiempo real |
| **Spring Data JPA** | 3.x | Acceso a datos |
| **MySQL** | 8.0+ | Base de datos |
| **Maven** | 3.8.0+ | Gestor de dependencias |
| **Jackson** | 2.x | Serialización JSON |

### Frontend
| Tecnología | Versión | Propósito |
|-----------|---------|----------|
| **Java** | 21 | Lenguaje principal |
| **JavaFX** | 21 | Framework de UI |
| **Maven** | 3.8.0+ | Gestor de dependencias |
| **HttpClient** | Java 21 | Cliente HTTP |
| **WebSocket** | Java 21 | Cliente WebSocket |

---

## ✅ Características Implementadas

### Gestión de Usuarios
- ✅ Registro de usuarios con validación de email
- ✅ Autenticación segura con hashing BCrypt
- ✅ Inicio de sesión
- ✅ Cierre de sesión
- ✅ Recuperación de contraseña

### Gestión de Salas
- ✅ Crear salas de juego privadas
- ✅ Unirse a salas con ID único
- ✅ Límite de 4 jugadores por sala
- ✅ Visualización en tiempo real de jugadores
- ✅ Inicio automático de partida

### Mecánica de Juego
- ✅ Reparto de 14 fichas iniciales
- ✅ Tablero interactivo con drag & drop
- ✅ Validación de grupos y escaleras
- ✅ Soporte para comodines (Jokers)
- ✅ Sincronización de tablero entre jugadores
- ✅ Restauración de movimientos inválidos
- ✅ Sistema de turnos
- ✅ Detección de ganador
- ✅ Popup de fin de partida

### Comunicación
- ✅ WebSockets para actualización en tiempo real
- ✅ Sincronización de estado del juego
- ✅ Notificaciones de cambios de turno
- ✅ Manejo de desconexiones

---

## 🔭 Scope del Proyecto

### 🐛 Bugs Conocidos

| # | Severidad | Descripción | Ubicación |
|---|-----------|-------------|----------|
| 1 | 🟡 Media | **Logs de debug en producción**: Múltiples `System.out.println("[DEBUG]...")` dejados en el código de producción que generan ruido en consola | `GameBoard.java`, `GameSceneIndividuals.java` |
| 2 | 🟡 Media | **Reconexión WebSocket no implementada**: Si la conexión WebSocket se pierde durante una partida, no hay mecanismo de reconexión automática | `GameWebSocketClient.java` |
| 3 | 🟢 Baja | **Typo en nombre de clase**: `GameWebScoketHandler.java` debería ser `GameWebSocketHandler.java` | `websocket/` |
| 4 | 🟢 Baja | **Llamada doble a `initTileContainers()`**: En `createPlayerBoard()` se llama `initTileContainers()` → `resetTileContainers()` → `initTileContainers()`, lo cual es redundante | `GameSceneIndividuals.java` |

### 🔧 Deuda Técnica / Refactoring Pendiente

#### Frontend
- **Refactoring de componentes UI**: Varias clases del frontend (`GameBoard.java` ~684 líneas, `GameSceneIndividuals.java` ~416 líneas) son demasiado extensas y mezclan lógica de negocio con lógica de presentación. Se recomienda:
  - Separar la lógica de drag-and-drop en una clase dedicada (ej. `DragDropManager`)
  - Extraer la gestión de snapshot/restore del tablero a un servicio independiente
  - Eliminar el uso excesivo de campos `static` en `GameSceneIndividuals` que dificultan el testing y pueden causar bugs de estado compartido
- **Patrón Singleton con estado estático**: `GameBoard` usa `instance` estático junto con `draggedTile` y `draggedSourceKey` estáticos, lo que dificulta la testabilidad y puede causar problemas si se instancian múltiples tableros
- **URL del servidor hardcodeada**: La URL de conexión WebSocket (`wss://rummiqback.onrender.com/ws/game`) está hardcodeada en `MainScene.java`. Debería extraerse a un archivo de configuración
- **Sin CSS externo**: Todos los estilos están inline en el código Java. Migrar a archivos `.css` mejoraría la mantenibilidad
- **Sin tests unitarios**: No hay tests implementados para el frontend

#### Backend
- **CORS pendiente por configurar**: La configuración de CORS no está implementada correctamente para producción
- **Sin tests unitarios**: No hay tests implementados para los servicios del backend

### 📋 Funcionalidades Pendientes

- [ ] **Chat en juego**: La UI tiene el ícono de chat pero la funcionalidad no está conectada en la vista del juego
- [ ] **Historial de partidas**: No se registra ni muestra el historial de partidas jugadas
- [ ] **Sistema de puntuación persistente**: Los puntos se calculan por partida pero no se acumulan
- [ ] **Despliegue con Docker**: El `Dockerfile` del backend existe pero no hay `docker-compose.yml` funcional para el proyecto completo
- [ ] **Configuración de CORS** para producción
- [ ] **Tests unitarios y de integración** (Frontend y Backend)

### 🚀 Mejoras Futuras

- **Reconexión automática de WebSocket** con backoff exponencial
- **Sistema de invitaciones** para salas de juego
- **Modo espectador** para observar partidas en curso
- **Animaciones de fichas** más fluidas con transiciones JavaFX
- **Soporte multiplataforma** del ejecutable (macOS, Linux)
- **Internacionalización (i18n)** para soporte en inglés y español

---

## 📚 Documentación Adicional

- [Historias de Usuario](HistoriasDeUsuario_Entrega.md) - Requisitos detallados del proyecto
- [README Backend](Backend/readme_backend.md) - Detalles técnicos del servidor
- [README Frontend](Frontend/readme_frontend.md) - Detalles técnicos de la interfaz

---

## 🐛 Reporte de Bugs

Si encuentras un bug, por favor crea un issue con:
1. Descripción clara del problema
2. Pasos para reproducir
3. Comportamiento esperado vs actual
4. Información del sistema (OS, Java version, etc.)

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Para cambios mayores:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 👨‍💻 Equipo de Desarrollo

- **Desarrollo Backend**: Spring Boot + MySQL
- **Desarrollo Frontend**: JavaFX + WebSocket Client
- **Arquitectura**: Microservicios en tiempo real

- Geana Marcela Duarte López
- Daniela Chamorro Chaves
- Cristian Eduardo Jimenez Trujillo


---

## 📞 Contacto

Para preguntas o sugerencias, contacta al equipo de desarrollo.

---

**Última actualización**: Junio 2026
**Versión**: 1.0.1

---

## 🔗 Links Útiles

- [Documentación de JavaFX](https://openjfx.io/)
- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Reglas de Rummikub](https://es.wikipedia.org/wiki/Rummikub)
- [Guía de WebSocket en Java](https://www.baeldung.com/websockets-spring)
- [Instructivo de instalación y ejecución](https://github.com/MARLOPD/RummiQ/releases)

