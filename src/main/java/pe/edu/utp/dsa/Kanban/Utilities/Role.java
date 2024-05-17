package pe.edu.utp.dsa.Kanban.Utilities;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.Kanban.Kanban;
import static pe.edu.utp.dsa.Kanban.Utilities.Utilities.capitalize;

import java.util.Objects;

public class Role implements Comparable<Role>{
    private String rol;

    public Role(String rol){
        this.rol = capitalize(rol);
    }

    public String getRolName(){
        return rol;
    }

    @Override
    public int compareTo(Role o) {
        return this.rol.compareTo(o.getRolName())*-1;
    }

    public boolean equals(Role obj){
        return rol.equals(obj.getRolName());
    }

    public Pane getPane(){
        double height = 15;

        Label rol = new Label(this.rol);

        Pane paneRol = new Pane();

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
