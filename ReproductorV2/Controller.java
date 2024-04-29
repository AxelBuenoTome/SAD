package ReproductorV2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Controller extends BufferedReader {

    private Model model;

    public Controller(InputStreamReader in, Model model) {
        super(in);
        this.model = model;
        initApplication();
    }

    public void initApplication() {
        model.createSongList();
    }

    public void setRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty -echo raw </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unsetRaw() {
        String[] cmd = { "/bin/sh", "-c", "stty echo cooked </dev/tty" };
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read() throws IOException {
        int inputChar;
        if (match(KEY.UP_STR)) {
            return KEY.UP_VAL;
        }
        if (match(KEY.DOWN_STR)) {
            return KEY.DOWN_VAL;
        }

        inputChar = super.read();
        return inputChar;
    }

    private boolean match(String escape) throws IOException {
        mark(escape.length());
        try {
            for (int i = 0; i < escape.length(); i++) {
                int ch = super.read();
                if (ch != escape.charAt(i)) {
                    reset();
                    return false;
                }
            }
            return true;
        } finally {
        }

    }

    public void processInput() throws IOException {
        setRaw();
        int actualChar;
        while ((actualChar = this.read()) != 4) { 
            switch (actualChar) {

                case KEY.UP_VAL:
                    model.up();
                    break;

                case KEY.DOWN_VAL:
                    model.down();
                    break;

                case KEY.ENTER:
                    model.play();
                    break;
            }
        }
        //creo que hemos de detener al hilo para poder cerrar el programa
        model.stopThread();
        unsetRaw();
    }
}
