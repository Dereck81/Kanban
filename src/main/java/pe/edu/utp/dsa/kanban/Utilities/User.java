package pe.edu.utp.dsa.kanban.Utilities;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.kanban.Kanban;

import java.util.Objects;

public class User{

    private String name;
    private String rol;


    public User(String name, String rol){
        this.name = name;
        this.rol = rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Pane getPane(){
        double height = 35;
        Pane paneUser = new Pane();
        Label name = new Label(this.name);
        Label rol = new Label("Rol: "+this.rol);
        ImageView imageViewRol = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/rol.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        ImageView imageViewUser = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/user.png")).getFile()
                .replaceAll("%20", " ").substring(1)));

        // size
        paneUser.setPrefHeight(height);
        imageViewUser.setFitWidth(15);
        imageViewUser.setFitHeight(15);
        imageViewRol.setFitWidth(15);
        imageViewRol.setFitHeight(15);

        //set Layout
        imageViewUser.setY(height-35);
        imageViewRol.setY(height-15);
        rol.setLayoutX(18);
        rol.setLayoutY(height-15);
        name.setLayoutX(18);
        name.setLayoutY(height-35);

        //Add
        paneUser.getChildren().addAll(name, imageViewUser, rol, imageViewRol);

        return paneUser;
    }


}
