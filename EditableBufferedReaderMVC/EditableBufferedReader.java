package EditableBufferedReaderMVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



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
    static final int ENTER = 13;

    static final int RIGHT_VAL = 169;
    static final int LEFT_VAL = 170;
    static final int DEL_VAL = 171;
    static final int HOME_VAL = 172;
    static final int END_VAL = 173;
    static final int INS_VAL = 174;

    private Line line;
    private Console console;

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        line = new Line();
        console = new Console (line);
        line.addObserver(console);
    }

    public void setRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty -echo raw </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unsetRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty echo cooked </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read() throws IOException {
        int inputChar = super.read();
        if (inputChar == ESC) {
            inputChar = super.read();
            if (inputChar == SQUARE_BRAQUET) {
                inputChar = super.read();
                switch (inputChar) {
                    case LEFT:
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
                    case SQUARE_BRAQUET:
                        switch (inputChar = super.read()) {
                            case END:
                                return END_VAL;

                            case HOME:
                                return HOME_VAL;
                        }

                    default:
                        return inputChar;
                }
            }
        }
        return inputChar;
    }

    public String readLine() throws IOException {
        setRaw();
        int actualChar;
        while ((actualChar = this.read()) != ENTER) {
            switch (actualChar) {

                case RIGHT_VAL:
                    line.rightArrow();
                    break;

                case LEFT_VAL:
                    line.leftArrow();
                    break;

                case DEL_VAL:
                    line.delete();
                    break;

                case INS_VAL:
                    line.setInsert();
                    break;

                case BSK:
                    line.backspace();
                    break;

                case END:
                    line.setEnd();
                    break;

                case HOME:
                    line.setHome();
                    break;

                default:
                    line.addChar(actualChar);
                    break;
            }
        }
        unsetRaw();
        return line.toString();
    }
}
