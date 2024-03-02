package EditableBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditableBufferedReader extends BufferedReader {

    private Line line;

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        line = new Line();
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
    //Returns --> The character read, as an integer in the range 0 to 65535 (0x00-0xffff), or -1 if the end of the stream has been reached
    public int read() throws IOException {
        int inputChar = super.read();
        // Processa el caràcter llegit segons les teves necessitats
        return inputChar;
    }
    public String readLine() throws IOException {
        setRaw();
        int actualChar;

        // Leer caracteres hasta que se presione Enter (código ASCII 13)
        while ((actualChar = this.read()) != 13) {
            line.addChar(actualChar);
            System.out.print("\r" + line.toString());   
        }
        unsetRaw();
        return line.toString();
    }
/* 
    **** ESTO YA SE PUEDE BORRAR ****
    // Mètode per llegir una línia amb possibilitat d'editar-la
    //Returns --> A String containing the contents of the line, not including any line-termination characters, or null if the end of the stream has been reached
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
    **** ESTO YA SE PUEDE BORRAR ****
    // Exemple d'ús
    public static void main(String[] args) throws IOException {
        EditableBufferedReader reader = new EditableBufferedReader(new InputStreamReader(System.in));
        System.out.println("Introdueix una línia: ");
        reader.setRaw();
        String line = reader.readLine();
        reader.unsetRaw();
        System.out.println("Línia llegida: " + line);
    }
    */
}
