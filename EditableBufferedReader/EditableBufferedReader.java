package EditableBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditableBufferedReader extends BufferedReader {

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
    }

    // Mètode per passar la consola a mode raw
    public void setRaw() {
        String[] cmd = {"/bin/sh", "-c", "stty -echo raw </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mètode per passar la consola a mode cooked
    public void unsetRaw() {
        String[] cmd = {"/bin/sh", "-c", "stty echo cooked </dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
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
        Line line = new Line();
        int input;
        while ((input = read()) != 13) {
            if (input == 127 || input == 8) {
                // Delete the previous character in case of backspace
                if (line.length() > 0) {
                    line.deleteChar();
                    System.out.print("\b \b");
                }
            } else {
                // Add the read character to the line
                line.append((char) input);
                System.out.print((char) input);
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
