package ReproductorV2;

import java.util.Observer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class View implements Observer {

    private Model model;
    private boolean first;
    private int numberOfSongs;
    private static int numSongs = 9; 

    public View(Model model) {
        this.model = model;
        model.addObserver(this);
        first = true;
    }

    @Override
    public void update(Observable o, Object arg) {
        UpdateInfo updateInfo = (UpdateInfo) arg; //convertimos el objeto a UpdateInfo
        switch (updateInfo.getType()) {
            case KEY.POSITION:
                refreshList((int) updateInfo.getValue()); 
                refreshSong((int) updateInfo.getValue());
                break;
            case KEY.PROGRESS:
                displayProgress((String) updateInfo.getValue()); //convertimos la información en String
                break;
            case KEY.INIT:
                initInterface();
        }
    }

    private void initInterface() {
        System.out.print("\033[H\033[2J"); //hace un clear en la pantalla
        System.out.println("*** Reproductor V2 ***\n");
    }

    public void refreshList(int position) {
        ArrayList<Song> songs = model.getSongs();
        numberOfSongs =songs.size();
        if (!first) {
            System.out.print("\033[" + numSongs + "A");
        }
        first = false;    
        // Calcula el rango de índices de canciones a mostrar
        int start = Math.max(0, position - numSongs / 2);
        int end = Math.min(numberOfSongs, start + numSongs);
    
        // Ajusta el inicio si el rango es menor que numSongs y todavía hay espacio por encima
        if (end - start < numSongs && start > 0) {
            start = Math.max(0, end - numSongs);
        }
    
        // Imprime cada canción en el rango visible
        for (int i = start; i < end; i++) {
            System.out.print("\r"); // Vuelve al comienzo de la línea
            if (i == position) {
                System.out.print("   \033[7m"); // Activa el modo de inversión de colores // Marca la canción actual
            } else {
                System.out.print("   ");  // Espacios para canciones no seleccionadas
            }
            System.out.println(songs.get(i).getFileName() + "\033[m" + "\033[K"); // Imprime el nombre de la canción y borra el resto de la línea
        }
    
        // Limpia las líneas adicionales si el último grupo es menor que numSongs
        for (int i = end; i < start + numSongs; i++) {
            System.out.print("\r\033[K\n"); // Borra la línea
        }
    
        System.out.print("\r"); // Posiciona el cursor al inicio de la línea para la siguiente impresión
    }
    /*public void displayProgress(String progress) {
        System.out.print("\rProgreso: " + progress + "%"+'\r');
    }*/
    
    public void displayProgress(String progress) {
        int totalLength = model.countColumns() - 30; // Longitud dinámica de la barra de de progreso
        float progressPercentage = Float.parseFloat(progress); 
        int fullBlocks = (int) (progressPercentage * totalLength / 100); //Escala
        int partialBlockPercentage = (int) ((progressPercentage * totalLength) % 100) / (100 / 8);
    
        // Caracteres Unicode para bloques parciales (de menos lleno a más lleno)
        String[] blockFractions = {" ", "▏", "▎", "▍", "▌", "▋", "▊", "▉", "█"};
    
        StringBuilder progressBar = new StringBuilder();
        progressBar.append("\033[31m"); // Rojo
        for (int i = 0; i < fullBlocks; i++) {
            progressBar.append("█"); // Bloque completo
        }
        if (fullBlocks < totalLength) {
            progressBar.append(blockFractions[partialBlockPercentage]); // Bloque parcial
        }
        for (int i = fullBlocks + 1; i < totalLength; i++) {
            progressBar.append(" "); // Espacios para la parte vacía
        }
        progressBar.append("\033[0m"); // Resetea el color
    
        System.out.print("\r[" + progressBar + "] " + String.format("%.2f", progressPercentage) + "%" + "\033[K");
    }

    public void refreshSong(int position){
        Song song = model.getSong(position);
        System.out.print("\033[" + numSongs + "A");
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Información de la canción:");
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Título: " + song.getTitle());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Artista: " + song.getArtist());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Álbum: " + song.getAlbum());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Género: " + song.getGenre());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Duración: " + song.getDuration());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Año: " + song.getYear());
        System.out.print("\033[" + 7 + "A");
        System.out.print("\033[" + numSongs + "B\r");
    }
}
