package pe.edu.utp.dsa.Kanban.Task;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.Kanban.Kanban;

import static pe.edu.utp.dsa.Kanban.Utilities.Utilities.truncateString;

import java.time.LocalDate;
import java.util.Objects;

public class KanbanTask implements Comparable<KanbanTask>{

    private String name;
    private byte priority;
    private int numberTask;
    private String author;
    private User userAssignedToTheTask;
    private LocalDate registrationDate;
    private String description;
    private LocalDate finishDate;

    KanbanTaskPropertyGetter<?>[] propertyGetters = new KanbanTaskPropertyGetter[]{
            this::getAuthor,
            this::getDescription,
            this::getPriority,
            this::getName,
            this::getuserAssignedToTheTask,
            this::getRegistrationDate,
            this::getNumberTask,
            this::getFinishDate
    };

    /**
     * Constructs a new KanbanTask with the specified attributes.
     * Throws an IllegalArgumentException if the priority is out of range or the finish date is before the current date.
     *
     * @param name the name of the task
     * @param userAssignedToTheTask the user assigned to the task
     * @param author the author of the task
     * @param numberTask the task number
     * @param priority the priority of the task (0-5)
     * @param description the description of the task
     * @param finishDate the finish date of the task
     * @throws IllegalArgumentException if the priority is out of range or the finish date is before the current date
     */
    public KanbanTask(String name, User userAssignedToTheTask, String author, int numberTask,
                      byte priority, String description, LocalDate finishDate) throws IllegalArgumentException{

        if((priority < 0 || priority > 5) || (finishDate.isBefore(LocalDate.now())))
            throw new IllegalArgumentException("The data entered is invalid");

        this.name = name;
        this.userAssignedToTheTask = userAssignedToTheTask;
        this.numberTask = numberTask;
        this.finishDate = finishDate;
        this.registrationDate = LocalDate.now();
        this.priority = priority;
        this.description = description;
        this.author = author;
    }

    /**
     * Constructs a new KanbanTask without a user assigned to it.
     * Throws an IllegalArgumentException if the priority is out of range or the finish date is before the current date.
     *
     * @param name the name of the task
     * @param author the author of the task
     * @param numberTask the task number
     * @param priority the priority of the task (0-5)
     * @param description the description of the task
     * @param finishDate the finish date of the task
     * @throws IllegalArgumentException if the priority is out of range or the finish date is before the current date
     */
    public KanbanTask(String name, String author, int numberTask,
                      byte priority, String description, LocalDate finishDate) throws IllegalArgumentException{

        if((priority < 0 || priority > 5) || (finishDate.isBefore(LocalDate.now())))
            throw new IllegalArgumentException("The data entered is invalid");

        this.name = name;
        this.userAssignedToTheTask = null;
        this.numberTask = numberTask;
        this.finishDate = finishDate;
        this.registrationDate = LocalDate.now();
        this.priority = priority;
        this.description = description;
        this.author = author;
    }

    /**
     * Compares this KanbanTask with another KanbanTask for order based on priority and finish date.
     *
     * @param o the KanbanTask to be compared
     * @return a negative integer, zero, or a positive integer as this task is less than, equal to, or greater than the specified task
     */
    @Override
    public int compareTo(KanbanTask o) {
        int oP = o.getPriority();
        LocalDate oD = o.getFinishDate();
        if(this.priority > oP || this.priority < oP) return priority - oP;
        else
        if(this.finishDate.isBefore(oD))
            return -1;
        else if(this.finishDate.isAfter(oD))
            return 1;
        else
            return 0;
    }

