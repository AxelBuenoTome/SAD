package ReproductorV2;

import java.util.Observer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class View implements Observer {

    private Model model;
    private boolean first;

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
        }
    }

    public void refreshList(int position) {
        ArrayList<String> songs = model.getSongs();
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
        System.out.print("Progreso: " + progress + "%");
        System.out.print('\r');
    }
}
