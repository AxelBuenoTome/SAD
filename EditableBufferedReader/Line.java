package EditableBufferedReader;

import java.util.ArrayList;

public class Line {
    private ArrayList<Character> characters;
    private int cursorPosition;
    
    public Line() {
        characters = new ArrayList<>();
        cursorPosition = 0;
    }

    public int getCursorPosition() { //to refresh
        return cursorPosition;
    }

    public void addChar(int c) {
        char character = (char) c; //convertimos el entero pasado como parámetro a carácter
            characters.add(cursorPosition, character);
            cursorPosition++;
        /* 
        *** ESTA LÓGICA SERVIRÁ PARA CUANDO USEMOS LA TECLA INSERT ***
        *** falta rematarlo *** 
        if (cursorPosition >= characters.size()) {
            characters.add(cursorPosition, character);
            cursorPosition++;
        } else { //para el insert
            characters.set(cursorPosition, character);
            cursorPosition++;
        }
      */
    }

    public void leftArrow(){
        if(cursorPosition > 1){
            cursorPosition--;
        }
    }
    public void rightArrow(){
        if (cursorPosition <= characters.size() ){
            cursorPosition++;
        }
    }

    public void backspace(){
        if (cursorPosition > 0){
            int position = cursorPosition - 1;
            characters.remove(position);
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
