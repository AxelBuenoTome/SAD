package CountColumns;

import java.io.IOException;

public class CountColumns {
    public static void main(String[] args) throws IOException {

        CountColumns countColumns = new CountColumns();
        countColumns.Columns();

    }
    public void Columns() {
        //desviamos la salida hacia /dev/tty para que se muestre el resultado en el terminal actual
        //Así evitamos el uso de un BufferedReader
        String[] cmd = {"/bin/sh", "-c", "tput cols > /dev/tty"};
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
