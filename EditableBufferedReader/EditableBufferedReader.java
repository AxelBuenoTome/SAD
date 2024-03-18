package EditableBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//¿Quién ha puesto esto aquí?
import javax.swing.DefaultBoundedRangeModel;

public class EditableBufferedReader extends BufferedReader {

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
        if (inputChar == KEY.ESC) {
            inputChar = super.read();
            if (inputChar == KEY.SQUARE_BRACKET) {
                inputChar = super.read();
                switch (inputChar) {
                    case KEY.INS: //2
                    case KEY.DEL: //3
                        int aux = super.read();
                        if(aux == KEY.TILDE){
                            return KEY.JOKER + inputChar;
                        }else{
                            break;
                        }  
                    case KEY.RIGHT: //C
                    case KEY.LEFT: //D
                    case KEY.END: //F
                    case KEY.HOME: //H
                        return KEY.JOKER + inputChar;
                }
            }
        }
        return inputChar;
    }
    /* @Override
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
    } */

    public String readLine() throws IOException {
        setRaw();
        int actualChar;
        // Leer caracteres hasta que se presione Enter (código ASCII 13) (no sé si el do
        // while es lo mejor)
        while ((actualChar = this.read()) != KEY.ENTER) {
            // hay que hacer un switch con los diferentes casos. El default será el .addChar
            switch (actualChar) {

                case KEY.RIGHT_VAL:
                    line.rightArrow();
                    break;

                case KEY.LEFT_VAL:
                    line.leftArrow();
                    break;
                // Llevo un lio bueno, no sé cual es delete ni backspace
                case KEY.DEL_VAL:
                    line.delete();
                    break;

                case KEY.INS_VAL:
                    line.setInsert();
                    break;

                case KEY.BSK:
                    line.backspace();
                    break;

                case KEY.END_VAL:
                    line.setEnd();
                    break;

                case KEY.HOME_VAL:
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
