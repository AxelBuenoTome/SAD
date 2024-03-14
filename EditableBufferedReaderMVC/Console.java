package EditableBufferedReaderMVC;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class Console implements Observer{

    static final int CHAR = 206;

    Line line;

    public Console (Line line){
        this.line = line;
    }

    @Override
    public void update(Observable o, Object arg) {
        int cmd = (int) arg;
        switch(cmd){
            case CHAR:
                System.out.print("\033[2K\033[1G");
                System.out.print("\r" + line.toString());
                // Mueve el cursor al printar el terminal!!!
                System.out.print("\033[" + (line.getCursorPosition() + 1) + "G");
                break;
            default:

                break;
        }
    }

}
