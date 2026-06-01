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
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Arquitectura](#arquitectura)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Características Implementadas](#características-implementadas)
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
git clone https://github.com/tuusuario/RummiQ.git
cd RummiQ
```

### 2. Configurar Base de Datos

#### Crear Base de Datos MySQL

```sql
CREATE DATABASE rummiq;
CREATE USER 'rummiq_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON rummiq.* TO 'rummiq_user'@'localhost';
FLUSH PRIVILEGES;
```

#### Ejecutar Scripts de Inicialización

Los scripts SQL se encuentran en `Backend/rummyq-backend/src/main/resources/schema/` y se ejecutarán automáticamente con Spring Boot.

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

Editar `Backend/rummyq-backend/src/main/resources/application.properties`:

```properties
# Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/rummiq
spring.datasource.username=rummiq_user
spring.datasource.password=secure_password
spring.jpa.hibernate.ddl-auto=update

# WebSocket
server.port=8080
spring.application.name=rummiq-backend

# JWT (si aplica)
jwt.secret=your_secret_key_here
jwt.expiration=86400000
```

### Frontend

La aplicación frontend se conecta automáticamente al backend en `ws://rummiqback.onrender.com/ws/game`.

Para cambiar la URL del servidor, editar:
- `Frontend/src/main/java/com/rummyq/view/MainScene.java`

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

El servidor estará disponible en `https://rummiqback.onrender.com`

#### Frontend (Terminal 2)

```bash
cd Frontend
mvn javafx:run
```

La aplicación se abrirá en una ventana de escritorio.

### Opción 2: Con Docker (Próximamente)

```bash
docker-compose up
```

---

## 📁 Estructura del Proyecto

```
RummiQ/
├── Backend/
│   └── rummyq-backend/
│       ├── src/main/java/com/rummyq/backend/
│       │   ├── config/           # Configuraciones de aplicación
│       │   ├── controllers/       # REST endpoints
│       │   ├── models/            # Entidades JPA
│       │   ├── repositories/      # Acceso a datos
│       │   ├── services/          # Lógica de negocio
│       │   └── websocket/         # Manejo de WebSocket
│       ├── src/main/resources/
│       │   ├── application.properties
│       │   └── schema/            # Scripts SQL
│       └── pom.xml
├── Frontend/
│   ├── src/main/java/com/rummyq/
│   │   ├── api/                   # Cliente REST
│   │   ├── core/                  # Lógica central
│   │   ├── features/
│   │   │   ├── gameScene/         # Escena principal de juego
│   │   │   ├── loginScene/        # Pantalla de login
│   │   │   ├── mainScene/         # Menú principal
│   │   │   ├── passwordRecoveryScene/ # Recuperación de contraseña
│   │   │   └── signUp/            # Registro de usuarios
│   │   ├── model/                 # Modelos de datos
│   │   ├── util/                  # Utilidades (hash, etc.)
│   │   ├── view/                  # Vistas principales
│   │   ├── websocket/             # Cliente WebSocket
│   │   └── MainApp.java           # Punto de entrada
│   ├── src/main/resources/assets/ # Recursos gráficos
│   └── pom.xml
├── README.md                      # Este archivo
└── HistoriasDeUsuario_Entrega.md # Documentación de requisitos
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
| **Lombok** | 1.x | Reducción de boilerplate |
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

---

## 📞 Contacto

Para preguntas o sugerencias, contacta al equipo de desarrollo.

---

**Última actualización**: Mayo 2026
**Versión**: 1.0.0

---

## 🔗 Links Útiles

- [Documentación de JavaFX](https://openjfx.io/)
- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Reglas de Rummikub](https://es.wikipedia.org/wiki/Rummikub)
- [Guía de WebSocket en Java](https://www.baeldung.com/websockets-spring)

