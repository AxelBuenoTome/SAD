# ChatTextual

#### Primer paso
Programar dos clases MySocket y MyServerSocket que sean funcionalmente equivalentes a las clases de Java Socket y ServerSocket pero que encapsulen excepciones y los correspondientes "streams" de texto BufferedReader y PrintWriter. Estas clases deberán disponer de métodos de lectura/escritura de los tipos básicos.
#### Segundo paso
Programa el cliente como dos threads concurrentes, uno que lea líneas del teclado y las envía al servidor y otro que lea lineas del servidor )enviadas por otro cliente) y las imprima por pantalla. La plantilla es basicamente:

```java
//Input Thread
while ((line = in.readLine()) != null)
    //escribir linea para socket
//Output Thread
while (/*hay linea del servidor*/)
    //escribir por pantalla
```

### Clase `MyServerSocket`:
- Esta clase extiende `ServerSocket`, lo que significa que hereda sus métodos y funcionalidades.
- El constructor de `MyServerSocket` acepta un puerto como argumento y crea un objeto `ServerSocket` en ese puerto.
- El método `accept()` sobrescribe el método `accept()` de `ServerSocket`. Este método espera y acepta una conexión de un cliente y devuelve un objeto `MySocket`, que es una versión personalizada de `Socket`.
- El método `close()` sobrescribe el método `close()` de `ServerSocket`. Este método cierra el `ServerSocket`.

### Clase `MySocket`:
- Esta clase extiende `Socket`
- Tiene dos constructores: uno que acepta un host y un puerto, y otro que acepta un objeto `Socket`.
- El primer constructor crea un objeto `Socket` utilizando el host y el puerto especificados y luego configura los streams de lectura y escritura.
- El segundo constructor toma un objeto `Socket` como argumento y lo utiliza para configurar los streams de lectura y escritura.

### Funcionamiento General:
1. Al iniciar tu aplicación, creas un objeto `MyServerSocket` pasando el puerto en el que quieres que el servidor escuche.
2. El servidor espera y acepta conexiones de clientes utilizando el método `accept()` de `MyServerSocket`. Cuando se acepta una conexión, se crea un objeto `MySocket` que representa la conexión con ese cliente.
3. Los clientes se conectan al servidor utilizando objetos `MySocket`, que utilizan los streams de lectura y escritura para comunicarse con el servidor.
4. El servidor puede leer los mensajes enviados por los clientes utilizando los streams de lectura de los objetos `MySocket`, y puede enviar mensajes a los clientes utilizando los streams de escritura de los objetos `MySocket`.
5. Cuando el servidor ya no necesita estar escuchando conexiones de clientes, puede cerrar el `MyServerSocket` utilizando el método `close()`.

En resumen, has creado una implementación personalizada de un servidor de sockets (`MyServerSocket`) y una implementación personalizada de un socket (`MySocket`). Estas clases te permiten crear un servidor que puede aceptar conexiones de clientes y comunicarse con ellos utilizando tus propias implementaciones de sockets.
