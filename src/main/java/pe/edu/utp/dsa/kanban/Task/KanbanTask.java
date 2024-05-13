package pe.edu.utp.dsa.kanban.Task;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.kanban.Kanban;

import java.time.LocalDate;
import java.util.Objects;

public class KanbanTask implements Comparable<KanbanTask>{

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

    private String name;
    private int priority;
    private int numberTask;
    private String role;
    private String author;
    private String userAssignedToTheTask;
    private LocalDate registrationDate;
    private LocalDate finishDate;

    public KanbanTask(String userAssignedToTheTask, String author, String role, int numberTask,
                      int priority, LocalDate finishDate) throws IllegalArgumentException{

        if((priority < 0 || priority > 5) || (finishDate.isBefore(LocalDate.now())))
            throw new IllegalArgumentException("The data entered is invalid");

        this.userAssignedToTheTask = userAssignedToTheTask;
        this.numberTask = numberTask;
        this.finishDate = finishDate;
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

    public LocalDate getFinishDate(){
        return finishDate;
    }


    public Pane getPaneTask(){
        double heightPaneTask = 100.0;
        double widthPaneTask = 198.0;
        Label author = new Label(this.author);
        Label finishDate = new Label(this.finishDate.toString());

        Pane paneTask = new Pane();
        paneTask.setStyle("-fx-border");
        ImageView iconCalendar = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/calendar.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        ImageView iconUser = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/user.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        // substring(1) was placed because it returned the directory with a /
        // at the beginning and that caused it to not find the image

        // size
        paneTask.setPrefHeight(100);
        iconUser.setFitWidth(20);
        iconUser.setFitHeight(20);
        iconCalendar.setFitWidth(22);
        iconCalendar.setFitHeight(22);

        //config layout (X, Y)
        author.setLayoutX(30);
        author.setLayoutY(heightPaneTask-22);
        finishDate.setLayoutX(30);
        finishDate.setLayoutY(8);
        iconUser.setLayoutY(heightPaneTask-20);
        iconCalendar.setLayoutY(5);

        //Add
        paneTask.getChildren().addAll(iconUser, author, iconCalendar, finishDate);

        return paneTask;
    }


}
