package EchoChat;

import java.io.IOException;

import javax.annotation.processing.SupportedOptions;

public class EchoServer {
    public static void main (String [] args) throws IOException{
        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        while(true){
            MySocket s = ss.accept(); //MyServerSocket.accept()
            new Thread(){
                public void run(){
                    String line;
                    //System.out.println("hemos intentado hacer un Echo");
                    try {
                        while((line=s.readLine())!= null){
                            System.out.println("\u001B[31m" + "entra aquí el puto ¿?"+ "\u001B[0m"); //COLOR ROJO
                            s.println(line); //Este es el echo
                            System.out.println("\u001B[31m" +"Espero que entre aquí también"+ "\u001B[0m" ); //COLOR ROJO
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("\u001B[31m" +"excepcion de mierda"+ "\u001B[0m"); //COLOR ROJO
                    }
                    try {   
                        s.close();
                        System.out.println("\u001B[31m" +"Se ha cerrado el Server¿?"+ "\u001B[0m"); //COLOR ROJO
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
