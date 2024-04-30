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
    private static final int NUM_OF_LABELS = 20;
    private final List<Label> labelList = new ArrayList<>();
    private int selectedLabelIndex = -1;
    private final Panel mainPanel;

    public View(String title) {
        super(title);
        setHints(List.of(Window.Hint.CENTERED));

        mainPanel = new Panel(new BorderLayout());

        Panel leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        for (int i = 0; i < NUM_OF_LABELS; i++) {
            Label label = new Label("Label " + (i + 1));
            labelList.add(label);
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
        for (int i = 0; i < NUM_OF_LABELS; i++) {
            int index = i;
            labelList.get(i).addListener((Component.Listener<Label>) (label, labelMouseEvent) -> {
                if (labelMouseEvent.getButton() == 1) { // Left mouse button
                    selectLabel(index);
                    updateRightText("Label " + (index + 1) + " clicked");
                }
            });
        }
    }

    private void selectLabel(int index) {
        if (selectedLabelIndex != -1) {
            labelList.get(selectedLabelIndex).setBackgroundColor(TextColor.ANSI.DEFAULT);
        }
        selectedLabelIndex = index;
        labelList.get(index).setBackgroundColor(TextColor.ANSI.BLUE);
    }

    private void updateRightText(String newText) {
        // Get the right-side text area
        TextBox textBox = (TextBox) ((Panel) ((BorderLayout) mainPanel.getLayoutManager()).getComponent(BorderLayout.Location.RIGHT)).getComponent(0);
        // Update the text
        textBox.setText(newText);
    }

    public static void main(String[] args) {
        View view = new View("Scrollable Labels Example");
        MultiWindowTextGUI gui = new MultiWindowTextGUI(TerminalFacade.createTerminal());
        gui.addWindowAndWait(view);
    }
}
