package pe.edu.utp.dsa.Kanban.Task;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.Kanban.Kanban;
import static pe.edu.utp.dsa.Kanban.Utilities.Utilities.capitalize;
import java.util.Objects;

public class User implements Comparable<User>{

    private String name;
    private Role rol;

    public User(String name, Role rol){
        this.name = capitalize(name);
        this.rol = rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = capitalize(name);
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    @Override
    public int compareTo(User o) {
        if(this.name.compareTo(o.getName())*-1 != 0)
            return this.name.compareTo(o.getName())*-1;
        else
            return this.rol.getRolName().compareTo(o.getRol().getRolName())*-1;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || obj.getClass() != getClass())
            return false;
        if(name.equals(((User) obj).getName()))
            return rol.equals(((User) obj).getRol());
        return false;
    }

    public Pane getPane(){
        double height = 35;

        Label name = new Label(this.name);
        Label rol = new Label("Rol: "+this.rol.getRolName());

        Pane paneUser = new Pane();

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
