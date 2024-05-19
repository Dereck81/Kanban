package pe.edu.utp.dsa.Kanban.Task;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.Kanban.Kanban;
import static pe.edu.utp.dsa.Kanban.Utilities.Utilities.capitalize;

import java.util.Objects;

public class Role implements Comparable<Role> {
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

    public void setRolName(String rol){
        this.rol = rol;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        /*
            Interestingly, every time an item in the listView was right-clicked,
            an exception was thrown regarding each class's overridden equality method,
            stating that the item passed in or being compared to is null, which was illogical .
            because no comparison was made (based on the code that was written up to that point),
            apparently the error may be in some javaFX method or function like
            .getSelectionModel().getSelectedItems().isEmpty(),
            which is possibly using equals (this last one is a guess).
         */
        if(obj == null || obj.getClass() != getClass())
            return false;
        return rol.equals(((Role) obj).getRolName());
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
