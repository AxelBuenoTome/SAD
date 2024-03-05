package EditableBufferedReader;

import java.util.ArrayList;

public class Line {
    private ArrayList<Character> characters;
    private int cursorPosition;
    private boolean insert;
    
    public Line() {
        characters = new ArrayList<>();
        cursorPosition = 0;
        insert = false;
    }

    public int getCursorPosition() { //to refresh
        return cursorPosition;
    }

    public void addChar(int c) {
        char character = (char) c; //convertimos el entero pasado como parámetro a carácter
            
        /* 
        *** ESTA LÓGICA SERVIRÁ PARA CUANDO USEMOS LA TECLA INSERT ***
        *** falta rematarlo (modo edición o modo escritura)*** 
        */
        if (!insert) {
            characters.add(cursorPosition, character);
            cursorPosition++;
        } else { //para el insert
            characters.set(cursorPosition, character);
            cursorPosition++;
        }
      
    }

    //este metodo pasa el terminal a modo insertar
    public void setInsert(){
        insert = false;
    }

    //este metodo pasa el terminal a modo insertar
    public void unSetInsert(){
        insert = false;
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
    //Sin este nuevo String se imprimen los identificadores Hexadecimales
    //es más efectivo usar un stringBuilder que un String
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (char character : characters) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

}
