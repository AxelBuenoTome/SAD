# Cambio de Modo Raw a Cooked en Java

## Con la Clase Runtime

Usamos el método `exec` de la clase `Runtime` para ejecutar un comando que cambia el modo de la terminal de "raw" a "cooked". Aquí está la línea clave para crear el comando:


Con esta clase usaremos el método exec. Que ejecutará un comando. Este “comannd”, no es más que un vector de Strings que será lo que el programa introducirá en el terminal.
Esta es la línea buena para crear un comando: 
```java
String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
```
En primer lugar, se hace una llamada a “/bin/sh”. Esto lo que indica al sistema es que estamos ejecutando un Shell. El “-c” indica que la cadena que vamos a introducir es un comando para ejecutar. Tal vez en algún sistema de Linux, esto funcione, pero desde Windows a pesar de usar el Terminal de Linux con WSL, no funciona. 
Otro punto importante es el “```java </dev/tty```”, es una redirección de la entrada stdin, que representa el Terminal actual del usuario, muy importante esto último. Sin esto el programa no detecta que este sea el terminal sobre el que ejecutar el terminal, y el funcionamiento del programa puede no ser el esperado si queremos interactuar con dicho terminal.
El stty -echo raw es lo que nos permite pasar a modo raw. El “-echo” lo único que hace es que el texto introducido no se muestre por pantalla. Aunque al quitarlo tampoco veo nada por el terminal.
Este es el método exec, que ejecuta el comando. Tenemos que poner la IOException. Es una Checked Exception, por lo que debe ser tratada inmediatamente. 

## Con la Clase ProcessBuilder
En este enfoque, utilizamos la clase ProcessBuilder para crear y ejecutar el proceso. Además, se redirigen los flujos estándar al proceso de Java que lo invoca. Aquí hay un resumen del código:

```java
Copy code
ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "stty -echo raw < /dev/tty");
pb.inheritIO(); // Redirige entrada/salida/errores al proceso Java que lo invoca
pb.start().waitFor(); // Ejecuta el proceso y espera a que termine
```

Este enfoque permite heredar los flujos de datos de manera sencilla y crear una base para ejecutar varios comandos con la misma estructura.
Es importante señalar que, aunque este enfoque puede funcionar en sistemas Linux/Unix, puede no ser completamente compatible con Windows, incluso al usar el Terminal de Linux con WSL.

