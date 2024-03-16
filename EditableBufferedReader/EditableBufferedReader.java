package EditableBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//¿Quién ha puesto esto aquí?
import javax.swing.DefaultBoundedRangeModel;

public class EditableBufferedReader extends BufferedReader {

    static final int RIGHT = 67; // C porque es ^[[C
    static final int LEFT = 68; // D porque es ^[[D
    static final int ESC = 27; // ESC
    static final int SQUARE_BRAQUET = 91; // "["
    static final int DEL = 51; // 3 porque es ^[[3~
    static final int INS = 50; // 2 porque ^[[2~
    static final int END = 70; // F porque ^[[F
    static final int HOME = 72; // H porque ^[[H
    static final int TILDE = 126; // "~"
    static final int BSK = 127; // bacspace "\b"

    // Reasignaciones de la tabla ASCII, a partir de valores que no usamos
    static final int RIGHT_VAL = 169;
    static final int LEFT_VAL = 170;
    static final int DEL_VAL = 171;
    static final int HOME_VAL = 172;
    static final int END_VAL = 173;
    static final int INS_VAL = 174;

    private Line line;

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        line = new Line();
    }

    // Mètode per passar la consola a mode raw
    public void setRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty -echo raw </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mètode per passar la consola a mode cooked
    public void unsetRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty echo cooked </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Mètode per llegir el següent caràcter o la següent tecla de cursor
    // Returns --> The character read, as an integer in the range 0 to 65535
    // (0x00-0xffff), or -1 if the end of the stream has been reached
    @Override
    public int read() throws IOException {
        int inputChar = super.read();
        // *** los métodos no se ejecutan en el read, el read devuelve el entero, y este
        // se comprueba en readLine ***
        // Processa el caràcter llegit segons les teves necessitats
        // comprobamos si es un caracter escape
        if (inputChar == ESC) {
            // Hay que volver a leer para ver si es un caracter de edición
            inputChar = super.read();
            if (inputChar == SQUARE_BRAQUET) {
                // ahora viene el switch entre todos los posibles casos, tenemos que leer una
                // última vez
                inputChar = super.read();
                switch (inputChar) {
                    // en caso de que el último caracter sea el 68 --> D porque izquierda es ^[[D
                    case LEFT:
                        // retornamos un número asignado por nosotros que no se use de la tabla ASCII
                        // porque el 91 está asignado a la letra D
                        return LEFT_VAL;

                    case RIGHT:
                        return RIGHT_VAL;

                    case DEL:
                        inputChar = super.read();
                        if (inputChar == TILDE) {
                            return DEL_VAL;
                        }

                    case INS:
                        inputChar = super.read();
                        if (inputChar == TILDE) {
                            return INS_VAL;
                        }
                    case END:
                        return END_VAL;

                    case HOME:
                        return HOME_VAL;

                    default:
                        // esto no estoy del todo seguro de si deberíamos cambiarlo. Al pulsar flecha
                        // arriba retorna una A y al pulsar flecha abajo una B
                        return inputChar;
                }
            }
        }
        return inputChar;
    }

    public String readLine() throws IOException {
        setRaw();
        int actualChar;
        // Leer caracteres hasta que se presione Enter (código ASCII 13) (no sé si el do
        // while es lo mejor)
        while ((actualChar = this.read()) != 13) {
            // hay que hacer un switch con los diferentes casos. El default será el .addChar
            switch (actualChar) {

                case RIGHT_VAL:
                    line.rightArrow();
                    break;

                case LEFT_VAL:
                    line.leftArrow();
                    break;
                // Llevo un lio bueno, no sé cual es delete ni backspace
                case DEL_VAL:
                    line.delete();
                    break;

                case INS_VAL:
                    line.setInsert();
                    break;

                case BSK:
                    line.backspace();
                    break;

                case END_VAL:
                    line.setEnd();
                    break;

                case HOME_VAL:
                    line.setHome();
                    break;

                default:
                    line.addChar(actualChar);
                    break;
            }
            System.out.print("\033[2K\033[1G");
            System.out.print("\r" + line.toString());
            // Mueve el cursor al printar el terminal!!!
            System.out.print("\033[" + (line.getCursorPosition() + 1) + "G");
        }
        unsetRaw();
        return line.toString();
    }
}
