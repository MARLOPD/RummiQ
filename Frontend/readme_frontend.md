# 🎮 RummiQ Frontend - Cliente JavaFX

> Aplicación de escritorio **JavaFX 21** para el juego **Rummikub** multijugador con interfaz interactiva y soporte **WebSocket** en tiempo real.

![Java](https://img.shields.io/badge/Java-21-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-21-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.8.0+-blue)
![WebSocket](https://img.shields.io/badge/WebSocket-Client-brightgreen)

---

## 📋 Tabla de Contenidos

- [Descripción](#descripción)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Componentes Principales](#componentes-principales)
- [Escenas (Scenes)](#escenas-scenes)
- [Sistema de Juego](#sistema-de-juego)
- [Comunicación WebSocket](#comunicación-websocket)
- [Ejecutar la Aplicación](#ejecutar-la-aplicación)
- [Customización de UI](#customización-de-ui)

---

## 📝 Descripción

El frontend de RummiQ es una aplicación JavaFX moderna que proporciona:

- **Interfaz Gráfica Intuitiva**: Diseño limpio y responsive
- **Gestión de Usuarios**: Registro, login y recuperación de contraseña
- **Tablero Interactivo**: Drag & drop, visualización en tiempo real
- **Comunicación WebSocket**: Sincronización instantánea con otros jugadores
- **Validación Local**: Feedback inmediato de movimientos inválidos
- **Manejo de Fichas**: Visualización clara con soporte para comodines

---

## 💻 Requisitos

- **Java**: 21 o superior
- **Maven**: 3.8.0 o superior
- **JavaFX SDK**: 21
- **Git**: Para clonar el repositorio

### Requisitos Opcionales
- **IntelliJ IDEA**: Recomendado para desarrollo
- **Scene Builder**: Para editar FXML (si aplica)

---

## 🔧 Instalación

### 1. Clonar el Repositorio

```bash
git clone https://github.com/MARLOPD/RummiQ.git
cd RummiQ/Frontend
```

### 2. Instalar Dependencias

```bash
mvn clean install
```

### 3. Configurar JavaFX (si es necesario)

Si Maven no descarga JavaFX automáticamente:

```bash
# Editar pom.xml y verificar la sección <javafx.version>
mvn dependency:resolve
```

---

## 📁 Estructura del Proyecto

```
. 📂 Frontend
├── 📄 pom.xml
├── 📄 readme_frontend.md
└── 📂 src/
│  └── 📂 main/
│    └── 📂 java/
│      └── 📂 com/
│        └── 📂 rummyq/
│          ├── 📄 MainApp.java
│          └── 📂 api/
│            ├── 📄 AuthService.java
│            ├── 📄 RecoveryPasswordService.java
│            ├── 📄 RegistrationService.java
│          └── 📂 core/
│            ├── 📄 ComponentFactory.java
│            ├── 📄 DialogReglas.java
│            ├── 📄 EnterRoom.java
│            ├── 📄 UIColors.java
│            ├── 📄 WaitingRoom.java
│          └── 📂 features/
│            └── 📂 gameScene/
│              ├── 📄 GameBoard.java
│              ├── 📄 GameSceneActions.java
│              ├── 📄 GameSceneIndividuals.java
│              ├── 📄 GameTiles.java
│              ├── 📄 PlayerPositions.java
│            └── 📂 loginScene/
│              ├── 📄 LoginSceneActions.java
│              ├── 📄 LoginSceneAnimationEffects.java
│              ├── 📄 LoginSceneForms.java
│              ├── 📄 LoginSceneIndividuals.java
│            └── 📂 mainScene/
│              ├── 📄 MainSceneActions.java
│              ├── 📄 MainSceneAnimationEffects.java
│              ├── 📄 MainSceneIndividuals.java
│            └── 📂 passwordRecoveryScene/
│              ├── 📄 RecoverySceneActions.java
│              ├── 📄 RecoverySceneFormInformation.java
│              ├── 📄 RecoverySceneForms.java
│              ├── 📄 RecoverySceneIndividuals.java
│            └── 📂 signUp/
│              ├── 📄 SecurityQuestions.java
│              ├── 📄 SignUpSceneActions.java
│              ├── 📄 SignUpSceneForms.java
│              ├── 📄 SignUpSceneIndividuals.java
│          └── 📂 model/
│            ├── 📄 ScreenConfig.java
│            ├── 📄 SignUpForm.java
│            ├── 📄 User.java
│          └── 📂 util/
│            ├── 📄 HashUtil.java
│          └── 📂 view/
│            ├── 📄 GameScene.java
│            ├── 📄 LoginScene.java
│            ├── 📄 MainScene.java
│            ├── 📄 PasswordRecoveryScene.java
│            ├── 📄 SignUpScene.java
│          └── 📂 websocket/
│            ├── 📄 GameWebScoketHandler.java
│            ├── 📄 GameWebSocketClient.java
│            └── 📂 dto/
│              ├── 📄 GameMessageDTO.java
│              ├── 📄 GameStatusDTO.java
│              ├── 📄 RoomDTO.java
│              ├── 📄 TileDTO.java
│      ├── 📄 module-info.java
│    └── 📂 resources/
│      └── 📂 assets/
│        ├── 📄 ajustesIcon.png
│        ├── 📄 chatIcon.png
│        ├── 📄 exitIcon.png
│        ├── 📄 jugadoresIcon.png
│        ├── 📄 secreteTile.png
│        ├── 📄 wood.png
└── 📂 target/
```

---

## 🏗️ Componentes Principales

### MainApp.java

Punto de entrada principal de la aplicación:

```java
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Inicializar escena de login
        // Configurar dimensiones de ventana
        // Establecer estilos CSS
    }
}
```

**Responsabilidades**:
- Inicialización de la ventana principal
- Gestión del ciclo de vida
- Cambio de escenas

### WebSocket Client

#### GameWebSocketClient.java

Cliente WebSocket para comunicación en tiempo real:

```java
public class GameWebSocketClient {
    public void connect(String uriStr);
    public void startGame();
    public void joinGame();
    public void enviarJugada(List<List<TileDTO>> groups);
    public void passTurn();
}
```

La conexión se inicia en `com.rummyq.view.MainScene.java`:
```java
com.rummyq.websocket.GameWebSocketClient.getInstance().connect("ws://rummiqback.onrender.com/ws/game");
```

**Responsabilidades**:
- Establecer conexión WebSocket
- Enviar mensajes de jugadas
- Recibir actualizaciones del servidor

#### GameWebScoketHandler.java

Procesador de mensajes WebSocket:

---

## 🎮 Escenas (Scenes)

### Flujo de Aplicación

```
LoginScene
    ↓ (autenticación exitosa)
MainScene (menú principal)
    ├─ Crear Sala → WaitingRoom
    ├─ Unirse a Sala → EnterRoom → WaitingRoom
    └─ Salir
    
WaitingRoom
    ├─ Esperando jugadores...
    └─ (2+ jugadores) → GameScene
    
GameScene
    ├─ Tablero interactivo
    ├─ Fichas en mano
    └─ FIN_PARTIDA → MainScene (popup con resultado)
```

### LoginScene

**Componentes**:
- Email TextField
- Password PasswordField
- Botón Login
- Link "¿Olvidó contraseña?"
- Link "Registrarse"

**Validaciones**:
- Email válido
- Contraseña no vacía
- Conexión con servidor

### MainScene (Menú Principal)

**Componentes**:
- Bienvenida al usuario
- Botón "Crear Sala"
- Botón "Unirse a Sala"
- Botón "Ver Reglas"
- Botón "Cerrar Sesión"

**Funcionalidad**:
- Listar salas disponibles
- Mostrar información de perfil

### GameScene (Tablero)

**Componentes**:
- **GameBoard**: Tablero interactivo
- **RackView**: Visualización de fichas en mano
- **PlayersInfo**: Información de jugadores
- **TurnoDisplay**: Indicador de turno actual
- **ChatPanel**: Panel de mensajes (opcional)

**Funcionalidad**:
- Drag & drop de fichas
- Validación local de movimientos
- Visualización de tablero actualizado
- Indicador visual de cambios

---

## 🎯 Sistema de Juego

### GameBoard.java

Gestión del tablero interactivo:

```java
public class GameBoard extends GridPane {
    private Map<String, Tile> tiles;
    private Set<String> currentTurnPlacedKeys;
    
    public void placeTile(Tile tile, int row, int col);
    public void removeTile(String key);
    public void restoreSnapshot();
    public BoardSnapshot takeSnapshot();
}
```

**Responsabilidades**:
- Crear GridPane del tablero
- Manejar drag & drop
- Restaurar movimientos inválidos
- Detectar grupos y escaleras

### GameTiles.java

Visualización de fichas:

```java
public class GameTiles {
    // Colores: ROJO, AZUL, NEGRO, NARANJA
    // Números: 1-13, Comodín (☆)
    
    public StackPane createTile(String color, Integer numero);
    public StackPane createJokerTile();
}
```

**Características**:
- Tiles con color y número
- Visualización de comodines (☆)
- Efectos hover
- Drag & drop visualmente claro

---

## ▶️ Ejecutar la Aplicación

### Opción 1: Maven (Recomendado)

```bash
# Compilar y ejecutar
mvn clean javafx:run

# O en dos pasos
mvn clean package
java -jar target/rummiq-frontend-1.0.0-jfx.jar
```

### Opción 2: IDE

**IntelliJ IDEA**:
1. Hacer click derecho en `MainApp.java`
2. Seleccionar "Run 'MainApp.main()'"
3. O presionar `Shift+F10`

**Eclipse**:
1. Click derecho en el proyecto
2. "Run As" → "Java Application"
3. Seleccionar `MainApp`

### Opción 3: Línea de Comandos

```bash
# Asegurarse que está en directorio Frontend
cd Frontend

# Ejecutar con módulos de JavaFX
java --module-path $JAVAFX_HOME/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes com.rummyq.MainApp
```

---

## 🎨 Customización de UI

### Paleta de Colores

En `UIColors.java`:

```java
public class UIColors {
    public static final Color ROJO = Color.web("#E74C3C");
    public static final Color AZUL = Color.web("#3498DB");
    public static final Color NEGRO = Color.web("#2C3E50");
    public static final Color NARANJA = Color.web("#F39C12");
    
    public static final Color BACKGROUND = Color.web("#1a1a1a");
    public static final Color PANEL = Color.web("#2d2d2d");
    public static final Color TEXT = Color.web("#FFFFFF");
}
```

---

## 🧪 Testing

### Tests Unitarios (Pendiente por implementación)

```bash
mvn test
```

Tests incluyen:
- Validación de fichas
- Lógica del tablero
- Serialización/deserialización JSON
- Conexión WebSocket (mock)

---

## 📊 Performance

### Optimizaciones Implementadas

- ✅ **Lazy Loading**: Escenas se cargan bajo demanda
- ✅ **Caching**: Tiles y componentes reutilizables
- ✅ **Event Batching**: Actualizaciones agrupadas de UI
- ✅ **Memory Management**: Limpieza de recursos

### Moniteo de Performance

```bash
# Ejecutar con profiler
jps -l
jvisualvm
```

---

## 🔧 Troubleshooting

### Error: "JavaFX Runtime Components are missing"

```bash
# Solución: Descargar JavaFX SDK
# https://gluonhq.com/products/javafx/

# Agregar en pom.xml:
<properties>
    <javafx.version>21.0.1</javafx.version>
</properties>
```

### Error: "Cannot connect to WebSocket"

```bash
# Verificar que el servidor está corriendo
curl https://rummiqback.onrender.com/api/health

# Cambiar URL en MainScene.java
com.rummyq.websocket.GameWebSocketClient.getInstance().connect("ws://rummiqback.onrender.com/ws/game");
```

### Error: "Module not found"

```bash
# Ejecutar Maven con limpieza
mvn clean compile

# Si persiste, regenerar IDE project files
mvn clean idea:clean idea:idea
```

---

## 📚 Documentación Adicional

- [README Principal](../README.md)
- [README Backend](../Backend/readme_backend.md)
- [Historias de Usuario](../HistoriasDeUsuario_Entrega.md)

---

## 📝 Historial de Versiones

| Versión | Fecha | Cambios |
|---------|-------|---------|
| 1.0.0 | Mayo 2026 | Lanzamiento inicial |
| 1.0.1 | Junio 2026 | Conexión con la API de multiplayer |  

---

## 📞 Contacto y Soporte

Para problemas técnicos o preguntas sobre el frontend, contacta al equipo de desarrollo o deja una **Issue** en el repositorio.

---

**Última actualización**: Junio 2026  
**Versión**: 1.0.1

