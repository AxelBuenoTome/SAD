package EchoChat;

import java.io.IOException;

public class EchoClient {
    public static void main (String [] args) throws IOException{
        MySocket s = new MySocket(args[0],Integer.parseInt(args[1]));
        //INPUT THREAD
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
        //OUTPUT THREAD
        new Thread(){
            public void run(){
                String line;
                try {
                    while((line=s.readLine())!= null){
                        System.out.println(line);
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
