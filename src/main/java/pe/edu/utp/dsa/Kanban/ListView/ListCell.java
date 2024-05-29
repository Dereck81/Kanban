package pe.edu.utp.dsa.Kanban.ListView;

import pe.edu.utp.dsa.Kanban.Task.Role;
import pe.edu.utp.dsa.Kanban.Task.KanbanTask;
import pe.edu.utp.dsa.Kanban.Task.User;

public class ListCell<T> extends javafx.scene.control.ListCell<T>{

    /**
     * This method is called to update the item in the ListView.
     * It customizes the appearance of the cell based on the type of the item.
     *
     * @param element the item to update
     * @param empty whether the cell is empty
     */
    @Override
    protected void updateItem(T element, boolean empty) {
        super.updateItem(element, empty);
        // Clear the graphic if the cell is empty or the element is null
        if (empty || element == null)
            setGraphic(null);
        else
            // Set a custom graphic based on the type of the element
            if (element instanceof User)
                setGraphic(((User) element).getPane());
            else if (element instanceof Role)
                setGraphic(((Role) element).getPane());
            else if (element instanceof KanbanTask)
                setGraphic(((KanbanTask) element).getPaneTask());

    }
}
