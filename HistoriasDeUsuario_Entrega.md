Sistema de Usuario
Como jugador quiero crear una cuenta para acceder al juego y guardar mi progreso.
Criterios de aceptación: 
El usuario debe proporcionar un nombre de usuario, correo electrónico y contraseña.
La contraseña debe almacenarse de forma segura utilizando hashing (BCrypt).
El sistema debe validar que el correo electrónico no esté registrado previamente.
Al completar el registro, el usuario debe recibir una confirmación de éxito.
Como jugador quiero iniciar sesión con mis credenciales para entrar al sistema de forma segura.
Criterios de aceptación: 
El usuario debe ingresar su correo y contraseña.
El sistema debe verificar la contraseña contra el hash almacenado en la base de datos.
Si las credenciales son correctas, el sistema debe permitir el acceso a las funciones del juego.
Si las credenciales son incorrectas, el sistema debe mostrar un mensaje de error descriptivo (por ejemplo:  "Contraseña incorrecta").
Como jugador quiero cerrar mi sesión para proteger mi cuenta y evitar accesos no autorizados.
Criterios de aceptación: 
El usuario debe poder seleccionar una opción de "Cerrar sesión" desde la interfaz principal.
El sistema debe invalidar la sesión actual del usuario.
El usuario debe ser redirigido a la pantalla de inicio o login.
Lobby y multijugador
Como jugador quiero crear una sala de juego privada para invitar a mis amigos y configurar la partida.
Criterios de aceptación: 
El sistema debe permitir al jugador host definir el nombre de la sala.
La sala debe generarse con un identificador único (UUID) que pueda ser compartido.
El host debe ser asignado automáticamente a la sala creada y aparecer en la lista de jugadores.
El sistema debe mostrar el ID de la sala de forma visible con opción de copiarlo al portapapeles.
Como jugador quiero unirme a una sala ya creada para participar en una partida con otros jugadores.
Criterios de aceptación: 
El jugador debe poder ingresar a una sala mediante su identificador único.
El sistema debe validar que la sala no esté llena (máximo 4 jugadores).
El sistema debe mostrar un mensaje de error si la sala ya inició o está llena.
El nombre del jugador debe aparecer en la lista de miembros de la sala en tiempo real vía WebSocket.
Como jugador quiero ver a los demás jugadores dentro de la sala para saber quién participará en la partida.
Criterios de aceptación: 
La sala debe mostrar en tiempo real la lista de jugadores conectados.
Cada jugador debe ser identificado por su nombre de usuario.
La lista debe actualizarse automáticamente cuando un jugador se une o se desconecta.
El sistema debe indicar cuántos jugadores están en la sala y cuántos faltan para poder iniciar.
Inicio de Partida
Como jugador quiero recibir mis fichas iniciales al comenzar la partida para poder empezar a realizar jugadas.
Criterios de aceptación: 
Al iniciar la partida, el sistema debe repartir automáticamente 14 fichas de forma aleatoria a cada jugador.
Las fichas repartidas deben ser eliminadas de la bolsa común.
Cada jugador debe recibir fichas únicas conforme al reglamento (2 series de 1–13 por color más 2 comodines).
El sistema debe requerir al menos 2 jugadores conectados para poder iniciar la partida.
Como jugador quiero ver mi rack de fichas claramente en pantalla para organizar mi estrategia de juego.
Criterios de aceptación: 
La interfaz debe mostrar gráficamente las fichas que el jugador posee actualmente.
Solo el jugador dueño del rack debe poder ver sus fichas (privacidad de mano), enviadas de forma individual por WebSocket.
El rack debe actualizarse automáticamente cuando el jugador roba o coloca fichas en el tablero.
Cada ficha debe mostrar su número y color de forma legible.
Como jugador quiero ver el tablero central para conocer las combinaciones que han sido jugadas.
Criterios de aceptación: 
El tablero debe mostrar todos los grupos y escaleras colocados por los jugadores.
El tablero debe actualizarse en tiempo real para todos los jugadores vía WebSocket.
Cada combinación en el tablero debe mostrar sus fichas con número y color.
El estado del tablero debe ser consistente y sincronizado entre todos los jugadores conectados.
Sistema de turnos
Como jugador quiero saber cuándo es mi turno para poder realizar mis jugadas oportunamente.
Criterios de aceptación: 
El sistema debe enviar una notificación WebSocket de tipo TURNO a todos los jugadores indicando el nombre del jugador activo.
La interfaz debe resaltar visualmente cuando es el turno del jugador.
El jugador debe poder ver el nombre del jugador que tiene el turno en todo momento.
El indicador de turno debe actualizarse automáticamente al pasar al siguiente jugador.

