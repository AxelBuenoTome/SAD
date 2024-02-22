package CountColumns;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CountColumnsTerminal {

    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    synchronized void refresh() {
        try {
            // Ejecutar el comando "tput cols" para obtener el ancho
            ProcessBuilder colsProcessBuilder = new ProcessBuilder("bash", "-c", "tput cols 2> /dev/tty");
            Process colsProcess = colsProcessBuilder.start();
            BufferedReader colsReader = new BufferedReader(new InputStreamReader(colsProcess.getInputStream(), Charset.forName("utf-8")));
            String cols = colsReader.readLine();
            width = Integer.parseInt(cols);

            // Ejecutar el comando "tput lines" para obtener la altura
            ProcessBuilder linesProcessBuilder = new ProcessBuilder("bash", "-c", "tput lines 2> /dev/tty");
            Process linesProcess = linesProcessBuilder.start();
            BufferedReader linesReader = new BufferedReader(new InputStreamReader(linesProcess.getInputStream(), Charset.forName("utf-8")));
            String lines = linesReader.readLine();
            height = Integer.parseInt(lines);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountColumnsTerminal terminal = new CountColumnsTerminal();
        terminal.refresh();
        System.out.println("Ancho del terminal: " + terminal.getWidth());
        System.out.println("Altura del terminal: " + terminal.getHeight());
    }
}
