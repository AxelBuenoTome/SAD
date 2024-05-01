package ReproductorV3;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class View extends BasicWindow {
    private final List<Label> songs = new ArrayList<>();
    private int seletedSong = -1;
    private final Panel mainPanel;
    private Model model;

    public View(Model model) {
        super();
        this.model = model;
        setHints(List.of(Window.Hint.CENTERED));

        mainPanel = new Panel(new BorderLayout());

        Panel leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        for (int i = 0; i < model.getSongs().size(); i++) {
            Label label = new Label(model.getSongName(i));
            songs.add(label);
            leftPanel.addComponent(label);
        }

        Panel rightPanel = new Panel();
        rightPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        TextBox textBox = new TextBox().addTo(rightPanel);
        textBox.setReadOnly(true);
        textBox.setText("Right side text area");

        mainPanel.addComponent(leftPanel, BorderLayout.Location.LEFT);
        mainPanel.addComponent(rightPanel, BorderLayout.Location.RIGHT);

        setComponent(mainPanel);

        // Event handling
        for (int i = 0; i < model.getSongs().size(); i++) {
            int index = i;
            songs.get(i).addListener((Component.Listener<Label>) (label, labelMouseEvent) -> {
                if (labelMouseEvent.getButton() == 1) { // Left mouse button
                    selectLabel(index);
                    updateRightText();
                }
            });
        }
    }

    //funcion q seleccióna una cancion y la cambia la apariencia gráfica
    //guardamos el indice de la canción selecciónada en
    private void selectLabel(int index) {
        if (seletedSong != -1) { //este -1 est solo para la primera vez q no hay ninguna cancion seleccionada
            songs.get(seletedSong).setBackgroundColor(TextColor.ANSI.DEFAULT);
        }
        seletedSong = index;
        labelList.get(index).setBackgroundColor(TextColor.ANSI.BLUE);
    }

    //funcion para camniar el texto a la derecha y poner la info
    private void updateRightText() {
        // Get the right-side text area
        TextBox textoDerecha = (TextBox) ((Panel) ((BorderLayout) mainPanel.getLayoutManager()).getComponent(BorderLayout.Location.RIGHT)).getComponent(0);
        // Update the text
        textoDerecha.setText();
    }

    public static void main(String[] args) {
        View view = new View("Scrollable Labels Example");
        MultiWindowTextGUI gui = new MultiWindowTextGUI(TerminalFacade.createTerminal());
        gui.addWindowAndWait(view);
    }
}
