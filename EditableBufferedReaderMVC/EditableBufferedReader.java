package EditableBufferedReaderMVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class EditableBufferedReader extends BufferedReader {

    private Line line;
    private Console console;

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        line = new Line();
        console = new Console(line);
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
    
     //MÉTODO READ CON MATCH (FUNCIONA)
     @Override
     public int read() throws IOException {
         int inputChar;
         if (match(KEY.HOME_STR)){
             return KEY.HOME_VAL;
         }
         if (match(KEY.END_STR)){
             return KEY.END_VAL;
         }
         if (match(KEY.LEFT_STR)){
             return KEY.LEFT_VAL;
         }
         if (match(KEY.RIGHT_STR)){
             return KEY.RIGHT_VAL;
         }
         if (match(KEY.INS_STR)){
             return KEY.INS_VAL;
         }
         if (match(KEY.DEL_STR)){
             return KEY.DEL_VAL;
         }
         //en caso de que no haya match, devolvemos el caracter
         inputChar = super.read();
         return inputChar; 
     }
     
     private boolean match(String escape) throws IOException{
         //lo primero es guardar el estado actual del lector con mark(), perteneciente a BufferedReader
         //indicamos el tamaño del String escape, para luego volver atrás la misma cifra
         mark(escape.length());
         try{
             for(int i=0 ; i<escape.length(); i++){
                 int ch = super.read();
                 //miramos si el caracter leído coincide con el String escape, caracter a caracter.
                 if(ch != escape.charAt(i)){
                     //si no hay match usamos el reset y devolvemos false (para el backtracking)
                     reset();
                     return false;
                 }
             }
             return true;
         }finally{
         }
 
     }
    /*@Override
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
    }*/
    public String readLine() throws IOException {
        setRaw();
        int actualChar;
        while ((actualChar = this.read()) != KEY.ENTER) {
            switch (actualChar) {

                case KEY.RIGHT_VAL:
                    line.rightArrow();
                    break;

                case KEY.LEFT_VAL:
                    line.leftArrow();
                    break;

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
        }
        unsetRaw();
        return line.toString();
    }
}
