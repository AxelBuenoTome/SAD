package EditableBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//¿Quién ha puesto esto aquí?
import javax.swing.DefaultBoundedRangeModel;

public class EditableBufferedReader extends BufferedReader {

    private Line line;
    private Scanner sc;

    public EditableBufferedReader(InputStreamReader in) {
        super(in);
        line = new Line();
        sc = new Scanner(System.in);
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

    @Override
     public int read() throws IOException {
        int inputChar;
        if (match("\033\\[[ABCDEFGH]")){
	        return KEY.UP_VAL + group(1).charAt(0);
        }
        if (match("\033\\[[234]")){
	        return KEY.INS_VAL + group(1).charAt(0);
        }
        inputChar = super.read();
        return inputChar;
     }
     
    public boolean match (String s) throws IOException{
        return sc.skip("(?:" + s + ")?").match().group().length() > 0;
        }
        
    public String group (int index){
        return sc.match().group(index);
        }
        
    /*
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
    }*/

    //METODO READ CON SWITCH (VERSION II) - FUNCIONA
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

    //METODO READ VERSION I - FUNCIONA
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
