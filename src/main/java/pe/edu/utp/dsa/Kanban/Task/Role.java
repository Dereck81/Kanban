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

    /**
     * Constructs a new Role with the specified role name.
     * The role name is capitalized upon creation.
     *
     * @param rol the name of the role
     */
    public Role(String rol){
        this.rol = capitalize(rol);
    }

    /**
     * Gets the name of the role.
     *
     * @return the name of the role
     */
    public String getRolName(){
        return rol;
    }

    /**
     * Compares this role to another role for ordering.
     * Roles are compared by their names in descending order.
     *
     * @param o the other role to compare to
     * @return a negative integer, zero, or a positive integer as this role is
     * less than, equal to, or greater than the specified role
     */
    @Override
    public int compareTo(Role o) {
        return this.rol.compareTo(o.getRolName())*-1;
    }

    /**
     * Sets the name of the role.
     * The name is capitalized before being set.
     *
     * @param rol the new name of the role
     */
    public void setRolName(String rol){
        this.rol = rol;
    }

    /**
     * Checks if this role is equal to another object.
     * Two roles are considered equal if their names are equal.
     *
     * @param obj the object to compare to
     * @return true if this role is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if(obj == null || obj.getClass() != getClass())
            return false;
        return rol.equals(((Role) obj).getRolName());
    }

    /**
     * Creates a Pane representation of the role, including its name,
     * with an associated image.
     *
     * @return a Pane containing the role's details
     */
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
