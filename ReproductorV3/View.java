package ReproductorV3;

import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
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
    private final Panel mainPanel;
    private Model model;

    public View(Model model) {
        super();
        this.model = model;
        model.addObserver(this);

        setHints(List.of(Window.Hint.CENTERED));
        mainPanel = new Panel(new BorderLayout());

        this.createWidgets();
    }

    private void createWidgets() {

        Panel leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Panel rightPanel = new Panel();
        rightPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        TextBox textBox = new TextBox().addTo(rightPanel);
        textBox.setReadOnly(true);
        textBox.setText("texto prueva");

        mainPanel.addComponent(leftPanel, BorderLayout.Location.LEFT);
        mainPanel.addComponent(rightPanel, BorderLayout.Location.RIGHT);

        setComponent(mainPanel);

        for (String songString : model.getSongs()) {
            Button song = new Button(songString);
            song.addListener( (onClick) -> {
                if (selectedSong != null) {
                    // Deselect the currently selected button
                    selectedSong = null;
                }

                // Select the new button
                selectedSong = song;
                model.setPosition(model.getSongs().indexOf(song.getLabel()));
            });
            songs.add(song);
            leftPanel.addComponent(song);
        }
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
    private void updateRightText() {
        // Get the right-side text area
        // Update the text
    }

    private void updateBar(){

    }

    @Override
    public void update(Observable o, Object arg) {
    }

}
