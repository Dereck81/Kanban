package pe.edu.utp.dsa.kanban.Utilities;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.kanban.Kanban;

import java.util.Objects;

public class Role {

    private String rol;

    public Role(String rol){
        this.rol = rol;
    }

    public Pane getPane(){
        double height = 15;
        Pane paneRol = new Pane();
        Label rol = new Label(this.rol);
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Kanban.class.getResource("imgs/rol.png")).getFile()
                .replaceAll("%20", " ").substring(1)));
        //Size
        paneRol.setPrefHeight(height);
        imageView.setFitHeight(height);
        imageView.setFitWidth(15);
        imageView.setY(2);
        //SetLayout
        rol.setLayoutY(2);
        rol.setLayoutX(18);
        // add
        paneRol.getChildren().addAll(imageView, rol);

        return paneRol;

    }


}
