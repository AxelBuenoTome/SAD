package CookedRaw;

import java.io.Console;
import java.io.IOException;

public class CookedRaw {

    public static void main(String[] args) throws IOException {
        Console console = System.console();

        if (console == null) {
            System.err.println("El sistema no tiene consola disponible");
            System.exit(1);
        }

        try {
            // Cambiar al modo raw
            setRawMode(console);

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
            restoreCookedMode(console);
            System.out.println("Has vuelto al modo cooked.");
        }
    }

    private static void setRawMode(Console console) throws IOException {
        // Implementación básica para cambiar al modo raw
        ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "stty -echo raw < /dev/tty");
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process process = pb.start();

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void restoreCookedMode(Console console) throws IOException {
        // Implementación básica para restaurar al modo cooked
        ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", "stty echo cooked < /dev/tty");
        pb.redirectInput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process process = pb.start();

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
