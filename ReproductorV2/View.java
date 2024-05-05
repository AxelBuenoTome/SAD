package ReproductorV2;

import java.util.Observer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class View implements Observer {

    private Model model;
    private boolean first;
    private int Nsongs;

    public View(Model model) {
        this.model = model;
        model.addObserver(this);
        first = true;
    }

    @Override
    public void update(Observable o, Object arg) {
        UpdateInfo updateInfo = (UpdateInfo) arg; //convertimos el objeto a UpdateInfo
        switch (updateInfo.getType()) {
            case KEY.POSSITION:
                refreshList((int) updateInfo.getValue()); //convertimos la información en integer
                refreshSong((int) updateInfo.getValue());
                break;
            case KEY.PROGRESS:
                displayProgress((String) updateInfo.getValue()); //convertimos la información en String
                break;
            case KEY.SONG:
            //System.out.println("aquí llegamos");
                //refreshSong((Song)updateInfo.getValue()); //actualizamos la info de la canción
                break;
        }
    }

    public void refreshList(int position) {
        ArrayList<Song> songs = model.getSongs();
        Nsongs =songs.size();
        if (!first) {
            System.out.print("\033[" + songs.size() + "A");
        }
        first = false;
        for (int index = 0; index < songs.size(); index++) {
            System.out.print('\r');
            if (index == position) {
                System.out.print("-->");
            } else {
                System.out.print("   ");
            }
            System.out.println(songs.get(index).getFileName());
        }
        System.out.print('\r');
    }
    /*public void displayProgress(String progress) {
        System.out.print("\rProgreso: " + progress + "%"+'\r');
    }*/
    public void displayProgress(String progress) {
        int totalLength = model.countColumns() - 30; // Define la longitud total de la barra de progreso
        float progressPercentage = Float.parseFloat(progress); // Convierte el String a float
        int fullBlocks = (int) (progressPercentage * totalLength / 100); // Cálculo de bloques completos
        int partialBlockPercentage = (int) ((progressPercentage * totalLength) % 100) / (100 / 8); // Cálculo de la fracción de bloque
    
        // Caracteres Unicode para bloques parciales (de menos lleno a más lleno)
        String[] blockFractions = {" ", "▏", "▎", "▍", "▌", "▋", "▊", "▉", "█"};
    
        StringBuilder progressBar = new StringBuilder();
        progressBar.append("\033[31m"); // Comienza con color rojo
        for (int i = 0; i < fullBlocks; i++) {
            progressBar.append("█"); // Bloque completo
        }
        if (fullBlocks < totalLength) {
            progressBar.append(blockFractions[partialBlockPercentage]); // Añade el bloque parcial
        }
        for (int i = fullBlocks + 1; i < totalLength; i++) {
            progressBar.append(" "); // Espacios para la parte vacía
        }
        progressBar.append("\033[0m"); // Resetea el color
    
        // Imprimir la barra de progreso con el porcentaje y limpiar el resto de la línea
        System.out.print("\r" + progressBar + String.format("%.2f", progressPercentage) + "%" + "\033[K");
        System.out.flush(); // Asegura que todo se imprima correctamente en la consola
    }
    
    
    

    public void refreshSong(int position){
        Song song = model.getSong(position);
        System.out.print("\033[" + Nsongs + "A");
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Información de la canción:");
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Título: " + song.getTitle());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Artista: " + song.getArtist());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Álbum: " + song.getAlbum());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Género: " + song.getGenre());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Duración: " + song.getDuration());
        System.out.println("\r\t\t\t\t\t" + "\033[K" + "Año: " + song.getYear());
        System.out.print("\033[" + 7 + "A");
        System.out.print("\033[" + Nsongs + "B\r");
    }
}
