package ChatTextual;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {
    private ServerSocket serverSocket;

    public MyServerSocket(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public MySocket accept() throws IOException {
        Socket socket = serverSocket.accept();
        System.out.println("\u001B[33m" + "Hemos aceptado el Socket correctamente" + "\u001B[0m"); // COLOR AMARILLO
        return new MySocket(socket);
    }

    public void close() throws IOException {
        serverSocket.close();
        System.out.println("\u001B[33m" + "Se ha cerrado el servidor" + "\u001B[0m"); // COLOR AMARILLO
    }
}