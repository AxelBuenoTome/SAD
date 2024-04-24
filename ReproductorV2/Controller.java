package ReproductorV2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller extends BufferedReader {

    public Controller(InputStreamReader in) {
        super(in);
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

  @Override
  public int read() throws IOException {
      int inputChar;
      if (match(KEY.UP_STR)){
          return KEY.UP_VAL;
      }
      if (match(KEY.DOWN_STR)){
          return KEY.DOWN_VAL;
      }
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

 public String readLine() throws IOException {
     setRaw();
     int actualChar;
     while ((actualChar = this.read()) != KEY.ENTER) {
         switch (actualChar) {

             case KEY.UP_VAL:
                 Model.up();
                 break;

             case KEY.DOWN_VAL:
                 Model.down();
                 break;

            case KEY.ENTER:
                 Model.play();
                 break;
         }
     }
     unsetRaw();
 }
}