    /**
     * Checks if this KanbanTask is equal to another object.
     * Two KanbanTasks are considered equal if all their properties are equal.
     *
     * @param kbt the object to compare to
     * @return true if this task is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object kbt){
        if (this == kbt) return true;

        if(kbt == null || kbt.getClass() != getClass())
            return false;
        for (int i = 0; i < propertyGetters.length; i++) {
            if(!propertyGetters[i].gets().equals(((KanbanTask) kbt).propertyGetters[i].gets()))
                return false;
        }
        return true;
    }

    // Getters and Setters

    public User getuserAssignedToTheTask() {
        if(userAssignedToTheTask == null)
            return new User("Not assigned", null);
        return userAssignedToTheTask;
    }

    public void setuserAssignedToTheTask(User userAssignedToTheTask) {
        this.userAssignedToTheTask = userAssignedToTheTask;
    }

    public int getNumberTask(){
        return numberTask;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getFinishDate(){
        return finishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }


    /**
     * Creates a Pane representation of the KanbanTask
     * including various attributes and icons.
     *
     * @return a Pane containing the task's details
     */
    public Pane getPaneTask(){
        double heightPaneTask = 150.0;
        double widthPaneTask = 178.0;

        String priorityIconDirectory;
        String userAssignment = (this.userAssignedToTheTask == null)
                ? "Not assigned" : this.userAssignedToTheTask.getName();

        if(priority <= 2) priorityIconDirectory = "imgs/lowerpriority.png";
        else if (priority <= 4) priorityIconDirectory = "imgs/mediumpriority.png";
        else priorityIconDirectory = "imgs/highpriority.png";

        Separator separator = new Separator(Orientation.HORIZONTAL);
        Label userAssignedToTheTask = new Label(truncateString(userAssignment, 22));
        Label finishDate = new Label("Finish: " + this.finishDate.toString());
        Label numberTask = new Label("N° Task: #" + this.numberTask);
        Label priority = new Label("P: " + this.priority);
        TextArea description = new TextArea("Description: "+this.description);

        Pane paneTask = new Pane();

        ImageView iconDescription = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/description.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        ImageView iconCalendar = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/calendar.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        ImageView iconUser = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/user.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        ImageView iconNTask = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/ntask.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        ImageView iconPriority = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource(priorityIconDirectory)).getFile()
                .replaceAll("%20", " ").substring(1)));
        // substring(1) was placed because it returned the directory with a /
        // at the beginning and that caused it to not find the image

        //style
        separator.setStyle("-fx-border-width: 5px;");
        description.setWrapText(true);
        description.setDisable(false);
        description.setEditable(false);

        // size
        separator.setPrefWidth(widthPaneTask-10);

        iconDescription.setFitWidth(25);
        iconDescription.setFitHeight(25);
        description.setPrefSize(140, 60);

        iconUser.setFitWidth(20);
        iconUser.setFitHeight(20);
        iconCalendar.setFitWidth(22);
        iconCalendar.setFitHeight(22);
        iconNTask.setFitHeight(20);
        iconNTask.setFitWidth(20);
        iconPriority.setFitHeight(20);
        iconPriority.setFitWidth(20);

        //config layout (X, Y)
        separator.setLayoutY(heightPaneTask+3);

        iconDescription.setLayoutY(58);
        description.setLayoutX(25);
        description.setLayoutY(61);


        iconUser.setLayoutY(heightPaneTask-20);
        userAssignedToTheTask.setLayoutX(24);
        userAssignedToTheTask.setLayoutY(heightPaneTask-18);

        iconNTask.setLayoutY(5);
        numberTask.setLayoutY(8);
        numberTask.setLayoutX(23);

        iconPriority.setLayoutX(widthPaneTask-55);
        iconPriority.setLayoutY(5);
        priority.setLayoutX(widthPaneTask-33);
        priority.setLayoutY(7);

        iconCalendar.setLayoutY(30);
        finishDate.setLayoutX(24);
        finishDate.setLayoutY(33);

        //Add
        paneTask.getChildren().addAll(
                iconUser,
                userAssignedToTheTask,
                iconCalendar,
                finishDate,
                iconNTask,
                numberTask,
                iconPriority,
                priority,
                iconDescription,
                description,
                separator
        );

        return paneTask;
    }

    public void setFinishDate(LocalDate d) {
        this.finishDate = d;
    }
}
