package pe.edu.utp.dsa.kanban.Utilities;

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
}
