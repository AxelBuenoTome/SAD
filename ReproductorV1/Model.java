package ReproductorV1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Model {
    // Método para listar TODOS los archivos de un directorio
    public void listFiles(String directoryPath) {
        //Creamos un objeto File que será el directorio donde están nuestras canciones
        File directory = new File(directoryPath);

        //Verificamos si el objeto File es un directorio, o no existe.
        if (directory.isDirectory()) {
            //Obtener una lista de archivos del directorio elegido
            File[] files = directory.listFiles();
            //Verifica si la lista está vacía
            if (files != null) {
                //Iteramos sobre los archivos, obtenemos el nombre y lo printamos
                for (File file : files) {
                    System.out.println(file.getName());
                }
            } else {
                System.out.println("El directorio está vacío.");
            }
        } else {
            //mensaje importantísimo que solo le aparecerá a Josep, porque es un despistado y no cambiará el path
            System.out.println("La ruta especificada no es válida Josep, has de cambiar el directoryPath.");
        }
    }

    // Método para reproducir el archivo seleccionado
    // le pasaremos el nombre como parametro y la ruta
    /*public void play(String fileName, String filePath){
        //String[] cmd = { "/bin/sh", "-c", "play " + filePath + File.separator + fileName };
        //String[] cmd = { "/bin/sh", "-c", "play " + filePath + File.separator + fileName + " 2>/dev/tty"}; //funciona pero no se puede parar
        String[] cmd = { "/bin/sh", "-c", "play " + filePath + File.separator + fileName + " 2> output.txt"}; // sigue sin poder pararse, pero crear un txt qcon el que podemos ver cómo es la salida que tendremos que parsear
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("Reproduciendo...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    // Método para reproducir un archivo de audio 
    /*public void play(String fileName, String filePath) {
        try {
            // Construir la lista de comandos para reproducir el archivo
            ProcessBuilder pb = new ProcessBuilder("play", filePath + File.separator + fileName);
            // Redirigir la salida y el error estándar al flujo de salida del proceso actual
            //pb.redirectErrorStream(true);
            // Iniciar el proceso
            Process process = pb.start();
            // Esperar a que el proceso termine
            int exitCode = process.waitFor();
            // Verificar si el proceso finalizó correctamente
            if (exitCode == 0) {
                System.out.println("Reproducción finalizada exitosamente.");
            } else {
                System.out.println("Ocurrió un error durante la reproducción.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    //toda la salida de error se ve en la pantalla
    public void play(String fileName, String filePath) {
        try {
            // Comando 'play'
            ProcessBuilder pb = new ProcessBuilder("play", filePath + File.separator + fileName);
            pb.redirectErrorStream(true); // Redirigimos la salida de error
            Process process = pb.start(); // Iniciamos el proceso
            // Creamos un BufferedReader para leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //PROVISIONAL MIENTRAS NO HAYA PARSING
            // Leemos cada linea del proceso
            String line;
            while ((line = reader.readLine()) != null) { 
                // Procesamos cada línea
                System.out.println(line); // Imprimirmos la línea (pq no hay parsing todavía)
            }
            
            // Esperamos a que el proceso termine
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Reproducción finalizada");
            } else {
                System.out.println("Error durante el play.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
