package pe.edu.utp.dsa.Kanban.Task;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pe.edu.utp.dsa.Kanban.Kanban;
import pe.edu.utp.dsa.StringManipulation.StringBuilderWrapper;
import pe.edu.utp.dsa.StringManipulation.StringCreator;
import pe.edu.utp.dsa.XML.XMLIteratorSerializer;
import pe.edu.utp.dsa.XML.XMLSerializable;

import static pe.edu.utp.dsa.Kanban.Utilities.Utilities.capitalize;
import java.util.Objects;

public class User implements Comparable<User>, XMLSerializable {

    private String name;
    private Role rol;

    private int hierachy;

    /**
     * Constructs a new User with the specified name and role.
     * The name is capitalized upon creation.
     *
     * @param name the name of the user
     * @param rol  the role of the user
     */
    public User(String name, Role rol){
        this.name = capitalize(name);
        this.rol = rol;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * The name is capitalized before being set.
     *
     * @param name the new name of the user
     */
    public void setName(String name) {
        this.name = capitalize(name);
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    public Role getRol() {
        return rol;
    }

    /**
     * Sets the role of the user.
     *
     * @param rol the new role of the user
     */
    public void setRol(Role rol) {
        this.rol = rol;
    }

    /**
     * Compares this user to another user for ordering.
     * Users are compared first by their names in descending order.
     * If the names are the same, they are compared by their role names in descending order.
     *
     * @param o the other user to compare to
     * @return a negative integer, zero, or a positive integer as this user is
     * less than, equal to, or greater than the specified user
     */
    @Override
    public int compareTo(User o) {
        if(this.name.compareTo(o.getName())*-1 != 0)
            return this.name.compareTo(o.getName())*-1;
        else
            return this.rol.getRolName().compareTo(o.getRol().getRolName())*-1;
    }

    /**
     * Checks if this user is equal to another object.
     * Two users are considered equal if their names and roles are both equal.
     *
     * @param obj the object to compare to
     * @return true if this user is equal to the specified object, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || obj.getClass() != getClass())
            return false;
        if(name.equals(((User) obj).getName()))
            return rol.equals(((User) obj).getRol());
        return false;
    }

    /**
     * Creates a Pane representation of the user, including their name and role,
     * with associated images.
     *
     * @return a Pane containing the user's details
     */
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

    @Override
    public String serialize() {
        StringBuilderWrapper sbw = new StringBuilderWrapper();

        String indentation = StringCreator.ntimes('\t', hierachy);

        rol.setHierachy(hierachy + 1);

        sbw.setPreffix(indentation)
                .line("<User>")
                .line("\t<UserName>")
                .line("\t\t" + name)
                .line("\t</UserName>")
                .line(rol.serialize())
                .line("</User>");

        return sbw.toString();
    }

    @Override
    public void setHierachy(int h) {
        hierachy = h;
    }
}
