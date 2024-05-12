package pe.edu.utp.dsa.kanban.Task;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.kanban.Kanban;

import java.time.LocalDate;
import java.util.Objects;

public class KanbanTask {

    private String name;
    private int priority;
    private int numberTask;
    private String role;
    private String author;
    private String userAssignedToTheTask;
    private LocalDate registrationDate;
    private LocalDate deadLine;

    public KanbanTask(String userAssignedToTheTask, String author, String role, int numberTask,
                      int priority, LocalDate deadLine) throws IllegalArgumentException{

        if((priority < 0 || priority > 5) || (deadLine.isBefore(LocalDate.now())))
            throw new IllegalArgumentException("The data entered is invalid");

        this.userAssignedToTheTask = userAssignedToTheTask;
        this.numberTask = numberTask;
        this.deadLine = deadLine;
        this.registrationDate = LocalDate.now();
        this.priority = priority;
        this.role = role;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

    public Pane getPaneTask(){
        double heightPaneTask = 100.0;
        double widthPaneTask = 198.0;
        Label author = new Label(this.author);

        Pane paneTask = new Pane();
        ImageView icon = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/user.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        // substring(1) was placed because it returned the directory with a /
        // at the beginning and that caused it to not find the image

        // size
        paneTask.setPrefHeight(100);
        icon.setFitWidth(20);
        icon.setFitHeight(20);

        //config layout (X, Y)
        author.setLayoutX(30);
        author.setLayoutY(heightPaneTask-20);
        icon.setLayoutY(heightPaneTask-20);

        //Add
        paneTask.getChildren().addAll(icon, author);

        return paneTask;
    }

}
