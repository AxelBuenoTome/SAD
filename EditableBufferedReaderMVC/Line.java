package EditableBufferedReaderMVC;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Line extends Observable{
    private ArrayList<Character> characters;
    private int cursorPosition;
    private boolean isinsert;
    //añadimos el Arraylist de observers
    private ArrayList<Observer> observers;
    public Line() {
        characters = new ArrayList<>();
        cursorPosition = 0;
        isinsert = false;
        //añadimos el Arraylist de observers
        observers = new ArrayList<>();
    }

    //método para registrar y notificar observers
    public void addObserver(Observer observer){
        observers.add(observer);
    }


    public int getCursorPosition() { //to refresh
        return cursorPosition;
    }

    public void addChar(int c) {
        char character = (char) c; 

        if (!isinsert) {
            characters.add(cursorPosition, character);
            cursorPosition++;
        } else { //para el insert
            characters.set(cursorPosition, character);
            cursorPosition++;
        }
      
    }

    public void setHome(){
        cursorPosition = 0;
        // se usará notifyObservers(CONSTANTE); y en el update lo recibirá para hacer lo pertinente
    }

    public void setEnd(){
        cursorPosition = characters.size();
    }

    public void setInsert(){
        isinsert = !isinsert;
    }

    public void leftArrow(){
        if(cursorPosition > 0){
            cursorPosition--;
        }
    }
    public void rightArrow(){
        if (cursorPosition < characters.size() ){
            cursorPosition++;
        }
    }

    public void backspace(){
        if (cursorPosition > 0){
            cursorPosition --;
            characters.remove(cursorPosition);
        }
    }

    public void delete(){
        if (cursorPosition < characters.size() ){
            characters.remove(cursorPosition);
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