Como jugador quiero que el turno pase automáticamente al siguiente jugador para mantener el flujo del juego.
Criterios de aceptación: 
El sistema debe pasar el turno al siguiente jugador después de que el jugador actual realice una jugada válida, robe una ficha, o pase el turno.
El orden de turnos debe seguir el orden en que los jugadores se unieron a la sala.
El turno debe ser circular: después del último jugador vuelve al primero.
Si un jugador se desconecta durante la partida, su turno debe omitirse automáticamente.
Como jugador quiero robar una ficha de la bolsa cuando no pueda realizar una jugada para continuar con el juego.
Criterios de aceptación: 
El jugador solo puede robar una ficha si es su turno.
La ficha robada debe ser extraída de la bolsa y añadida al rack del jugador.
Tras robar, el turno debe pasar automáticamente al siguiente jugador.
Si la bolsa está vacía, el sistema debe informar al jugador que no hay fichas disponibles.
Colocación de fichas
Como jugador quiero arrastrar fichas desde mi rack al tablero para colocarlas en la partida.
Criterios de aceptación: 
El jugador debe poder seleccionar fichas de su rack y enviarlas al tablero.
Solo el jugador con el turno activo puede colocar fichas.
El sistema debe verificar que el jugador posee las fichas que intenta colocar.
Si el jugador no tiene la ficha seleccionada, el sistema debe mostrar un mensaje de error.
Como jugador quiero crear combinaciones de fichas para jugarlas en el tablero y avanzar en la partida.
Criterios de aceptación: 
El jugador debe poder agrupar fichas en conjuntos (grupos del mismo número, distinto color) o escaleras (mismo color, números consecutivos).
El sistema debe validar automáticamente si la combinación cumple las reglas antes de confirmarla.
Para la apertura, la suma de puntos de las combinaciones jugadas debe ser igual o mayor a 30.
Un comodín puede sustituir cualquier ficha en una combinación.
Validación de reglas
Como jugador quiero que el sistema valide automáticamente mis jugadas para asegurarme de cumplir las reglas.
Criterios de aceptación: 
El sistema debe validar cada jugada antes de confirmarla en el tablero.
Una jugada válida debe contener al menos una combinación con un mínimo de 3 fichas.
El sistema debe distinguir entre jugadas de apertura y jugadas normales aplicando reglas distintas.
El resultado de la validación debe ser comunicado al jugador con un mensaje claro.
Como jugador quiero recibir un mensaje automático cuando mi jugada es inválida para saber que fue rechazada.  
Criterios de aceptación: 
El sistema debe enviar un mensaje de error al jugador cuando su jugada no cumple las reglas.
El mensaje debe indicar que la jugada fue rechazada, aunque sin detallar el motivo específico del fallo.
Las fichas rechazadas deben permanecer en el rack del jugador sin modificar el estado del tablero.
El turno del jugador no debe avanzar si la jugada fue rechazada.
Como jugador quiero que el sistema detecte automáticamente escaleras y grupos válidos para facilitar el juego.
Criterios de aceptación:  
Un grupo válido debe tener entre 3 y 4 fichas del mismo número con colores distintos.
Una escalera válida debe tener mínimo 3 fichas del mismo color con números consecutivos.
Un comodín puede usarse en cualquier posición dentro de un grupo o escalera.
El sistema debe rechazar combinaciones que no cumplan ninguno de estos dos criterios.
Comunicación en partida
7.1 	Como jugador quiero enviar mensajes durante la partida para comunicarme    con ellos.
Criterios de aceptación: 
El sistema debe permitir al jugador enviar mensajes de texto a todos los participantes de la sala.
Cada mensaje debe registrar el identificador del jugador que lo envió y la hora de envío.
El sistema debe rechazar mensajes con contenido vacío o nulo.
Los mensajes deben ser almacenados en la base de datos y recuperables durante la partida.
7.2	Como jugador quiero ver los mensajes enviados por otros jugadores para seguir la conversación durante la partida.
Criterios de aceptación: 
El jugador debe poder consultar el historial de mensajes de la sala en cualquier momento.
Los mensajes deben estar ordenados cronológicamente.
El historial debe ser accesible mediante una petición GET al endpoint de chat de la partida.
Los mensajes deben mostrarse con el identificador del remitente y el contenido de forma legible.
Manipulación avanzada del tablero
Como jugador quiero reorganizar las fichas del tablero para crear nuevas combinaciones aprovechando las fichas existentes.
Criterios de aceptación: 
El jugador debe poder mover fichas entre combinaciones existentes en el tablero durante su turno.
Al finalizar el turno, el estado resultante del tablero debe ser válido según las reglas del juego.
Si la reorganización resulta en un estado inválido, el sistema debe revertir el tablero a su estado anterior.
El tablero reorganizado debe sincronizarse con todos los jugadores conectados.
Como jugador quiero mover combinaciones completas dentro del tablero para facilitar la reorganización estratégica.
Criterios de aceptación: 
El jugador debe poder reubicar grupos completos dentro del espacio del tablero.
Los movimientos de combinaciones deben validarse para garantizar que el tablero permanezca en estado válido.
Los cambios deben reflejarse en tiempo real para todos los jugadores.
El sistema debe actualizar el estado interno del tablero tras cada movimiento confirmado.
Fin de partida
Como jugador quiero que el sistema detecte automáticamente cuando alguien gana para finalizar la partida correctamente.
Criterios de aceptación: 
El sistema debe detectar automáticamente cuando un jugador coloca su última ficha en el tablero.
Al detectar un ganador, el sistema debe enviar un mensaje WebSocket de tipo “Fin de partida” a todos los jugadores.
La partida debe marcarse como terminada e impedir que se realicen más jugadas.
El nombre del ganador debe ser comunicado claramente a todos los jugadores.
Como jugador quiero ver los puntajes finales de todos los jugadores al terminar la partida para conocer los resultados.
Criterios de aceptación: 
Al finalizar la partida, el sistema debe calcular los puntos de cada jugador sumando el valor de las fichas restantes en su rack.
Los comodines deben contabilizarse como 13 puntos cada uno en el cálculo final.
El sistema debe mostrar una pantalla de resultados con el ranking de jugadores.
El jugador con menor puntaje debe ser destacado como segundo lugar si no fue el ganador directo.
Mejoras finales
Como jugador quiero ver animaciones básicas del juego para una experiencia más fluida y atractiva.
Criterios de aceptación: 
El sistema debe incluir animaciones al colocar fichas en el tablero.
Las transiciones entre pantallas deben ser suaves y no bloquear la interacción del usuario.
Las animaciones no deben afectar el rendimiento del sistema ni causar demoras en las jugadas.
Las animaciones deben ser visibles en distintas resoluciones de pantalla.
Como jugador quiero recibir mensajes de error claros y descriptivos para entender qué salió mal y cómo corregirlo.
Criterios de aceptación: 
El sistema debe mostrar mensajes de error para cada tipo de fallo (jugada inválida, sala llena, credenciales incorrectas, etc.).
Los mensajes de error no deben interrumpir el flujo del juego; deben mostrarse de forma no intrusiva.
Los mensajes deben estar redactados de forma comprensible para el jugador, sin terminología técnica.
El sistema debe manejar errores de conexión o desconexión sin que la aplicación falle completamente.
Como jugador quiero poder volver al lobby al terminar la partida para iniciar una nueva o unirme a otra sala.
Criterios de aceptación: 
Al finalizar la partida, el sistema debe mostrar una opción clara para volver al lobby.
Al volver al lobby, la sesión de la partida anterior debe cerrarse correctamente.
El jugador debe poder crear una nueva sala o unirse a una existente desde el lobby.
El estado del jugador debe resetearse al salir de la partida para no arrastrar datos de la sesión anterior.

