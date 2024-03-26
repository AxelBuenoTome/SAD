package EditableBufferedReaderMVC;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class Console implements Observer {

    /*
     * static final String INS_STR = "\033[4h";
     * static final String HOME_STR = "\033[1G";
     * static final String END_STR = "\u001b[4~";
     * static final String LEFT_STR = "\u001B[1D";
     * static final String RIGHT_STR = "\u001B[1C";
     * static final String DEL_STR = "\u007F";
     * static final String BSK_STR = "\b";
     */

    Line line;
    int co, lo, c, l;
    boolean ins;

    public Console(Line line) {
        this.line = line;
        ins = false;
        this.getCursorPosition();
        l = lo;
        c = co;
        System.out.print("\033[" + l + ";" + c + "H");
    }

    public void getCursorPosition() {
        try {
            System.out.print("\033[6n");

            // Wait for response from the terminal
            StringBuilder response = new StringBuilder();
            int character;
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            // Loop until response received
            while ((character = reader.read()) != 'R') {
                // Append characters to response
                response.append((char) character);
            }

            // Parse the response to extract the cursor position
            String[] parts = response.substring(2).split(";");
            lo = Integer.parseInt(parts[0]);
            co = Integer.parseInt(parts[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int countColumns() {
        // desviamos la salida hacia /dev/tty para que se muestre el resultado en el
        // terminal actual
        // Así evitamos el uso de un BufferedReader
        int numColumns = 0;
        try {
            Process p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "tput cols 2>/dev/tty" });
            numColumns = Integer.parseInt(new BufferedReader(new InputStreamReader(p.getInputStream())).readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // funciona bien, comprobado conSystem.out.println(numColumns);
        return numColumns;
    }

    public void desplazrCursor(int d, int limit, String text) {
        int c1 = c + d;
        int l1 = l;
        for (int i = line.getCursorPosition(); i < text.length(); i++) {
            System.out.print(text.charAt(i));
            c1++;
            if (c1 >= limit) {
                System.out.print("\n");
                c1 = c1 % limit;
                l1++;
            }
            System.out.print("\033[" + l1 + ";" + c1 + "H");
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        int cmd = (int) arg;
        int limit = countColumns();
        String text=line.toString();
        switch (cmd) {
            case KEY.CHAR:
                if (ins) {
                    System.out.print("\033[P");
                }
                System.out.print("\033[@" + text.charAt(line.getCursorPosition() - 1));
                c++;
                System.out.print("\033[" + l + ";" + c + "H");
                break;
            case KEY.INS_VAL:
                ins = !ins;
                break;
            case KEY.END_VAL:

                c = text.length() % limit;
                l = text.length() / limit;
                System.out.print("\033[" + l + ";" + c + "H");
                break;
            case KEY.HOME_VAL:
                c = co;
                l = lo;
                System.out.print("\033[" + l + ";" + c + "H");
                break;
            case KEY.DOWN_VAL:

                break;
            case KEY.UP_VAL:

                break;
            case KEY.RIGHT_VAL:
                c++;
                System.out.print("\033[" + l + ";" + c + "H");
                break;
            case KEY.LEFT_VAL:
                c--;
                System.out.print("\033[" + l + ";" + c + "H");
                break;
            case KEY.DEL_VAL:
                System.out.print("\033[P");
                break;
            case KEY.BSK_VAL:
                c--;
                System.out.print("\033[" + l + ";" + c + "H");
                System.out.print("\033[P");

                break;
            default:
                break;
        }
    }

}
