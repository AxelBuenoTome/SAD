package ReproductorV2;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model extends Observable {
    
    private int position;
    private String filePath;
    private ArrayList<String> songs;
    // añadimos el Arraylist de observers
    private ArrayList<Observer> observers;
    //creamos el proceso para poder destruirlo
    private Process process = null;
    //creamos el hilo para poder destruirlo
    private Thread parseThread = null;

    //constructor de la clase model
    public Model(){
        position = 0;
        filePath = "//mnt/c/Users/Axel/OneDrive/Documents/UPC/SAD/canciones"; ///mnt/c/Users/Pepus/Desktop/SAD/musica
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
        position = (position - 1 + songs.size()) % songs.size();
            setChanged();  // Marca el Observable como modificado
            notifyObservers(new UpdateInfo(KEY.POSSITION, position));  // le pasa la posición 
    }
    //versión inicial del down
    public void down(){
            position = (position + 1) % songs.size();
            setChanged();  // Marca el Observable como modificado
            notifyObservers(new UpdateInfo(KEY.POSSITION, position));  // le pasa la posición 
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
                    notifyObservers(new UpdateInfo(KEY.POSSITION, position));  // le pasa la posición 
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
            if (process!=null){
                process.destroy();
            }
            ProcessBuilder pb = new ProcessBuilder("play", filePath + File.separator + getSongName(position));
            pb.redirectErrorStream(true);
            process = pb.start();
            
            //Creamos el hilo que se encargará de parsear
            parseThread = new Thread(new Runnable() {
                public void run() {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            parseProgress(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            parseThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseProgress(String line){
    Pattern pattern = Pattern.compile("In:(\\d+\\.\\d+)%"); //usamos expresiones regulares
    Matcher matcher = pattern.matcher(line);
    if (matcher.find()) {
        String progressString = matcher.group(1); 
        //float progress = Float.parseFloat(progressString);
        setChanged(); 
        notifyObservers(new UpdateInfo(KEY.PROGRESS, progressString)); 
        }
    }
}
