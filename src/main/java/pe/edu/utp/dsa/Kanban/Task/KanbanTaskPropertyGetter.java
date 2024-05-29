package pe.edu.utp.dsa.Kanban.Task;

@FunctionalInterface
public interface KanbanTaskPropertyGetter<T> {
    /**
     * Retrieves the property of a Kanban task.
     *
     * @return the property value
     */
    T gets();
}
