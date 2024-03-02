Actualizado '03/03/2024'

# EditableBufferedReader Clase

La clase `EditableBufferedReader` extiende la funcionalidad de `BufferedReader` para proporcionar una interfaz de lectura de línea en modo editado. La clase utiliza una instancia de la clase `Line` para gestionar el estado de la línea en edición.

## Métodos

### `setRaw()`

Este método cambia la configuración de la consola a modo raw, deshabilitando la visualización de la entrada y permitiendo un control más preciso del flujo de entrada.

### `unsetRaw()`

Este método revierte la configuración de la consola a modo cooked, restaurando la visualización de la entrada y el comportamiento normal de lectura.

### `read()`

El método `read()` heredado de `BufferedReader` lee el siguiente carácter o tecla de cursor desde la entrada. Retorna el carácter leído como un entero en el rango de 0 a 65535 (0x00-0xffff) o -1 si se alcanza el final del flujo. 

### `readLine()`

El método `readLine()` se encarga de leer caracteres hasta que se presiona la tecla Enter (código ASCII 13). Durante la lectura, utiliza la instancia de la clase `Line` para gestionar el estado de la línea en edición. Muestra dinámicamente la línea editada en la consola y retorna la línea resultante una vez se presiona Enter.

# Line Clase

La clase `Line` representa una línea en edición para ser utilizada con `EditableBufferedReader`. Gestiona el contenido y la posición del cursor dentro de la línea.

## Atributos

- `characters`: Un `ArrayList` que almacena los caracteres de la línea. (Fácil de gestionar el añadir y quitar elementos)
- `cursorPosition`: La posición actual del cursor en la línea.

## Métodos

### `Line()`

El constructor de la clase inicializa la lista de caracteres y establece la posición del cursor en 0.

### `getCursorPosition()`

Este método devuelve la posición actual del cursor en la línea. 

### `addChar(int c)`

El método `addChar` agrega un carácter a la línea en la posición actual del cursor. La lógica garantiza que el carácter se inserte correctamente, incrementando la posición del cursor.

### `toString()`

Este método ha sido sobreescrito sobre el toString por defecto de la clase Line. El que se usaba por defecto imprimía los identificadores Hexadecimales. Utiliza un StringBuilder para un rendimiento más eficiente al concatenar caracteres (en lugar de usar un String y sumar un caracter a otro).

