package ReproductorV2;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Model extends Observable {
    
    private int position;
    private String filePath;
    private ArrayList<String> songs;
    // añadimos el Arraylist de observers
    private ArrayList<Observer> observers;

    //constructor de la clase model
    public Model(){
        position = 0;
        filePath = "/mnt/c/Users/Axel/OneDrive/Documents/UPC/SAD/canciones";
        songs = new ArrayList<>(); //En el main se llama al controlador, y este al model para crearlo.
        // añadimos el Arraylist de observers
        observers = new ArrayList<>();
    }

    //miramos la posición en la que estamos y devolvemos el nombre de la canción
    public String getSongName(int position){
        return songs.get(position);
    }

    public int getPosition(){
        return position;
    }
    //método a priori innecesario
    public String getFilePath(){
        return filePath;
    }
    public ArrayList<String> getSongs(){
        return songs;
    }

    //versión inicial del up
    public void up(){
        if(position>0){
            position --;
            setChanged();  // Marca el Observable como modificado
            notifyObservers(position);  // le pasa la posición 
        }
    }
    //versión inicial del down
    public void down(){
        if(position<songs.size()){
            position++;
            setChanged();  // Marca el Observable como modificado
            notifyObservers(position);  // le pasa la posición 
        }
    }
    public void createSongList() {
        File directory = new File(filePath);
        try {
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        // Añadimos el nombre de la canción a la lista
                        songs.add(file.getName());
                    }
                    setChanged();  // Marca el Observable como modificado
                    notifyObservers(position);  // le pasa la posición 
                } else {
                    throw new IOException("El directorio está vacío.");
                }
            } else {
                // Mensaje importante para Josep
                throw new IllegalArgumentException("La ruta especificada no es válida, Josep. Debes cambiar el directoryPath.");
            }
        } catch (IllegalArgumentException ia) {
            // Manejo del error cuando la ruta no es válida
            System.err.println(ia.getMessage());
        } catch (IOException io) {
            // Manejo de errores de I/O como un directorio vacío
            System.err.println(io.getMessage());
        }
    }
    
    public void play() {
        try {
            ProcessBuilder pb = new ProcessBuilder("play", filePath + File.separator + getSongName(position));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            /*
            //PROVISIONAL MIENTRAS NO HAYA PARSING
            // Leemos cada linea del proceso
            String line;
            while ((line = reader.readLine()) != null) { 
                // Procesamos cada línea
                System.out.println(line); // Imprimirmos la línea (pq no hay parsing todavía)
            }
            */
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
