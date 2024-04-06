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
        reader = new BufferedReader(new InputStreamReader(System.in)); //creo que este ha de ser Sytem.in pq lee del teclado
        writer = new PrintWriter(socket.getOutputStream()); 
        System.out.println("\u001B[34m" + "Socket creado correstamente :)"+ "\u001B[0m"); //COLOR AZUL
    }
    
    public MySocket(Socket accept) throws IOException {
        //System.out.println("Se ha aceptado el socket desde mySocket");
        this.socket = accept;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //este creo que ha de ser así pq espera que escriba el server
        writer = new PrintWriter(socket.getOutputStream());
    }
    public String readLine() throws IOException{
        System.out.println("\u001B[34m" + "Se esta leyendo la linea " + "\u001B[0m"); //COLOR AZUL
        return reader.readLine();
    }
    public void println(String message){
        //System.out.println("estamos escribiendo la linea");
        writer.println(message);
    }
    public void close() throws IOException{
        reader.close();
        writer.close();
        socket.close();
    }
}
