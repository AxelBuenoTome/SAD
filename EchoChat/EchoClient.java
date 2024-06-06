package EchoChat;

import java.io.IOException;

public class EchoClient {
    public static void main (String [] args) throws IOException{
        MySocket s = new MySocket(args[0],Integer.parseInt(args[1]));
        //INPUT THREAD
        new Thread(){
            public void run(){
                String line;
                System.out.println("\u001B[35m" + "Cabron, si que entras" + "\u001B[0m"); //COLOR MAGENTA
                try {
                    while((line=s.readLine())!= null){  
                        s.println(line); //Este es el echo
                        System.out.println("\u001B[35m" + "estamos intentando mandar un Echo"+ "\u001B[0m"); //COLOR MAGENTA
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    s.close();
                    System.out.println("\u001B[35m" + "Cerrada la conexión del cliente"+ "\u001B[0m");//COLOR MAGENTA
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
        //OUTPUT THREAD
        new Thread(){
            public void run(){
                String line;
                try {
                    while((line=s.readLine())!= null){
                        System.out.println("Server Echo: " + line);
                        System.out.println("\u001B[35m" + "este es el cabron que debería funcionar" + "\u001B[0m"); //COLOR MAGENTA
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    s.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
