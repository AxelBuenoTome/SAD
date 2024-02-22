import java.io.*;

public class CountColumns {

        public static void main(String args[]) {
		String s = null;
                try {
			String comando;
			comando = "tput cols";
			// Ejecutamos el comando
                        Process p = Runtime.getRuntime().exec(comando);

                        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        // Leemos la salida del comando
                        System.out.print("El número de columnas es ");
                        while ((s = stdInput.readLine()) != null) {
                                System.out.println(s);
                        }
			System.exit(0);
                } catch (IOException e) {
                        System.out.println("Excepción: ");
                        e.printStackTrace();
                        System.exit(-1);
                }
        }
}