package EchoChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MySocket {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public MySocket(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        initializeStreams();
    }

    public MySocket(Socket socket) throws IOException {
        this.socket = socket;
        initializeStreams();
    }

    private void initializeStreams() throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("\u001B[34m" + "Socket creado correctamente :)" + "\u001B[0m"); // COLOR AZUL
    }

    public String readLine() throws IOException {
        //System.out.println("\u001B[34m" + "Se está leyendo la línea " + "\u001B[0m"); // COLOR AZUL
        return reader.readLine();
    }

    public void println(String message) {
        writer.println(message);
    }

    public void close() throws IOException {
        reader.close();
        writer.close();
        socket.close();
        System.out.println("\u001B[34m" + "Socket cerrado correctamente" + "\u001B[0m"); // COLOR AZUL
    }
}
