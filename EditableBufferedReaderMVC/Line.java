package EditableBufferedReaderMVC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.AttributeSet.CharacterAttribute;

public class Line extends Observable {

    private ArrayList<Character> characters;
    private int cursorPosition;
    private int numColumns;
    private boolean isinsert;
    // añadimos el Arraylist de observers
    private ArrayList<Observer> observers;

    public Line() {
        characters = new ArrayList<>();
        cursorPosition = 0;
        isinsert = false;
        // añadimos el Arraylist de observers
        observers = new ArrayList<>();
    }

    public int getCursorPosition() { // to refresh
        return cursorPosition;
    }

    public void addChar(int c) {
        char character = (char) c;

        if (isinsert) {
            this.delete();
        }
        characters.add(cursorPosition, character);
        cursorPosition++;

        // indicamos que ha habido un cambio
        this.setChanged();
        // notificamos que ha habido un cambio
        this.notifyObservers(KEY.CHAR);

    }

    public void setHome() {
        cursorPosition = 0;
        // indicamos que ha habido un cambio
        this.setChanged();
        // notificamos que ha habido un cambio
        this.notifyObservers(KEY.CHAR);
    }

    public void setEnd() {
        cursorPosition = characters.size();
        // indicamos que ha habido un cambio
        this.setChanged();
        // notificamos que ha habido un cambio
        this.notifyObservers(KEY.CHAR);
    }

    public void setInsert() {
        isinsert = !isinsert;
        // indicamos que ha habido un cambio
        this.setChanged();
        // notificamos que ha habido un cambio
        this.notifyObservers(KEY.INS_VAL);
    }

    public void leftArrow() {
        if (cursorPosition > 0) {
            cursorPosition--;
            // indicamos que ha habido un cambio
            this.setChanged();
            // notificamos que ha habido un cambio
            this.notifyObservers(KEY.CHAR);
        }
    }

    public void rightArrow() {
        if (cursorPosition < characters.size()) {
            cursorPosition++;
            // indicamos que ha habido un cambio
            this.setChanged();
            // notificamos que ha habido un cambio
            this.notifyObservers(KEY.CHAR);
        }
    }

    public void backspace() {
        if (cursorPosition > 0) {
            cursorPosition--;
            characters.remove(cursorPosition);
            // indicamos que ha habido un cambio
            this.setChanged();
            // notificamos que ha habido un cambio
            this.notifyObservers(KEY.CHAR);
        }
    }

    public void delete() {
        if (cursorPosition < characters.size()) {
            characters.remove(cursorPosition);
            // indicamos que ha habido un cambio
            this.setChanged();
            // notificamos que ha habido un cambio
            this.notifyObservers(KEY.CHAR);
        }
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : characters) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

}
