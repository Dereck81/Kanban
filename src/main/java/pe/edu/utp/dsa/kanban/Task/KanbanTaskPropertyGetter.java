package pe.edu.utp.dsa.kanban.Task;

@FunctionalInterface
public interface KanbanTaskPropertyGetter<T> {
    T gets();
}
