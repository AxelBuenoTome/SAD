package EchoChat;

import java.io.IOException;
import java.net.ServerSocket;

public class MyServerSocket extends ServerSocket{ //deberíamos no extender?
    
    
    //Tenemos el IOException y el IllegalArgumentException
    public MyServerSocket (int port) throws IOException{
        super(port);
        System.out.println("\u001B[34m" + "Servidor creado correctamente :)"+ "\u001B[0m"); //COLOR AZUL
    }

    //aceptar conexiones de clientes, ha de devolver un objeto MyServerSocket, es lo interesante
    //IOException y SocketTimeoutException
    @Override
    public MySocket accept() throws IOException{
        try{
            MySocket socket = new MySocket(super.accept()); //MySocket.accept()
            System.out.println("\u001B[33m" + "Hemos aceptado el Socket correctamente" + "\u001B[0m"); //COLOR AMARILLO
            return socket;
        }
        catch(IOException e){//TODO: hay que usar el e
            e.printStackTrace();
        }
        return null;
    }

    //cierra el servidor
    @Override
    public void close() throws IOException{
        try{
            System.out.println("\u001B[33m" + "Se ha cerrado el servidor" + "\u001B[0m"); //COLOR AMARILLO
            super.close();
        }
        catch(IOException e){//TODO: hay que usar el e
        }

    }
}

