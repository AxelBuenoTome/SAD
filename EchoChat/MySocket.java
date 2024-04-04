package EchoChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MySocket extends Socket{
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    //throws UnknownHostException, IOException
    //https://docs.oracle.com/javase/8/docs/api/java/net/Socket.html#Socket--
    public MySocket(String host, int port) throws IOException{ 
        //Creamos el socket
        socket = new Socket(host, port);
        //streams de lectura y escritura
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(),true); //no tengo muy claro lo del autoFlush
    }

    public MySocket(Socket accept) throws IOException {
        this.socket = accept;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
    }
    public String readLine() throws IOException{
        return reader.readLine();
    }
    public void println(String message){
        writer.println(message);
    }
    public void close() throws IOException{
        reader.close();
        writer.close();
        socket.close();
    }
}
