# Reproductor de música
## Descripción
Para comenzar con este proyecto se ha decidido implementarlo utilizando el modelo MVC con Observers, Observables. 

### Model
Desde esta parte manejaremos los datos y la lógica de la aplicación, tales como la lista de reproducción, la reproducción (play), la pausa, ...

El modelo contendrá la lógica de la aplicación y contendrá el estado de reproducción, pausa, progreso de la canción, etc.
Al parsear los archivos de audio, podríamos almacenar más datos relevantes como el título, el artista, la duración, etc. Podríamos guardarlos como objetos 'Canción' y crear una lista de estas canciones con toda esta información.

NOTA: Tal vez este sea un buen punto de partida para la Fase 1, a trabajar de forma paralela con el play

### View
La parte de 'View' será la interfaz de usuario. Con posibilidad de una interfaz gráfica elaborada con alguna librería, o con el terminal de Linux.

Deberemos utilizar métodos de impresión y formateo de texto para mostrar la información por el terminal.

### Controller
Este actuará como intermediario entre el `View' y el 'Model'. Manejará las interacciones del usuario y actualizará la vista para reflejar los cambios en el modelo. 

Para leer archivos de una carpeta podemos utilizar métodos de entrada y salida de arvhicos de Java para obtener una lista de arvhivos en una carpeta con una ruta específica.

### Parsing
Para poder mostrar la información de las canciones y el progreso, deberemos hacer 'parsing' sobre estas. Necesitaremos analizar los archivos de audio para obtener la información relevante (y guardarla en el objeto 'Canción')

### Play 
Utilizaremos la clase 'ProcessBuilder' o la alternativa 'Runtime' para ejecutar comandos externos como el Play para reproducir archivos de audio. (Importante manejar correctamente las excepciones)

## Fases del proyecto
Con tal de avanzar progresivamente, vamos a plantear el proyecto de forma escalonada.
Además, gracias al modelo MVC podremos considerar la sustitución de alguna de estas clases por otra más adelante (como podría ser el caso de una interfaz gráfica con la biblioteca Swing en lugar del terminal)

### Fase 1 (ReproductorV1)
El objetivo de esta primera fase será trabajar los métodos que nos permitirán:
- Leer las carpetas y crear una lista de canciones
- Reproducir una canción
Para ello crearemos una clase que contendrá el 'main' y nos permitirá escribir manualmente la canción que queremos reproducir de nuestro listado de canciones.
Por otro lado, crearemos el 'Model', y trabajaremos el "cómo" llamar al método play para poder reproducir arvhivos de audio (Tendremos en cuenta las limitaciones de formato)

### Fase 2 (ReproductorV2)
El objetivo de esta segunda fase será la implementación de la interfaz gráfica y el controlador para poder interactuar que nos permitrá:
- Visualizar el listado de canciones por el terminal
- Desplazarnos con las flechas entre las canciones y pulsar 'Enter' para reproducirlas
- Utilizar atajos de teclado para reproducir canciones
Para ello crearemos la clase 'Controller' y la clase 'View'
Utilizaremos los notifyObservers para comunicarnos con la Vista y solicitar los cambios al interactuar con el teclado.

### Fase 3 (ReproductorV3)
En esta sección nos adentraremos en el aspecto más estético y en mejorar funcionalidades
- Crearemos una barra de progreso (aplicando el parsing a las canciones)
- Permitiremos la interacción del usuario con el ratón
- Mejoraremos la interfaz

NOTA: Es importante tener en cuenta que el reproductor debe seguir funcionando así como la barra de progreso mientras nos desplazamos por la interfaz. 