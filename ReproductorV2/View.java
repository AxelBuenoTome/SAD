package ReproductorV2;

import java.util.Observer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class View implements Observer {

    private Model model;
    private boolean first;
    private int Nsongs;
    private static int numSongs = 8; //solo funciona con pares

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
                refreshList((int) updateInfo.getValue()); 
                refreshSong((int) updateInfo.getValue());
                break;
            case KEY.PROGRESS:
                displayProgress((String) updateInfo.getValue()); //convertimos la información en String
                break;
        }
    }

    public void refreshList(int position) {
        ArrayList<Song> songs = model.getSongs();
        Nsongs =songs.size();
        if (!first) {
            System.out.print("\033[" + numSongs + "A");
        }
        first = false;
        for (int index = 0; index < songs.size(); index++) {
            if ((index >= (position - numSongs/2) && index < (position + numSongs/2))||(position <= numSongs/2 && index < numSongs)||(numSongs>Nsongs)){
                System.out.print('\r');
                if (index == position) {
                    System.out.print("-->");
                } else {
                    System.out.print("   ");
                }
                System.out.println(songs.get(index).getFileName());
            }
        }
        if(position == Nsongs-1 && numSongs<Nsongs){
            System.out.print("\r\033[K\n");
        }
        System.out.print('\r');
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
