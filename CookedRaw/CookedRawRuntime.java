package CookedRaw;

import java.io.Console;
import java.io.IOException;

public class CookedRawRuntime {

    public static void main(String[] args) throws IOException {
        CookedRawRuntime cookedRaw = new CookedRawRuntime();

        Console console = System.console();

        if (console == null) {
            System.err.println("El sistema no tiene consola disponible");
            System.exit(1);
        }

        try {
            // Cambiar al modo raw
            cookedRaw.setRaw();

            // Imprimir mensaje para verificar que está en modo raw
            System.out.println("Estás en modo raw. Presiona una tecla para ver el mensaje o 'q' para salir.");

            // Bucle para leer teclas en modo raw
            while (true) {
                int input = System.in.read();

                if (input == -1 || input == 'q') {
                    // Salir del bucle si se presiona 'q' o se alcanza el EOF
                    break;
                }

                System.out.println("Has presionado la tecla: " + (char) input);
            }

        } finally {
            // Restaurar al modo cooked al finalizar
            cookedRaw.unsetRaw();
            System.out.println("Has vuelto al modo cooked.");
        }
    }

    public void setRaw() {
        String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unsetRaw() {
        String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
