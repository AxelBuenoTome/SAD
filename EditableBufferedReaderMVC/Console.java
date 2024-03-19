package EditableBufferedReaderMVC;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class Console implements Observer {

    static final String INS_STR = "\033[4h";
    /*static final String HOME_STR = "\033[1G";
    static final String END_STR = "\u001b[4~";
    static final String LEFT_STR = "\u001B[1D";
    static final String RIGHT_STR = "\u001B[1C";
    static final String DEL_STR = "\u007F";
    static final String BSK_STR = "\b";*/

    Line line;
    int nlines;

    public Console(Line line) {
        this.line = line;
        nlines=1;
    }

    @Override
    public void update(Observable o, Object arg) {
        int cmd = (int) arg;
        switch (cmd) {
            case KEY.CHAR:
                int limit = line.countColumns(); //limites del terminal
                System.out.print("\u001B[9999;9999H"); //vamos a la ultima posicion del texto

                //borramos el texto anterior
                String text = line.toString();
                for(int i=1;i<nlines;i++){
                    System.out.print("\u001B[2K");
                    System.out.print("\u001B[F");
                }

                //se reescribe el texto
                nlines=1;
                do{
                    if(text.length() >= limit){
                        System.out.print("\r" + text.substring(0, limit) + "\n");
                        text = text.substring(limit);
                        nlines++;
                    }
                }while(text.length() >= limit);
                System.out.print("\r" + text);


                // Mueve el cursor al printar el terminal!!!
                int CursorPosition = line.getCursorPosition()%limit;
                int lineCursor = (line.getCursorPosition()/limit)+1;
                for(int i=lineCursor;i<nlines;i++){
                    System.out.print("\u001B[F");
                }
                System.out.print("\033[" + (CursorPosition + 1) + "G");



                break;
            case KEY.INS_VAL:
                System.out.print(INS_STR);
                break;
            default:
                break;
        }
    }

}
