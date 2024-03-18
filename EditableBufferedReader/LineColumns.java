package EditableBufferedReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LineColumns {
    private ArrayList<Character> characters;
    private int cursorPosition;
    private boolean isinsert;
    //para las columnas
    private int currentLine;
    private int numColumns;
    private int numLines;
    //qué pasa cuando se modifica el tamaño del terminal¿?
    //Se tendría que llamar constantemente a countColumns¿?
    //tal vez un thread siempre activo contando las columnas? No creo
    //Si ENTER, tenemos que crear una nueva linea
    //creo que el tema de printar ya se buscará la vida el EBR

    public LineColumns() {
        characters = new ArrayList<>();
        cursorPosition = 0;
        isinsert = false;
        //para las columnas
        currentLine = 0;
        numColumns = countColumns();
        numLines = 1;
    }

    public int getCursorPosition() { // to refresh
        return cursorPosition;
    }

    //metodo para columnas
    public int getCurrentLine(){
        return currentLine;
    }
    public int countColumns(){
        //desviamos la salida hacia /dev/tty para que se muestre el resultado en el terminal actual
        //Así evitamos el uso de un BufferedReader
        try {
            Process p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "tput cols 2>/dev/tty"});
            numColumns = Integer.parseInt(new BufferedReader(new InputStreamReader(p.getInputStream())).readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //funciona bien, comprobado conSystem.out.println(numColumns);
        return numColumns;
    }
    public void addColumn(){
        //intentamos agregar un "\n" como caracter, para que se imprima un salto de linea (NO FUNCIONA)
        characters.add(cursorPosition, (char) KEY.ENTER);
        //Creamos una nueva linea (y estamos sobre ella)
        currentLine++;
        numLines++;
        //nos ponemos en la primera columna
        cursorPosition = 0;

    }

    public void addChar(int c) {
        char character = (char) c; // convertimos el entero pasado como parámetro a carácter
        if(isinsert){
            this.delete();
        }
        characters.add(cursorPosition, character);
        //revisamos el numero de columnas
        // en el if va: cursorPosition == numColumns-1
        if(cursorPosition==25){
            addColumn();
        }
        else{
            cursorPosition++;
        }

    }

    public void setHome() {
        cursorPosition = 0;
        //volver a la primera linea
        currentLine = 0;
    }

    public void setEnd() {
        //esto es muy raro, pero podría funcionar
        cursorPosition = characters.size()/numColumns;
        //numero de lineas
        currentLine = numLines;
    }

    // este metodo pasa el terminal a modo insertar
    public void setInsert() {
        isinsert = !isinsert;
    }

    public void leftArrow() {
        if (cursorPosition > 0) {
            cursorPosition--;
        }
        if(cursorPosition == 0 && currentLine > 0){
            currentLine--;
            cursorPosition=numColumns;
        }
    }

    public void rightArrow() {
        //hay que ver cómo hacerlo
        if (cursorPosition < characters.size()) {
            cursorPosition++;
        }
    }

    public void backspace() {
        if (cursorPosition > 0) {
            cursorPosition--;
            characters.remove(cursorPosition);
        }
        //tendremos que hacer numLines--;
    }

    public void delete() {
        if (cursorPosition < characters.size()) {
            characters.remove(cursorPosition);
        }
    }

    // Sin este nuevo String se imprimen los identificadores Hexadecimales
    // es más efectivo usar un stringBuilder que un String
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : characters) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }
}
