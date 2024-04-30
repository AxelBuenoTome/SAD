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
    private String currentMinute = "00:00:00.00"; //iniciamos a 0
    private String currentSong;
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
            songInfo();
    }
    //versión inicial del down
    public void down(){
            position = (position + 1) % songs.size();
            setChanged();  // Marca el Observable como modificado
            notifyObservers(new UpdateInfo(KEY.POSSITION, position));  // le pasa la posición 
            songInfo();
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
                    songInfo();
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
            stopThread();
            if (process!=null){
                process.destroy();
            }
            ProcessBuilder pb = new ProcessBuilder("play", filePath + File.separator + getSongName(position));
            pb.redirectErrorStream(true);
            process = pb.start();
            currentSong = getSongName(position);
            //Creamos el hilo que se encargará de parsear
            parseThread = new Thread(new Runnable() {
                public void run() {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        //while ((line = reader.readLine()) != null) {
                        while (!parseThread.isInterrupted() && (line = reader.readLine()) != null) {

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
        Pattern progressPattern = Pattern.compile("In:(\\d+\\.\\d+)%"); //usamos expresiones regulares
        Matcher matcher = progressPattern.matcher(line);
        if (matcher.find()) {
            String progressString = matcher.group(1); 
            //float progress = Float.parseFloat(progressString);
            setChanged(); 
            notifyObservers(new UpdateInfo(KEY.PROGRESS, progressString)); 
            }
            // Obtenemos el tiempo transcurrido de la siguiente en el siguiente formato 00:00:20.81
        Pattern timePattern = Pattern.compile("(?<=\\%\\s)\\d{2}:\\d{2}:\\d{2}\\.\\d{2}");
        Matcher timeMatcher = timePattern.matcher(line);
        if (timeMatcher.find()) {
            currentMinute = timeMatcher.group(0); // Captura todo el bloque de tiempo como un String
        }
    }

    //método para finalizar el thread.
    public void stopThread(){
        if (parseThread != null){
            parseThread.interrupt();
            try {
                parseThread.join();  // Espera a que el hilo termine
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //sirve tanto para pausar como para "despausar"
    //necesitamos saber en que minuto nos encontramos. Será necesario parsear --> trim usa el formato: hh:mm:ss[.frac] parsearemos eso
    public void pause(){
        try {
            //comprobamos si hay un proceso corriendo (tenemos que hacer pause)
            if(process!=null){
                stopThread(); //tenemos que detener el thread
                process.destroy();
                process=null;
            }
            //si no hay proceso corriendo (despausar) --> crear un proceso nuevo a partir del minuto guardado
            //es como el play, pero cambia el trim
            else{
                ProcessBuilder pb = new ProcessBuilder("play", filePath + File.separator + currentSong, "trim", currentMinute);
                pb.redirectErrorStream(true);
                process = pb.start();
                
                //Creamos el hilo que se encargará de parsear
                parseThread = new Thread(new Runnable() {
                    public void run() {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                            String line;
                            //while ((line = reader.readLine()) != null) {
                            while (!parseThread.isInterrupted() && (line = reader.readLine()) != null) {

                                parseProgress(line);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                parseThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //hay dos opciones, pasamos linea a linea haciendo un while a parse Song, o creamos un StringBuilder y le pasamos todo de golpe
    public void songInfo(){
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder("mediainfo", filePath + File.separator + getSongName(position));
            pb.redirectErrorStream(true);
            Process infoProcess = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(infoProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null){
                output.append(line).append("\n");
            }
            parseSong(output.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseSong(String output){
        String title = null, artist = null, album = null, genre = null, duration = null;
        int year = 0;

        // Patrones regex para capturar información
        Pattern titlePattern = Pattern.compile("Track name\\s*:\\s*(.+)");
        Pattern artistPattern = Pattern.compile("Director\\s*:\\s*(.+)");
        Pattern genrePattern = Pattern.compile("Genre\\s*:\\s*(.+)");
        Pattern yearPattern = Pattern.compile("Recorded date\\s*:\\s*(\\d{4})");
        Pattern albumPattern = Pattern.compile("Original source form/Name\\s*:\\s*(.+)");
        Pattern durationPattern = Pattern.compile("Duration\\s*:\\s*(\\d+ min \\d+ s)");

        // Extraer título
        Matcher matcher = titlePattern.matcher(output);
        if (matcher.find()) {
            title = matcher.group(1);
        }

        // Extraer artista
        matcher = artistPattern.matcher(output);
        if (matcher.find()) {
            artist = matcher.group(1);
        }

        // Extraer género
        matcher = genrePattern.matcher(output);
        if (matcher.find()) {
            genre = matcher.group(1);
        }

        // Extraer año
        matcher = yearPattern.matcher(output);
        if (matcher.find()) {
            year = Integer.parseInt(matcher.group(1));
        }

        // Extraer álbum
        matcher = albumPattern.matcher(output);
        if (matcher.find()) {
            album = matcher.group(1);
        }

        // Extraer duración como string
        matcher = durationPattern.matcher(output);
        if (matcher.find()) {
            duration = matcher.group(1);  // Captura la duración como "X min Y s"
        }

        Song song = new Song(title, artist, album, duration, genre, year);
        setChanged(); 
        notifyObservers(new UpdateInfo(KEY.SONG, song)); 
        /*
        // Imprimir la información de la canción
        System.out.println("Información de la canción:");
        System.out.println("Título: " + song.getTitle());
        System.out.println("Artista: " + song.getArtist());
        System.out.println("Álbum: " + song.getAlbum());
        System.out.println("Género: " + song.getGenre());
        System.out.println("Duración: " + song.getDuration());
        System.out.println("Año: " + song.getYear());
        */
    }

}
