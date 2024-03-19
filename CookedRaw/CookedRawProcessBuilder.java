package CookedRaw;

import java.io.IOException;

public class CookedRawProcessBuilder {

    public static void main(String[] args) throws IOException {
        try {
            // Cambiar al modo raw
            setRawMode();

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
            restoreCookedMode();
            System.out.println("Has vuelto al modo cooked.");
        }
    }

    private static void setRawMode() throws IOException {
        // Cambiar al modo raw
        executeCommand("stty -echo raw");
    }

    private static void restoreCookedMode() throws IOException {
        // Restaurar al modo cooked
        executeCommand("stty echo cooked");
    }

    private static void executeCommand(String command) throws IOException {
        // Ejecutar el comando en un shell
        ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", command);
        pb.inheritIO(); // Redirigir entrada/salida/errores al proceso Java que lo invoca
        try {
            pb.start().waitFor(); // Esperar a que el proceso termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
