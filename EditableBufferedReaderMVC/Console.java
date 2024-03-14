package EditableBufferedReaderMVC;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class Console implements Observer{

    static final int CHAR = 206;
    static final int RIGHT_VAL = 169;
    static final int LEFT_VAL = 170;
    static final int DEL_VAL = 171;
    static final int HOME_VAL = 172;
    static final int END_VAL = 173;
    static final int INS_VAL = 174;

    static final String INS_STR = "\033[4h";
    static final String HOME_STR = "\033[1G";

    Line line;

    public Console (Line line){
        this.line = line;
    }

    @Override
    public void update(Observable o, Object arg) {
        int cmd = (int) arg;
        switch(cmd){
            case CHAR:
                System.out.print(line.toString().charAt(line.getCursorPosition() - 1));
                break;
            case INS_VAL:
                System.out.print(INS_STR);
                break;
            case HOME_VAL:
                System.out.print(HOME_STR);
                break;
            default:
                break;
        }
    }

}
