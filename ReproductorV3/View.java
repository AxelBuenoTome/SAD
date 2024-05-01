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

public class View extends BasicWindow implements Observer {
    private final List<Button> songs = new ArrayList<>();
    private static Button selectedSong = null;
    private final Panel mainPanel, leftPanel, rightPanel, controlPanel;
    private Model model;
    private Label titulo, artista, album, genero, duracion, año;
    private Screen screen; // Campo para almacenar la referencia a Screen

    public View(Model model) {
        super("EsPoTifai");  // Asignar un título a la ventana
        this.model = model;
        model.addObserver(this);
    
        setHints(List.of(Window.Hint.CENTERED));
        mainPanel = new Panel(new BorderLayout());
        leftPanel = new Panel(new LinearLayout());
        rightPanel = new Panel(new LinearLayout());
        controlPanel = new Panel(new LinearLayout(Direction.HORIZONTAL)); 


        this.createWidgets();
        this.setupScreen();  // Asegurar que setupScreen se llama aquí
    }

    private void createWidgets() {
        for (int i = 0; i < model.getSongs().size(); i++) {
            Button song = new Button(model.getSongName(i)).addTo(leftPanel);
            song.addListener((onClick) -> {
                model.setPosition(model.getSongs().indexOf(song.getLabel()));
                model.play();
            });
        }

        new Label("Información de la canción:").addTo(rightPanel);
        titulo = new Label("Título: -").addTo(rightPanel);
        artista = new Label("Artista: -").addTo(rightPanel);
        album = new Label("Álbum: -").addTo(rightPanel);
        genero = new Label("Género: -").addTo(rightPanel);
        duracion = new Label("Duración: -").addTo(rightPanel);
        año = new Label("Año: -").addTo(rightPanel);

        // Botón de Pausa
        Button pauseButton = new Button("\u23F8");
        pauseButton.addListener((onClick) -> {
            model.pause();  // Suponiendo que tienes un método pause() en tu modelo
        });
        controlPanel.addComponent(pauseButton);


        mainPanel.addComponent(leftPanel, BorderLayout.Location.LEFT);
        mainPanel.addComponent(rightPanel, BorderLayout.Location.RIGHT);
        mainPanel.addComponent(controlPanel, BorderLayout.Location.BOTTOM);

        setComponent(mainPanel);

    }

    public void setupScreen() {
        try {
            // Configurar las capas de terminal y pantalla
            screen = new TerminalScreen(new DefaultTerminalFactory().createTerminal());
            screen.startScreen();
            WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
            gui.addWindowAndWait(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshSong(Song song) {
        titulo.setText("Título: " + song.getTitle());
        artista.setText("Artista: " + song.getArtist());
        album.setText("Álbum: " + song.getAlbum());
        genero.setText("Género: " + song.getGenre());
        duracion.setText("Duración: " + song.getDuration());
        año.setText("Año: " + song.getYear());

        try {
            screen.refresh();  // Refrescar la pantalla para mostrar los cambios
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        UpdateInfo updateInfo = (UpdateInfo) arg;
        switch (updateInfo.getType()) {
            case KEY.POSSITION:
                break;
            case KEY.PROGRESS:
                //displayProgress((String) updateInfo.getValue());
                break;
            case KEY.SONG:
                refreshSong((Song) updateInfo.getValue());
                break;
        }
    }
    
}
