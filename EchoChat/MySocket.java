package EchoChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MySocket extends Socket{
    private BufferedReader reader;
    private PrintWriter writer;

    //throws UnknownHostException, IOException
    //https://docs.oracle.com/javase/8/docs/api/java/net/Socket.html#Socket--
    public MySocket(String host, int port) throws IOException{ 
        //Creamos el socket
        super(host, port);
        //streams de lectura y escritura
        reader = new BufferedReader(new InputStreamReader(System.in)); 
        writer = new PrintWriter(super.getOutputStream(), true); // no tengo claro qué es el autoflush
        System.out.println("\u001B[34m" + "Socket creado correstamente :)"+ "\u001B[0m"); //COLOR AZUL
    }
    
    public MySocket(Socket accept) throws IOException {
        //System.out.println("Se ha aceptado el socket desde mySocket");
        super(accept.getInetAddress(), accept.getPort());
        //reader = new BufferedReader(new InputStreamReader(super.getInputStream())); //este creo que ha de ser así pq espera que escriba el server
        //writer = new PrintWriter(super.getOutputStream(), true);
    }
    public String readLine() throws IOException{
        System.out.println("\u001B[34m" + "Se esta leyendo la linea " + "\u001B[0m"); //COLOR AZUL
        return reader.readLine();
    }
    public void println(String message){
        //System.out.println("estamos escribiendo la linea");
        writer.println(message);
    }
    @Override
    public void close() throws IOException{
        reader.close();
        writer.close();
        super.close();
    }
}
