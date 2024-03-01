package EditableBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
    }

    // Mètode per passar la consola a mode raw
    private static void setRaw() throws IOException {
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

    // Mètode per passar la consola a mode cooked
    private static void unsetRaw() throws IOException {
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

    // Mètode per llegir el següent caràcter o la següent tecla de cursor
    public int read() throws IOException {
        int inputChar = super.read();
        // Processa el caràcter llegit segons les teves necessitats
        return inputChar;
    }

    // Mètode per llegir una línia amb possibilitat d'editar-la
    public String readLine() throws IOException {
        StringBuilder line = new StringBuilder();
        int input;
            while ((input = super.read()) != 13) {
                if (input == 127 || input == 8) {
                    // Esborra el caràcter anterior en cas de backspace
                    if (line.length() > 0) {
                        line.deleteCharAt(line.length() - 1);
                        System.out.print("\b \b"); // Mostra el backspace per simular l'efecte visual
                    }
                } else {
                    // Afegix el caràcter llegit a la línia
                    line.append((char) input);
                    System.out.print((char) input); // Mostra el caràcter llegit
                }
            }
        return line.toString();
    }

    // Exemple d'ús
    public static void main(String[] args) throws IOException {
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in));
        System.out.println("Introdueix una línia: ");
        reader.setRaw();
        String line = reader.readLine();
        reader.unsetRaw();
        System.out.println("Línia llegida: " + line);
    }
}