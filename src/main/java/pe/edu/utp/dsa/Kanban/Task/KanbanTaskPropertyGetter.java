package pe.edu.utp.dsa.Kanban.Task;

@FunctionalInterface
public interface KanbanTaskPropertyGetter<T> {
    T gets();
}
