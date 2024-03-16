package EditableBufferedReaderMVC;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class Console implements Observer {

    static final int CHAR = 206;
    static final int RIGHT_VAL = 169;
    static final int LEFT_VAL = 170;
    static final int DEL_VAL = 171;
    static final int HOME_VAL = 172;
    static final int END_VAL = 173;
    static final int INS_VAL = 174;
    static final int CURSOR_VAL = 175;
    static final int BSK = 127;

    static final String INS_STR = "\033[4h";
    /*static final String HOME_STR = "\033[1G";
    static final String END_STR = "\u001b[4~";
    static final String LEFT_STR = "\u001B[1D";
    static final String RIGHT_STR = "\u001B[1C";
    static final String DEL_STR = "\u007F";
    static final String BSK_STR = "\b";*/

    Line line;

    public Console(Line line) {
        this.line = line;
    }

    @Override
    public void update(Observable o, Object arg) {
        int cmd = (int) arg;
        switch (cmd) {
            case CHAR:
                // System.out.print("\r"+line.toString().charAt(line.getCursorPosition() - 1));

                System.out.print("\033[2K\033[1G");
                System.out.print("\r" + line.toString());
                // Mueve el cursor al printar el terminal!!!
                System.out.print("\033[" + (line.getCursorPosition() + 1) + "G");

                break;
            case INS_VAL:
                System.out.print(INS_STR);
                break;
            case CURSOR_VAL:
                System.out.print("\033[" + (line.getCursorPosition()) + "G");
            break;
            default:
                break;
        }
    }

}
