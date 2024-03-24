package ChatTextual;

import java.io.IOException;
import java.net.ServerSocket;

public class MyServerSocket extends ServerSocket{
    
    private ServerSocket serverSocket;
    private MySocket socket;
    
    //Tenemos el IOException y el IllegalArgumentException
    public MyServerSocket (int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    //aceptar conexiones de clientes
    //IOException y SocketTimeoutException
    @Override
    public MySocket accept() throws IOException{
        try{
            socket = new MySocket(serverSocket.accept());
            return socket;
        }
        catch(IOException e){//TODO: hay que usar el e
            return null;
        }
    }

    //cierra el servidor
    @Override
    public void close() throws IOException{
        try{
            serverSocket.close();
        }
        catch(IOException e){//TODO: hay que usar el e
        }

    }
}

