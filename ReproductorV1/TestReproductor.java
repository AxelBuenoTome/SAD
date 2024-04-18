package ReproductorV1;

import java.util.Scanner;

public class TestReproductor {
    public static void main(String[] args) {
        // Instanciar un objeto Model
        Model model = new Model();

        // Ruta del directorio que deseas explorar
        String directoryPath = "/mnt/c/Users/Axel/OneDrive/Documents/UPC/SAD/canciones";
        
        // Llamar al método listFiles para mostrar los nombres de los archivos en el directorio
        model.listFiles(directoryPath);
        
        // Solicitar al usuario que elija una canción
        Scanner scanner = new Scanner(System.in);
        System.out.print("¿Qué canción quieres escuchar? Escriba el nombre del archivo: ");
        String nombreCancion = scanner.nextLine();
        
        // Aquí podrías llamar a un método para reproducir la canción elegida
        // Por ahora, simplemente mostraremos un mensaje con la canción elegida
        System.out.println("Has elegido reproducir la canción: " + nombreCancion);
        model.play(nombreCancion, directoryPath);
    }
}
