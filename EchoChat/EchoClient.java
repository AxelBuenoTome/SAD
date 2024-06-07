package EchoChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        MySocket s = new MySocket(args[0], Integer.parseInt(args[1]));

        // INPUT THREAD
        new Thread() {
            public void run() {
                String line;
                try (BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
                    while ((line = stdIn.readLine()) != null) {
                        s.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        s.close();
                        System.out.println("\u001B[35m" + "Se ha cerrado el cliente" + "\u001B[0m"); // COLOR MAGENTA
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        // OUTPUT THREAD
        new Thread() {
            public void run() {
                String line;
                try {
                    while ((line = s.readLine()) != null) {
                        System.out.println("Server Echo: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
