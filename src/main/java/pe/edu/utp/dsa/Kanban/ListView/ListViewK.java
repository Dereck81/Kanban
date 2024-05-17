package pe.edu.utp.dsa.Kanban.ListView;

import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class ListViewK {
    public static void exchangeBetweenListView(ListView<Pane> from, int elementIndex, ListView<Pane> to){
        to.getItems().add(from.getItems().get(elementIndex));
        from.getItems().remove(elementIndex);
    }

}
