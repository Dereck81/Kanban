package pe.edu.utp.dsa.kanban.ListView;

import pe.edu.utp.dsa.kanban.Utilities.Role;
import pe.edu.utp.dsa.kanban.Task.KanbanTask;
import pe.edu.utp.dsa.kanban.Utilities.User;

public class ListCell<T> extends javafx.scene.control.ListCell<T>{
    @Override
    protected void updateItem(T element, boolean empty) {
        super.updateItem(element, empty);

        if (empty || element == null)
            setGraphic(null);
        else
            if (element instanceof User)
                setGraphic(((User) element).getPane());
            else if (element instanceof Role)
                setGraphic(((Role) element).getPane());
            else if (element instanceof KanbanTask)
                setGraphic(((KanbanTask) element).getPaneTask());

        //setText(String.valueOf(element));
    }
}
