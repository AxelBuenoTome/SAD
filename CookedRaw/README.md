# CookedRaw

Este es un programa simple en Java diseñado para cambiar entre el modo "cooked" y "raw" en el terminal de Linux y verificar su funcionamiento básico.

## Funcionamiento

1. El programa cambia al modo "raw" utilizando el comando `stty`.
2. Muestra un mensaje para indicar que está en modo "raw".
3. Entra en un bucle que espera constantemente la entrada del usuario.
4. Muestra un mensaje cada vez que se presiona una tecla (excepto 'q').
5. Para salir del programa, presiona la tecla 'q' o alcanza el final del archivo (EOF).
6. Restaura al modo "cooked" al finalizar.

## Uso

1. Compila el programa Java:

   ```bash
   javac CookedRaw.java
