# RummiQ

Objetivos  
1.1 Objetivo general: Desarrollar un programa que simule el funcionamiento y la lógica presentes en el juego Rummi-Q. Mediante la definición e interacción de clases, objetos, métodos y atributos, se diseñará una estructura de software que represente los distintos componentes del juego y permita modelar su dinámica.  
1.2 Objetivos específicos: 
1.2.1 Representar cada elemento del juego mediante clases, garantizando un óptimo funcionamiento entre entidades como fichas, jugadores, jugadas y el tablero.  
1.2.2 Implementar objetos para representar dinámicamente el estado del juego al ejecutar el programa.  
1.2.3 Definir la interacción entre objetos para realizar acciones como repartir fichas, formar grupos y administrar turnos.  

Contexto y justificación  
Los juegos de mesa son una actividad milenaria, que desde hace más de 5000 años han sido cruciales como herramientas de estrategia, representación de creencias y entretenimiento. Desde civilizaciones tan antiguas como la egipcia hasta la realidad actual, es indudable que estos juegos han trascendido generaciones, ideologías, clases sociales e incluso fronteras. Entre reflejar creencias, representar conceptos matemáticos, trabajar la atención y el pensamiento estratégico, enseñar didácticamente o disfrutar del ocio, cada juego de mesa ha representado un momento de la historia de la humanidad que ha sido capaz de marcar generaciones enteras. 	
Uno de estos es Rummikub (conocido en español como Rummi-Q), un juego que nació en un contexto de opresión por parte del régimen comunista y representó la capacidad y fortaleza del ser humano en situaciones de peligro e incertidumbre. En un momento en el que jugar cartas era prohibido, Ephraim Hertzano, un hombre trabajador que vivía en Rumania, ingenió un juego que usa pequeñas fichas, que puede ser jugado por personas de todas las edades y (muy importante para la época) que no impone ninguna barrera de idioma o religión. Después de la guerra, en los años 40’ hizo realidad su visión al mudarse a Israel, desarrollar el juego y publicarlo al mercado.  
Este es un claro ejemplo de la enorme trascendencia, importancia y papel que tienen los juegos de mesa en nuestra sociedad, pues además de entretener, demuestran contextos totalmente distintos, incluso opuestos, que son capaces de desafiar autoridades, fusionar culturas y garantizar un momento de diversión.  
Ahora, la era digital ha abierto un campo totalmente nuevo, innovador, emocionante y desbordante de posibilidades. Desde la creación del primer computador, la invención del World Wide Web, o el actual auge de la Inteligencia Artificial la humanidad ha sido testigo de cambios de tamaño astronómico. Los juegos digitales aparecieron en los años 50´y 60´como experimentos académicos, y al día de hoy hay innumerables consolas, interfaces y programas que representan ese inicio que alguna vez tuvieron los juegos de mesa.  
Utilizando conocimientos de Programación Orientada a Objetos buscaremos simular las complejas interfaces, relaciones y momentos que ocurren al jugar Rummi-Q, afrontando diversos retos que se interpondrán en el camino de representar un orden de pensamiento, reglas y estrategias propia de este juego de mesa.  

Requisitos funcionales:  
  
El sistema debe permitir registrar jugadores con nombre	.  
El sistema debe permitir crear nuevas partidas indicando número de jugadores. (2 a 4 jugadores)  
El sistema debe implementar las reglas oficiales de Rummi-Q y Rummikub.  
El sistema debe repartir fichas iniciales y permitir jugadas como colocar, reorganizar y robar fichas.  
El sistema debe validar automáticamente si las jugadas cumplen las reglas.  
El sistema debe controlar el orden de los turnos y notificar al jugador activo.  
El sistema debe detectar condiciones de victoria (cuando un jugador se queda sin fichas o el jugador con menor cantidad de puntos).  
El sistema debe llevar registro de movimientos realizados durante la partida.  
El sistema debe mostrar las fichas de cada jugador y el tablero.  
El sistema debe permitir partidas en red/local con varios jugadores.  
El sistema debe ofrecer un canal de comunicación entre jugadores durante la partida.  
El sistema debe permitir configurar variantes de reglas.  
El sistema debe incluir un modo tutorial para enseñar a nuevos jugadores cómo jugar.  
El sistema debe generar un ranking de jugadores basado en el desempeño de la partida.  
El sistema debe permitir ajustar parámetros como tiempo máximo por turno.  
El sistema debe permitir crear salas privadas con lista de jugadores invitados y opciones de configuración. (ej. reglas, tiempo por turno)  
El sistema debe registrar estadísticas individuales (victorias, derrotas, promedio de puntos, tiempo de juego).  
El sistema debe permitir jugar contra un oponente controlado por la computadora.  

Requisitos no funcionales:  
  
El sistema debe responder a las acciones del jugador rápidamente. (en menos de 3 segundos)  
La interfaz debe ser intuitiva y fácil de usar.  
El sistema debe proteger los datos de los jugadores. (credenciales, estadísticas)  
El código debe estar documentado y estructurado para facilitar futuras mejoras.  
El sistema debe garantizar que las reglas se apliquen correctamente de acuerdo al modo de juego. (Normal o torneo)  
El sistema debe estar disponible para jugar en cualquier momento sin interrupciones inesperadas.  
El sistema debe ser compatible con diferentes resoluciones de pantalla y dispositivos.  
El sistema debe manejar y corregir errores (ej. jugadas inválidas, desconexiones) sin fallar.  
El sistema debe permitir cambiar el idioma de la interfaz. (ej. español/inglés)  
El sistema debe incluir documentación técnica y manual de usuario.  
El sistema debe estar optimizado para ejecutarse en equipos de gama media/baja.  
El sistema debe mantener estabilidad en sesiones largas de juego. (más de 2 horas continuas)  
El sistema debe manejar múltiples jugadores conectados en red sin errores de sincronización.  
