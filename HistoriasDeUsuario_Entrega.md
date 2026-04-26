# Avance de Historias de Usuario - Entrega Actual: RummiQ

Este documento detalla las historias de usuario que han sido implementadas y validadas para la entrega actual del proyecto RummiQ, incluyendo sus respectivos criterios de aceptación.

---

## 1. Gestión de Usuarios y Seguridad

### HU01: Registro de Cuenta
**Como** jugador, **quiero** crear una cuenta en el sistema **para** poder acceder al juego y guardar mi progreso.

*   **Criterios de Aceptación:**
    *   El usuario debe proporcionar un nombre de usuario, correo electrónico y contraseña.
    *   La contraseña debe almacenarse de forma segura utilizando hashing (BCrypt).
    *   El sistema debe validar que el correo electrónico no esté registrado previamente.
    *   Al completar el registro, el usuario debe recibir una confirmación de éxito.

### HU02: Inicio de Sesión
**Como** jugador, **quiero** iniciar sesión con mis credenciales **para** entrar al sistema de forma segura.

*   **Criterios de Aceptación:**
    *   El usuario debe ingresar su correo y contraseña.
    *   El sistema debe verificar la contraseña contra el hash almacenado en la base de datos.
    *   Si las credenciales son correctas, el sistema debe permitir el acceso a las funciones del juego.
    *   Si las credenciales son incorrectas, el sistema debe mostrar un mensaje de error descriptivo (ej. "Contraseña incorrecta").

### HU03: Cierre de Sesión
**Como** jugador, **quiero** cerrar mi sesión **para** proteger mi cuenta y evitar accesos no autorizados.

*   **Criterios de Aceptación:**
    *   El usuario debe poder seleccionar una opción de "Cerrar sesión" desde la interfaz principal.
    *   El sistema debe invalidar la sesión actual del usuario.
    *   El usuario debe ser redirigido a la pantalla de inicio o login.

---

## 2. Gestión de Salas de Juego

### HU04: Crear Sala de Juego
**Como** jugador, **quiero** crear una sala de juego privada **para** invitar a mis amigos y configurar la partida.

*   **Criterios de Aceptación:**
    *   El sistema debe permitir al jugador host definir el nombre de la sala.
    *   La sala debe generarse con un identificador único.
    *   El host debe ser asignado automáticamente a la sala creada.

### HU05: Unirse a Sala Existente
**Como** jugador, **quiero** unirme a una sala ya creada **para** participar en una partida con otros jugadores.

*   **Criterios de Aceptación:**
    *   El jugador debe poder ingresar a una sala mediante su identificador o lista de salas disponibles.
    *   El sistema debe validar que la sala no esté llena (máximo 4 jugadores).
    *   El nombre del jugador debe aparecer en la lista de miembros de la sala en tiempo real.

---

## 3. Mecánicas Iniciales de Partida

### HU06: Recibir Fichas Iniciales
**Como** jugador, **quiero** recibir mis fichas iniciales al comenzar la partida **para** poder empezar a realizar jugadas.

*   **Criterios de Aceptación:**
    *   Al iniciar la partida, el sistema debe repartir automáticamente 14 fichas de forma aleatoria a cada jugador.
    *   Las fichas repartidas deben ser eliminadas del pozo (montón) común.
    *   Cada jugador debe recibir fichas únicas (no duplicadas más allá de lo permitido por el reglamento).

### HU07: Visualización de Rack de Fichas
**Como** jugador, **quiero** ver mi rack de fichas claramente en pantalla **para** organizar mi estrategia de juego.

*   **Criterios de Aceptación:**
    *   La interfaz debe mostrar gráficamente las fichas que el jugador posee actualmente.
    *   Solo el jugador dueño del rack debe poder ver sus fichas (privacidad de mano).
    *   El rack debe actualizarse automáticamente cuando el jugador roba o coloca fichas en el tablero.

---

**Estado de Entrega:** Todas las historias listadas se consideran **Implementadas** y en fase de validación final.
