package ReproductorV2;

import java.util.Observer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class View implements Observer {

    private Model model;

    public View(Model model){
        this.model = model;
        model.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshList((int) arg);
    }

    public void refreshList(int position){
        ArrayList<String> songs = model.getSongs();
        for (int index = 0; index < songs.size(); index++) {    
            if(index==position){
                System.out.print("-->");
            }
            else{
                System.out.print("   ");
            }
            System.out.println(songs.get(index));
        }
    }
}
