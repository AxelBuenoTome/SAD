package EchoChat;

import java.io.IOException;

public class EchoServer {
    public static void main (String [] args) throws IOException{
        MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
        while(true){
            MySocket s = ss.accept();
            new Thread(){
                public void run(){
                    String line;
                    try {
                        while((line=s.readLine())!= null){
                            s.println(line); //Este es el echo
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
}
