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
    private int priority;
    private int numberTask;
    private String role;
    private String author;
    private String userAssignedToTheTask;
    private LocalDate registrationDate;
    private String description;
    private LocalDate finishDate;

    KanbanTaskPropertyGetter<?>[] propertyGetters = new KanbanTaskPropertyGetter[]{
            this::getAuthor,
            this::getDescription,
            this::getPriority,
            this::getName,
            this::getuserAssignedToTheTask,
            this::getRole,
            this::getRegistrationDate,
            this::getNumberTask,
            this::getFinishDate
    };

    public KanbanTask(String name, String userAssignedToTheTask, String author, String role, int numberTask,
                      int priority, String description, LocalDate finishDate) throws IllegalArgumentException{

        if((priority < 0 || priority > 5) || (finishDate.isBefore(LocalDate.now())))
            throw new IllegalArgumentException("The data entered is invalid");

        this.name = name;
        this.userAssignedToTheTask = userAssignedToTheTask;
        this.numberTask = numberTask;
        this.finishDate = finishDate;
        this.registrationDate = LocalDate.now();
        this.priority = priority;
        this.role = role;
        this.description = description;
        this.author = author;
    }

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

    public boolean equals(KanbanTask kbt){
        for (int i = 0; i < propertyGetters.length; i++) {
            if(!propertyGetters[i].gets().equals(kbt.propertyGetters[i].gets()))
                return false;
        }
        return true;
    }

    public String getuserAssignedToTheTask() {
        return userAssignedToTheTask;
    }

    public void setuserAssignedToTheTask(String userAssignedToTheTask) {
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Pane getPaneTask(){
        double heightPaneTask = 150.0;
        double widthPaneTask = 178.0;

        String priorityIconDirectory;

        if(priority <= 2) priorityIconDirectory = "imgs/lowerpriority.png";
        else if (priority <= 4) priorityIconDirectory = "imgs/mediumpriority.png";
        else priorityIconDirectory = "imgs/highpriority.png";

        Separator separator = new Separator(Orientation.HORIZONTAL);
        Label userAssignedToTheTask = new Label(truncateString(this.userAssignedToTheTask, 23));
        Label finishDate = new Label("Finish: " + this.finishDate.toString());
        Label numberTask = new Label("N° Task: #" + this.numberTask);
        Label priority = new Label("P: " + this.priority);
        TextArea description = new TextArea(truncateString("Description: "+this.description, 100));

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
        description.setDisable(true);

        // size
        separator.setPrefWidth(widthPaneTask-10);

        iconDescription.setFitWidth(25);
        iconDescription.setFitHeight(25);
        description.setPrefSize(150, 60);

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

}
