package EchoChat;

import java.io.IOException;

public class EchoServer {
    public static void main (String [] args) throws IOException{
        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        while(true){
            MySocket s = ss.accept(); //MyServerSocket.accept()
            new Thread(){
                public void run(){
                    String line;
                    try {
                        while((line=s.readLine())!= null){
                            System.out.println("Recibido: " + line);
                            s.println(line); //Este es el echo
                            //System.out.println("\u001B[31m" +"Espero que entre aquí también"+ "\u001B[0m" ); //COLOR ROJO
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("\u001B[31m" +"IOException"+ "\u001B[0m"); //COLOR ROJO
                    }
                    try {   
                        s.close(); 
                        System.out.println("\u001B[31m" +"Cerrada conexión"+ "\u001B[0m"); //COLOR ROJO
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        System.out.println("\u001B[31m" +"Error al cerrar conexión"+ "\u001B[0m");
                    }
                }
            }.start();
        }
    }
}
