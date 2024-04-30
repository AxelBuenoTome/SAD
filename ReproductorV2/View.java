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
                break;
            case KEY.PROGRESS:
                displayProgress((String) updateInfo.getValue()); //convertimos la información en String
                break;
            case KEY.SONG:
                refreshSong((Song)updateInfo.getValue()); //actualizamos la info de la canción
                break;
        }
    }

    public void refreshList(int position) {
        ArrayList<String> songs = model.getSongs();
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
            System.out.println(songs.get(index));
        }
        System.out.print('\r');
    }
    public void displayProgress(String progress) {
        System.out.print("\rProgreso: " + progress + "%"+'\r');
    }
    public void refreshSong(Song song){
        System.out.print("\033[" + Nsongs + "A");
        System.out.println("\r\t\t\t\t\t" + "Información de la canción:");
        System.out.println("\r\t\t\t\t\t" + "Título: " + song.getTitle());
        System.out.println("\r\t\t\t\t\t" + "Artista: " + song.getArtist());
        System.out.println("\r\t\t\t\t\t" + "Álbum: " + song.getAlbum());
        System.out.println("\r\t\t\t\t\t" + "Género: " + song.getGenre());
        System.out.println("\r\t\t\t\t\t" + "Duración: " + song.getDuration());
        System.out.println("\r\t\t\t\t\t" + "Año: " + song.getYear());
        System.out.print("\033[" + 7 + "A");
        System.out.print("\033[" + Nsongs + "B\r");
    }
}
