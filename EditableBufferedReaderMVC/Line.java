package EditableBufferedReaderMVC;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.AttributeSet.CharacterAttribute;

public class Line extends Observable {

    static final int CHAR = 206;
    static final int RIGHT_VAL = 169;
    static final int LEFT_VAL = 170;
    static final int DEL_VAL = 171;
    static final int HOME_VAL = 172;
    static final int END_VAL = 173;
    static final int INS_VAL = 174;
    static final int CURSOR_VAL = 175;
    static final int BSK = 127;

    private ArrayList<Character> characters;
    private int cursorPosition;
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
        this.notifyObservers(CHAR);

    }

    public void setHome() {
        cursorPosition = 0;
        // indicamos que ha habido un cambio
        this.setChanged();
        // notificamos que ha habido un cambio
        this.notifyObservers(CURSOR_VAL);
    }

    public void setEnd() {
        cursorPosition = characters.size();
        // indicamos que ha habido un cambio
        this.setChanged();
        // notificamos que ha habido un cambio
        this.notifyObservers(CURSOR_VAL);
    }

    public void setInsert() {
        isinsert = !isinsert;
        // indicamos que ha habido un cambio
        this.setChanged();
        // notificamos que ha habido un cambio
        this.notifyObservers(INS_VAL);
    }

    public void leftArrow() {
        if (cursorPosition > 0) {
            cursorPosition--;
            // indicamos que ha habido un cambio
            this.setChanged();
            // notificamos que ha habido un cambio
            this.notifyObservers(CURSOR_VAL);
        }
    }

    public void rightArrow() {
        if (cursorPosition < characters.size()) {
            cursorPosition++;
            // indicamos que ha habido un cambio
            this.setChanged();
            // notificamos que ha habido un cambio
            this.notifyObservers(CURSOR_VAL);
        }
    }

    public void backspace() {
        if (cursorPosition > 0) {
            cursorPosition--;
            characters.remove(cursorPosition);
            // indicamos que ha habido un cambio
            this.setChanged();
            // notificamos que ha habido un cambio
            this.notifyObservers(CHAR);
        }
    }

    public void delete() {
        if (cursorPosition < characters.size()) {
            characters.remove(cursorPosition);
            // indicamos que ha habido un cambio
            this.setChanged();
            // notificamos que ha habido un cambio
            this.notifyObservers(CHAR);
        }
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
