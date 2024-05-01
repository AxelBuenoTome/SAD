package ReproductorV3;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class View extends BasicWindow implements Observer{
    private final List<Button> songs = new ArrayList<>();
    private static Button selectedSong = null;
    private final Panel mainPanel, leftPanel, rightPanel;
    private Model model;
    private Label titulo, artista, album, genero, duracion, año;

    public View(Model model) {
        super();
        this.model = model;
        model.addObserver(this);

        setHints(List.of(Window.Hint.CENTERED));
        mainPanel = new Panel(new BorderLayout());
        leftPanel = new Panel(new LinearLayout());
        rightPanel = new Panel(new LinearLayout());

        //this.setFixedSize(new TerminalSize(50, 20));
        this.createWidgets();
    }

    private void createWidgets() {

        for (int i=0; i< model.getSongs().size(); i++) {
            Button song = new Button(model.getSongName(i)).addTo(leftPanel);
            song.addListener( (onClick) -> {
                model.setPosition(model.getSongs().indexOf(song.getLabel()));
                model.play();
            });
        }

        new Label("Información de la canción:").addTo(rightPanel);
        titulo = new Label("Título: ").addTo(rightPanel);
        artista = new Label("Artista: ").addTo(rightPanel);
        album = new Label("Álbum: ").addTo(rightPanel);
        genero = new Label("Género: ").addTo(rightPanel);
        duracion = new Label("Duración: ").addTo(rightPanel);
        año = new Label("Año: ").addTo(rightPanel);

        mainPanel.addComponent(leftPanel, BorderLayout.Location.LEFT);
        mainPanel.addComponent(rightPanel, BorderLayout.Location.RIGHT);

        setComponent(mainPanel);
    }

    public void setupScreen() {
        try {
            // Setup terminal and screen layers
            Screen screen = new TerminalScreen(new DefaultTerminalFactory().createTerminal());
            screen.startScreen();
            WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
            gui.addWindowAndWait(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // funcion para camniar el texto a la derecha y poner la info

    private void displayProgress(String prores){

    }

    private void refreshSong(Song song) {
        titulo = new Label("Título: " + song.getTitle()).addTo(rightPanel);
        artista = new Label("Artista: " + song.getArtist()).addTo(rightPanel);
        album = new Label("Álbum: " + song.getAlbum()).addTo(rightPanel);
        genero = new Label("Género: " + song.getGenre()).addTo(rightPanel);
        duracion = new Label("Duración: " + song.getDuration()).addTo(rightPanel);
        año = new Label("Año: " + song.getYear()).addTo(rightPanel);
    }

    @Override
    public void update(Observable o, Object arg) {
        UpdateInfo updateInfo = (UpdateInfo) arg; //convertimos el objeto a UpdateInfo
        switch (updateInfo.getType()) {
            case KEY.POSSITION:
                //refreshList((int) updateInfo.getValue()); //convertimos la información en integer
                break;
            case KEY.PROGRESS:
                displayProgress((String) updateInfo.getValue()); //convertimos la información en String
                break;
            case KEY.SONG:
                refreshSong((Song)updateInfo.getValue()); //actualizamos la info de la canción
                break;
        }
    }
    

}
